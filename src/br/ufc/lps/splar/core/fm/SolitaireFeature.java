package br.ufc.lps.splar.core.fm;

import br.ufc.lps.controller.features.TypeFeature;

public class SolitaireFeature extends FeatureTreeNode {

	private boolean isOptional = false;
	private TypeFeature type = TypeFeature.MANDATORY;
	
	public SolitaireFeature( boolean isOptional,  String id, String name, IFNodeRenderer nodeRenderer ) {		
		
		super(id,name,nodeRenderer);
		this.isOptional = isOptional;
		
		setTypeFeature(TypeFeature.MANDATORY);
		
		if(isOptional){
			setTypeFeature(TypeFeature.OPTIONAL);
		}
	}
	
	public boolean isOptional() {
		return isOptional;
	}

	public String toString() {
		String str = "";
		str = isOptional ? ":o " : ":m ";
		return str + super.toString();
	}
	
}
