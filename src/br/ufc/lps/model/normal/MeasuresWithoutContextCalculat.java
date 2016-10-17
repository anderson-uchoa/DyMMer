package br.ufc.lps.model.normal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import br.ufc.lps.model.ModelFactory;
import br.ufc.lps.splar.core.fm.FeatureModel;
import br.ufc.lps.splar.core.fm.FeatureModelException;
import br.ufc.lps.splar.core.fm.FeatureModelStatistics;
import br.ufc.lps.splar.core.fm.FeatureTreeNode;
import br.ufc.lps.splar.core.heuristics.FTPreOrderSortedECTraversalHeuristic;
import br.ufc.lps.splar.core.heuristics.VariableOrderingHeuristic;
import br.ufc.lps.splar.core.heuristics.VariableOrderingHeuristicsManager;
import br.ufc.lps.splar.plugins.reasoners.bdd.javabdd.FMReasoningWithBDD;
import br.ufc.lps.splar.plugins.reasoners.bdd.javabdd.ReasoningWithBDD;
import br.ufc.lps.splar.plugins.reasoners.sat.sat4j.ReasoningWithSAT;

public class MeasuresWithoutContextCalculat implements IMeasuresWithoutContext {

	private FeatureModel featureModel;
	private ReasoningWithBDD bddReasoner;
	private ReasoningWithSAT satReasoner;
	private FeatureModelStatistics featureModelStatistics;
	private int modelID;

	public MeasuresWithoutContextCalculat() {
	}

	public MeasuresWithoutContextCalculat(String pathModelFile, int modelID) {

		this.modelID = modelID;

		featureModel = (FeatureModel) ModelFactory.getInstance().createModel(modelID, pathModelFile);
		try {
			featureModel.loadModel();
			featureModelStatistics = new FeatureModelStatistics(featureModel);
			featureModelStatistics.update();
			createBDDReasoner();

		} catch (FeatureModelException e) {
			e.printStackTrace();
		}
	}

