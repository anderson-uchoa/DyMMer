package br.ufc.lps.view.panels;

import java.awt.BorderLayout;
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
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JFileChooser;
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
import javax.swing.tree.DefaultMutableTreeNode;
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
import br.ufc.lps.model.contextaware.Constraint;
import br.ufc.lps.model.contextaware.Context;
import br.ufc.lps.model.contextaware.Literal;
import br.ufc.lps.model.contextaware.Resolution;
import br.ufc.lps.model.rnf.ContextoRnf;
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
import br.ufc.lps.splar.core.fm.RootNode;
import br.ufc.lps.splar.core.heuristics.FTPreOrderSortedECTraversalHeuristic;
import br.ufc.lps.splar.core.heuristics.VariableOrderingHeuristic;
import br.ufc.lps.splar.core.heuristics.VariableOrderingHeuristicsManager;
import br.ufc.lps.splar.plugins.reasoners.bdd.javabdd.FMReasoningWithBDD;
import br.ufc.lps.view.Main;
import br.ufc.lps.view.list.ConstraintsListModel;
import br.ufc.lps.view.trees.FeatureModelTree;
import br.ufc.lps.view.trees.FeaturesTreeCellRenderer;

public class CreatorPanel extends JPanel implements ActionListener {

	protected static final String BASE_NAME_CONSTRAINT = "contextConstraint";
	private JTree tree;
	private FeatureTreeNode selectedNode;
	private JTextArea txtMessageText;
	private Context defaultContext;
	public List<Resolution> resolutions;
	private Map<String, String> constraints;
	private List<Literal> constraintLiterals;
	public ConstraintsListModel constraintsListModel;
	private int selectedConstraintIndex;
	private List<Constraint> constraintsList;
	private int constraintNumber;
	private Integer modelID;
	private String pathModelFile = "modelBase/model_base.xml";
	private Main main;
	private JButton jbuttonSalvar;
	private JButton jbuttonLocal;
	private ControllerFeatures controllerFeatures;
	private JPopupMenu menu;
	private File fileTemp;

