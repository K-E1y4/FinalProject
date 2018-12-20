package FinalProject2.model;

public class EmploymentInfoForm {

	private String employee_id, department_id;
	private int position_id, basic_salary, norma;
	public String getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}
	public String getDepartment_id() {
		return department_id;
	}
	public void setDepartment_id(String department_id) {
		this.department_id = department_id;
	}
	public int getPosition_id() {
		return position_id;
	}
	public void setPosition_id(int position_id) {
		this.position_id = position_id;
	}
	public int getBasic_salary() {
		return basic_salary;
	}
	public void setBasic_salary(int basic_salary) {
		this.basic_salary = basic_salary;
	}
	public int getNorma() {
		return norma;
	}
	public void setNorma(int norma) {
		this.norma = norma;
	}
	
}
