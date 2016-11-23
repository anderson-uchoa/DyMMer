package br.ufc.lps.view.list;

import java.util.List;

import javax.swing.AbstractListModel;

import br.ufc.lps.model.contextaware.Constraint;

public class ConstraintsListModelItensRnfs extends AbstractListModel<String>{

	private List<String> constraints;
	
	public ConstraintsListModelItensRnfs(List<String> constraints) {
		this.constraints = constraints;
	}

	@Override
	public String getElementAt(int position) {
		return constraints.get(position);
	}

	@Override
	public int getSize() {
		return constraints.size();
	}
	
	public void update(){
		this.fireContentsChanged(this, 0, constraints.size() - 1);
	}
}
