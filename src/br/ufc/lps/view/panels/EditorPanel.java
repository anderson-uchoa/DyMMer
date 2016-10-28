package br.ufc.lps.view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
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
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
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
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import br.ufc.lps.controller.features.ControllerFeatures;
import br.ufc.lps.controller.features.TypeFeature;
import br.ufc.lps.controller.xml.ControladorXml;
import br.ufc.lps.controller.xml.WriteXMLmodel;
import br.ufc.lps.model.ModelFactory;
import br.ufc.lps.model.adaptation.ContextoAdaptacao;
import br.ufc.lps.model.context.SplotContextModel;
import br.ufc.lps.model.contextaware.Constraint;
import br.ufc.lps.model.contextaware.Context;
import br.ufc.lps.model.contextaware.Literal;
import br.ufc.lps.model.contextaware.Resolution;
import br.ufc.lps.model.normal.IModel;
import br.ufc.lps.model.rnf.Caracteristica;
import br.ufc.lps.model.rnf.ContextoRnf;
import br.ufc.lps.model.rnf.NameImpacto;
import br.ufc.lps.model.rnf.PropriedadeNFuncional;
import br.ufc.lps.model.rnf.Rnf;
import br.ufc.lps.model.rnf.Subcaracteristica;
import br.ufc.lps.model.rnf.ValorContextoRnf;
import br.ufc.lps.repositorio.SchemeXml;
import br.ufc.lps.splar.core.constraints.BooleanVariable;
import br.ufc.lps.splar.core.constraints.CNFClause;
import br.ufc.lps.splar.core.constraints.PropositionalFormula;
import br.ufc.lps.splar.core.constraints.parsing.CNFClauseParseException;
import br.ufc.lps.splar.core.constraints.parsing.CNFClauseParser;
import br.ufc.lps.splar.core.fm.FeatureGroup;
import br.ufc.lps.splar.core.fm.FeatureModel;
import br.ufc.lps.splar.core.fm.FeatureModelException;
import br.ufc.lps.splar.core.fm.FeatureTreeNode;
import br.ufc.lps.splar.core.fm.GroupedFeature;
import br.ufc.lps.splar.core.heuristics.FTPreOrderSortedECTraversalHeuristic;
import br.ufc.lps.splar.core.heuristics.VariableOrderingHeuristic;
import br.ufc.lps.splar.core.heuristics.VariableOrderingHeuristicsManager;
import br.ufc.lps.splar.plugins.reasoners.bdd.javabdd.FMReasoningWithBDD;
import br.ufc.lps.view.Main;
import br.ufc.lps.view.list.ConstraintsListModel;
import br.ufc.lps.view.list.ConstraintsRnfListModel;
import br.ufc.lps.view.trees.FeatureModelTree;
import br.ufc.lps.view.trees.FeaturesTreeCellRenderer;
import br.ufc.lps.view.trees.adaptation.Adaptacao;
import br.ufc.lps.view.trees.adaptation.CheckBoxNodeData;
import br.ufc.lps.view.trees.adaptation.CheckBoxNodeEditor;
import br.ufc.lps.view.trees.adaptation.CheckBoxNodeRenderer;
import br.ufc.lps.view.trees.adaptation.ValorAdaptacao;

public class EditorPanel extends JPanel implements ActionListener {

	protected static final String BASE_NAME_CONSTRAINT = "contextConstraint";
	private IModel model;
	private JTextField textFieldNewContext;
	public JTree tree;
	public JTree treeRnf;
	public JTree treeAdaptation;
	private FeatureTreeNode selectedNode;
	private JTextArea txtMessageText;
	private JButton btnNewContext;
	private JLabel lblNewContext;
	private Context defaultContext;
	public List<Resolution> resolutions;
	private JTextField txtAddTheFeatures;
	private JTextField txtAddConstraintRnf;
	private Map<String, String> constraints;
	private List<Literal> constraintLiterals;
	private JList list;
	private JList listConstraintsRnf;
	public ConstraintsListModel constraintsListModel;
	public ConstraintsRnfListModel constraintsRnfListModel;
	private int selectedConstraintIndex;
	private List<Constraint> constraintsList;
	private ValorContextoRnf constraintsRnf;
	private List<ValorContextoRnf> constraintsListRnf;
	private int constraintNumber;
	private Integer modelID;
	private String pathModelFile;
	private Boolean treeRnfAdicionada = false;
	private Main main;
	private JButton jbuttonSalvar;
	private ControllerFeatures controllerFeatures;
	private SplotContextModel splotContextModel;
	private DefaultTreeModel treeModel;
    private Map<String, br.ufc.lps.model.adaptation.Adaptacao> adaptacoes;
	private JPopupMenu menu;
	private ContextoRnf contextoRnf;
	
