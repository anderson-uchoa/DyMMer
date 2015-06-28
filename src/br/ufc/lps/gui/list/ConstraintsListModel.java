package br.ufc.lps.gui.list;

import java.util.List;

import javax.swing.AbstractListModel;

import br.ufc.lps.contextaware.Constraint;

public class ConstraintsListModel extends AbstractListModel<String>{

	private List<Constraint> constraints;
	
	public ConstraintsListModel(List<Constraint> constraints) {
		this.constraints = constraints;
	}

	@Override
	public String getElementAt(int position) {
		// TODO Auto-generated method stub
		return constraints.get(position).getClause();
	}

	@Override
	public int getSize() {
		
		return constraints.size();
	}
	
	public void update(){
		this.fireContentsChanged(this, 0, constraints.size() - 1);
	}


}
