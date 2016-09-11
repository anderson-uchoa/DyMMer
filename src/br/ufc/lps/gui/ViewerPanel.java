package br.ufc.lps.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import br.ufc.lps.contextaware.Context;
import br.ufc.lps.gui.charts.TreeView;
import br.ufc.lps.gui.tree.FeaturesTreeCellRenderer;
import br.ufc.lps.model.context.ContextModel;
import br.ufc.lps.repositorio.ControladorXml;
import br.ufc.lps.repositorio.SchemeXml;
import br.ufc.lps.splar.core.constraints.BooleanVariable;
import br.ufc.lps.splar.core.constraints.PropositionalFormula;
import br.ufc.lps.splar.core.fm.FeatureModel;
import br.ufc.lps.splar.core.fm.FeatureTreeNode;
import prefuse.Constants;
import prefuse.Visualization;
import prefuse.controls.ControlAdapter;
import prefuse.data.Node;
import prefuse.data.Table;
import prefuse.data.Tree;
import prefuse.data.parser.DataParseException;
import prefuse.data.parser.DataParser;
import prefuse.data.parser.ParserFactory;
import prefuse.util.FontLib;
import prefuse.util.ui.JFastLabel;
import prefuse.util.ui.JSearchPanel;
import prefuse.visual.VisualItem;

public class ViewerPanel extends JPanel {

	private JLabel lblResultReasoning;
	private JTree tree;
	private JComboBox comboBoxContexts;
	private ContextModel model;
	private String modelName;
	private TextArea constraintsPanel;
	private Main main;
	private Tree treeP;
	private JScrollPane scrollPane;
    public static final String INT = "Int";
    public static final String INTEGER = "Integer";
    public static final String LONG = "Long";
    public static final String FLOAT = "Float";
    public static final String REAL = "Real";
    public static final String STRING = "String";
    public static final String DATE = "Date";
    public static final String CATEGORY = "Category";
   
   // prefuse-specific allowed types
   public static final String BOOLEAN = "Boolean";
   public static final String DOUBLE = "Double";
private Table table;
	 private ParserFactory m_pf = ParserFactory.getDefaultFactory();
	
