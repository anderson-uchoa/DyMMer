package br.ufc.lps.model.rnf;
import java.util.List;

public class ContextoRnf {
	private String nome;
	private List<ValorContextoRnf> valorContextoRnf;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public List<ValorContextoRnf> getValorContextoRnf() {
		return valorContextoRnf;
	}
	
	public void setValorContextoRnf(List<ValorContextoRnf> valorContextoRnf) {
		this.valorContextoRnf = valorContextoRnf;
	}
}
