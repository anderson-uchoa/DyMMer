package br.ufc.lps.model.visualization.d3.config;

import java.util.List;

public class Children {
	private String name;
	private Double size;
	private List<Children> children;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getSize() {
		return size;
	}
	public void setSize(Double size) {
		this.size = size;
	}
	public List<Children> getChildren() {
		return children;
	}
	public void setChildren(List<Children> children) {
		this.children = children;
	}
	
	
}