 	public ViewerPanel(final ContextModel model, File file, SchemeXml schemeXml, Main main) {
		
		this.model = model;
		this.main = main;
		this.treeP = new Tree();
		this.table = this.treeP.getNodeTable();
		Class t = parseType("String");
		
		table.addColumn("name", t);
		Class t2 = Boolean.class;
		
		table.addColumn("ativa", boolean.class);
		
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));
		
		JPanel resultReasoningPanel = new JPanel();
		add(resultReasoningPanel, BorderLayout.SOUTH);
		resultReasoningPanel.setLayout(new GridLayout(0, 1, 0, 0));
	
		JLabel lblResult = new JLabel("Result:");
		resultReasoningPanel.add(lblResult);
		
		lblResultReasoning = new JLabel("Choose one measure");
		resultReasoningPanel.add(lblResultReasoning);
		
		//PAINEL PARA ADICIONAR INFORMAÇÕES DA ARVORES
		JPanel panelTrees = new JPanel();
		panelTrees.setLayout(new BorderLayout(0, 0));
		add(panelTrees, BorderLayout.CENTER);
		
		tree = new JTree();
		scrollPane = new JScrollPane(tree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panelTrees.add(scrollPane, BorderLayout.CENTER);
		
		JPanel panelBotoesTree = new JPanel();
		panelBotoesTree.setLayout(new GridLayout(1, 0));
		panelTrees.add(panelBotoesTree, BorderLayout.NORTH);
		
		JButton butaoMudarLayout1 = new JButton(">");
		butaoMudarLayout1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tview.setOrientationAction(Constants.ORIENT_LEFT_RIGHT);
			}
		});
		
		JButton butaoMudarLayoutM = new JButton("Mudar tipo de arvore");
		butaoMudarLayoutM.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(scrollPane.getViewport().getView().equals(tree))
					scrollPane.getViewport().setView(tview);
				else
					scrollPane.getViewport().setView(tree);
			}
		});
		
		JButton butaoMudarLayout2 = new JButton("<");
		butaoMudarLayout2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tview.setOrientationAction(Constants.ORIENT_RIGHT_LEFT);
					
			}
		});
		
		JButton butaoMudarLayout3 = new JButton("v");
		butaoMudarLayout3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tview.setOrientationAction(Constants.ORIENT_TOP_BOTTOM);
			}
		});
		JButton butaoMudarLayout4 = new JButton("^");
		butaoMudarLayout4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tview.setOrientationAction(Constants.ORIENT_BOTTOM_TOP);
			}
		});
		panelBotoesTree.add(butaoMudarLayoutM);
		panelBotoesTree.add(butaoMudarLayout1);
		panelBotoesTree.add(butaoMudarLayout2);
		panelBotoesTree.add(butaoMudarLayout3);
		panelBotoesTree.add(butaoMudarLayout4);
		
		JPanel panelInfos = new JPanel();
		add(panelInfos, BorderLayout.EAST);
		panelInfos.setLayout(new BorderLayout(0, 0));
		
		JPanel panelInfoContexts = new JPanel();
		panelInfos.add(panelInfoContexts, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Contexts");
		panelInfoContexts.add(lblNewLabel);
		
		JPanel panelInfoConstraints = new JPanel();
		panelInfos.add(panelInfoConstraints, BorderLayout.SOUTH);
		constraintsPanel = new TextArea();
		constraintsPanel.setEditable(false);
		panelInfoConstraints.add(constraintsPanel);
		
		comboBoxContexts = new JComboBox();
		for(String contextNames : model.getContexts().keySet()){
			comboBoxContexts.addItem(contextNames);
		}
		panelInfoContexts.add(comboBoxContexts);
		
		if(schemeXml==null){
			JButton botaoSalvar = new JButton("Salvar");
			
			panelInfoContexts.add(botaoSalvar);
			
			botaoSalvar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					boolean resultado = ControladorXml.salvarXMLRepositorio(file, schemeXml);
					if(resultado){
						botaoSalvar.setEnabled(false);
						JOptionPane.showMessageDialog(null, "Salvo com sucesso!");
						ViewerPanel.this.main.recarregarListaFeatures();
					}
				}
			});
		}
		
		updateConstraintsPainel(model.getContexts().get(comboBoxContexts.getItemAt(0)));
		
		comboBoxContexts.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox comboBox = (JComboBox) e.getSource();
				final String contextName = (String) comboBox.getSelectedItem();
				if(contextName != null){
					setTreeVisualization(contextName);
					Context context = model.getContexts().get(contextName);
					updateConstraintsPainel(context);
				}
			}
		});
		String contextName = (String) comboBoxContexts.getItemAt(0);
		setTreeVisualization(contextName);
		this.main.expandAllNodes(tree, 0, tree.getRowCount());
		
	}
	
	public void updateConstraintsPainel(Context context){
		FeatureModel featureModel = context.getFeatureModel();
		String constraints = "";	
		Collection<BooleanVariable> variables = new ArrayList<BooleanVariable>();
		Collection<PropositionalFormula> formulas = featureModel.getConstraints();
		
		for(Iterator<PropositionalFormula> it = formulas.iterator(); it.hasNext() ; ) {
			PropositionalFormula formula = it.next();
			variables = formula.getVariables();
			
			for(Iterator<BooleanVariable> it2 = variables.iterator(); it2.hasNext() ; ) {
				BooleanVariable variable = it2.next();
				
				if(featureModel.getNodeByID(variable.getName()).getValue() != 0){
					if(!FeatureTreeNode.isActiveHierarchy(featureModel.getNodeByID(variable.getName()))){
						continue;
					}
					
					if(variable.getState() == false){
						constraints+="~";
					}

					constraints += featureModel.getNodeByID(variable.getName()).getName();
					if(it2.hasNext())
						constraints += " or ";
				}
			}
			constraints +="\n";
		}
		
		constraintsPanel.setText(constraints);
	}
	
	public JLabel getLblResultReasoning() {
		return lblResultReasoning;
	}

	public ContextModel getModel() {
		return model;
	}

	public String getModelName() {
		return modelName;
	}

	public FeatureTreeNode readAllNodes(Node pai, FeatureTreeNode a){
		
		if(pai==null)
			pai = this.treeP.addNode();
		
		if(a.getChildCount() > 0){
			for (int i = 0; i < a.getChildCount(); i++) {
				FeatureTreeNode f = (FeatureTreeNode) a.getChildAt(i); 
				Node node = this.treeP.addChild(pai);
				FeatureTreeNode fe = readAllNodes(node, f);
				
				if(fe!=null){
				Object val;
				try {
					val = parse(fe.getName(), this.table.getColumnType("name"));
					node.set("name", val);
					node.set("ativa", fe.isActiveInContext());
					
					//node.setString("tipo", fe.getName());
				} catch (DataParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				
			}
			return a;
		}else{
			return a;
		}
	}
	
	protected Class parseType(String type) {
        type = Character.toUpperCase(type.charAt(0)) +
               type.substring(1).toLowerCase();
        if ( type.equals(INT) || type.equals(INTEGER) ) {
            return int.class;
        } else if ( type.equals(LONG) ) {
            return long.class;
        } else if ( type.equals(FLOAT) ) {
            return float.class;
        } else if ( type.equals(DOUBLE) || type.equals(REAL)) {
            return double.class;
        } else if ( type.equals(BOOLEAN) ) {
            return boolean.class;
        } else if ( type.equals(STRING) ) {
            return String.class;
        } else if ( type.equals(DATE) ) {
            return Date.class;
        } else {
            throw new RuntimeException("Unrecognized data type: "+type);
        }
    }
    
	  protected Object parse(String s, Class type)
	            throws DataParseException
	        {
	            DataParser dp = m_pf.getParser(type);
	            return dp.parse(s);
	        }
	
	private void setTreeVisualization(final String contextName) {
		
		if(model.getContexts().isEmpty()){
			JOptionPane.showMessageDialog(ViewerPanel.this, "It's not supported, because it does not have any context. Please, first edit it and add one context.");
			tree.setModel(null);
			
			return;
		}	
		
		Context context = model.getContexts().get(contextName);
		FeatureModel featureModel = model.setFeatureModel(context);
		modelName = featureModel.getName();
		tree.setModel(featureModel);
		tree.setCellRenderer(new FeaturesTreeCellRenderer(context));
		readAllNodes(null,model.getFeatureModel().getRoot());
		
		JPanel a = panelTreePerfuse(this.treeP, "name");
		scrollPane.getViewport().setView(a);
	}
	
	private TreeView tview;
	
   public JPanel panelTreePerfuse(Tree t, final String label) {
        Color BACKGROUND = Color.WHITE;
        Color FOREGROUND = Color.BLACK;
            
        tview = new TreeView(t, label);
        
        tview.setBackground(BACKGROUND);
        tview.setForeground(FOREGROUND);
        
        // create a search panel for the tree map
        JSearchPanel search = new JSearchPanel(tview.getVisualization(),
            TreeView.treeNodes, Visualization.SEARCH_ITEMS, label, true, true);
        search.setShowResultCount(true);
        search.setBorder(BorderFactory.createEmptyBorder(5,5,4,0));
        search.setFont(FontLib.getFont("Tahoma", Font.PLAIN, 11));
        search.setBackground(BACKGROUND);
        search.setForeground(FOREGROUND);
        
        final JFastLabel title = new JFastLabel("                 ");
        title.setPreferredSize(new Dimension(350, 20));
        title.setVerticalAlignment(SwingConstants.BOTTOM);
        title.setBorder(BorderFactory.createEmptyBorder(3,0,0,0));
        title.setFont(FontLib.getFont("Tahoma", Font.PLAIN, 16));
        title.setBackground(BACKGROUND);
        title.setForeground(FOREGROUND);
        
        tview.addControlListener(new ControlAdapter() {
            public void itemEntered(VisualItem item, MouseEvent e) {
                if ( item.canGetString(label) )
                    title.setText(item.getString(label));
            }
            public void itemExited(VisualItem item, MouseEvent e) {
                title.setText(null);
            }
        });
        
        Box box = new Box(BoxLayout.X_AXIS);
        box.add(Box.createHorizontalStrut(10));
        box.add(title);
        box.add(Box.createHorizontalGlue());
        box.add(search);
        box.add(Box.createHorizontalStrut(3));
        box.setBackground(BACKGROUND);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND);
        panel.setForeground(FOREGROUND);
        panel.add(tview, BorderLayout.CENTER);
        panel.add(box, BorderLayout.SOUTH);
        return panel;
    }
	  

}
