package br.ufc.lps.model;

import java.util.List;

public class MappingItensRnfObjecJson {
	private String name;
	private List<MappingItensRnfObjecJson> children;
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<MappingItensRnfObjecJson> getChildren() {
		return children;
	}
	
	public void setChildren(List<MappingItensRnfObjecJson> children) {
		this.children = children;
	}
	
	@Override
	public String toString() {
		return "MappingItensRnfObjecJson [name=" + name + ", children=" + children + "]";
	}
}
