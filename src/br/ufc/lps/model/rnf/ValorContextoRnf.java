package br.ufc.lps.model.rnf;

public class ValorContextoRnf {
	private String idFeature;
	private String nomeFeature;
	private String idRnf;
	private String nomeRnf;
	private Integer impacto;
	private boolean terminate;
	
	public String getNomeFeature() {
		return nomeFeature;
	}
	
	public void setNomeFeature(String nomeFeature) {
		this.nomeFeature = nomeFeature;
	}
	
	public void setNomeRnf(String nomeRnf) {
		this.nomeRnf = nomeRnf;
	}
	
	public String getNomeRnf() {
		return nomeRnf;
	}
	
	public ValorContextoRnf() {
		this.terminate = false;
	}
	
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
	public boolean isTerminate() {
		return terminate;
	}
	public void setTerminate(boolean terminate) {
		this.terminate = terminate;
	}
	
	@Override
	public String toString() {
		
		if(idRnf==null)
			return nomeFeature +" "+NameImpacto.getNameByImpacto(this.impacto);
		
		return nomeFeature +" "+NameImpacto.getNameByImpacto(this.impacto) + " " + idRnf;
	}
	
	
}
