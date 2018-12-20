package FinalProject2.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="tbl_task_detail")
public class TaskDetail implements Serializable {
	
	@Id
	@Column(name = "task_detail_id")
	@NotNull
	private String task_detail_id;
	
	@Column(name = "task_id", insertable = false, updatable = false)
	@NotNull
	private String task_id;
	
	@Column(name = "task_detail_name")
	@NotNull
	private String task_detail_name;
	
	@Column(name = "task_division_id")
	@NotNull
	private int task_division_id;
	
	@Column(name = "detail")
	@NotNull
	private String detail;
	
	@Column(name = "due_date")
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
	@JoinColumn(name="task_id")
	private Task task;
	
	@OneToMany(mappedBy="taskDetail")
	private List<TaskManagement> taskManagementList;

	public String getTask_detail_id() {
		return task_detail_id;
	}

	public void setTask_detail_id(String task_detail_id) {
		this.task_detail_id = task_detail_id;
	}

	public String getTask_id() {
		return task_id;
	}

	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}

	public String getTask_detail_name() {
		return task_detail_name;
	}

	public void setTask_detail_name(String task_detail_name) {
		this.task_detail_name = task_detail_name;
	}

	public int getTask_division_id() {
		return task_division_id;
	}

	public void setTask_division_id(int task_division_id) {
		this.task_division_id = task_division_id;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
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

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public List<TaskManagement> getTaskManagementList() {
		return taskManagementList;
	}

	public void setTaskManagementList(List<TaskManagement> taskManagementList) {
		this.taskManagementList = taskManagementList;
	}

}
