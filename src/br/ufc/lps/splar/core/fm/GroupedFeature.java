package br.ufc.lps.splar.core.fm;

import br.ufc.lps.controller.features.TypeFeature;

public class GroupedFeature extends FeatureTreeNode {

	public GroupedFeature( String id, String name, IFNodeRenderer nodeRenderer ) {		
		super(id,name,nodeRenderer);
		setTypeFeature(TypeFeature.GROUPED_FEATURE);
	}
	
	public FeatureGroup getGroup() {
		return (FeatureGroup)getParent();
	}
	
	public String toString() {
		return ": " + super.toString();
		
	}
	
}