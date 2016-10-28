package br.ufc.lps.model.adaptation;
import java.util.List;

public class Adaptacao {
	
	private String nome;
	private List<ContextoAdaptacao> valorAdaptacao;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<ContextoAdaptacao> getValorAdaptacao() {
		return valorAdaptacao;
	}
	public void setValorAdaptacao(List<ContextoAdaptacao> valorAdaptacao) {
		this.valorAdaptacao = valorAdaptacao;
	}	
}
