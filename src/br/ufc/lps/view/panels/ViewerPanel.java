package br.ufc.lps.view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import br.ufc.lps.controller.xml.ControladorXml;
import br.ufc.lps.model.adaptation.ContextoAdaptacao;
import br.ufc.lps.model.context.ContextModel;
import br.ufc.lps.model.contextaware.Context;
import br.ufc.lps.model.rnf.Caracteristica;
import br.ufc.lps.model.rnf.ContextoRnf;
import br.ufc.lps.model.rnf.NameImpacto;
import br.ufc.lps.model.rnf.PropriedadeNFuncional;
import br.ufc.lps.model.rnf.Rnf;
import br.ufc.lps.model.rnf.Subcaracteristica;
import br.ufc.lps.model.rnf.ValorContextoRnf;
import br.ufc.lps.repository.SchemeXml;
import br.ufc.lps.splar.core.constraints.BooleanVariable;
import br.ufc.lps.splar.core.constraints.PropositionalFormula;
import br.ufc.lps.splar.core.fm.FeatureModel;
import br.ufc.lps.splar.core.fm.FeatureTreeNode;
import br.ufc.lps.view.Main;
import br.ufc.lps.view.list.CellRenderList;
import br.ufc.lps.view.list.ConstraintsListModelAdaptations;
import br.ufc.lps.view.trees.FeaturesRnfTreeCellRenderer;
import br.ufc.lps.view.trees.FeaturesTreeCellRenderer;
import br.ufc.lps.view.trees.FeaturesTreePrefuseControl;
import br.ufc.lps.view.trees.FeaturesTreeViewPerfuse;
import br.ufc.lps.view.trees.adaptation.Adaptacao;
import br.ufc.lps.view.trees.adaptation.CheckBoxNodeData;
import br.ufc.lps.view.trees.adaptation.CheckBoxNodeEditor;
import br.ufc.lps.view.trees.adaptation.CheckBoxNodeRenderer;
import br.ufc.lps.view.trees.adaptation.ValorAdaptacao;
import br.ufc.lps.view.trees.rnf.Characteristic;
import br.ufc.lps.view.trees.rnf.NFP;
import br.ufc.lps.view.trees.rnf.SubCharacteristic;
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
	private TextArea constraintsPanelRnf;
	private Main main;
	private Tree treeP;
	private JList<br.ufc.lps.model.adaptation.Adaptacao> listaPossibilidadesAdaptacoes;
	private ConstraintsListModelAdaptations listModelAdaptations;
	private JScrollPane scrollPane;
	private JPanel panelBotoesLayoutTree;
	private JTree treeAdaptation;
	private JTree treeRnf;
	private JPanel panelTreePerfuse;
   	private Table table;
    private FeaturesTreeViewPerfuse tview;
    private JPanel panelSelecionada;
    private JPanel panelTree;
    private Map<String, ContextoRnf> contextoRnf;
    private boolean primeira = true;
    private boolean rnfVazio = true;
    private boolean adaptationVazio = true;
    private DefaultTreeModel treeModel;
    private DefaultTreeModel treeModelRnf;
    List<br.ufc.lps.model.adaptation.Adaptacao> adaptacoesDisponiveis;
	
	private void inicializarTreePerfuse(){
		this.treeP = new Tree();
		this.table = this.treeP.getNodeTable();
		
		table.addColumn("name", String.class);
		table.addColumn("image", ImageIcon.class);
		table.addColumn("id", String.class);
	}
   	
 	public ViewerPanel(final ContextModel model, File file, SchemeXml schemeXml, Main main, boolean preview) {
		
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
		listaPossibilidadesAdaptacoes = new JList<br.ufc.lps.model.adaptation.Adaptacao>();
		
		resultReasoningPanel.add(lblResultReasoning);
		
		//LISTA DE POSSIBILIDADES DE ADAPTAÇÔES
		adaptacoesDisponiveis = new ArrayList<>();
		for ( Map.Entry<String, br.ufc.lps.model.adaptation.Adaptacao> entry : model.getAdaptacoes().entrySet()) {
		    String key = entry.getKey();
		    br.ufc.lps.model.adaptation.Adaptacao value = entry.getValue();
		    adaptacoesDisponiveis.add(value);
		}

		listModelAdaptations = new ConstraintsListModelAdaptations(adaptacoesDisponiveis);
		
		listaPossibilidadesAdaptacoes = new JList<br.ufc.lps.model.adaptation.Adaptacao>(listModelAdaptations);
		listaPossibilidadesAdaptacoes.setCellRenderer(new CellRenderList());
		listaPossibilidadesAdaptacoes.addMouseListener(getMouseListener());
		
		JScrollPane scrollPaneListaAdaptations = new JScrollPane(listaPossibilidadesAdaptacoes, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		JPanel panelListaAdaptations = new JPanel(new BorderLayout());
		
		panelListaAdaptations.add(new JLabel("Adaptations Scenarios"), BorderLayout.NORTH);
		panelListaAdaptations.add(scrollPaneListaAdaptations, BorderLayout.CENTER);
		
		//PAINEL PARA ADICIONAR INFORMAÇÕES DA ARVORES
		JPanel panelTrees = new JPanel();
		panelTrees.setLayout(new BorderLayout(0, 0));
		add(panelTrees, BorderLayout.CENTER);
		
		//ARVORE RNF
		treeRnf = new JTree();
		treeRnf.setEditable(true);
		preenchendoArvoreRnf(model.getArvoreRnf());
		contextoRnf = model.getContextoRnf();
		
		//ARVORE DA ADAPTAÇÃO
		treeAdaptation = new JTree();
		preenchendoArvore(model.getArvoreAdaptacao(), false);
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
	
		tree = new JTree();
		
		//Painel para a arvore;
			panelTree = new JPanel();
			panelTree.setLayout(new GridLayout(1, 0, 2, 2));
		
		JPanel painelTreeFeatures = new JPanel(new BorderLayout());
		painelTreeFeatures.add(new JLabel("Feature Model"), BorderLayout.NORTH);
		painelTreeFeatures.add(tree, BorderLayout.CENTER);
		panelTree.add(painelTreeFeatures);
		
		if(!rnfVazio){
			JPanel painelTreeFeaturesRnf = new JPanel(new BorderLayout());
			painelTreeFeaturesRnf.add(new JLabel("Quality Feature Model"), BorderLayout.NORTH);
			painelTreeFeaturesRnf.add(treeRnf, BorderLayout.CENTER);
			panelTree.add(painelTreeFeaturesRnf);
		}
		
		if(!adaptationVazio){
			JPanel painelTreeFeaturesAdap = new JPanel(new BorderLayout());
			painelTreeFeaturesAdap.add(new JLabel("Adaptaion Context Feature"), BorderLayout.NORTH);
			painelTreeFeaturesAdap.add(treeAdaptation, BorderLayout.CENTER);
			panelTree.add(painelTreeFeaturesAdap);
		}
		
		scrollPane = new JScrollPane(panelTree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panelTrees.add(scrollPane, BorderLayout.CENTER);
		
		//PAINEL ONDE FICARÀ O BOTÃO PARA MODIFICAR TIPO DE ÁRVORE
		JPanel panelBotoesTree = new JPanel();
		panelBotoesTree.setLayout(new GridLayout(1, 0));
		panelTrees.add(panelBotoesTree, BorderLayout.NORTH);
		
		panelBotoesLayoutTree = new JPanel();
		panelBotoesTree.setLayout(new GridLayout(1, 0));
		
		JButton butaoMudarLayoutM = new JButton("Change type of tree");
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
		
		JLabel textoMudancaLayout = new JLabel("Change Layout: ");
		
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
		add(panelInfos, BorderLayout.AFTER_LINE_ENDS);
		panelInfos.setLayout(new BorderLayout(0, 0));
		
		JPanel panelInfoContexts = new JPanel();
		panelInfos.add(panelInfoContexts, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Contexts");
		//panelInfoContexts.add(lblNewLabel);
		
//		JPanel painelTreeAdaptation = new JPanel(new BorderLayout());
//		painelTreeAdaptation.add(treeAdaptation);
//		JLabel tituloTree = new JLabel("Tree Adaptation");
//		painelTreeAdaptation.add(tituloTree, BorderLayout.NORTH);
//		
//		JScrollPane scrollPane = new JScrollPane(painelTreeAdaptation, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
//				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//		
		//panelInfoContexts.add(scrollPane);
		
		JPanel panelInfoConstraints = new JPanel(new GridLayout(0, 1));
		
		if(adaptacoesDisponiveis.size() > 0)
			panelInfos.add(panelListaAdaptations, BorderLayout.CENTER);
		
		panelInfos.add(panelInfoConstraints, BorderLayout.SOUTH);
		
		JPanel panelConstraintsPanel = new JPanel(new BorderLayout());
		constraintsPanel = new TextArea();
		constraintsPanel.setEditable(false);
		panelConstraintsPanel.add(new JLabel("Constraints: "), BorderLayout.NORTH);
		panelConstraintsPanel.add(constraintsPanel, BorderLayout.CENTER);
		panelInfoConstraints.add(panelConstraintsPanel);
		
		JPanel panelConstraintsPanelRnf = new JPanel(new BorderLayout());
		constraintsPanelRnf = new TextArea();
		constraintsPanelRnf.setEditable(false);
		panelConstraintsPanelRnf.add(new JLabel("Constraints RNF: "), BorderLayout.NORTH);
		panelConstraintsPanelRnf.add(constraintsPanelRnf, BorderLayout.CENTER);
		panelInfoConstraints.add(panelConstraintsPanelRnf);
		
		comboBoxContexts = new JComboBox();
		for(String contextNames : model.getContexts().keySet()){
			comboBoxContexts.addItem(contextNames);
		}
		//panelInfoContexts.add(comboBoxContexts);
		
		if(!preview){
			if(schemeXml==null){
				JButton botaoSalvar = new JButton("Save");
				
				panelInfoContexts.add(botaoSalvar);
				
				botaoSalvar.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						
						String nome = "";
						
						if(schemeXml!=null)
							nome  = JOptionPane.showInputDialog("Type the new model name", schemeXml.getNameXml());
						else
							nome  = JOptionPane.showInputDialog("Type the new model name", model.getModelName());
								
						if(nome.equals("")){
							JOptionPane.showMessageDialog(null, "You need at least one letter to the name");
							return;
						}
						
						boolean resultado = ControladorXml.salvarXMLRepositorio(nome, file, null);
						if(resultado){
							botaoSalvar.setEnabled(false);
							JOptionPane.showMessageDialog(null, "Saved successfully!");
							ViewerPanel.this.main.recarregarListaFeatures();
						}
					}
				});
			}
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
		//String contextName = (String) comboBoxContexts.getItemAt(0);
		setTreeVisualization(ContextModel.DEFAULT_CONTEXT);
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

				if (event.getButton() == MouseEvent.BUTTON1) {

					 if (event.getSource() == listaPossibilidadesAdaptacoes) {

						int selectedConstraintIndex = listaPossibilidadesAdaptacoes.locationToIndex(event.getPoint());
						listaPossibilidadesAdaptacoes.setSelectedIndex(selectedConstraintIndex);
						System.out.println((selectedConstraintIndex));
						System.out.println(adaptacoesDisponiveis.get(selectedConstraintIndex).getNome());
						//ViewerPanel.this.preenchendoArvore(adaptacoesDisponiveis.get(selectedConstraintIndex), true);
						ViewerPanel.this.alterandoArvore(adaptacoesDisponiveis.get(selectedConstraintIndex));
						
					}
				}
			}
		};
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
		
		String nome = verificandoArvore((DefaultMutableTreeNode)treeModel.getRoot());
		System.out.println("MUDANDO ÁRVORE: contexto:"+nome);
		if(nome!=null)
			setTreeVisualization(nome);
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
	
	public JLabel getLblResultReasoning() {
		return lblResultReasoning;
	}

	public ContextModel getModel() {
		return model;
	}

	public String getModelName() {
		return modelName;
	}
 
	private void setTreeVisualization(String contextName) {
		
		if(model.getContexts().isEmpty()){
			JOptionPane.showMessageDialog(ViewerPanel.this, "It's not supported, because it does not have any context. Please, first edit it and add one context.");
			tree.setModel(null);
			return;
		}	
		
		if(!model.getContexts().containsKey(contextName)){
			System.out.println("não contain");
			contextName = "default";
			constraintsPanelRnf.setText("");
		}
		
		Context context = model.getContexts().get(contextName);
		FeatureModel featureModel = model.setFeatureModel(context);
		modelName = featureModel.getName();
		adicionarConstraintsDoContexto(context);
		
		tree.setModel(featureModel);
		tree.setCellRenderer(new FeaturesTreeCellRenderer(context));
		main.expandAllNodes(tree, 0, tree.getRowCount());
		
		inicializarTreePerfuse();
		
		FeaturesTreePrefuseControl fpc = new FeaturesTreePrefuseControl();
		fpc.getTree(context, treeP, null ,model.getFeatureModel().getRoot());
		
		if(contextoRnf.containsKey(contextName))
			preenchendoContextosRnf(contextoRnf.get(contextName));
		else{
			constraintsPanelRnf.setText("");
		}
		
//		Collection<BooleanVariable> variables = new ArrayList<BooleanVariable>();
//		Collection<PropositionalFormula> formulas = featureModel.getConstraints();
//		
//		for(Iterator<PropositionalFormula> it = formulas.iterator(); it.hasNext() ; ) {
//			PropositionalFormula formula = it.next();
//			variables = formula.getVariables();
//			
//			//System.out.println("-------------");
//			
//			java.util.List<String> a = new ArrayList();
//			
//			for(Iterator<BooleanVariable> it2 = variables.iterator(); it2.hasNext() ; ) {
//				BooleanVariable variable = it2.next();
//				
//				a.add(variable.getID());
//		
//				
//		
//				/*	
//				if(featureModel.getNodeByID(variable.getName()).getValue() != 0){
//					if(!FeatureTreeNode.isActiveHierarchy(featureModel.getNodeByID(variable.getName()))){
//						continue;
//					}
//					
//					if(variable.getState() == false){
//						constraints+="~";
//					}
//
//					constraints += featureModel.getNodeByID(variable.getName()).getName();
//					if(it2.hasNext())
//						constraints += " or ";
//				}
//				*/
//			}
//			//fpc.bindRestrict(this.treeP, a);
//			//System.out.println("--------------");
//		}
//		
//		//for (Iterator iterator = fpc.getLista().keySet().iterator(); iterator.hasNext();) {
//			//String type = (String) iterator.next();
//			//System.out.println("adicionado: "+type);
//	//	}
		
	
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
	}
	
