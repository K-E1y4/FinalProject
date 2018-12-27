package FinalProject2.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="tbl_task_monthly_result")
public class TaskMonthlyResult implements Serializable {
	
	@Id
	@Column(name = "task_monthly_result_id")
	@NotNull
	private String task_monthly_result_id;
	
	@Column(name = "employee_id")
	@NotNull
	private String employee_id;
	
	@Column(name = "result_point")
	@NotNull
	private int result_point;
	
	@Column(name = "bonus_point_add")
	@NotNull
	private int bonus_point_add;
	
	@Column(name = "norma")
	@NotNull
	private int norma;
	
	@Column(name = "result_date")
	@NotNull
	private LocalDate result_date;
	
	@Column(name = "department_id", insertable = false, updatable = false)
	@NotNull
	private String department_id;
	
	@Column(name = "make_date")
	@NotNull
	private Timestamp make_date;
	
	@Column(name = "make_user")
	@NotNull
	private String make_user;
	
	@Column(name = "update_date")
	@NotNull
	private Timestamp update_date;
	
	@Column(name = "update_user")
	@NotNull
	private String update_user;
	
	@ManyToOne
	@JoinColumn(name="department_id")
	private Department department;

	public String getTask_monthly_result_id() {
		return task_monthly_result_id;
	}

	public void setTask_monthly_result_id(String task_monthly_result_id) {
		this.task_monthly_result_id = task_monthly_result_id;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public int getResult_point() {
		return result_point;
	}

	public void setResult_point(int result_point) {
		this.result_point = result_point;
	}

	public int getBonus_point_add() {
		return bonus_point_add;
	}

	public void setBonus_point_add(int bonus_point_add) {
		this.bonus_point_add = bonus_point_add;
	}

	public int getNorma() {
		return norma;
	}

	public void setNorma(int norma) {
		this.norma = norma;
	}

	public LocalDate getResult_date() {
		return result_date;
	}

	public void setResult_date(LocalDate result_date) {
		this.result_date = result_date;
	}

	public Timestamp getMake_date() {
		return make_date;
	}

	public void setMake_date(Timestamp make_date) {
		this.make_date = make_date;
	}

	public String getMake_user() {
		return make_user;
	}

	public void setMake_user(String make_user) {
		this.make_user = make_user;
	}

	public Timestamp getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Timestamp update_date) {
		this.update_date = update_date;
	}

	public String getUpdate_user() {
		return update_user;
	}

	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}

	public String getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(String department_id) {
		this.department_id = department_id;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

}
