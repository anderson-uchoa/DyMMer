package br.ufc.lps.contextaware;

import br.ufc.lps.splar.core.fm.FeatureTreeNode;

public class Literal {

	private FeatureTreeNode feature;
	private boolean state;
	
	public Literal(FeatureTreeNode feature, boolean state) {
		this.feature = feature;
		this.state = state;
	}
	
	/**
	 * @return the feature
	 */
	public FeatureTreeNode getFeature() {
		return feature;
	}
	/**
	 * @param feature the feature to set
	 */
	public void setFeature(FeatureTreeNode feature) {
		this.feature = feature;
	}
	/**
	 * @return the state
	 */
	public boolean isState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(boolean state) {
		this.state = state;
	}
	
	
	
}
