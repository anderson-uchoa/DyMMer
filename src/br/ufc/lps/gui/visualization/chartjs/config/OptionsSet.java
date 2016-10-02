package br.ufc.lps.gui.visualization.chartjs.config;

public class OptionsSet {
	
	private ScalesSet scales;
	private Boolean responsive;
	private TitleSet title;
	private Tooltips tooltips;
	private HoverSet hover;
	private LegendSet legend;
	
	public LegendSet getLegend() {
		return legend;
	}

	public void setLegend(LegendSet legend) {
		this.legend = legend;
	}

	public ScalesSet getScales() {
		return scales;
	}

	public void setScales(ScalesSet scales) {
		this.scales = scales;
	}

	public HoverSet getHover() {
		return hover;
	}

	public void setHover(HoverSet hover) {
		this.hover = hover;
	}

	public Boolean getResponsive() {
		return responsive;
	}

	public void setResponsive(Boolean responsive) {
		this.responsive = responsive;
	}

	public Tooltips getTooltips() {
		return tooltips;
	}

	public void setTooltips(Tooltips tooltips) {
		this.tooltips = tooltips;
	}

	public TitleSet getTitle() {
		return title;
	}

	public void setTitle(TitleSet title) {
		this.title = title;
	}
	
	
}
