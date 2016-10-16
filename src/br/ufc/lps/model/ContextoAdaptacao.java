package br.ufc.lps.model;


import java.util.List;

public class ContextoAdaptacao {
	private String nome;
	private List<ValorAdaptacao> valorAdaptacao;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<ValorAdaptacao> getValorAdaptacao() {
		return valorAdaptacao;
	}
	public void setValorAdaptacao(List<ValorAdaptacao> valorAdaptacao) {
		this.valorAdaptacao = valorAdaptacao;
	}
	
	
}
