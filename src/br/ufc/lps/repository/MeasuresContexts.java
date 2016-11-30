package br.ufc.lps.repository;

import com.google.gson.annotations.Expose;

public class MeasuresContexts {
	
	@Expose
	private String nameContext;
	@Expose
	private Integer numberOfFeatures;
	@Expose
	private Integer numberOfOptionalFeatures;
	@Expose
	private Integer numberOfMandatoryFeatures;
	@Expose
	private Integer numberOfTopFeatures;
	@Expose
	private Integer numberOfLeafFeatures;
	@Expose
	private Integer depthOfTreeMax;
	@Expose
	private Double depthOfTreeMedian;
	@Expose
	private Integer cognitiveComplexityOfAFeatureModel;
	@Expose
	private Double flexibilityOfConfiguration;
	@Expose
	private Integer singleCyclicDependentFeatures;
	@Expose
	private Integer multipleCyclicDependentFeatures;
	@Expose
	private Integer featureExtendibility;
	@Expose
	private Integer cyclomaticComplexity;
	@Expose
	private Integer variableCrosstreeConstraints;
	@Expose
	private Double compoundComplexity;
	@Expose
	private Integer numberOfGroupingFeatures;
	@Expose
	private Double crossTreeConstraintsRate;
	@Expose
	private Double coeficientOfConnectivityDensity;
	@Expose
	private Integer numberOfVariableFeatures;
	@Expose
	private Integer singleVariationPointsFeatures;
	@Expose
	private Integer multipleVariationPointsFeatures;
	@Expose
	private Integer rigidNoVariationPointsFeatures;
	@Expose
	private Double numberOfValidConfigurations;
	@Expose
	private Integer branchingFactorsMax;
	@Expose
	private Double branchingFactorsMedian;
	@Expose
	private Double orRate;
	@Expose
	private Double xorRate;
	@Expose
	private Double ratioOfVariability;
	@Expose
	private Double nonFunctionalCommonality;
	@Expose
	private Integer numberOfActivatedFeatures;
	@Expose
	private Integer numberOfDeactivatedFeatures;
	@Expose
	private Integer numberOfContextConstraints;
	@Expose
	private Double activatedFeaturesByContextAdaptation;
	@Expose
	private Double desactivatedFeaturesByContextAdaptation;
	@Expose
	private Integer nonContextFeatures;
	@Expose
	private Integer contextFeaturesContraints;
	
