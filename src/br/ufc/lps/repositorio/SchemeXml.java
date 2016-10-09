package br.ufc.lps.repositorio;

import java.io.File;
import java.util.List;

import com.google.gson.annotations.Expose;

public class SchemeXml {
	
	@Expose
	private String nameXml;
	@Expose
	private String xml;
	@Expose
	private String _id;
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
	private Integer variableCrosstreeConstraints;//
	@Expose
	private Double compoundComplexity;
	@Expose
	private Integer numberOfGroupingFeatures;
	@Expose
	private Double crossTreeConstraintsRate;//
	@Expose
	private Double coeficientOfConnectivityDensity;
	@Expose
	private Integer numberOfVariableFeatures;
	@Expose
	private Integer singleVariationPointsFeatures;//
	@Expose
	private Integer multipleVariationPointsFeatures;//
	@Expose
	private Integer rigidNoVariationPointsFeatures;//
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
	private Integer numberOfContexts;
	@Expose
	private List<MedidasContexto> medidasContexto;
	
	private File file;
	
	public String getNameXml() {
		return nameXml;
	}

	public void setNameXml(String nameXml) {
		this.nameXml = nameXml;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public File getFile() {
		return file;
	}

	public Integer getNumberOfFeatures() {
		return numberOfFeatures;
	}

	public void setNumberOfFeatures(Integer numberOfFeatures) {
		this.numberOfFeatures = numberOfFeatures;
	}

	public void setFile(File file) {
		this.file = file;
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

	public Integer getNumberOfContexts() {
		return numberOfContexts;
	}

	public void setNumberOfContexts(Integer numberOfContexts) {
		this.numberOfContexts = numberOfContexts;
	}

	public List<MedidasContexto> getMedidasContexto() {
		return medidasContexto;
	}

	public void setMedidasContexto(List<MedidasContexto> medidasContexto) {
		this.medidasContexto = medidasContexto;
	}

	@Override
	public String toString() {
		return "SchemeXml [numberOfFeatures=" + numberOfFeatures + ", numberOfOptionalFeatures="
				+ numberOfOptionalFeatures + ", numberOfMandatoryFeatures=" + numberOfMandatoryFeatures
				+ ", numberOfTopFeatures=" + numberOfTopFeatures + ", numberOfLeafFeatures=" + numberOfLeafFeatures
				+ ", depthOfTreeMax=" + depthOfTreeMax + ", depthOfTreeMedian=" + depthOfTreeMedian
				+ ", cognitiveComplexityOfAFeatureModel=" + cognitiveComplexityOfAFeatureModel
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
				+ nonFunctionalCommonality + ", numberOfContexts=" + numberOfContexts + ", medidasContexto="
				+ medidasContexto + ", file=" + file + "]";
	}	
	
	
	
}
