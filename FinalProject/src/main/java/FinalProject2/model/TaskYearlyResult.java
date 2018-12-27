package FinalProject2.model;

public class TaskYearlyResult {
	
	private String id;
	private long result_point, norma;
	
	public TaskYearlyResult(String id, long result_point, long norma) {
		super();
		this.id = id;
		this.result_point = result_point;
		this.norma = norma;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getResult_point() {
		return result_point;
	}
	public void setResult_point(long result_point) {
		this.result_point = result_point;
	}
	public long getNorma() {
		return norma;
	}
	public void setNorma(long norma) {
		this.norma = norma;
	}
}
