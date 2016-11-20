package br.ufc.lps.view.list;

import java.util.List;

import javax.swing.AbstractListModel;

import br.ufc.lps.model.adaptation.Adaptacao;

public class ConstraintsListModelAdaptations extends AbstractListModel<String>{

	private List<Adaptacao> adaptations;
	
	public ConstraintsListModelAdaptations(List<Adaptacao> adaptations) {
		this.adaptations = adaptations;
	}

	@Override
	public String getElementAt(int position) {
		return "Configuration "+position;
	}

	@Override
	public int getSize() {
		return adaptations.size();
	}
	
	public void update(){
		this.fireContentsChanged(this, 0, adaptations.size() - 1);
	}


}
