package br.ufc.lps.gui.visualization.chartjs.config.types.bar;

import java.util.List;

public class DataSetBar extends br.ufc.lps.gui.visualization.chartjs.config.DataSet{
	
	private List<String> backgroundColor;
	private List<String> hoverBackgroundColor;
	private List<String> borderColor;
	
	public List<String> getHoverBackgroundColor() {
		return hoverBackgroundColor;
	}
	public void setHoverBackgroundColor(List<String> hoverBackgroundColor) {
		this.hoverBackgroundColor = hoverBackgroundColor;
	}
	public List<String> getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(List<String> backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public List<String> getBorderColor() {
		return borderColor;
	}
	public void setBorderColor(List<String> borderColor) {
		this.borderColor = borderColor;
	}
	
	
}