	public EditorPanel(IModel model, int modelID, String pathModelFile, SchemeXml schemeXml, Main main) {
		setLayout(new GridLayout(1, 0));
		this.main = main;
		constraints = new HashMap<String, String>();
		constraintLiterals = new ArrayList<Literal>();
		constraintsList = new ArrayList<Constraint>();
		constraintsListRnf = new ArrayList<>();
		
		splotContextModel = new SplotContextModel(pathModelFile);
		
		menu = new JPopupMenu();

		constraintNumber = 0;

		this.modelID = modelID;
		this.pathModelFile = pathModelFile;

		this.model = model;
		resolutions = new ArrayList<Resolution>();

		tree = new   JTree();
		treeRnf = new JTree();
		treeAdaptation = new JTree();
		controllerFeatures = new ControllerFeatures();

		tree.setModel(new FeatureModelTree(model.getFeatureModel().getRoot()));

		tree.setEditable(true);
		tree.setComponentPopupMenu(getComponentPopupMenu());
		tree.addMouseListener(getMouseListener());

		treeRnf.setEditable(true);
		preenchendoArvoreRnf(splotContextModel.getArvoreRnf());
		
		adaptacoes = splotContextModel.getAdaptacoes();
		//ARVORE DA ADAPTAÇÃO
		preenchendoArvore(splotContextModel.getArvoreAdaptacao());
		final CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
		treeAdaptation.setCellRenderer(renderer);
		final CheckBoxNodeEditor editor = new CheckBoxNodeEditor(treeAdaptation);
		treeAdaptation.setCellEditor(editor);
		treeAdaptation.setEditable(true);

		treeModel.addTreeModelListener(new TreeModelListener() {

			@Override
			public void treeNodesChanged(final TreeModelEvent e) {
				mudancaCheckBoxArvoreAdaptacao();
			}

			@Override
			public void treeNodesInserted(TreeModelEvent e) {}

			@Override
			public void treeNodesRemoved(TreeModelEvent e) {}

			@Override
			public void treeStructureChanged(TreeModelEvent e){}
		});
		
		treeAdaptation.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON3){
					mouseClickArvoreAdaptacao(e);    
				}
			}
		});
		
		treeRnf.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {}
			
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON3){
					mouseClickArvoreRnf(e);    
				}
			}
		});

		
		defaultContext = new Context("default", resolutions, null);
		tree.setCellRenderer(new FeaturesTreeCellRenderer(defaultContext));
		
		JPanel panelTree = new JPanel(new BorderLayout());
		panelTree.add(tree, BorderLayout.CENTER);
		JLabel tituloTree = new JLabel("Feature Model");
		panelTree.add(tituloTree, BorderLayout.NORTH);
		JScrollPane scrollPane = new JScrollPane(panelTree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane);
		
		JPanel panelTreeAdaptation = new JPanel(new BorderLayout());
		panelTreeAdaptation.add(treeAdaptation, BorderLayout.CENTER);
		JLabel tituloTreeAdaptation = new JLabel("Adaptation Context Feature Model");
		panelTreeAdaptation.add(tituloTreeAdaptation, BorderLayout.NORTH);
		JScrollPane scrollPaneAdaptation = new JScrollPane(panelTreeAdaptation, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollPaneAdaptation);
		
		JPanel panelTreeRnf = new JPanel(new BorderLayout());
		panelTreeRnf.add(treeRnf, BorderLayout.CENTER);
		JLabel tituloTreeRnf = new JLabel("Quality Feature Model");
		panelTreeRnf.add(tituloTreeRnf, BorderLayout.NORTH);
		JScrollPane scrollPaneRnf = new JScrollPane(panelTreeRnf, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		JPanel panelInfos = new JPanel();
		add(panelInfos);
		panelInfos.setLayout(new BorderLayout(1, 1));

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

				if (textFieldNewContext.getText().equals("")) {
					txtMessageText.setText("Please, type the context name.");
					lblNewContext.setForeground(Color.RED);
					textFieldNewContext.requestFocus();
				} else {

					DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

					try {
						
						DefaultMutableTreeNode rootArvoreAdaptacao = (DefaultMutableTreeNode)treeAdaptation.getModel().getRoot();

						String nomeContexto = verificandoArvore(rootArvoreAdaptacao);
						
						if(nomeContexto==null){
							JOptionPane.showMessageDialog(null, "Check someone item in Tree Adaptation");
							return;
						}
						
						DocumentBuilder db = dbf.newDocumentBuilder();
						Document doc = db.parse(EditorPanel.this.pathModelFile);

						Element rootEle = doc.getDocumentElement();

						FeatureTreeNode root = (FeatureTreeNode)tree.getModel().getRoot();
						
						Node tree = null;
						
						for(int i=0; i < rootEle.getChildNodes().getLength(); i++){
							tree = rootEle.getChildNodes().item(i);
							if(tree.getNodeName().equals("feature_tree"))
								rootEle.removeChild(tree);
							if(tree.getNodeName().equals("constraints"))
								rootEle.removeChild(tree);
							if(tree.getNodeName().equals("arvore_adaptacao"))
								rootEle.removeChild(tree);
							if(tree.getNodeName().equals("arvore_rnf"))
								rootEle.removeChild(tree);
						}
						
						controllerFeatures.drawTree(root);
						
						Node newTree = doc.createElement("feature_tree");
						
						newTree.appendChild(doc.createTextNode(controllerFeatures.getArvoreDesenhada()));
						
						rootEle.appendChild(newTree);

						rootEle.appendChild(WriteXMLmodel.getArvoreRnf(doc, (DefaultMutableTreeNode)treeRnf.getModel().getRoot()));
						
						rootEle.appendChild(WriteXMLmodel.getArvoreAdaptacao(doc, rootArvoreAdaptacao));
												
						rootEle.appendChild(WriteXMLmodel.getAdaptacao(doc, rootArvoreAdaptacao, nomeContexto));
						
						rootEle.appendChild(WriteXMLmodel.getContext(doc, nomeContexto,
									EditorPanel.this.resolutions, new ArrayList<String>(constraints.values())));
						
						ContextoRnf contextoRnf = new ContextoRnf();
						
						contextoRnf.setNome(nomeContexto);
						contextoRnf.setValorContextoRnf(constraintsListRnf);
						
						rootEle.appendChild(WriteXMLmodel.getContextoRnf(doc, contextoRnf));

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

						EditorPanel.this.tree.updateUI();
						JOptionPane.showMessageDialog(EditorPanel.this,
								"Your context has been saved. Now, open the file to see it.");

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

		JButton addTreeRnf = new JButton("Add RNF");
		
		addTreeRnf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton botao = ((JButton)e.getSource());
				if(!treeRnfAdicionada){
					if(!getComponent(1).equals(scrollPaneRnf)){
						botao.setText("Remove RNF");
						treeRnfAdicionada = true;
						add(scrollPaneRnf, 1);
						updateUI();
					}
				}else{
					if(getComponent(1).equals(scrollPaneRnf)){
						botao.setText("Add RNF");
						treeRnfAdicionada = false;
						remove(1);
						updateUI();
					}
				}
			}
		});
		
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

		addTreeRnf.setHorizontalAlignment(SwingConstants.CENTER);
		panelConstraint.add(addTreeRnf);

		jbuttonSalvar = new JButton("Save in repository.");
		jbuttonSalvar.setHorizontalAlignment(SwingConstants.CENTER);
		panelConstraint.add(jbuttonSalvar);
		
		JButton jbuttonSalvarNew = new JButton("Save in repository ( New Model )");
		jbuttonSalvarNew.setHorizontalAlignment(SwingConstants.CENTER);
		
		if(schemeXml!=null)
			panelConstraint.add(jbuttonSalvarNew);
		
		JLabel lblConstraint = new JLabel("Constraint:");
		lblConstraint.setHorizontalAlignment(SwingConstants.CENTER);
		panelConstraint.add(lblConstraint);

		jbuttonSalvar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String nome = null;
				
				if(schemeXml==null)
					nome = JOptionPane.showInputDialog("Type the name of Model", model.getModelName());
				
				File file = new File(pathModelFile);
				Boolean resultado = ControladorXml.salvarXMLRepositorio(nome, file, schemeXml);
				if (resultado) {
					JOptionPane.showMessageDialog(null, "Save Successfull");
					EditorPanel.this.main.recarregarListaFeatures();
				}
			}
		});
		
		jbuttonSalvarNew.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String nome  = JOptionPane.showInputDialog("Type the name of new model:", schemeXml.getNameXml());
				
				if(nome.equals("")){
					JOptionPane.showMessageDialog(null, "Type a name valid!");
					return;
				}
				
				if(nome.equals(schemeXml.getNameXml())){
					JOptionPane.showMessageDialog(null, "Type a different name!");
					return;
				}
				
				schemeXml.setNameXml(nome);
				File file = new File(pathModelFile);
				Boolean resultado = ControladorXml.salvarXMLRepositorio(nome, file, null);
				if (resultado) {
					JOptionPane.showMessageDialog(null, "Save successful!");
					EditorPanel.this.main.recarregarListaFeatures();
				}
			}
		});

		txtAddTheFeatures = new JTextField();
		txtAddTheFeatures.setText("Add the features...");
		txtAddTheFeatures.setEditable(false);
		txtAddTheFeatures.setColumns(10);

		JScrollPane scrollPane_2 = new JScrollPane(txtAddTheFeatures, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
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

				if (constraintLiterals.size() < 2) {
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

				for (int i = 1; i < constraintLiterals.size(); i++) {
					String variable = null;
					Literal tempLiteral = constraintLiterals.get(i);

					if (tempLiteral.isState())
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
				for (Entry<String, String> constsString : constraints.entrySet()) {

					System.out.println(constsString.getKey() + ": " + constsString.getValue());

					CNFClause clause = null;
					try {
						clause = parser.parse(constsString.getValue());

						// otherModel.addConstraint(new
						// PropositionalFormula(constsString.getKey(),
						// clause.toString2()));
						otherModel.addConstraint(new PropositionalFormula(constsString.getKey(), clause.toString()));
					System.out.println( clause.toString());
					} catch (CNFClauseParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				System.out.println("Size model's constraints: " + otherModel.getConstraints().size());

				boolean consistency = isModelConsistent(otherModel);

				if (consistency)
					System.out.println("CONSISTENT");
				else
					System.out.println("NOT CONSISTENT");

			}
		});

		constraintsListModel = new ConstraintsListModel(constraintsList);
		list = new JList<String>(constraintsListModel);
		list.setComponentPopupMenu(getComponentPopupMenuConstraintsList());
		list.addMouseListener(getMouseListener());

		JScrollPane scrollPane_1 = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		constraintsRnfListModel = new ConstraintsRnfListModel(constraintsListRnf);
		listConstraintsRnf = new JList<String>(constraintsRnfListModel);
		listConstraintsRnf.setComponentPopupMenu(getComponentPopupMenuConstraintsList());
		listConstraintsRnf.addMouseListener(getMouseListener());

		JScrollPane scrollPane_4 = new JScrollPane(listConstraintsRnf, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		JPanel painelJlists = new JPanel(new GridLayout(1, 0));
		
		JPanel painelTxtConstraints = new JPanel(new BorderLayout());
			painelTxtConstraints.add(new JLabel("Constraints"), BorderLayout.NORTH);
			painelTxtConstraints.add(scrollPane_1, BorderLayout.CENTER);
		JPanel painelTxtConstraintsRnf = new JPanel(new BorderLayout());
			painelTxtConstraintsRnf.add(new JLabel("Constraints Rnf"), BorderLayout.NORTH);
			painelTxtConstraintsRnf.add(scrollPane_4, BorderLayout.CENTER);
		
			painelJlists.add(painelTxtConstraints);
		painelJlists.add(painelTxtConstraintsRnf);	
		
		panel.add(painelJlists, BorderLayout.CENTER);
		
		// PAINEL CONSTRAINT RNF
		JLabel lblConstraintRnf = new JLabel("Constraints Rnf:");
		lblConstraintRnf.setHorizontalAlignment(SwingConstants.CENTER);
		panelConstraint.add(lblConstraintRnf);
		
		txtAddConstraintRnf = new JTextField();
		txtAddConstraintRnf.setText("Add Constraint Rnf...");
		txtAddConstraintRnf.setEditable(false);
		txtAddConstraintRnf.setColumns(10);

		JScrollPane scrollPane_3 = new JScrollPane(txtAddConstraintRnf, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panelConstraint.add(scrollPane_3);

		JPanel panel_2 = new JPanel();
		panelConstraint.add(panel_2);

		JButton btnRemoveConstraintRnf = new JButton("Remove Constraint");
		panel_2.add(btnRemoveConstraintRnf);
		
		btnRemoveConstraintRnf.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				constraintsRnf = null;
				txtAddConstraintRnf.setText("");
			}
		});
		
		JButton btnAddConstraintRnf = new JButton("Add Constraint");
		panel_2.add(btnAddConstraintRnf);

		btnAddConstraintRnf.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if(constraintsRnf!=null && constraintsRnf.isTerminate()){
					constraintsListRnf.add(constraintsRnf);
					constraintsRnfListModel.update();
					constraintsRnf = null;
					txtAddConstraintRnf.setText("");
				}

			}
		});

		
		this.main.expandAllNodes(tree, 0, tree.getRowCount());
	}
	
	public String getModelName() {

		return model.getModelName();
	}

	private void mudancaCheckBoxArvoreAdaptacao(){
		TreePath currentSelection = treeAdaptation.getSelectionPath();

		if (currentSelection != null) {   				
			DefaultMutableTreeNode tipoSelecionado = (DefaultMutableTreeNode) currentSelection.getLastPathComponent();
			if(tipoSelecionado instanceof ValorAdaptacao){
				Object ob = tipoSelecionado.getUserObject();
				if(ob instanceof CheckBoxNodeData){
					CheckBoxNodeData check = (CheckBoxNodeData) ob;
					if(check.isChecked()){
						DefaultMutableTreeNode pai = (DefaultMutableTreeNode) tipoSelecionado.getParent();
						for(int i=0; i<pai.getChildCount(); i++){
							if(!pai.getChildAt(i).equals(tipoSelecionado)){
								CheckBoxNodeData c = (CheckBoxNodeData) ((DefaultMutableTreeNode)pai.getChildAt(i)).getUserObject();
								c.setChecked(false);
							}
						}
						treeAdaptation.updateUI();
					}
				}
			}
		}
		
		//String nome = verificandoArvore((DefaultMutableTreeNode)treeModel.getRoot());
		
	//	if(nome!=null)
			//setTreeVisualization(nome);
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

				if (event.getButton() == MouseEvent.BUTTON3) {

					if (event.getSource() == tree) {

						TreePath pathForLocation = tree.getPathForLocation(event.getPoint().x, event.getPoint().y);
						if (pathForLocation != null) {
							System.out.println("NOT NULL");

							selectedNode = (FeatureTreeNode) pathForLocation.getLastPathComponent();

							menu = MenuFactory.getIntance(EditorPanel.this, selectedNode)
									.verificarMenuDeSelecao(selectedNode.getTypeFeature());

							menu.show(tree, event.getPoint().x, event.getPoint().y);

						} else {
							System.out.println("NULL");
							selectedNode = null;
						}

					} else if (event.getSource() == list) {

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

	public void removeFromContextConstraint(FeatureTreeNode selectedNode) {

		List<Constraint> constraintsToRemove = new ArrayList<Constraint>();

		for (int i = 0; i < constraintsList.size(); i++) {
			Constraint constr = constraintsList.get(i);

			if (constr.getClause().contains(selectedNode.getName())) {
				constraintsToRemove.add(constr);
			}
		}

		for (int i = 0; i < constraintsToRemove.size(); i++) {
			constraintsList.remove(constraintsToRemove.get(i));
			constraints.remove(BASE_NAME_CONSTRAINT + String.valueOf(constraintsToRemove.get(i).getId()));
		}

	}

	public void addToConstraint(Literal literal) {

		String toAdd = null;

		if (literal.isState())
			toAdd = literal.getFeature().getName();
		else
			toAdd = "~" + literal.getFeature().getName();
			

		if (constraintLiterals.isEmpty()) {
			txtAddTheFeatures.setText("");
			txtAddTheFeatures.setText(toAdd);
		} else
			 if (toAdd.contains("~")  && txtAddTheFeatures.getText().contains("~") ){
					
				 txtAddTheFeatures.setText(txtAddTheFeatures.getText() + " excludes " + toAdd);
				 
			 }else {
				 
				 txtAddTheFeatures.setText(txtAddTheFeatures.getText() + " requires " + toAdd);
			 }
			txtAddTheFeatures.setText(txtAddTheFeatures.getText() + " V " + toAdd);

		constraintLiterals.add(literal);

	}

	public boolean isFeatureGroup(FeatureTreeNode node) {
		if (node instanceof FeatureGroup) {
			txtMessageText.setText("Feature Group can not be selected. Please, select its parent feature: \""
					+ ((FeatureTreeNode) selectedNode.getParent()).getName() + "\".");
			return true;
		}

		return false;
	}

	private boolean modifyOthers(FeatureTreeNode node) {
		if (node.getTypeFeature() == TypeFeature.GROUPED_FEATURE) {
			GroupedFeature fea = (GroupedFeature) node;
			FeatureGroup pai = (FeatureGroup) fea.getParent();

			if (pai != null) {
				if (pai.getMax() == 1) {
					int quantidadeDeFilhos = pai.getChildCount();
					for (int i = 0; i < quantidadeDeFilhos; i++) {
						GroupedFeature featureG = ((GroupedFeature) pai.getChildAt(i));
						if (!featureG.equals(fea)) {
							desactivateAllChild(featureG);
							selectedNode = featureG;
							changeStatusFeature(false, "mudanca");
						}
					}
				}
			}
		}
		return false;
	}

	private void desactivateAllChild(FeatureTreeNode node) {
		if (node != null && node.getChildCount() > 0) {
			for (int i = 0; i < node.getChildCount(); i++) {
				desactivateAllChild(((FeatureTreeNode) node.getChildAt(i)));
				selectedNode = (((FeatureTreeNode) node.getChildAt(i)));
				changeStatusFeature(false, "Change Status");
			}
		}
	}

	public void changeStatusFeature(boolean actualStatus, String message) {

		Resolution resolution = new Resolution(selectedNode.getID(), selectedNode.getName(), actualStatus);
		if (!resolutions.contains(resolution)) {
			resolutions.add(resolution);

			tree.repaint();
			tree.updateUI();

			System.out.println("Had not");
		} else {

			int index = resolutions.indexOf(resolution);
			Resolution res = resolutions.get(index);
			if (res.getStatus() != actualStatus) {
				res.setStatus(actualStatus);
				txtMessageText.setText("Selected Feature status changed.");
				tree.repaint();
				tree.updateUI();

			} else
				txtMessageText.setText(message);
		}
	}

	private FeatureModel createFeatureModel() {

		// Cria um novo modelo
		FeatureModel otherModel = (FeatureModel) ModelFactory.getInstance().createModel(modelID, pathModelFile);

		try {
			otherModel.loadModel();
		} catch (FeatureModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Configura os n�s que n�o far�o parte do modelo no devido contexto
		Map<String, Boolean> status = new HashMap<String, Boolean>();
		for (Resolution resolution : resolutions) {
			status.put(resolution.getIdFeature(), resolution.getStatus());
		}

		for (Map.Entry<String, FeatureTreeNode> node : otherModel.getNodesMap().entrySet()) {

			String idNode = node.getKey();
			if (status.containsKey(idNode) && status.get(idNode) == false)
				node.getValue().setActiveInContext(false);

		}

		// Busca os n�s que ser�o deletados conforme sejam desativados pelo
		// modelo
		List<FeatureTreeNode> nodesToDelete = new ArrayList<FeatureTreeNode>();

		for (FeatureTreeNode node : otherModel.getNodes()) {
			if (!node.isActiveInContext()) {

				if (!nodesToDelete.contains(node)) {
					nodesToDelete.add(node);

					otherModel.getSubtreeNodes(node, nodesToDelete);
				}
			}
		}

		// Busca as constraints que possuem n�s que n�o fazem parte do modelo
		List<PropositionalFormula> constraintsToDelete = new ArrayList<PropositionalFormula>();

		for (PropositionalFormula constraint : otherModel.getConstraints()) {
			for (BooleanVariable variable : constraint.getVariables()) {
				FeatureTreeNode auxNode = new FeatureTreeNode(variable.getID(), null, null);
				if (nodesToDelete.contains(auxNode)) {
					constraintsToDelete.add(constraint);
					break;
				}
			}
		}

		// Deleta os n�s da arvore
		for (FeatureTreeNode node : nodesToDelete) {
			otherModel.removeNodeFromParent(node);

		}

		// Deleta todos os n�s e constraints registrados
		otherModel.getNodes().removeAll(nodesToDelete);
		otherModel.getConstraints().removeAll(constraintsToDelete);

		return otherModel;

	}

	private boolean isModelConsistent(FeatureModel model) {

		try {

			// create BDD variable order heuristic
			new FTPreOrderSortedECTraversalHeuristic("Pre-CL-MinSpan", model,
					FTPreOrderSortedECTraversalHeuristic.FORCE_SORT);
			VariableOrderingHeuristic heuristic = VariableOrderingHeuristicsManager.createHeuristicsManager()
					.getHeuristic("Pre-CL-MinSpan");

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

	private void expandAllNodes(JTree tree, int startingIndex, int rowCount){
	    for(int i=startingIndex;i<rowCount;++i){
	        tree.expandRow(i);
	    }

	    if(tree.getRowCount()!=rowCount){
	        expandAllNodes(tree, rowCount, tree.getRowCount());
	    }
	}
	
	private void mouseClickArvoreAdaptacao(MouseEvent e){
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)treeAdaptation.getLastSelectedPathComponent();
		if(node.getLevel() == 0){
			JPopupMenu menu = new JPopupMenu();
			menu.add(new JLabel("Adaptation Options:"));
			menu.addSeparator();
			JMenuItem adicionar = new JMenuItem("Add");
			
			menu.add(adicionar);
			
			adicionar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String adaptacao = JOptionPane.showInputDialog("Add the name of adaptation");
					if(adaptacao!=null && !adaptacao.trim().isEmpty()){
						node.add(new Adaptacao(adaptacao));
						treeAdaptation.updateUI();
					}
				}
			});
			
			menu.show(treeAdaptation, e.getX(), e.getY());
			
		}else if(node.getLevel() == 1){
			JPopupMenu menu = new JPopupMenu("Value");
			menu.add(new JLabel("Value Options:"));
			menu.addSeparator();
			JMenuItem adicionar = new JMenuItem("Add");
			
			menu.add(adicionar);
			
			adicionar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String valor = JOptionPane.showInputDialog("Add the name of value");
					if(valor!=null && !valor.trim().isEmpty()){
						CheckBoxNodeData check = new CheckBoxNodeData(valor, false);
						node.add(new br.ufc.lps.view.trees.adaptation.ValorAdaptacao(check));
						treeAdaptation.updateUI();
					}
				}
			});
			
			menu.add(new JLabel("Adaptation Options:"));
			menu.addSeparator();
			JMenuItem remover = new JMenuItem("Remove");
			
			menu.add(remover);
			
			remover.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					node.removeFromParent();
					treeAdaptation.updateUI();
				}
			});
			
			menu.show(treeAdaptation, e.getX(), e.getY());
			
		}else{
			JPopupMenu menu = new JPopupMenu("Adaptation");
			menu.add(new JLabel("Value Options:"));
			menu.addSeparator();
			JMenuItem adicionar = new JMenuItem("Remove");
			
			menu.add(adicionar);
			
			adicionar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					node.removeFromParent();
					treeAdaptation.updateUI();
				}
			});
			
			menu.show(treeAdaptation, e.getX(), e.getY());
		}
	

	}
	
	private void mouseClickArvoreRnf(MouseEvent e){
		
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)treeRnf.getLastSelectedPathComponent();
		
		if(node.getLevel() == 0){
			JPopupMenu menu = new JPopupMenu();
			menu.add(new JLabel("Opções de características:"));
			menu.addSeparator();
			JMenuItem adicionar = new JMenuItem("Add");
			
			menu.add(adicionar);
			
			adicionar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String adaptacao = JOptionPane.showInputDialog("Adicione o nome da característica");
					if(adaptacao!=null && !adaptacao.trim().isEmpty()){
						node.add(new DefaultMutableTreeNode(adaptacao));
						treeRnf.updateUI();
					}
				}
			});
			
			menu.show(treeRnf, e.getX(), e.getY());
			
		}else if(node.getLevel() == 1){
			JPopupMenu menu = new JPopupMenu("Value");
			menu.add(new JLabel("Opções de subcaracterísticas:"));
			menu.addSeparator();
			JMenuItem adicionar = new JMenuItem("Add");
			
			menu.add(adicionar);
			
			adicionar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String valor = JOptionPane.showInputDialog("Adicione o nome da subcaracterísticas");
					if(valor!=null && !valor.trim().isEmpty()){
						node.add(new DefaultMutableTreeNode(valor));
						treeRnf.updateUI();
					}
				}
			});
			
			menu.add(new JLabel("Opções de características:"));
			menu.addSeparator();
			JMenuItem remover = new JMenuItem("Remove");
			
			menu.add(remover);
			
			remover.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					node.removeFromParent();
					treeRnf.updateUI();
				}
			});
			
			menu.show(treeRnf, e.getX(), e.getY());
			
		}else if(node.getLevel() == 2){
			JPopupMenu menu = new JPopupMenu("Value");
			menu.add(new JLabel("Opções de PNF:"));
			menu.addSeparator();
			JMenuItem adicionar = new JMenuItem("Add");
			
			menu.add(adicionar);
			
			adicionar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String valor = JOptionPane.showInputDialog("Add the name of PNF");
					if(valor!=null && !valor.trim().isEmpty()){
						node.add(new DefaultMutableTreeNode(valor));
						treeRnf.updateUI();
					}
				}
			});
			
			menu.add(new JLabel("Opções de subcaracterísticas:"));
			menu.addSeparator();
			JMenuItem remover = new JMenuItem("Remove");
			
			menu.add(remover);
			
			remover.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					node.removeFromParent();
					treeRnf.updateUI();
				}
			});
			
			menu.show(treeRnf, e.getX(), e.getY());
		}else if(node.getLevel() == 3){
			JPopupMenu menu = new JPopupMenu("Adaptation");
			menu.add(new JLabel("Opções de PNF:"));
			menu.addSeparator();
			
			JMenuItem remover = new JMenuItem("Remove");
			
			menu.add(remover);
			
			remover.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					node.removeFromParent();
					treeRnf.updateUI();
				}
			});
			
			menu.add(new JLabel("Opções de Contrainsts RNF:"));
			menu.addSeparator();
			
			JMenuItem addToConstraint = new JMenuItem("Add to Constraint Model Features");
			
			menu.add(addToConstraint);
			
			addToConstraint.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
	
					if(constraintsRnf != null && constraintsRnf.getIdFeature()!=null){
						constraintsRnf.setIdRnf(node.toString());
						constraintsRnf.setTerminate(true);
						txtAddConstraintRnf.setText(constraintsRnf.toString());
					}
					
				}
			});
			
			menu.show(treeRnf, e.getX(), e.getY());
		}
	}
	
	private void setTreeVisualization(final String contextName) {
		
		if(splotContextModel.getContexts().isEmpty()){
			JOptionPane.showMessageDialog(EditorPanel.this, "It's not supported, because it does not have any context. Please, first edit it and add one context.");
			tree.setModel(null);
			return;
		}	
		
		Context context = splotContextModel.getContexts().get(contextName);
		FeatureModel featureModel = splotContextModel.setFeatureModel(context);
		String modelName = featureModel.getName();
		
		
		tree.setModel(featureModel);	
		tree.setEditable(true);
		tree.setCellRenderer(new FeaturesTreeCellRenderer(context));
		expandAllNodes(tree, 0, tree.getRowCount());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	
		
		if (e.getActionCommand().equals("setActive")) {

			System.out.println("SetActivee:" + selectedNode.getName());

			if (isFeatureGroup(selectedNode))
				return;

			FeatureTreeNode atual = selectedNode;

			modifyOthers(selectedNode);
			selectedNode = atual;

			changeStatusFeature(true, "Selected Feature is already activated");

		} else if (e.getActionCommand().equals("setDeactive")) {

			if (isFeatureGroup(selectedNode))
				return;

			changeStatusFeature(false, "Selected Feature is already deactivated");

		} else if (e.getActionCommand().equals("takeOffContext")) {

			if (isFeatureGroup(selectedNode))
				return;

			Resolution resolution = new Resolution(selectedNode.getID(), null, false);
			resolutions.remove(resolution);
			tree.updateUI();

			removeFromContextConstraint(selectedNode);
			constraintsListModel.update();

		} else if (e.getActionCommand().equals("addConstraintPositive")) {

			if (isFeatureGroup(selectedNode))
				return;

			Literal literal = new Literal(selectedNode, true);

			addToConstraint(literal);

		} else if (e.getActionCommand().equals("addConstraintNegative")) {

			if (isFeatureGroup(selectedNode))
				return;

			Literal literal = new Literal(selectedNode, false);
			addToConstraint(literal);

		} else if (e.getActionCommand().equals("addOptionalFeature")) {
		
			
			String nome = JOptionPane.showInputDialog("Type the feature name:");
			
			if(nome==null || nome.equals("")){
				JOptionPane.showMessageDialog(null, "Type a valid feature name.");
				return;
			}
			
			controllerFeatures.addFeatures(selectedNode, TypeFeature.OPTIONAL , nome);
			
			tree.updateUI();
		

		} else if (e.getActionCommand().equals("addMandatoryFeature")) {

			String nome = JOptionPane.showInputDialog("Type the feature name:");
			
			if(nome==null || nome.equals("")){
				JOptionPane.showMessageDialog(null, "Type a valid feature name.");
				return;
			}

			controllerFeatures.addFeatures(selectedNode, TypeFeature.MANDATORY , nome);
			
			tree.updateUI();
		


		} else if (e.getActionCommand().equals("addXORGroup")) {

			controllerFeatures.addFeatures(selectedNode, TypeFeature.GROUP_XOR , null);
				
			tree.updateUI();

		} else if (e.getActionCommand().equals("addORGroup")) {
		
			controllerFeatures.addFeatures(selectedNode, TypeFeature.GROUP_OR , null);

			tree.updateUI();
		
		} else if(e.getActionCommand().equals("AddtoConstraintRNF-")){
			
			constraintsRnf = new ValorContextoRnf();
			
			constraintsRnf.setIdFeature(selectedNode.getID());
			constraintsRnf.setImpacto(NameImpacto.getImpactoByName("-"));
			
			System.out.println(constraintsRnf.toString());
			
			txtAddConstraintRnf.setText(constraintsRnf.toString());
			
		} else if(e.getActionCommand().equals("AddtoConstraintRNF--")){
			
			constraintsRnf = new ValorContextoRnf();
			
			constraintsRnf.setIdFeature(selectedNode.getID());
			constraintsRnf.setImpacto(NameImpacto.getImpactoByName("--"));
			
			System.out.println(constraintsRnf.toString());
			
			txtAddConstraintRnf.setText(constraintsRnf.toString());
			
		} else if(e.getActionCommand().equals("AddtoConstraintRNF+")){

			constraintsRnf = new ValorContextoRnf();
			
			constraintsRnf.setIdFeature(selectedNode.getID());
			constraintsRnf.setImpacto(NameImpacto.getImpactoByName("+"));
			
			System.out.println(constraintsRnf.toString());
			
			txtAddConstraintRnf.setText(constraintsRnf.toString());
			
		} else if(e.getActionCommand().equals("AddtoConstraintRNF++")){

			constraintsRnf = new ValorContextoRnf();
			
			constraintsRnf.setIdFeature(selectedNode.getID());
			constraintsRnf.setImpacto(NameImpacto.getImpactoByName("++"));
			
			System.out.println(constraintsRnf.toString());
			
			txtAddConstraintRnf.setText(constraintsRnf.toString());
			
		} else{
			
			controllerFeatures.removeFeatures(selectedNode);

			tree.updateUI();

		} 
	}

	private void criarModelo(){

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	
		try {
	
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(EditorPanel.this.pathModelFile);
	
			Element rootEle = doc.getDocumentElement();
	
			FeatureTreeNode root = (FeatureTreeNode)tree.getModel().getRoot();
			
			Node tree = null;
			
			for(int i=0; i < rootEle.getChildNodes().getLength(); i++){
				tree = rootEle.getChildNodes().item(i);
				if(tree.getNodeName().equals("feature_tree"))
					break;
			}
			
			for(int i=0; i < rootEle.getChildNodes().getLength(); i++){
				Node tr = rootEle.getChildNodes().item(i);
				if(tr.getNodeName().equals("constraints")){
					rootEle.removeChild(tr);
					break;
				}
					
			}
			
			rootEle.removeChild(tree);
			
			controllerFeatures.drawTree(root);
			controllerFeatures.getArvoreDesenhada();
			
			Node newTree = doc.createElement("feature_tree");
			
			newTree.appendChild(doc.createTextNode(controllerFeatures.getArvoreDesenhada()));
			
			rootEle.appendChild(newTree);
	
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			StreamResult console = new StreamResult(new FileOutputStream(EditorPanel.this.pathModelFile));
			transformer.transform(source, console);
	
			JOptionPane.showMessageDialog(EditorPanel.this,
					"Your model has been saved. Now, open the file to see it.");
	
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

	private String verificandoArvore(DefaultMutableTreeNode root){
		String nome = "";
		boolean pelomenosum = false;
		for(int i=0; i < root.getChildCount(); i++){
			DefaultMutableTreeNode filho = (DefaultMutableTreeNode) root.getChildAt(i);
			
			for(int j=0; j < filho.getChildCount(); j++){
				DefaultMutableTreeNode neto = (DefaultMutableTreeNode) filho.getChildAt(j);
				CheckBoxNodeData dado = (CheckBoxNodeData) neto.getUserObject();
				if(dado.isChecked()){
					nome=nome+filho.toString();
					nome=nome+dado.getText();
					pelomenosum = true;
					break;
				}
			}
		}
		
		if(pelomenosum){
			return nome.replaceAll(" ", "");
		}
		
		return null;
	}
	
	private void preenchendoArvore(br.ufc.lps.model.adaptation.Adaptacao adaptacao){
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Context adaptations");
		
		if(adaptacao!=null && adaptacao.getValorAdaptacao()!=null){
			for(ContextoAdaptacao contextoAdaptacao : adaptacao.getValorAdaptacao()){
				Adaptacao contexto = new Adaptacao(contextoAdaptacao.getNome());
				
				for(br.ufc.lps.model.adaptation.ValorAdaptacao valorAdaptacao : contextoAdaptacao.getValorAdaptacao()){
					CheckBoxNodeData data = new CheckBoxNodeData(valorAdaptacao.getNome(), false);
					contexto.add(new br.ufc.lps.view.trees.adaptation.ValorAdaptacao(data));
					
				}
				
				root.add(contexto);
			}
		}
		treeModel = new DefaultTreeModel(root);
		treeAdaptation = new JTree(treeModel);
		treeAdaptation.setModel(treeModel);
		treeAdaptation.updateUI();
		expandAllNodes(treeAdaptation, 0, treeAdaptation.getRowCount());
	}
	
	private void preenchendoArvoreRnf(Rnf rnf){
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Rnf");
		
		if(rnf!=null && rnf.getCaracteristicas()!=null){
			
			for(Caracteristica caracteristica : rnf.getCaracteristicas()){
				DefaultMutableTreeNode carac = new DefaultMutableTreeNode(caracteristica.getNome());
				
				for(Subcaracteristica subcaracteristica : caracteristica.getSubcaracteristicas()){
					DefaultMutableTreeNode sub = new DefaultMutableTreeNode(subcaracteristica.getNome());
					carac.add(sub);
					
					for(PropriedadeNFuncional propriedadeNFuncional : subcaracteristica.getPropriedadeNFuncionais()){
						DefaultMutableTreeNode pnf = new DefaultMutableTreeNode(propriedadeNFuncional.getPropriedade());
						sub.add(pnf);
					}
				}
				
				root.add(carac);
			}
			
		}
		treeModel = new DefaultTreeModel(root);
		treeRnf = new JTree(treeModel);
		treeRnf.setModel(treeModel);
		treeRnf.updateUI();
		expandAllNodes(treeRnf, 0, treeRnf.getRowCount());
	}
	
}