package vo;

public class Goal {
	private String n;
	private Integer p;
	private Integer o;
	
	public String getN() {
		return n;
	}
	public void setN(String n) {
		if(n == null || n.trim().isEmpty()){
			this.n = null;
		}
		this.n = n.trim();
	}
	public Integer getP() {
		return p;
	}
	public void setP(Integer p) {
		this.p = p;
	}
	public Integer getO() {
		return o;
	}
	public void setO(Integer o) {
		this.o = o;
	}

	
	
}
