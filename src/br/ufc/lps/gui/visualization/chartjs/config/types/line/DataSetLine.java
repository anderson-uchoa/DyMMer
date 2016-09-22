package br.ufc.lps.gui.visualization.chartjs.config.types.line;

import br.ufc.lps.gui.visualization.chartjs.config.DataSet;

public class DataSetLine extends DataSet{
	private String backgroundColor;
	private String borderColor;
	
	public String getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public String getBorderColor() {
		return borderColor;
	}
	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}
}
