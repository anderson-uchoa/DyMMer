package br.ufc.lps.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.tree.TreePath;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import br.ufc.lps.contextaware.Constraint;
import br.ufc.lps.contextaware.Context;
import br.ufc.lps.contextaware.Literal;
import br.ufc.lps.contextaware.Resolution;
import br.ufc.lps.gui.export.WriteXMLmodel;
import br.ufc.lps.gui.list.ConstraintsListModel;
import br.ufc.lps.gui.tree.FeatureModelTree;
import br.ufc.lps.gui.tree.FeaturesTreeCellRenderer;
import br.ufc.lps.model.ModelFactory;
import br.ufc.lps.model.normal.IModel;
import br.ufc.lps.splar.core.constraints.BooleanVariable;
import br.ufc.lps.splar.core.constraints.CNFClause;
import br.ufc.lps.splar.core.constraints.PropositionalFormula;
import br.ufc.lps.splar.core.constraints.parsing.CNFClauseParseException;
import br.ufc.lps.splar.core.constraints.parsing.CNFClauseParser;
import br.ufc.lps.splar.core.fm.FeatureGroup;
import br.ufc.lps.splar.core.fm.FeatureModel;
import br.ufc.lps.splar.core.fm.FeatureModelException;
import br.ufc.lps.splar.core.fm.FeatureTreeNode;
import br.ufc.lps.splar.core.heuristics.FTPreOrderSortedECTraversalHeuristic;
import br.ufc.lps.splar.core.heuristics.VariableOrderingHeuristic;
import br.ufc.lps.splar.core.heuristics.VariableOrderingHeuristicsManager;
import br.ufc.lps.splar.plugins.reasoners.bdd.javabdd.FMReasoningWithBDD;

public class EditorPanel extends JPanel {

	protected static final String BASE_NAME_CONSTRAINT = "contextConstraint";
	private IModel model;
	private JTextField textFieldNewContext;
	private JTree tree;
	
	private FeatureTreeNode selectedNode;
	private JTextArea txtMessageText;
	private JButton btnNewContext;
	private JLabel lblNewContext;
	private Context defaultContext;
	private List<Resolution> resolutions;
	private JTextField txtAddTheFeatures;
	
	private Map<String, String> constraints;
	private List<Literal> constraintLiterals;
	
	
	private JList list;
	private ConstraintsListModel constraintsListModel;
	private int selectedConstraintIndex;
	private List<Constraint> constraintsList;
	private int constraintNumber;
	private Integer modelID;
	private String pathModelFile;
	
	/**
	 * Create the panel.
	 * @param model 
	 */
	
