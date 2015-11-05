package br.ufc.lps.test;

import static org.junit.Assert.*;

import org.junit.Test;






import br.ufc.lps.gui.ViewerPanel;
import br.ufc.lps.model.context.ContextModel;
import br.ufc.lps.model.context.SplotContextModel;
import br.ufc.lps.model.normal.SplotModel;
import br.ufc.lps.model.xml.XMLSplotModel;
import br.ufc.lps.splar.core.fm.FeatureModel;
import br.ufc.lps.splar.core.fm.FeatureModelException;
import br.ufc.lps.splar.core.fm.XMLFeatureModel;

public class ModelTest {
	public static String featureModelPath = "./tests/Heidelberg Ion-Beam Therapy Center.xml";
	private SplotModel model;
	@Test
	public void featureModelTest() throws FeatureModelException {
		FeatureModel featureModel = new XMLFeatureModel(featureModelPath);
		featureModel.loadModel();
		
		assertEquals(5, featureModel.countConstraints());
		assertEquals(6, featureModel.countConstraintsVariables());
		featureModel.removeAllConstraints();
		assertEquals(0, featureModel.countConstraints());
		assertEquals(47, featureModel.countFeatures());
		assertEquals(7, featureModel.depth());
		assertEquals(37, featureModel.getLeaves().size());
		assertEquals("Heidelberg Ion-Beam Therapy Center", featureModel.getName());
	}
	
	@Test
	public void MetricsTest(){
		new XMLSplotModel();
		final SplotModel modelTest = new SplotModel(featureModelPath);
		model = modelTest;
		assertEquals(7, model.branchingFactorsMax());//Number max of features children
		assertEquals(2528.0, Math.ceil(model.compoundComplexity()));//NF2+ (Rand2+ 2*Ror2+ 3*Rcase2+ 3*Rgr2+ 3*R2)/9
		assertEquals(5, model.cognitiveComplexityOfFeatureModel());//Number of variants points
		assertEquals(5, model.crossTreeConstraints());//Number of constraints
		assertEquals(5, model.cyclomaticComplexity());//Number of integrity constraints
		assertEquals(7, model.depthOfTreeMax()); //Number of features of the longest path from the root of the feature model
		assertEquals(40, model.featureExtendibility()); //NLeaf + SCDF + MCDF
		assertEquals(0, model.multipleCyclicDependentFeatures());//participants  in  features  constraints  and  daughters  of  variant points with cardinality [1..*]
		assertEquals(37, model.numberOfLeafFeatures()); //Number of features without children
		assertEquals(5, model.numberOfTopFeatures()); //Number of root descendants
		assertEquals(13020.0, model.numberOfValidConfigurations()); //Number of possible and valid configurations of the feature model
		assertEquals(25, model.rigidNotVariationPointsFeatures()); //Number of features not daughters of variant points
		assertEquals(3, model.singleCyclicDependentFeatures()); //participants  in  features  constraints  and  daughters  of  variant points with cardinality [1..1]
		assertEquals(22, model.numberOfVariableFeatures()); //NA + NO
		assertEquals(22, model.singleVariationPointsFeatures()); //Number of features daughters of variant points with cardinality [1..1]
		assertEquals(0, model.multipleVariationPointsFeatures()); //Number of features daughters of variant points with cardinality [1..*]
	}
}
