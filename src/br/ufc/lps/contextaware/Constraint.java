package br.ufc.lps.contextaware;

public class Constraint {
	
	private int id;
	private String clause;
	
	public Constraint(int id, String clause) {
		this.id = id;
		this.clause = clause;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the clause
	 */
	public String getClause() {
		return clause;
	}
	/**
	 * @param clause the clause to set
	 */
	public void setClause(String clause) {
		this.clause = clause;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Constraint other = (Constraint) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	

}