	public EditorPanel(IModel model, int modelID, String pathModelFile) {
		setLayout(new BorderLayout(0, 0));
		
		constraints = new HashMap<String, String>();
		constraintLiterals = new ArrayList<Literal>();
		constraintsList = new ArrayList<Constraint>();
		
		constraintNumber = 0;
		
		this.modelID = modelID;
		this.pathModelFile = pathModelFile;
		
		this.model = model;
		resolutions = new ArrayList<Resolution>();
		
		
		tree = new JTree();
		tree.setModel(new FeatureModelTree(model.getFeatureModel().getRoot()));
		tree.setEditable(true);
		tree.setComponentPopupMenu(getComponentPopupMenu());
		tree.addMouseListener(getMouseListener());
		
		defaultContext = new Context("default", resolutions, null);
		tree.setCellRenderer(new FeaturesTreeCellRenderer(defaultContext));
		
		JScrollPane scrollPane = new JScrollPane(tree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane, BorderLayout.CENTER);
		
		JPanel panelInfos = new JPanel();
		add(panelInfos, BorderLayout.EAST);
		panelInfos.setLayout(new BorderLayout(0, 0));
		
		JPanel panelNewContext = new JPanel();
		panelInfos.add(panelNewContext, BorderLayout.NORTH);
		panelNewContext.setLayout(new GridLayout(0, 2, 0, 0));
		
		lblNewContext = new JLabel("New context");
		panelNewContext.add(lblNewContext);
		
		textFieldNewContext = new JTextField();
		textFieldNewContext.setToolTipText("New context's name");
		panelNewContext.add(textFieldNewContext);
		textFieldNewContext.setColumns(10);
		
		JLabel lblBlankSpace = new JLabel("");
		panelNewContext.add(lblBlankSpace);
		
		btnNewContext = new JButton("Add");
		btnNewContext.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(textFieldNewContext.getText().equals("")){
					txtMessageText.setText("Please, type the context name.");
					lblNewContext.setForeground(Color.RED);
					textFieldNewContext.requestFocus();
				}else{
					
					DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
					
							
					try {
					
						DocumentBuilder db = dbf.newDocumentBuilder();
						Document doc = db.parse(EditorPanel.this.pathModelFile);
					
						Element rootEle = doc.getDocumentElement();
										
				
						rootEle.appendChild(WriteXMLmodel.getContext(doc, textFieldNewContext.getText(), EditorPanel.this.resolutions, new ArrayList<String>(constraints.values())));
						
						Transformer transformer = TransformerFactory.newInstance().newTransformer();
			            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
			            DOMSource source = new DOMSource(doc);
			            StreamResult console = new StreamResult(new FileOutputStream(EditorPanel.this.pathModelFile));
			            transformer.transform(source, console);
			            
			            textFieldNewContext.setText("");
						constraintLiterals.clear();
						constraints.clear();
						constraintsList.clear();
						constraintsListModel.update();
						txtAddTheFeatures.setText("");
						resolutions.clear();
						txtMessageText.setText("None for while...");
						constraintNumber = 0;				
						
						
						tree.repaint();
						tree.updateUI();
						JOptionPane.showMessageDialog(EditorPanel.this, "Your context has been saved. Now, open the file to see it.");
						
					} catch (SAXException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ParserConfigurationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (TransformerConfigurationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (TransformerFactoryConfigurationError e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (TransformerException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}
		});
		panelNewContext.add(btnNewContext);
		
		
		
		
		JPanel panelMessage = new JPanel();
		panelInfos.add(panelMessage, BorderLayout.SOUTH);
		panelMessage.setLayout(new GridLayout(0, 1, 0, 0));
		
		JLabel lblMessageTitle = new JLabel("Message:");
		lblMessageTitle.setHorizontalAlignment(SwingConstants.LEFT);
		panelMessage.add(lblMessageTitle);
		
		txtMessageText = new JTextArea();
		txtMessageText.setText("None for while...");
		txtMessageText.setLineWrap(true);
		txtMessageText.setEditable(false);
		txtMessageText.setBackground(SystemColor.menu);
		panelMessage.add(txtMessageText);
		
		JPanel panel = new JPanel();
		panelInfos.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panelConstraint = new JPanel();
		panel.add(panelConstraint, BorderLayout.NORTH);
		panelConstraint.setLayout(new GridLayout(0, 1, 0, 0));
		
		JSeparator separator = new JSeparator();
		panelConstraint.add(separator);
		
		JLabel lblConstraint = new JLabel("Constraint:");
		lblConstraint.setHorizontalAlignment(SwingConstants.CENTER);
		panelConstraint.add(lblConstraint);
		
		
		txtAddTheFeatures = new JTextField();
		txtAddTheFeatures.setText("Add the features...");
		txtAddTheFeatures.setEditable(false);
		txtAddTheFeatures.setColumns(10);
		
		JScrollPane scrollPane_2 = new JScrollPane(txtAddTheFeatures, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panelConstraint.add(scrollPane_2);
		
				
		JPanel panel_1 = new JPanel();
		panelConstraint.add(panel_1);
		
		JButton btnRemoveConstraint = new JButton("Remove Constraint");
		panel_1.add(btnRemoveConstraint);
		
		btnRemoveConstraint.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				txtAddTheFeatures.setText("");
				constraintLiterals.clear();
			}
		});
		
		JButton btnAddConstraint = new JButton("Add Constraint");
		panel_1.add(btnAddConstraint);
		
		btnAddConstraint.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if(constraintLiterals.size() < 2){
					txtMessageText.setText("Constraints must have at least two features.");
					return;
				}
				
				Constraint newConstraint = new Constraint(constraintNumber, txtAddTheFeatures.getText());
				constraintNumber++;
				constraintsList.add(newConstraint);
				constraintsListModel.update();		
				
				StringBuilder clauseConstraint = new StringBuilder();
				
				Literal literal = constraintLiterals.get(0);
				String idFeature = literal.getFeature().getID();
				clauseConstraint.append(literal.isState() ? idFeature : "~" + idFeature);
				
				for(int i = 1; i < constraintLiterals.size(); i++){
					String variable = null;
					Literal tempLiteral = constraintLiterals.get(i);
					
					if(tempLiteral.isState())
						variable = tempLiteral.getFeature().getID();
					else
						variable = "~" + tempLiteral.getFeature().getID();
				
					clauseConstraint.append(" or " + variable);
				}
				
				String constraintName = BASE_NAME_CONSTRAINT + String.valueOf(newConstraint.getId());
				constraints.put(constraintName, clauseConstraint.toString());
				
					
				constraintLiterals.clear();
				txtAddTheFeatures.setText("");
				System.out.println(clauseConstraint.toString());
				
				
				
				FeatureModel otherModel = createFeatureModel();
				
				System.out.println("STRINGS CONSTRAINTS: ");
				CNFClauseParser parser = new CNFClauseParser();
				for(Entry<String, String> constsString : constraints.entrySet()){
					
					System.out.println(constsString.getKey() + ": " + constsString.getValue());
										
					CNFClause clause = null;
					try {
						clause = parser.parse(constsString.getValue());
										
//						otherModel.addConstraint(new PropositionalFormula(constsString.getKey(), clause.toString2()));
						otherModel.addConstraint(new PropositionalFormula(constsString.getKey(), clause.toString()));
						
					} catch (CNFClauseParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
				}
		
				
				System.out.println("Size model's constraints: "+ otherModel.getConstraints().size());
				
				boolean consistency = isModelConsistent(otherModel);
				
				
			
				if(consistency)
					System.out.println("CONSISTENT");
				else
					System.out.println("NOT CONSISTENT");
				
				
			}
		});
		
		constraintsListModel = new ConstraintsListModel(constraintsList);
		list = new JList<String>(constraintsListModel);
		list.setComponentPopupMenu(getComponentPopupMenuConstraintsList());
		list.addMouseListener(getMouseListener());
		
		JScrollPane scrollPane_1 = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panel.add(scrollPane_1, BorderLayout.CENTER);
		
		
			
	}

	public String getModelName() {
		
		return model.getModelName();
	}

	
	
	private MouseListener getMouseListener() {
	    return new MouseAdapter() {

	    	@Override
	        public void mousePressed(MouseEvent event) {
	            			
	    		super.mousePressed(event);
	    					
	        }
	    		    	
	    	
	    	@Override
	    	public void mouseReleased(MouseEvent event) {
	    		// TODO Auto-generated method stub
	    		super.mouseReleased(event);
	    	
	    		System.out.println("Mouse event");
				System.out.println(event.getButton());
				
				if(event.getButton() == MouseEvent.BUTTON3){
	               
					System.out.println("BUTTON 3");
					
					if(event.getSource() == tree){
					
						System.out.println("Arvore");
						
						TreePath pathForLocation = tree.getPathForLocation(event.getPoint().x, event.getPoint().y);
		                if(pathForLocation != null){
		                	System.out.println("NOT NULL");
		                    selectedNode = (FeatureTreeNode) pathForLocation.getLastPathComponent();
		                } else{
		                	System.out.println("NULL");
		                    selectedNode = null;
		                }
		                
					}else if(event.getSource() == list){
						
						
						selectedConstraintIndex = list.locationToIndex(event.getPoint());
						list.setSelectedIndex(selectedConstraintIndex);
						System.out.println(String.valueOf(selectedConstraintIndex));
						
					}
	            }
	    	}
	    };
	}
	
	private JPopupMenu getComponentPopupMenuConstraintsList() {
		
		JPopupMenu menu = new JPopupMenu();
		JMenuItem removeConstraint = new JMenuItem("Remove Constraint");
		
		removeConstraint.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				int idConstraint = constraintsList.get(selectedConstraintIndex).getId();
				constraintsList.remove(selectedConstraintIndex);
				constraintsListModel.update();
				
				constraints.remove(BASE_NAME_CONSTRAINT + String.valueOf(idConstraint));
				System.out.println("Removed: " + String.valueOf(idConstraint));
			}
		});
		
		menu.add(removeConstraint);
		
		return menu;
	}
	
	@Override
	public JPopupMenu getComponentPopupMenu() {
		
		JPopupMenu menu = new JPopupMenu();
		JMenuItem setActive = new JMenuItem("Set as active node");
		JMenuItem setDeactive = new JMenuItem("Set as deactive node");
		JMenuItem takeOffContext = new JMenuItem("Take it off from context");
		
		
		
		JMenuItem addConstraintPositive = new JMenuItem("Add to Constraint as Positive");
		JMenuItem addConstraintNegative = new JMenuItem("Add to Constraint as Negative");
		
		setActive.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("SetActivee:" + selectedNode.getName());
				
				if(isFeatureGroup(selectedNode))
					return;
				
				changeStatusFeature(true, "Selected Feature is already activated");
				
				
			}

			
		});
		menu.add(setActive);
		
		setDeactive.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(isFeatureGroup(selectedNode))
					return;
				
				changeStatusFeature(false, "Selected Feature is already deactivated");
			
				
			}
		});
		menu.add(setDeactive);
		
		takeOffContext.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				if(isFeatureGroup(selectedNode))
					return;
				
				Resolution resolution = new Resolution(selectedNode.getID(), null, false);
				resolutions.remove(resolution);
				tree.repaint();
				tree.updateUI();
				
				removeFromContextConstraint(selectedNode);
				constraintsListModel.update();
			}
		});
		menu.add(takeOffContext);
		
		JSeparator separator = new JSeparator();
		menu.add(separator);
		
		
		addConstraintPositive.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if(isFeatureGroup(selectedNode))
					return;
					
				Literal literal = new Literal(selectedNode, true);
				
				addToConstraint(literal);
			}
		});
		menu.add(addConstraintPositive);
		
		addConstraintNegative.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if(isFeatureGroup(selectedNode))
					return;
				
				Literal literal = new Literal(selectedNode, false);
				addToConstraint(literal);
			}
		});
		menu.add(addConstraintNegative);
		
		return menu;
	}


	protected void removeFromContextConstraint(FeatureTreeNode selectedNode) {
		
		List<Constraint> constraintsToRemove = new ArrayList<Constraint>();
		
		
		for(int i = 0; i < constraintsList.size(); i++){
			Constraint constr = constraintsList.get(i);
			
			if(constr.getClause().contains(selectedNode.getName())){
				constraintsToRemove.add(constr);
			}
		}
		
		for(int i = 0; i < constraintsToRemove.size(); i++){
			constraintsList.remove(constraintsToRemove.get(i));
			constraints.remove(BASE_NAME_CONSTRAINT + String.valueOf(constraintsToRemove.get(i).getId()));
		}
		
		
	}

	protected void addToConstraint(Literal literal) {
		
		
		
		String toAdd = null;
		
		if(literal.isState())
			toAdd = literal.getFeature().getName();
		else
			toAdd = "~" + literal.getFeature().getName();
		
		if(constraintLiterals.isEmpty()){
			txtAddTheFeatures.setText("");
			txtAddTheFeatures.setText(toAdd);
		}else
			txtAddTheFeatures.setText(txtAddTheFeatures.getText() + " V " + toAdd);
		
		constraintLiterals.add(literal);
		
	}


	private boolean isFeatureGroup(FeatureTreeNode node) {
		if(node instanceof FeatureGroup){
			txtMessageText.setText("Feature Group can not be selected. Please, select its parent feature: \"" + ((FeatureTreeNode)selectedNode.getParent()).getName() + "\".");
			return true;
		}
		
		return false;
	}
	
	private void changeStatusFeature(boolean actualStatus, String message) {
		
		Resolution resolution = new Resolution(selectedNode.getID(), selectedNode.getName(), actualStatus);
		if(!resolutions.contains(resolution)){
			resolutions.add(resolution);
		
			tree.repaint();
			tree.updateUI();
		
			System.out.println("Had not");	
		}else{
			
			int index = resolutions.indexOf(resolution);
			Resolution res = resolutions.get(index);
			if(res.getStatus() != actualStatus){
				res.setStatus(actualStatus);
				txtMessageText.setText("Selected Feature status changed.");
				tree.repaint();
				tree.updateUI();
				
			}else
				txtMessageText.setText(message);
		}
	}
	
	/*
	 * Cria um novo feature model e retira os n�s e restri��es que n�o compoem o contexto para repassar na cria��o do BDD
	 */
	private FeatureModel createFeatureModel(){
		
		//Cria um novo modelo
		FeatureModel otherModel = (FeatureModel)ModelFactory.getInstance().createModel(modelID, pathModelFile);
		
		try {
			otherModel.loadModel();
		} catch (FeatureModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//Configura os n�s que n�o far�o parte do modelo no devido contexto
		Map<String, Boolean> status = new HashMap<String, Boolean>();
		for(Resolution resolution : resolutions){
			status.put(resolution.getIdFeature(), resolution.getStatus());
		}
		
		for(Map.Entry<String, FeatureTreeNode> node : otherModel.getNodesMap().entrySet()){
			
			String idNode = node.getKey();
			if(status.containsKey(idNode) && status.get(idNode) == false)
				node.getValue().setActiveInContext(false);
			
		}
		
		//Busca os n�s que ser�o deletados conforme sejam desativados pelo modelo
		List<FeatureTreeNode> nodesToDelete = new ArrayList<FeatureTreeNode>();
		
		for(FeatureTreeNode node : otherModel.getNodes()){
			if(!node.isActiveInContext()){
				
				
				
				if(!nodesToDelete.contains(node)){
					nodesToDelete.add(node);
				
					otherModel.getSubtreeNodes(node, nodesToDelete);
				}
			}
		}
		
		
		//Busca as constraints que possuem n�s que n�o fazem parte do modelo
		List<PropositionalFormula> constraintsToDelete = new ArrayList<PropositionalFormula>();
		
		for(PropositionalFormula constraint : otherModel.getConstraints()){
			for(BooleanVariable variable : constraint.getVariables()){
				FeatureTreeNode auxNode = new FeatureTreeNode(variable.getID(), null, null);
				if(nodesToDelete.contains(auxNode)){
					constraintsToDelete.add(constraint);
					break;
				}
			}
		}
		
		
		//Deleta os n�s da arvore
		for(FeatureTreeNode node : nodesToDelete){
			otherModel.removeNodeFromParent(node);
			
		}
		
		
		//Deleta todos os n�s e constraints registrados
		otherModel.getNodes().removeAll(nodesToDelete);
		otherModel.getConstraints().removeAll(constraintsToDelete);
		
		return otherModel;
		
	}
	
	private boolean isModelConsistent(FeatureModel model){
		
		try {
			
			// create BDD variable order heuristic
			new FTPreOrderSortedECTraversalHeuristic("Pre-CL-MinSpan", model, FTPreOrderSortedECTraversalHeuristic.FORCE_SORT);		
			VariableOrderingHeuristic heuristic = VariableOrderingHeuristicsManager.createHeuristicsManager().getHeuristic("Pre-CL-MinSpan");
			
			// Creates the BDD reasoner
			FMReasoningWithBDD bddReasoner = new FMReasoningWithBDD(model, heuristic, 50000, 50000, 60000, "pre-order");
					
			// Initialize the reasoner (BDD is created at this moment)
			
			bddReasoner.init();
			
			return bddReasoner.isConsistent();
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
		
	}

}
