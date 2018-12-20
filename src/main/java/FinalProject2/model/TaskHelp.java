package FinalProject2.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="tbl_task_help")
public class TaskHelp implements Serializable {
	
	@Id
	@Column(name = "task_help_id")
	@NotNull
	private String task_help_id;
	
	@Column(name = "task_management_id")
	@NotNull
	private String task_management_id;
	
	@Column(name = "use_point")
	@NotNull
	private int use_point;
	
	@Column(name = "employee_id")
	@NotNull
	private String employee_id;
	
	@Column(name = "point")
	@NotNull
	private int point;
	
	@Column(name = "due_date")
	@NotNull
	private LocalDate due_date;
	
	@Column(name = "start_date")
	@NotNull
	private LocalDate start_date;
	
	@Column(name = "end_date")
	@NotNull
	private LocalDate end_date;
	
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

	@OneToOne(mappedBy="taskHelp")
	private TaskManagement taskManagement;
	
	public String getTask_help_id() {
		return task_help_id;
	}

	public void setTask_help_id(String task_help_id) {
		this.task_help_id = task_help_id;
	}

	public String getTask_management_id() {
		return task_management_id;
	}

	public void setTask_management_id(String task_management_id) {
		this.task_management_id = task_management_id;
	}

	public int getUse_point() {
		return use_point;
	}

	public void setUse_point(int use_point) {
		this.use_point = use_point;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public LocalDate getDue_date() {
		return due_date;
	}

	public void setDue_date(LocalDate due_date) {
		this.due_date = due_date;
	}

	public LocalDate getStart_date() {
		return start_date;
	}

	public void setStart_date(LocalDate start_date) {
		this.start_date = start_date;
	}

	public LocalDate getEnd_date() {
		return end_date;
	}

	public void setEnd_date(LocalDate end_date) {
		this.end_date = end_date;
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
	

}
