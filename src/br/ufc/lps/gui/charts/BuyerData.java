package br.ufc.lps.gui.charts;

import java.util.List;

public class BuyerData {
	private List<String> labels;
	private List<DataSet> datasets;
	
	public List<String> getLabels() {
		return labels;
	}
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
	public List<DataSet> getDatasets() {
		return datasets;
	}
	public void setDatasets(List<DataSet> datasets) {
		this.datasets = datasets;
	}
}
