package br.ufc.lps.model;

public class ValueQuantificationBool extends ValueQuantification{
	private Boolean isQuantification;
	private String valueQuantification;
	
	public Boolean getIsQuantification() {
		return isQuantification;
	}

	public void setIsQuantification(Boolean isQuantification) {
		this.isQuantification = isQuantification;
	}
	
	public String getValueQuantification() {
		return valueQuantification;
	}
	
	public void setValueQuantification(String valueQuantification) {
		this.valueQuantification = valueQuantification;
	}
	
	@Override
	public boolean isComplete() {
		if(isQuantification)
			if(valueQuantification.trim().isEmpty())
				return false;
		
		return (super.isComplete() && true);
	}

	@Override
	public String toString() {
		return "ValueQuantificationBool [isQuantification=" + isQuantification + ", valueQuantification="
				+ valueQuantification + "]";
	}
}
