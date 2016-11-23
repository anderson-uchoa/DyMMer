package br.ufc.lps.model.adaptation;

public abstract class ValueQuantification {
	private String padrao;

	public String getPadrao() {
		return padrao;
	}

	public void setPadrao(String padrao) {
		this.padrao = padrao;
	}
	
	public boolean isComplete(){
		if(padrao.trim().isEmpty())
			return false;
		
		return true;
	}
}
