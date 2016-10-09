package br.ufc.lps.model.context;

import br.ufc.lps.model.normal.IModel;

public interface IContextModel extends IModel {

		int numberActivatedFeatures();
		int numberDeactivatedFeatures();
		int numberContextConstraints();
		int numberOfContexts();
		int numberOfContextAffectingProductConfiguration();
		int numberOfContextAdaptation();
		int contextAdaptationExtensibility();
		int contextAdaptationFlexibility();
}
