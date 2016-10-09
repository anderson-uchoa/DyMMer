package br.ufc.lps.model.normal;

import br.ufc.lps.splar.core.fm.FeatureModel;

public interface IModel {

	public FeatureModel getFeatureModel();
	public String getModelName();
	
	
	
	double nonFunctionCommonality();
	int numberOfFeatures();
	int numberOfTopFeatures();
	int numberOfLeafFeatures();
	int depthOfTreeMax();
	double depthOfTreeMedian();
	int cognitiveComplexityOfFeatureModel();
	int featureExtendibility();
	double flexibilityOfConfiguration();
	int singleCyclicDependentFeatures();
	int multipleCyclicDependentFeatures();
	int cyclomaticComplexity();
	double compoundComplexity();
	int crossTreeConstraints();
	double coefficientOfConnectivityDensity();
	int numberOfVariableFeatures();
	int numberOfVariationPoints();
	int singleVariationPointsFeatures();
	int multipleVariationPointsFeatures();
	int rigidNotVariationPointsFeatures();
	double ratioVariability();
	double numberOfValidConfigurations();
	double branchingFactorsMean();
	int branchingFactorsMax();
	double branchingFactorsMedian();
	double orRate();
	double xorRate();
	double crossTreeConstraintsRate();
	double connectivityDependencyGraphRate();
	double numberFeaturesReferencedConstraintsMean();
	int numberOfGroupsOR();
	int numberOfGroupsXOR();
	int numberOfOptionalFeatures();
	int numberOfMandatoryFeatures();
	int numberOfAlternativeFeatures();
	int groupingFeatures();
	double depthOfTreeMean();
	double ratioSwitchFeatures();
	int crossTreeConstraintsVariables();
	
}
