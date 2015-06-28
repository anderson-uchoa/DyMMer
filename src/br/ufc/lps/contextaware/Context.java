package br.ufc.lps.contextaware;

import java.util.List;

import br.ufc.lps.splar.core.fm.FeatureModel;

public class Context {

	private String name;
	private List<Resolution> resolutions;
	private FeatureModel featureModel;
	private List<Constraint> constraints;
	
	public Context(String name, List<Resolution> resolutions, List<Constraint> constraints) {
		super();
		this.name = name;
		this.resolutions = resolutions;
		this.setConstraints(constraints);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Resolution> getResolutions() {
		return resolutions;
	}

	public void setResolutions(List<Resolution> resolutions) {
		this.resolutions = resolutions;
	}

	public FeatureModel getFeatureModel() {
		return featureModel;
	}

	public void setFeatureModel(FeatureModel featureModel) {
		this.featureModel = featureModel;
	}
	
	public List<Constraint> getConstraints() {
		return constraints;
	}

	public void setConstraints(List<Constraint> constraints) {
		this.constraints = constraints;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Context [name=" + name + ", resolutions=" + resolutions
				+ ", featureModel=" + featureModel + ", constraints="
				+ constraints + "]";
	}
	
	

	
}
