package br.ufc.lps.model.xml;

public enum ModelID {

	SPLOT_MODEL(1), FAMILIAR_MODEL(2);
	
	private int id;
	
	private ModelID(int id) {
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
	
	
}
