package br.ufc.lps.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import br.ufc.lps.repositorio.MedidasContexto;
import br.ufc.lps.repositorio.SchemeXml;

public class ViewerPanelResulMeasures extends JPanel {
	
	private static String colunas[] = {"Name Context", "Number Of Features", "Number Of Optional Features", "Number Of Top Features", "Number Of Leaf Features",
			"Depth Of Tree Max", "Depth Of Tree Median", "Cognitive Complexity Of A Feature Model", "Flexibility Of Configuration", "Single Cyclic Dependent Features",
			"Multiple Cyclic Dependent Features", "Feature Extendibility", "Cyclomatic Complexity", "Variable Crosstree Constraints", "Compound Complexity", 
			"Number Of Grouping Features", "Cross Tree Constraints Rate", "Coeficient Of Connectivity Density", "Number Of Variable Features",
			"Single Variation Points Features", "Multiple Variation Points Features", "Rigid No Variation Points Features", "Ratio Of Variability",
			"Number Of Valid Configurations", "Branching Factors Max", "Or Rate", "Xor Rate", "Branching Factors Median", "Non Functional Commonality",
			"Number Of Activated Features", "Number Of Desactivated Features"};
	
	private DefaultTableModel mDefaultTableModel;
	private Main main;
	private JTable tabela;
	
	public ViewerPanelResulMeasures(final Main main, SchemeXml xml) {
		this.main = main;
		
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));
		
		JPanel painelTabela = new JPanel();
		add(painelTabela, BorderLayout.CENTER);
		painelTabela.setLayout(new GridLayout(0, 1, 0, 0));
		
		mDefaultTableModel = new DefaultTableModel(new String[][]{}, colunas);
		tabela = new JTable(mDefaultTableModel);
		JScrollPane barraRolagem = new JScrollPane(tabela, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		painelTabela.add(barraRolagem);
	
		carregarItens(xml);			
	}
	
	public void carregarItens(SchemeXml xml){
		if(xml.getMedidasContexto()!=null){
			if(xml.getMedidasContexto().size() > 0){
				for(MedidasContexto sc : xml.getMedidasContexto()){
					mDefaultTableModel.addRow(new String[]{sc.getNameContext(),
							sc.getNumberOfFeatures().toString(),
							sc.getNumberOfOptionalFeatures().toString(),
							sc.getNumberOfMandatoryFeatures().toString(),
							sc.getNumberOfTopFeatures().toString(),
							sc.getNumberOfLeafFeatures().toString(),
							sc.getDepthOfTreeMax().toString(),
							sc.getDepthOfTreeMedian().toString(),
							sc.getCognitiveComplexityOfAFeatureModel().toString(),
							sc.getFlexibilityOfConfiguration().toString(),
							sc.getSingleCyclicDependentFeatures().toString(),
							sc.getMultipleCyclicDependentFeatures().toString(),
							sc.getFeatureExtendibility().toString(),
							sc.getCyclomaticComplexity().toString(),
							sc.getVariableCrosstreeConstraints() == null ? "" : sc.getVariableCrosstreeConstraints().toString(),
							sc.getCompoundComplexity().toString(),
							sc.getNumberOfGroupingFeatures().toString(),
							sc.getCrossTreeConstraintsRate() == null ? "" : sc.getCrossTreeConstraintsRate().toString(),
							sc.getCoeficientOfConnectivityDensity().toString(),
							sc.getNumberOfVariableFeatures().toString(),
							sc.getSingleVariationPointsFeatures() == null ? "" : sc.getSingleVariationPointsFeatures().toString(),
							sc.getMultipleVariationPointsFeatures() == null ? "" : sc.getMultipleVariationPointsFeatures().toString(),
							sc.getRigidNoVariationPointsFeatures() == null ? "" : sc.getRigidNoVariationPointsFeatures().toString(),
							sc.getRatioOfVariability().toString(),
							sc.getNumberOfValidConfigurations().toString(),
							sc.getBranchingFactorsMax().toString(),
							sc.getOrRate().toString(),
							sc.getXorRate().toString(),
							sc.getBranchingFactorsMedian().toString(),
							sc.getNonFunctionalCommonality().toString(),
							sc.getNumberOfActivatedFeatures().toString(),
							sc.getNumberOfDeactivatedFeatures().toString(),
							sc.getNumberOfContextConstraints().toString(),
							sc.getActivatedFeaturesByContextAdaptation().toString(),
							sc.getDesactivatedFeaturesByContextAdaptation().toString(),
							sc.getNonContextFeatures().toString()});
				}
			}
		}else{
			JOptionPane.showMessageDialog(null, "Ocorreu algum problema na conexão");
		}
				
	}
}
