package br.ufc.lps.view.list;

import java.util.List;

import javax.swing.AbstractListModel;

import br.ufc.lps.model.adaptation.Adaptacao;
import br.ufc.lps.model.adaptation.ContextoAdaptacao;
import br.ufc.lps.model.adaptation.ValorAdaptacao;

public class ConstraintsListModelAdaptations extends AbstractListModel<Adaptacao>{

	private List<Adaptacao> adaptations;
	
	public ConstraintsListModelAdaptations(List<Adaptacao> adaptations) {
		this.adaptations = adaptations;
	}

	@Override
	public Adaptacao getElementAt(int position) {
		
		/*String mostragem = "";
		for(ContextoAdaptacao valor: adaptations.get(position).getValorAdaptacao()){
			mostragem = mostragem+valor.getNome()+" \n";
			for(ValorAdaptacao ada : valor.getValorAdaptacao()){
				if(ada.getStatus())
					mostragem = " \n"+mostragem+"\t"+ada.getNome()+" \n";
			}
		}*/
		
	    return adaptations.get(position);
	}

	@Override
	public int getSize() {
		return adaptations.size();
	}
	
	public void update(){
		this.fireContentsChanged(this, 0, adaptations.size() - 1);
	}


}
