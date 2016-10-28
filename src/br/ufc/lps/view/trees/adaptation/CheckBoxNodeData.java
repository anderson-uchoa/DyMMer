package br.ufc.lps.view.trees.adaptation;

public class CheckBoxNodeData {

	private String text;
	private boolean checked;

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

	@Override
	public String toString() {
		return getClass().getName() + "[" + text + "/" + checked + "]";
	}

}