package br.ufc.lps.view.list;

import java.util.List;

import javax.swing.AbstractListModel;

import br.ufc.lps.model.contextaware.Constraint;
import br.ufc.lps.repository.SchemeXml;

public class ConstraintsListModelItensSelectOrderModelVersion extends AbstractListModel<SchemeXml>{

	private List<SchemeXml> schemeXml;
	
	public ConstraintsListModelItensSelectOrderModelVersion(List<SchemeXml> schemeXml) {
		this.schemeXml = schemeXml;
	}

	@Override
	public SchemeXml getElementAt(int position) {
		return schemeXml.get(position);
	}
	
	public List<SchemeXml> getListSchemeXml() {
		return schemeXml;
	}

	@Override
	public int getSize() {
		return schemeXml.size();
	}
	
	public void update(){
		this.fireContentsChanged(this, 0, schemeXml.size() - 1);
	}
	
	public void set(int index, SchemeXml element) {
		schemeXml.set(index, element);
        fireContentsChanged(this, index, index);
    }
}
