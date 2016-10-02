package br.ufc.lps.gui.visualization.chartjs.config.types.bubble;

import java.util.List;

import br.ufc.lps.gui.visualization.chartjs.config.DataSet;

public class DataSetBubble extends DataSet{
	private List<DataBubble> data;
	private String backgroundColor;
	private String hoverBackgroundColor;
	private String borderColor;
	
	public String getHoverBackgroundColor() {
		return hoverBackgroundColor;
	}

	public void setHoverBackgroundColor(String hoverBackgroundColor) {
		this.hoverBackgroundColor = hoverBackgroundColor;
	}

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

	public List<DataBubble> getData() {
		return data;
	}

	public void setData(List<DataBubble> data) {
		this.data = data;
	}
	
}
