package br.ufc.lps.splar.core.fm;

import br.ufc.lps.controller.features.TypeFeature;

public class FeatureGroup extends FeatureTreeNode {

	private boolean isOptional = false;
	private int min=1, max=1;
	
	public FeatureGroup( String id, String name, int min, int max, IFNodeRenderer nodeRenderer ) {		
		super(id,name,nodeRenderer);
		this.min = min;
		this.max = max;
		
		super.setTypeFeature(TypeFeature.GROUP_XOR);	
		if (max == -1) {
			super.setTypeFeature(TypeFeature.GROUP_OR);
		}
	}
	
	public int getMin() {
		return min;
	}
	
	public int getMax() {
		return max;
	}
	
	

	public String getDescription() {
		String name = "";
		if ( !getName().startsWith("_")) {
			name = getName();
		}
		return name + " [" + getMin() + "," + (getMax()==-1?"*":getMax()) + "] ";
	}

	public String toString() {
		//return ":g "+ super.toString() + " [" + getMin() + "," + (getMax()==-1?"*":getMax()) + "] ";
		return ":g [" + getMin() + ", " + (getMax()==-1?"*":getMax()) + "]"; 
	}
}