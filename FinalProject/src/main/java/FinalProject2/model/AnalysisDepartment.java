package FinalProject2.model;

public class AnalysisDepartment {

	private String department_id, department_name;
	private long norma, point;
	private int achieve;
	
	public AnalysisDepartment(String department_id, String department_name, long norma, long point, int achieve) {
		super();
		this.department_id = department_id;
		this.department_name = department_name;
		this.norma = norma;
		this.point = point;
		this.achieve = achieve;
	}
	
	public String getDepartment_id() {
		return department_id;
	}
	public void setDepartment_id(String department_id) {
		this.department_id = department_id;
	}
	public String getDepartment_name() {
		return department_name;
	}
	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}
	public long getNorma() {
		return norma;
	}
	public void setNorma(long norma) {
		this.norma = norma;
	}
	public long getPoint() {
		return point;
	}
	public void setPoint(long point) {
		this.point = point;
	}
	public int getAchieve() {
		return achieve;
	}
	public void setAchieve(int achieve) {
		this.achieve = achieve;
	}

}
