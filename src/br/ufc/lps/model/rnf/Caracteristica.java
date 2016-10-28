package br.ufc.lps.model.rnf;

import java.util.List;

public class Caracteristica {
	
	private String nome;
	private List<Subcaracteristica> subcaracteristicas;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public List<Subcaracteristica> getSubcaracteristicas() {
		return subcaracteristicas;
	}
	
	public void setSubcaracteristicas(List<Subcaracteristica> subcaracteristicas) {
		this.subcaracteristicas = subcaracteristicas;
	}
}
