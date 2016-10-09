package br.ufc.lps.model.visualization.chartjs.config;

public class DataSet {
	private String fillColor;
	private String strokeColor;
	private String label;
	private Integer borderWidth;
	private String pointColor;
    private Boolean fill;
	private Integer lineTension;
	private String pointStrokeColor;
	
	public Boolean getFill() {
		return fill;
	}
	public void setFill(Boolean fill) {
		this.fill = fill;
	}
	public Integer getLineTension() {
		return lineTension;
	}
	public void setLineTension(Integer lineTension) {
		this.lineTension = lineTension;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Integer getBorderWidth() {
		return borderWidth;
	}
	public void setBorderWidth(Integer borderWidth) {
		this.borderWidth = borderWidth;
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
}
