package br.ufc.lps.model.rnf;

import java.util.List;

public class Subcaracteristica {
	
	private String nome;
	private List<PropriedadeNFuncional> propriedadeNFuncionais;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public List<PropriedadeNFuncional> getPropriedadeNFuncionais() {
		return propriedadeNFuncionais;
	}
	
	public void setPropriedadeNFuncionais(List<PropriedadeNFuncional> propriedadeNFuncionais) {
		this.propriedadeNFuncionais = propriedadeNFuncionais;
	}
}
