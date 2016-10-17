package br.ufc.lps.model.normal;

import br.ufc.lps.splar.core.fm.FeatureModel;

public interface IMeasuresWithoutContext {
	
	public FeatureModel getFeatureModel();

	public String getModelName();

	public double nonFunctionCommonality();

	public int numberOfFeatures();

	public int numberOfTopFeatures();

	public int numberOfLeafFeatures();

	public int depthOfTreeMax();

	public double depthOfTreeMedian();

	public int cognitiveComplexityOfFeatureModel();

	public int featureExtendibility();

	public double flexibilityOfConfiguration();

	public int singleCyclicDependentFeatures();

	public int multipleCyclicDependentFeatures();

	public int cyclomaticComplexity();

	public double compoundComplexity();

	public int crossTreeConstraints();

	public double coefficientOfConnectivityDensity();

	public int numberOfVariableFeatures();

	public int numberOfVariationPoints();

	public int singleVariationPointsFeatures();

	public int multipleVariationPointsFeatures();

	public int rigidNotVariationPointsFeatures();

	public double ratioVariability();

	public double numberOfValidConfigurations();

	public double branchingFactorsMean();

	public int branchingFactorsMax();

	public double branchingFactorsMedian();

	public double orRate();

	public double xorRate();

	public double crossTreeConstraintsRate();

	public double connectivityDependencyGraphRate();

	public double numberFeaturesReferencedConstraintsMean();

	public int numberOfGroupsOR();

	public int numberOfGroupsXOR();

	public int numberOfOptionalFeatures();

	public int numberOfMandatoryFeatures();

	public int numberOfAlternativeFeatures();

	public int groupingFeatures();

	public double depthOfTreeMean();

	public double ratioSwitchFeatures();

	public int crossTreeConstraintsVariables();
	

}
