package br.ufc.lps.model.context;

import java.util.ArrayList;

import br.ufc.lps.model.normal.IModel;

public interface IContextModel extends IModel {

	public int numberActivatedFeatures();

	public int numberDeactivatedFeatures();

	public int numberContextConstraints();

	public int numberOfContexts();

	public int numberOfContextAffectingProductConfiguration();

	public int numberOfContextAdaptation();

	public int contextAdaptationExtensibility();

	public int contextAdaptationFlexibility();
	
	public double activatedFeaturesByContextAdaptation();

	public double desactivatedFeaturesByContextAdaptation();

	public ArrayList<String> getAllDeactivatedFeatures();

	public int contextFeatures();

	public int contextFeaturesContraints();
}
