package br.ufc.lps.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;

import br.ufc.lps.contextaware.Constraint;
import br.ufc.lps.contextaware.Context;
import br.ufc.lps.gui.list.ConstraintsListModel;
import br.ufc.lps.gui.tree.FeaturesTreeCellRenderer;
import br.ufc.lps.model.context.ContextModel;
import br.ufc.lps.splar.core.constraints.PropositionalFormula;
import br.ufc.lps.splar.core.fm.FeatureModel;

public class ViewerPanel extends JPanel {

	private JLabel lblResultReasoning;
	private JTree tree;
	private JComboBox comboBoxContexts;
	private ContextModel model;
	private String modelName;
	private TextArea constraintsPanel;
	
	public ViewerPanel(final ContextModel model) {
		
		this.model = model;
		
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));
		
		JPanel resultReasoningPanel = new JPanel();
		add(resultReasoningPanel, BorderLayout.SOUTH);
		resultReasoningPanel.setLayout(new GridLayout(0, 1, 0, 0));
	
		JLabel lblResult = new JLabel("Result:");
		resultReasoningPanel.add(lblResult);
		
		lblResultReasoning = new JLabel("Choose one measure");
		resultReasoningPanel.add(lblResultReasoning);
		
		tree = new JTree();
		JScrollPane scrollPane = new JScrollPane(tree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane, BorderLayout.CENTER);
		
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
	
	/**
	 * @return the lblResultReasoning
	 */
	
	public void updateConstraintsPainel(Context context){
		FeatureModel featureModel = model.setFeatureModel(context);
		
		Collection<PropositionalFormula> constraints = featureModel.getConstraints();	
		
		String formulas = "";
		for (Iterator<PropositionalFormula> i = constraints.iterator(); i.hasNext(); ) {
			PropositionalFormula constraint = i.next();
			formulas += constraint.getFormula() + "\n";
		}
		
		constraintsPanel.setText(formulas);
	}
	
	public JLabel getLblResultReasoning() {
		return lblResultReasoning;
	}

	/**
	 * @return the model
	 */
	public ContextModel getModel() {
		return model;
	}

	/**
	 * @return the modelName
	 */
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
	}

}