	public CreatorPanel(int modelID, Main main, String nameRoot) {
		
		setLayout(new GridLayout(1, 0));
		this.main = main;
		constraints = new HashMap<String, String>();
		constraintLiterals = new ArrayList<Literal>();
		constraintsList = new ArrayList<Constraint>();
		
		try {
			fileTemp = File.createTempFile(UUID.randomUUID()+"", ".xml");
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		menu = new JPopupMenu();

		constraintNumber = 0;

		this.modelID = modelID;
		resolutions = new ArrayList<Resolution>();

		tree = new   JTree();
		controllerFeatures = new ControllerFeatures();

		FeatureTreeNode featureRoot = new RootNode("_r", nameRoot, null);
	
		tree.setModel(new FeatureModelTree(featureRoot));

		tree.setEditable(true);
		tree.setComponentPopupMenu(getComponentPopupMenu());
		tree.addMouseListener(getMouseListener());

		defaultContext = new Context("default", resolutions, null);
		tree.setCellRenderer(new FeaturesTreeCellRenderer(defaultContext));

		JPanel panelTree = new JPanel(new BorderLayout());
		panelTree.add(tree, BorderLayout.CENTER);
		JLabel tituloTree = new JLabel("Feature Model");
		panelTree.add(tituloTree, BorderLayout.NORTH);
		JScrollPane scrollPane = new JScrollPane(panelTree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane);
		
		JPanel panelInfos = new JPanel();
		add(panelInfos);
		panelInfos.setLayout(new BorderLayout(1, 1));

		JPanel panelNewContext = new JPanel();
		panelInfos.add(panelNewContext, BorderLayout.NORTH);
		panelNewContext.setLayout(new GridLayout(0, 2, 0, 0));

		JLabel lblBlankSpace = new JLabel("");
		panelNewContext.add(lblBlankSpace);

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

		jbuttonSalvar = new JButton("Save in repository (NÃO FUNCIONANDO AINDA)");
		jbuttonSalvar.setHorizontalAlignment(SwingConstants.CENTER);
		panelConstraint.add(jbuttonSalvar);
		
		jbuttonLocal = new JButton("Save in local");
		jbuttonLocal.setHorizontalAlignment(SwingConstants.CENTER);
		panelConstraint.add(jbuttonLocal);
		
		jbuttonLocal.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				JFileChooser chooser = new JFileChooser(); 
			    chooser.setCurrentDirectory(new java.io.File("."));
			    chooser.setDialogTitle("Select the path");
			    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			    
			    chooser.setAcceptAllFileFilterUsed(false);
			    
			    if (chooser.showOpenDialog(CreatorPanel.this) == JFileChooser.APPROVE_OPTION) { 
			      System.out.println("getCurrentDirectory(): " 
			         +  chooser.getCurrentDirectory());
			      System.out.println("getSelectedFile() : " 
			         +  chooser.getSelectedFile());
			      
			    
			    	String nomeArquivo = JOptionPane.showInputDialog("Type the name of File");
			    
			    	if(nomeArquivo!=null && nomeArquivo.trim().equals(""))
			    		System.out.println("nome incorreto");
			    	
			    	File file = new File(chooser.getSelectedFile()+"/"+nomeArquivo+".xml");
			    	
			    	CreatorPanel.this.fileTemp.renameTo(file);
			    	
			    	saveInFile(file);
			    
				}else {
			      System.out.println("No Selection ");
			    }

			   
			}
		});

		
		jbuttonSalvar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveInFile(CreatorPanel.this.fileTemp);
				Boolean resultado = ControladorXml.salvarXMLRepositorio(null, CreatorPanel.this.fileTemp, null);
				if (resultado) {
					JOptionPane.showMessageDialog(null, "Save Successfull");
					CreatorPanel.this.main.recarregarListaFeatures();
				}
			}
		});
		
		this.main.expandAllNodes(tree, 0, tree.getRowCount());
	}
	
	private void saveInFile(File file){
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {
			
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(CreatorPanel.this.pathModelFile);

			Element rootEle = doc.getDocumentElement();

			FeatureTreeNode root = (FeatureTreeNode)tree.getModel().getRoot();
			
			Node tree = null;
			
			for(int i=0; i < rootEle.getChildNodes().getLength(); i++){
				tree = rootEle.getChildNodes().item(i);
				if(tree.getNodeName().equals("feature_tree"))
					rootEle.removeChild(tree);
			}
			
			controllerFeatures.drawTree(root);
			
			Node newTree = doc.createElement("feature_tree");
			
			newTree.appendChild(doc.createTextNode(controllerFeatures.getArvoreDesenhada()));
			
			rootEle.appendChild(newTree);

			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			StreamResult console = new StreamResult(new FileOutputStream(file));
			transformer.transform(source, console);

			CreatorPanel.this.tree.updateUI();
			JOptionPane.showMessageDialog(CreatorPanel.this,
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

							menu = MenuFactory.getIntance(CreatorPanel.this, selectedNode)
									.verificarMenuDeSelecaoCreatorNewModel(selectedNode.getTypeFeature());

							menu.show(tree, event.getPoint().x, event.getPoint().y);

						} else {
							System.out.println("NULL");
							selectedNode = null;
						}

					}
				}
			}
		};
	}

	public boolean isFeatureGroup(FeatureTreeNode node) {
		if (node instanceof FeatureGroup) {
			txtMessageText.setText("Feature Group can not be selected. Please, select its parent feature: \""
					+ ((FeatureTreeNode) selectedNode.getParent()).getName() + "\".");
			return true;
		}

		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	
		if (e.getActionCommand().equals("addOptionalFeature")) {
		
			
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
		
		} else if (e.getActionCommand().equals("addSolitaire")) {
		
			String nome = JOptionPane.showInputDialog("Type the feature name:");
			
			if(nome==null || nome.equals("")){
				JOptionPane.showMessageDialog(null, "Type a valid feature name.");
				return;
			}
			
			controllerFeatures.addFeatures(selectedNode, TypeFeature.GROUPED_FEATURE , nome);
			
			tree.updateUI();
			
		} else {
			
			controllerFeatures.removeFeatures(selectedNode);

			tree.updateUI();

		}
	}
}