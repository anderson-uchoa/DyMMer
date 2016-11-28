package br.ufc.lps.view.trees.adaptation;

import br.ufc.lps.model.adaptation.ValueQuantification;

public class CheckBoxNodeData {

	private String text;
	private boolean checked;
	private ValueQuantification valueQuantification = new ValueQuantification();

	public CheckBoxNodeData(final String text, final boolean checked) {
		this.text = text;
		this.checked = checked;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(final boolean checked) {
		this.checked = checked;
	}

	public String getText() {
		return text;
	}

	public void setText(final String text) {
		this.text = text;
	}
	
	public ValueQuantification getValueQuantification() {
		return valueQuantification;
	}

	public void setValueQuantification(ValueQuantification valueQuantification) {
		this.valueQuantification = valueQuantification;
	}

	@Override
	public String toString() {
		return getClass().getName() + "[" + text + "/" + checked + "]";
	}

}