package FinalProject2.model;

import javax.persistence.Column;

public class AnalysisEmployee {

	@Column(name = "employee_id")
	private String employee_id;
	
	@Column(name = "employee_name")
	private String employee_name;
	
	@Column(name = "department_id")
	private String department_id;
	
	@Column(name = "department_name")
	private String department_name;
	
	@Column(name = "position_id")
	private int position_id;
	
	@Column(name = "position_name")
	private String position_name;
	
	@Column(name = "result")
	private long result;
	
	@Column(name = "norma")
	private long norma;
	
	@Column(name = "achieve")
	private int achieve;

	public AnalysisEmployee(String employee_id, String employee_name, String department_id, String department_name,
			int position_id, String position_name, long result, long norma, int achieve) {
		super();
		this.employee_id = employee_id;
		this.employee_name = employee_name;
		this.department_id = department_id;
		this.department_name = department_name;
		this.position_id = position_id;
		this.position_name = position_name;
		this.result = result;
		this.norma = norma;
		this.achieve = achieve;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
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

	public int getPosition_id() {
		return position_id;
	}

	public void setPosition_id(int position_id) {
		this.position_id = position_id;
	}

	public String getPosition_name() {
		return position_name;
	}

	public void setPosition_name(String position_name) {
		this.position_name = position_name;
	}

	public long getResult() {
		return result;
	}

	public void setResult(long result) {
		this.result = result;
	}

	public long getNorma() {
		return norma;
	}

	public void setNorma(long norma) {
		this.norma = norma;
	}

	public int getAchieve() {
		return achieve;
	}

	public void setAchieve(int achieve) {
		this.achieve = achieve;
	}
	
	
}
