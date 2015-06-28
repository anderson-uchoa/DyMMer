package br.ufc.lps.contextaware;

public class Resolution {

	private String idFeature;
	private String nameFeature;
	private boolean status;
	
	public Resolution(String idFeature, String nameFeature, boolean status) {
		super();
		this.idFeature = idFeature;
		this.nameFeature = nameFeature;
		this.status = status;
	}

	public String getIdFeature() {
		return idFeature;
	}

	public void setIdFeature(String idFeature) {
		this.idFeature = idFeature;
	}

	public String getNameFeature() {
		return nameFeature;
	}

	public void setNameFeature(String nameFeature) {
		this.nameFeature = nameFeature;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idFeature == null) ? 0 : idFeature.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Resolution other = (Resolution) obj;
		if (idFeature == null) {
			if (other.idFeature != null)
				return false;
		} else if (!idFeature.equals(other.idFeature))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Resolution [idFeature=" + idFeature + ", nameFeature="
				+ nameFeature + ", status=" + status + "]";
	}
	
	
	
	
}
