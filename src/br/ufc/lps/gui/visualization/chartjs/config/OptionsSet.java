package br.ufc.lps.gui.visualization.chartjs.config;

public class OptionsSet {
	/*
	 * responsive: true,
                title:{
                    display:true,
                    text:'Chart.js Line Chart - Cubic interpolation mode'
                },
                tooltips: {
                    mode: 'label'
                },
                hover: {
                    mode: 'dataset'
                },
                scales: {
                    xAxes: [{
                        display: true,
                        scaleLabel: {
                            display: true
                        }
                    }],
                    yAxes: [{
                        display: true,
                        scaleLabel: {
                            display: true,
                            labelString: 'Value'
                        },
                        ticks: {
                            suggestedMin: -10,
                            suggestedMax: 200,
                        }
                    }]
                }
	 */
	private TitleSet title;
	private Tooltips tooltips;

	public TitleSet getTitle() {
		return title;
	}

	public void setTitle(TitleSet title) {
		this.title = title;
	}
	
	
}