private void adicionarConstraintsDoContexto(Context contexto){
		
		constraintsPanel.setText("");
		
		updateConstraintsPainel(contexto);
		
		String nome = "";
		try{
			for(br.ufc.lps.model.contextaware.Constraint a : contexto.getConstraints()){
				System.out.println(a.getClause().trim());
				
				String [] formulas = a.getClause().trim().split("or");
				
				
				for(int i=0; i < formulas.length; i++){

					if(formulas[i].trim().startsWith("~")){
						nome += "~"+contexto.getFeatureModel().getNodeByID(formulas[i].replace("~", "").trim()).getName(); 
					}else{
						nome += contexto.getFeatureModel().getNodeByID(formulas[i].trim()).getName(); 
					}
					
					if(i+2 == formulas.length)
						nome += " or ";
				}
							nome +="\n";
			}
			constraintsPanel.setText(constraintsPanel.getText()+nome);
		}catch(Exception e){
			e.printStackTrace();
		}
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
	
	private void preenchendoArvore(br.ufc.lps.model.adaptation.Adaptacao adaptacao, boolean selecteds){
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Context adaptations");
		
		if(adaptacao!=null && adaptacao.getValorAdaptacao()!=null){
			
			for(ContextoAdaptacao contextoAdaptacao : adaptacao.getValorAdaptacao()){
				Adaptacao contexto = new Adaptacao(contextoAdaptacao.getNome());
				
				for(br.ufc.lps.model.adaptation.ValorAdaptacao valorAdaptacao : contextoAdaptacao.getValorAdaptacao()){
					CheckBoxNodeData data = null;
					
					if(!selecteds){
						data = new CheckBoxNodeData(valorAdaptacao.getNome(), false);
					}else{
						data = new CheckBoxNodeData(valorAdaptacao.getNome(), valorAdaptacao.getStatus());
					}
					
						contexto.add(new br.ufc.lps.view.trees.adaptation.ValorAdaptacao(data));
					
				}
				adaptationVazio = false;
				root.add(contexto);
			}
		}
		treeModel = new DefaultTreeModel(root);
		treeAdaptation = new JTree(treeModel);
		treeAdaptation.setModel(treeModel);
		treeAdaptation.setRootVisible(false);
		treeAdaptation.updateUI();
		expandAllNodes(treeAdaptation, 0, treeAdaptation.getRowCount());
	}
	
	private void alterandoArvore(br.ufc.lps.model.adaptation.Adaptacao adaptacao){
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Context adaptations");
		
		if(adaptacao!=null && adaptacao.getValorAdaptacao()!=null){
			
			for(ContextoAdaptacao contextoAdaptacao : adaptacao.getValorAdaptacao()){
				Adaptacao contexto = new Adaptacao(contextoAdaptacao.getNome());
				
				for(br.ufc.lps.model.adaptation.ValorAdaptacao valorAdaptacao : contextoAdaptacao.getValorAdaptacao()){
					CheckBoxNodeData data = null;
					
					data = new CheckBoxNodeData(valorAdaptacao.getNome(), valorAdaptacao.getStatus());
					contexto.add(new br.ufc.lps.view.trees.adaptation.ValorAdaptacao(data));
					
				}
				root.add(contexto);
			}
		}
		treeModel.setRoot(root);
		treeAdaptation.updateUI();
		
		expandAllNodes(treeAdaptation, 0, treeAdaptation.getRowCount());
		mudancaCheckBoxArvoreAdaptacao();
	}
	
	private void preenchendoArvoreRnf(Rnf rnf){
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Rnf");
		
		if(rnf!=null && rnf.getCaracteristicas()!=null){

			for(Caracteristica caracteristica : rnf.getCaracteristicas()){
				Characteristic carac = new Characteristic(caracteristica.getNome());
				
				for(Subcaracteristica subcaracteristica : caracteristica.getSubcaracteristicas()){
					SubCharacteristic sub = new SubCharacteristic(subcaracteristica.getNome());
					carac.add(sub);
					
					for(PropriedadeNFuncional propriedadeNFuncional : subcaracteristica.getPropriedadeNFuncionais()){
						NFP pnf = new NFP(propriedadeNFuncional.getPropriedade(), propriedadeNFuncional.getPadrao());
						sub.add(pnf);
					}
				}
				rnfVazio = false;
				root.add(carac);
			}
			
		}
		
		treeModelRnf = new DefaultTreeModel(root);
		treeRnf = new JTree(treeModelRnf);
		treeRnf.setCellRenderer(new FeaturesRnfTreeCellRenderer());
		treeRnf.setModel(treeModelRnf);
		treeRnf.setRootVisible(false);
		treeRnf.updateUI();
		expandAllNodes(treeRnf, 0, treeRnf.getRowCount());
	}	
	
	private void expandAllNodes(JTree tree, int startingIndex, int rowCount){
	    for(int i=startingIndex;i<rowCount;++i){
	        tree.expandRow(i);
	    }

	    if(tree.getRowCount()!=rowCount){
	        expandAllNodes(tree, rowCount, tree.getRowCount());
	    }
	}
	
	private void preenchendoContextosRnf(ContextoRnf contextoRnf){
		constraintsPanelRnf.setText("");
		for(ValorContextoRnf con : contextoRnf.getValorContextoRnf())
			constraintsPanelRnf.setText(con.getNomeFeature() + " " + NameImpacto.getNameByImpacto(con.getImpacto()) + " " + con.getIdRnf() + "\n" +constraintsPanelRnf.getText());
		
	}

}
