package br.ufc.lps.view.panels;

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
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import br.ufc.lps.controller.xml.ControladorXml;
import br.ufc.lps.model.context.ContextModel;
import br.ufc.lps.model.context.MeeasuresWithContextCalculator;
import br.ufc.lps.model.contextaware.Context;
import br.ufc.lps.repositorio.SchemeXml;
import br.ufc.lps.splar.core.constraints.BooleanVariable;
import br.ufc.lps.splar.core.constraints.PropositionalFormula;
import br.ufc.lps.splar.core.fm.FeatureModel;
import br.ufc.lps.splar.core.fm.FeatureTreeNode;
import br.ufc.lps.view.Main;
import br.ufc.lps.view.trees.FeaturesTreeCellRenderer;
import br.ufc.lps.view.trees.FeaturesTreePerfuseControl;
import br.ufc.lps.view.trees.FeaturesTreeViewPerfuse;
import prefuse.Constants;
import prefuse.Visualization;
import prefuse.controls.ControlAdapter;
import prefuse.data.Table;
import prefuse.data.Tree;
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
	private JPanel panelBotoesLayoutTree;
	private JPanel panelTreePerfuse;
   	private Table table;
    private FeaturesTreeViewPerfuse tview;
    private JPanel panelSelecionada;
    private JPanel panelTree;
    private boolean primeira = true;
	
	private void inicializarTreePerfuse(){
		this.treeP = new Tree();
		this.table = this.treeP.getNodeTable();
		
		table.addColumn("name", String.class);
		table.addColumn("image", ImageIcon.class);
		table.addColumn("id", String.class);
	}
   	
 	public ViewerPanel(final ContextModel model, File file, SchemeXml schemeXml, Main main) {
		
		this.model = model;
		this.main = main;
		
		inicializarTreePerfuse();
		
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
		
		//Painel para a arvore;
		panelTree = new JPanel();
		panelTree.setLayout(new GridLayout(1,0));
		panelTree.add(tree);

		scrollPane = new JScrollPane(panelTree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panelTrees.add(scrollPane, BorderLayout.CENTER);
		
		//PAINEL ONDE FICARÀ O BOTÃO PARA MODIFICAR TIPO DE ÁRVORE
		JPanel panelBotoesTree = new JPanel();
		panelBotoesTree.setLayout(new GridLayout(1, 0));
		panelTrees.add(panelBotoesTree, BorderLayout.NORTH);
		
		panelBotoesLayoutTree = new JPanel();
		panelBotoesTree.setLayout(new GridLayout(1, 0));
		
		JButton butaoMudarLayoutM = new JButton("Mudar tipo de arvore");
		butaoMudarLayoutM.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(scrollPane.getViewport().getView().equals(panelTree)){
					scrollPane.getViewport().setView(panelTreePerfuse);
					panelSelecionada = panelTreePerfuse;
				}
				else{
					scrollPane.getViewport().setView(panelTree);
					panelSelecionada = panelTree;
				}
			}
		});
		
		JLabel textoMudancaLayout = new JLabel("Modificar Layout: ");
		
		JButton butaoMudarLayout1 = new JButton("Left Right");
		butaoMudarLayout1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tview.setOrientationAction(Constants.ORIENT_LEFT_RIGHT);
			}
		});
		
		JButton butaoMudarLayout2 = new JButton("Right Left");
		butaoMudarLayout2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tview.setOrientationAction(Constants.ORIENT_RIGHT_LEFT);
					
			}
		});
		
		JButton butaoMudarLayout3 = new JButton("Top Bottom");
		butaoMudarLayout3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tview.setOrientationAction(Constants.ORIENT_TOP_BOTTOM);
			}
		});
		JButton butaoMudarLayout4 = new JButton("Bottom Top");
		butaoMudarLayout4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tview.setOrientationAction(Constants.ORIENT_BOTTOM_TOP);
			}
		});
		panelBotoesTree.add(butaoMudarLayoutM);
		
		panelBotoesLayoutTree.add(textoMudancaLayout);
		panelBotoesLayoutTree.add(butaoMudarLayout1);
		panelBotoesLayoutTree.add(butaoMudarLayout2);
		panelBotoesLayoutTree.add(butaoMudarLayout3);
		panelBotoesLayoutTree.add(butaoMudarLayout4);
		
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
					
					String nome  = JOptionPane.showInputDialog("Digite o novo nome do modelo");
					
					if(nome.equals("")){
						JOptionPane.showMessageDialog(null, "É necessário pelo menos uma letra para o nome");
						return;
					}
					
					boolean resultado = ControladorXml.salvarXMLRepositorio(nome, file, null);
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
		main.expandAllNodes(tree, 0, tree.getRowCount());
		
		inicializarTreePerfuse();
		
		FeaturesTreePerfuseControl fpc = new FeaturesTreePerfuseControl();
		fpc.getTree(context, treeP, null ,model.getFeatureModel().getRoot());
		
		Collection<BooleanVariable> variables = new ArrayList<BooleanVariable>();
		Collection<PropositionalFormula> formulas = featureModel.getConstraints();
		
		for(Iterator<PropositionalFormula> it = formulas.iterator(); it.hasNext() ; ) {
			PropositionalFormula formula = it.next();
			variables = formula.getVariables();
			
			System.out.println("-------------");
			
			java.util.List<String> a = new ArrayList();
			
			for(Iterator<BooleanVariable> it2 = variables.iterator(); it2.hasNext() ; ) {
				BooleanVariable variable = it2.next();
				
				a.add(variable.getID());
		
				
		
				/*	
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
				*/
			}
			//fpc.bindRestrict(this.treeP, a);
			System.out.println("--------------");
		}
		
		//for (Iterator iterator = fpc.getLista().keySet().iterator(); iterator.hasNext();) {
			//String type = (String) iterator.next();
			//System.out.println("adicionado: "+type);
	//	}
		
	
		panelTreePerfuse = panelTreePerfuse(treeP, "name", "image");
		
		if(panelSelecionada!=null && !panelSelecionada.equals(panelTree))
			panelSelecionada = panelTreePerfuse;	
		else
			panelSelecionada = panelTree;
		
		if(!primeira)
			scrollPane.getViewport().setView(panelSelecionada);
		else{
			scrollPane.getViewport().setView(panelTreePerfuse);
			panelSelecionada = panelTreePerfuse;
		}
		primeira = false;
		//scrollPane.repaint();
	}
	
	public JPanel panelTreePerfuse(Tree t, String label, String image) {
        Color BACKGROUND = Color.WHITE;
        Color FOREGROUND = Color.BLACK;
            
        tview = new FeaturesTreeViewPerfuse(t, label, image);
        
        tview.setBackground(BACKGROUND);
        tview.setForeground(FOREGROUND);
        
        // create a search panel for the tree map
        JSearchPanel search = new JSearchPanel(tview.getVisualization(),
            FeaturesTreeViewPerfuse.treeNodes, Visualization.SEARCH_ITEMS, label, true, true);
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
        panel.add(panelBotoesLayoutTree, BorderLayout.NORTH);
        return panel;
    }
	  

}
