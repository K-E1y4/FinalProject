package FinalProject2.model;

import java.io.Serializable;

public class EmployeeSearch implements Serializable {
	
	private String employeeId_start;
	
	private String employeeId_to;
	
	private String employee_name;
	
	private String sex;
	
	private String age;
	
	private String department_name;
	
	private String position_name;

	
	public String getEmployeeId_start() {
		return employeeId_start;
	}

	public void setEmployeeId_start(String employeeId_start) {
		this.employeeId_start = employeeId_start;
	}

	public String getEmployeeId_to() {
		return employeeId_to;
	}

	public void setEmployeeId_to(String employeeId_to) {
		this.employeeId_to = employeeId_to;
	}

	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getDepartment_name() {
		return department_name;
	}

	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}

	public String getPosition_name() {
		return position_name;
	}

	public void setPosition_name(String position_name) {
		this.position_name = position_name;
	}
	

}
