package br.ufc.lps.splar.core.fm;

import br.ufc.lps.controller.features.TypeFeature;

public class RootNode extends FeatureTreeNode {

	public RootNode(  String id, String name, IFNodeRenderer nodeRenderer ) {		
		super(id,name,nodeRenderer);
		setTypeFeature(TypeFeature.ROOT);
	}
	
	public String toString() {
		return ":r " + super.toString();
	}
}