	public String getNameContext() {
		return nameContext;
	}
	public void setNameContext(String nameContext) {
		this.nameContext = nameContext;
	}
	public Integer getContextFeaturesContraints() {
		return contextFeaturesContraints;
	}
	public void setContextFeaturesContraints(Integer contextFeaturesContraints) {
		this.contextFeaturesContraints = contextFeaturesContraints;
	}
	public Integer getNumberOfFeatures() {
		return numberOfFeatures;
	}
	public void setNumberOfFeatures(Integer numberOfFeatures) {
		this.numberOfFeatures = numberOfFeatures;
	}
	public Integer getNumberOfOptionalFeatures() {
		return numberOfOptionalFeatures;
	}
	public void setNumberOfOptionalFeatures(Integer numberOfOptionalFeatures) {
		this.numberOfOptionalFeatures = numberOfOptionalFeatures;
	}
	public Integer getNumberOfMandatoryFeatures() {
		return numberOfMandatoryFeatures;
	}
	public void setNumberOfMandatoryFeatures(Integer numberOfMandatoryFeatures) {
		this.numberOfMandatoryFeatures = numberOfMandatoryFeatures;
	}
	public Integer getNumberOfTopFeatures() {
		return numberOfTopFeatures;
	}
	public void setNumberOfTopFeatures(Integer numberOfTopFeatures) {
		this.numberOfTopFeatures = numberOfTopFeatures;
	}
	public Integer getNumberOfLeafFeatures() {
		return numberOfLeafFeatures;
	}
	public void setNumberOfLeafFeatures(Integer numberOfLeafFeatures) {
		this.numberOfLeafFeatures = numberOfLeafFeatures;
	}
	public Integer getDepthOfTreeMax() {
		return depthOfTreeMax;
	}
	public void setDepthOfTreeMax(Integer depthOfTreeMax) {
		this.depthOfTreeMax = depthOfTreeMax;
	}
	public Double getDepthOfTreeMedian() {
		return depthOfTreeMedian;
	}
	public void setDepthOfTreeMedian(Double depthOfTreeMedian) {
		this.depthOfTreeMedian = depthOfTreeMedian;
	}
	public Integer getCognitiveComplexityOfAFeatureModel() {
		return cognitiveComplexityOfAFeatureModel;
	}
	public void setCognitiveComplexityOfAFeatureModel(Integer cognitiveComplexityOfAFeatureModel) {
		this.cognitiveComplexityOfAFeatureModel = cognitiveComplexityOfAFeatureModel;
	}
	public Double getFlexibilityOfConfiguration() {
		return flexibilityOfConfiguration;
	}
	public void setFlexibilityOfConfiguration(Double flexibilityOfConfiguration) {
		this.flexibilityOfConfiguration = flexibilityOfConfiguration;
	}
	public Integer getSingleCyclicDependentFeatures() {
		return singleCyclicDependentFeatures;
	}
	public void setSingleCyclicDependentFeatures(Integer singleCyclicDependentFeatures) {
		this.singleCyclicDependentFeatures = singleCyclicDependentFeatures;
	}
	public Integer getMultipleCyclicDependentFeatures() {
		return multipleCyclicDependentFeatures;
	}
	public void setMultipleCyclicDependentFeatures(Integer multipleCyclicDependentFeatures) {
		this.multipleCyclicDependentFeatures = multipleCyclicDependentFeatures;
	}
	public Integer getFeatureExtendibility() {
		return featureExtendibility;
	}
	public void setFeatureExtendibility(Integer featureExtendibility) {
		this.featureExtendibility = featureExtendibility;
	}
	public Integer getCyclomaticComplexity() {
		return cyclomaticComplexity;
	}
	public void setCyclomaticComplexity(Integer cyclomaticComplexity) {
		this.cyclomaticComplexity = cyclomaticComplexity;
	}
	public Integer getVariableCrosstreeConstraints() {
		return variableCrosstreeConstraints;
	}
	public void setVariableCrosstreeConstraints(Integer variableCrosstreeConstraints) {
		this.variableCrosstreeConstraints = variableCrosstreeConstraints;
	}
	public Double getCompoundComplexity() {
		return compoundComplexity;
	}
	public void setCompoundComplexity(Double compoundComplexity) {
		this.compoundComplexity = compoundComplexity;
	}
	public Integer getNumberOfGroupingFeatures() {
		return numberOfGroupingFeatures;
	}
	public void setNumberOfGroupingFeatures(Integer numberOfGroupingFeatures) {
		this.numberOfGroupingFeatures = numberOfGroupingFeatures;
	}
	public Double getCrossTreeConstraintsRate() {
		return crossTreeConstraintsRate;
	}
	public void setCrossTreeConstraintsRate(Double crossTreeConstraintsRate) {
		this.crossTreeConstraintsRate = crossTreeConstraintsRate;
	}
	public Double getCoeficientOfConnectivityDensity() {
		return coeficientOfConnectivityDensity;
	}
	public void setCoeficientOfConnectivityDensity(Double coeficientOfConnectivityDensity) {
		this.coeficientOfConnectivityDensity = coeficientOfConnectivityDensity;
	}
	public Integer getNumberOfVariableFeatures() {
		return numberOfVariableFeatures;
	}
	public void setNumberOfVariableFeatures(Integer numberOfVariableFeatures) {
		this.numberOfVariableFeatures = numberOfVariableFeatures;
	}
	public Integer getSingleVariationPointsFeatures() {
		return singleVariationPointsFeatures;
	}
	public void setSingleVariationPointsFeatures(Integer singleVariationPointsFeatures) {
		this.singleVariationPointsFeatures = singleVariationPointsFeatures;
	}
	public Integer getMultipleVariationPointsFeatures() {
		return multipleVariationPointsFeatures;
	}
	public void setMultipleVariationPointsFeatures(Integer multipleVariationPointsFeatures) {
		this.multipleVariationPointsFeatures = multipleVariationPointsFeatures;
	}
	public Integer getRigidNoVariationPointsFeatures() {
		return rigidNoVariationPointsFeatures;
	}
	public void setRigidNoVariationPointsFeatures(Integer rigidNoVariationPointsFeatures) {
		this.rigidNoVariationPointsFeatures = rigidNoVariationPointsFeatures;
	}
	public Double getNumberOfValidConfigurations() {
		return numberOfValidConfigurations;
	}
	public void setNumberOfValidConfigurations(Double numberOfValidConfigurations) {
		this.numberOfValidConfigurations = numberOfValidConfigurations;
	}
	public Integer getBranchingFactorsMax() {
		return branchingFactorsMax;
	}
	public void setBranchingFactorsMax(Integer branchingFactorsMax) {
		this.branchingFactorsMax = branchingFactorsMax;
	}
	public Double getBranchingFactorsMedian() {
		return branchingFactorsMedian;
	}
	public void setBranchingFactorsMedian(Double branchingFactorsMedian) {
		this.branchingFactorsMedian = branchingFactorsMedian;
	}
	public Double getOrRate() {
		return orRate;
	}
	public void setOrRate(Double orRate) {
		this.orRate = orRate;
	}
	public Double getXorRate() {
		return xorRate;
	}
	public void setXorRate(Double xorRate) {
		this.xorRate = xorRate;
	}
	public Double getRatioOfVariability() {
		return ratioOfVariability;
	}
	public void setRatioOfVariability(Double ratioOfVariability) {
		this.ratioOfVariability = ratioOfVariability;
	}
	public Double getNonFunctionalCommonality() {
		return nonFunctionalCommonality;
	}
	public void setNonFunctionalCommonality(Double nonFunctionalCommonality) {
		this.nonFunctionalCommonality = nonFunctionalCommonality;
	}
	public Integer getNumberOfActivatedFeatures() {
		return numberOfActivatedFeatures;
	}
	public void setNumberOfActivatedFeatures(Integer numberOfActivatedFeatures) {
		this.numberOfActivatedFeatures = numberOfActivatedFeatures;
	}
	public Integer getNumberOfDeactivatedFeatures() {
		return numberOfDeactivatedFeatures;
	}
	public void setNumberOfDeactivatedFeatures(Integer numberOfDeactivatedFeatures) {
		this.numberOfDeactivatedFeatures = numberOfDeactivatedFeatures;
	}
	public Integer getNumberOfContextConstraints() {
		return numberOfContextConstraints;
	}
	public void setNumberOfContextConstraints(Integer numberOfContextConstraints) {
		this.numberOfContextConstraints = numberOfContextConstraints;
	}
	public Double getActivatedFeaturesByContextAdaptation() {
		return activatedFeaturesByContextAdaptation;
	}
	public void setActivatedFeaturesByContextAdaptation(Double activatedFeaturesByContextAdaptation) {
		this.activatedFeaturesByContextAdaptation = activatedFeaturesByContextAdaptation;
	}
	public Double getDesactivatedFeaturesByContextAdaptation() {
		return desactivatedFeaturesByContextAdaptation;
	}
	public void setDesactivatedFeaturesByContextAdaptation(Double desactivatedFeaturesByContextAdaptation) {
		this.desactivatedFeaturesByContextAdaptation = desactivatedFeaturesByContextAdaptation;
	}
	public Integer getNonContextFeatures() {
		return nonContextFeatures;
	}
	public void setNonContextFeatures(Integer nonContextFeatures) {
		this.nonContextFeatures = nonContextFeatures;
	}
	@Override
	public String toString() {
		return "MeasuresContexts [nameContext=" + nameContext + ", numberOfFeatures=" + numberOfFeatures
				+ ", numberOfOptionalFeatures=" + numberOfOptionalFeatures + ", numberOfMandatoryFeatures="
				+ numberOfMandatoryFeatures + ", numberOfTopFeatures=" + numberOfTopFeatures + ", numberOfLeafFeatures="
				+ numberOfLeafFeatures + ", depthOfTreeMax=" + depthOfTreeMax + ", depthOfTreeMedian="
				+ depthOfTreeMedian + ", cognitiveComplexityOfAFeatureModel=" + cognitiveComplexityOfAFeatureModel
				+ ", flexibilityOfConfiguration=" + flexibilityOfConfiguration + ", singleCyclicDependentFeatures="
				+ singleCyclicDependentFeatures + ", multipleCyclicDependentFeatures=" + multipleCyclicDependentFeatures
				+ ", featureExtendibility=" + featureExtendibility + ", cyclomaticComplexity=" + cyclomaticComplexity
				+ ", variableCrosstreeConstraints=" + variableCrosstreeConstraints + ", compoundComplexity="
				+ compoundComplexity + ", numberOfGroupingFeatures=" + numberOfGroupingFeatures
				+ ", crossTreeConstraintsRate=" + crossTreeConstraintsRate + ", coeficientOfConnectivityDensity="
				+ coeficientOfConnectivityDensity + ", numberOfVariableFeatures=" + numberOfVariableFeatures
				+ ", singleVariationPointsFeatures=" + singleVariationPointsFeatures
				+ ", multipleVariationPointsFeatures=" + multipleVariationPointsFeatures
				+ ", rigidNoVariationPointsFeatures=" + rigidNoVariationPointsFeatures
				+ ", numberOfValidConfigurations=" + numberOfValidConfigurations + ", branchingFactorsMax="
				+ branchingFactorsMax + ", branchingFactorsMedian=" + branchingFactorsMedian + ", orRate=" + orRate
				+ ", xorRate=" + xorRate + ", ratioOfVariability=" + ratioOfVariability + ", nonFunctionalCommonality="
				+ nonFunctionalCommonality + ", numberOfActivatedFeatures=" + numberOfActivatedFeatures
				+ ", numberOfDeactivatedFeatures=" + numberOfDeactivatedFeatures + ", numberOfContextConstraints="
				+ numberOfContextConstraints + ", activatedFeaturesByContextAdaptation="
				+ activatedFeaturesByContextAdaptation + ", desactivatedFeaturesByContextAdaptation="
				+ desactivatedFeaturesByContextAdaptation + ", nonContextFeatures=" + nonContextFeatures + "]";
	}
	
	
	
	
}
