package br.ufc.lps.view.list;

import java.util.List;

import javax.swing.AbstractListModel;

import br.ufc.lps.model.rnf.ValorContextoRnf;

public class ConstraintsRnfListModel extends AbstractListModel<String>{

	private List<ValorContextoRnf> constraintValor;
	
	public ConstraintsRnfListModel(List<ValorContextoRnf> constraints) {
		this.constraintValor = constraints;
	}

	@Override
	public String getElementAt(int position) {
		return constraintValor.get(position).toString();
	}

	@Override
	public int getSize() {
		return constraintValor.size();
	}
	
	public void update(){
		this.fireContentsChanged(this, 0, constraintValor.size() - 1);
	}
}
