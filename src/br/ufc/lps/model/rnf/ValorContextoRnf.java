package br.ufc.lps.model.rnf;

public class ValorContextoRnf {
	private String idFeature;
	private String idRnf;
	private Integer impacto;
	
	public String getIdFeature() {
		return idFeature;
	}
	public void setIdFeature(String idFeature) {
		this.idFeature = idFeature;
	}
	public String getIdRnf() {
		return idRnf;
	}
	public void setIdRnf(String idRnf) {
		this.idRnf = idRnf;
	}
	public Integer getImpacto() {
		return impacto;
	}
	public void setImpacto(Integer impacto) {
		this.impacto = impacto;
	}
	
	@Override
	public String toString() {
		return idFeature +" "+NameImpacto.getNameimpacto(this.impacto)+" "+idRnf;
	}
	
	
}
