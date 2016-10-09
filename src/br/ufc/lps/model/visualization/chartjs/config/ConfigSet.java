package br.ufc.lps.model.visualization.chartjs.config;

public class ConfigSet {
	private String type;
	private BuyerData data;
	private OptionsSet options;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BuyerData getData() {
		return data;
	}
	public void setData(BuyerData data) {
		this.data = data;
	}
	public OptionsSet getOptions() {
		return options;
	}
	public void setOptions(OptionsSet options) {
		this.options = options;
	}
	
	
}
