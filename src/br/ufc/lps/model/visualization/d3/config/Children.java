package br.ufc.lps.model.visualization.d3.config;

import java.util.List;

public class Children {
	private String name;
	private Integer size;
	private List<Children> children;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public List<Children> getChildren() {
		return children;
	}
	public void setChildren(List<Children> children) {
		this.children = children;
	}
	
	
}