	private void createBDDReasoner() {

		try {

			// create BDD variable order heuristic
			new FTPreOrderSortedECTraversalHeuristic("Pre-CL-MinSpan", featureModel,
					FTPreOrderSortedECTraversalHeuristic.FORCE_SORT);
			VariableOrderingHeuristic heuristic = VariableOrderingHeuristicsManager.createHeuristicsManager()
					.getHeuristic("Pre-CL-MinSpan");

			// Creates the BDD reasoner
			bddReasoner = new FMReasoningWithBDD(featureModel, heuristic, 50000, 50000, 60000, "pre-order");

			// Initialize the reasoner (BDD is created at this moment)

			bddReasoner.init();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@Override
	public FeatureModel getFeatureModel() {
		return featureModel;
	}

	@Override
	public String getModelName() {
		return featureModel.getName();
	}

	@Override
	public double nonFunctionCommonality() {
		return (double) featureModelStatistics.countMandatory() / featureModelStatistics.countFeatures();
	}

	@Override
	public int numberOfFeatures() {

		return featureModelStatistics.countFeatures();
	}

	@Override
	public int numberOfTopFeatures() {

		return featureModel.getNodesAtLevel(1).size();
	}

	@Override
	public int numberOfLeafFeatures() {

		return featureModel.getLeaves().size();
	}

	@Override
	public int depthOfTreeMax() {

		return featureModelStatistics.depth();

	}

	@Override
	public double depthOfTreeMedian() {

		List<FeatureTreeNode> nodesAtLevel = (List<FeatureTreeNode>) featureModel.getLeaves();
		int leaves = nodesAtLevel.size();
		if (leaves % 2 == 1) {
			return featureModel.depth(nodesAtLevel.get((leaves / 2) + 1));
		} else {
			int medianLeft = featureModel.depth(nodesAtLevel.get(leaves / 2));
			int medianRight = featureModel.depth(nodesAtLevel.get((leaves / 2) + 1));
			return (medianLeft + medianRight) / 2.0;
		}
	}

	@Override
	public int cognitiveComplexityOfFeatureModel() {
		
		return featureModelStatistics.countGroups11() + featureModelStatistics.countGroups1N();
	}

	public int featureExtendibility() {

		return numberOfLeafFeatures() + singleCyclicDependentFeatures() + multipleCyclicDependentFeatures();
	}

	@Override
	public double flexibilityOfConfiguration() {

		return (double) featureModelStatistics.countOptional() / featureModelStatistics.countFeatures();
	}

	@Override
	public int singleCyclicDependentFeatures() {

		return featureModelStatistics.countSingleCyclicDependentFeatures();
	}

	@Override
	public int multipleCyclicDependentFeatures() {

		return featureModelStatistics.countMultipleCyclicDependentFeatures();
	}

	@Override
	public int cyclomaticComplexity() {

		return featureModelStatistics.countConstraints();
	}

	

	@Override
	public double compoundComplexity() {

		int features = featureModelStatistics.countFeatures();
		int mandatoryFeatures = featureModelStatistics.countMandatory();
		int orFeatures = featureModelStatistics.countGroups1N();
		int xorFeatures = featureModelStatistics.countGroups11();
		int groups = orFeatures + xorFeatures;
		int constraints = featureModelStatistics.countConstraints();

		int allRelationship = constraints;

		double result = Math.pow(features, 2) + (Math.pow(mandatoryFeatures, 2) + 2 * Math.pow(orFeatures, 2)
				+ 3 * Math.pow(xorFeatures, 2) + 3 * Math.pow(groups, 2) + 3 * Math.pow(allRelationship, 2)) / 9;

		return result;
	}

	@Override
	public int crossTreeConstraints() {
		return featureModelStatistics.countConstraints();
	}

	@Override
	public double coefficientOfConnectivityDensity() {
		if((featureModelStatistics.countFeatures()-1) <= 0) return 0;
		return (double) (featureModelStatistics.countFeatures() - 1) / featureModelStatistics.countFeatures();
	}

	@Override
	public int numberOfVariableFeatures() {

		return featureModelStatistics.countGrouped() + featureModelStatistics.countOptional();
	}

	@Override
	public int numberOfVariationPoints() {
		return featureModelStatistics.countGroups11() + featureModelStatistics.countGroups1N();
	}

	@Override
	public int singleVariationPointsFeatures() {
		return featureModelStatistics.countGrouped11();
	}

	@Override
	public int multipleVariationPointsFeatures() {

		return featureModelStatistics.countGrouped1n();
	}

	@Override
	public int rigidNotVariationPointsFeatures() {

		return featureModelStatistics.countFeatures() - featureModelStatistics.countGrouped();
	}

	@Override
	public double ratioVariability() {
		Collection<FeatureTreeNode> nodes = new ArrayList<FeatureTreeNode>();
		nodes = featureModel.getNodes();
		double sum = 0;

		for (FeatureTreeNode node : nodes)
			sum += node.getChildCount();

		return sum / (nodes.size() - numberOfLeafFeatures());
	}

	@Override
	public double numberOfValidConfigurations() {

		try {
			return bddReasoner.countValidConfigurations();
		} catch (OperationNotSupportedException e) {

			e.printStackTrace();
		}

		return 0;
	}

	@Override
	public double branchingFactorsMean() {
		return featureModelStatistics.getChildrenStatistics().getMean();
	}

	@Override
	public int branchingFactorsMax() {
		return (int) featureModelStatistics.getChildrenStatistics().getMax();
	}

	@Override
	public double branchingFactorsMedian() {
		return featureModelStatistics.getChildrenStatistics().getPercentile(50);
	}

	@Override
	public double orRate() {
		return (double) featureModelStatistics.countFeaturesChildGroups1N() / featureModelStatistics.countFeatures();
	}

	@Override
	public double xorRate() {
		return (double) featureModelStatistics.countFeaturesChildGroups11() / featureModelStatistics.countFeatures();
	}

	@Override
	public double crossTreeConstraintsRate() {
		return (double) featureModelStatistics.countFeaturesInConstraints() / featureModelStatistics.countFeatures();
	}

	@Override
	public double connectivityDependencyGraphRate() {
		return (double) (featureModelStatistics.getConstraintsVariablesReference()) / numberOfFeatures();
	}

	@Override
	public double numberFeaturesReferencedConstraintsMean() {
		if (featureModelStatistics.countConstraints() != 0) {
			return (double) (featureModelStatistics.getConstraintsVariablesReferenced()
					/ featureModelStatistics.countConstraints());
		} else
			return 0;
	}

	@Override
	public int numberOfGroupsOR() {
		return featureModelStatistics.countGroups1N();
	}

	@Override
	public int numberOfGroupsXOR() {
		return featureModelStatistics.countGroups11();
	}

	@Override
	public int numberOfOptionalFeatures() {

		return featureModelStatistics.countOptional();
	}

	@Override
	public int numberOfMandatoryFeatures() {

		return featureModelStatistics.countMandatory();
	}

	@Override
	public int numberOfAlternativeFeatures() {

		return featureModelStatistics.countGrouped();
	}

	public int groupingFeatures() {
		return featureModelStatistics.countConstraints();
	}

	public double depthOfTreeMean() {
		return featureModelStatistics.getAverageDepth();
	}

	@Override
	public double ratioSwitchFeatures() {
		return (numberOfFeatures() - numberOfMandatoryFeatures() - 1) / numberOfFeatures();
	}

	@Override
	public int crossTreeConstraintsVariables() {
		return featureModelStatistics.countFeaturesInConstraints();
	}

}
