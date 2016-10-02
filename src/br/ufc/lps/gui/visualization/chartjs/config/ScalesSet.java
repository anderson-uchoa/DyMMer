package br.ufc.lps.gui.visualization.chartjs.config;

import java.util.List;

public class ScalesSet {

	private List<AxesSet> xAxes;
	private List<AxesSet> yAxes;
	
	public List<AxesSet> getxAxes() {
		return xAxes;
	}
	public void setxAxes(List<AxesSet> xAxes) {
		this.xAxes = xAxes;
	}
	public List<AxesSet> getyAxes() {
		return yAxes;
	}
	public void setyAxes(List<AxesSet> yAxes) {
		this.yAxes = yAxes;
	}
	
	
}
