package FinalProject2.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="tbl_task")
public class Task implements Serializable {
	
	@Id
	@Column(name = "task_id")
	@NotNull
	private String task_id;
	
	@Column(name = "task_name")
	@NotNull
	private String task_name;
	
	@Column(name = "task_division_id", insertable = false, updatable = false)
	@NotNull
	private int task_division_id;
	
	@Column(name = "budget")
	@NotNull
	private int budget;
	
	@Column(name = "detail")
	@NotNull
	private String detail;
	
	@Column(name = "employee_id", insertable = false, updatable = false)
	@NotNull
	private String employee_id;
	
	@Column(name = "due_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	private LocalDate due_date;
	
	@Column(name = "end_flg")
	@NotNull
	private boolean end_flg;
	
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
	@JoinColumn(name="task_division_id")
	private TaskDivision taskDivision;

	@OneToMany(mappedBy="task")
	private List<TaskDetail> taskDetailList;
	
	@ManyToOne
	@JoinColumn(name="employee_id")
	private Employee employee;

	public String getTask_id() {
		return task_id;
	}

	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}

	public String getTask_name() {
		return task_name;
	}

	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}

	public int getTask_division_id() {
		return task_division_id;
	}

	public void setTask_division_id(int task_division_id) {
		this.task_division_id = task_division_id;
	}

	public int getBudget() {
		return budget;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public LocalDate getDue_date() {
		return due_date;
	}

	public void setDue_date(LocalDate due_date) {
		this.due_date = due_date;
	}

	public boolean isEnd_flg() {
		return end_flg;
	}

	public void setEnd_flg(boolean end_flg) {
		this.end_flg = end_flg;
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

	public List<TaskDetail> getTaskDetailList() {
		return taskDetailList;
	}

	public void setTaskDetailList(List<TaskDetail> taskDetailList) {
		this.taskDetailList = taskDetailList;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public TaskDivision getTaskDivision() {
		return taskDivision;
	}

	public void setTaskDivision(TaskDivision taskDivision) {
		this.taskDivision = taskDivision;
	}

	
}
