package br.ufc.lps.model.adaptation;

public class ValorAdaptacao {
	private String nome;
	private Boolean status;
	private ValueQuantification valueQuantification;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	public ValueQuantification getValueQuantification() {
		return valueQuantification;
	}
	
	public void setValueQuantification(ValueQuantification valueQuantification) {
		this.valueQuantification = valueQuantification;
	}
	
	@Override
	public String toString() {
		return "ValorAdaptacao [nome=" + nome + ", status=" + status + "]";
	}
	
}
