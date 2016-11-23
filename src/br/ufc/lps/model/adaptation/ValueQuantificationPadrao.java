package br.ufc.lps.model.adaptation;

public class ValueQuantificationPadrao extends ValueQuantification{
	private Boolean isInterval;
	private String quantification1;
	private String valueQuantification1;
	private String quantification2;
	private String valueQuantification2;
	
	public Boolean getIsInterval() {
		return isInterval;
	}
	
	public void setIsInterval(Boolean isInterval) {
		this.isInterval = isInterval;
	}
	
	public String getQuantification1() {
		return quantification1;
	}
	
	public void setQuantification1(String quantification1) {
		this.quantification1 = quantification1;
	}
	
	public String getValueQuantification1() {
		return valueQuantification1;
	}
	
	public void setValueQuantification1(String valueQuantification1) {
		this.valueQuantification1 = valueQuantification1;
	}
	
	public String getQuantification2() {
		return quantification2;
	}
	
	public void setQuantification2(String quantification2) {
		this.quantification2 = quantification2;
	}
	
	public String getValueQuantification2() {
		return valueQuantification2;
	}

	public void setValueQuantification2(String valueQuantification2) {
		this.valueQuantification2 = valueQuantification2;
	}
	
	@Override
	public boolean isComplete() {
		
		if(valueQuantification1.trim().isEmpty())
			return false;
		
		if(isInterval)
			if(valueQuantification2.trim().isEmpty())
				return false;
		
		return (super.isComplete() && true);
	}

	@Override
	public String toString() {
		return "ValueQuantificationPadrao [isInterval=" + isInterval + ", quantification1=" + quantification1
				+ ", valueQuantification1=" + valueQuantification1 + ", quantification2=" + quantification2
				+ ", valueQuantification2=" + valueQuantification2 + "]";
	}
}
