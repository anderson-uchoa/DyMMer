package br.ufc.lps.gui.charts;

import java.util.List;

public class DataSet {
	private String fillColor;
	private String strokeColor;
	private String label;
	private Integer borderWidth;
	private String pointColor;
	private String pointStrokeColor;
	private List<Double> data;
	private List<String> backgroundColor;
	private List<String> borderColor;
	private List<String> hoverBackgroundColor;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public List<String> getBorderColor() {
		return borderColor;
	}
	public void setBorderColor(List<String> borderColor) {
		this.borderColor = borderColor;
	}
	public Integer getBorderWidth() {
		return borderWidth;
	}
	public void setBorderWidth(Integer borderWidth) {
		this.borderWidth = borderWidth;
	}
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
	public String getFillColor() {
		return fillColor;
	}
	public void setFillColor(String fillColor) {
		this.fillColor = fillColor;
	}
	public String getStrokeColor() {
		return strokeColor;
	}
	public void setStrokeColor(String strokeColor) {
		this.strokeColor = strokeColor;
	}
	public String getPointColor() {
		return pointColor;
	}
	public void setPointColor(String pointColor) {
		this.pointColor = pointColor;
	}
	public String getPointStrokeColor() {
		return pointStrokeColor;
	}
	public void setPointStrokeColor(String pointStrokeColor) {
		this.pointStrokeColor = pointStrokeColor;
	}
	public List<Double> getData() {
		return data;
	}
	public void setData(List<Double> data) {
		this.data = data;
	}
	
	
}
