package FinalProject2.model;

import java.io.Serializable;
import java.time.LocalDate;

public class TaskSearch implements Serializable {
	
	private String taskId_from;
	
	private String taskId_to;
	
	private String task_name;
	
	private String taskDivision;
	
	private String budget_from;
	
	private String budget_to;
	
	private String make_user;
	
	private String due_date_from;
	
	private String due_date_to;
	
	private String make_date_from;
	
	private String make_date_to;
	
	private String end_flg;

	public String getTaskId_from() {
		return taskId_from;
	}

	public void setTaskId_from(String taskId_from) {
		this.taskId_from = taskId_from;
	}

	public String getTaskId_to() {
		return taskId_to;
	}

	public void setTaskId_to(String taskId_to) {
		this.taskId_to = taskId_to;
	}

	public String getTask_name() {
		return task_name;
	}

	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}

	public String getTaskDivision() {
		return taskDivision;
	}

	public void setTaskDivision(String taskDivision) {
		this.taskDivision = taskDivision;
	}

	public String getBudget_from() {
		return budget_from;
	}

	public void setBudget_from(String budget_from) {
		this.budget_from = budget_from;
	}

	public String getBudget_to() {
		return budget_to;
	}

	public void setBudget_to(String budget_to) {
		this.budget_to = budget_to;
	}

	public String getMake_user() {
		return make_user;
	}

	public void setMake_user(String make_user) {
		this.make_user = make_user;
	}

	public String getDue_date_from() {
		return due_date_from;
	}

	public void setDue_date_from(String due_date_from) {
		this.due_date_from = due_date_from;
	}

	public String getDue_date_to() {
		return due_date_to;
	}

	public void setDue_date_to(String due_date_to) {
		this.due_date_to = due_date_to;
	}

	public String getMake_date_from() {
		return make_date_from;
	}

	public void setMake_date_from(String make_date_from) {
		this.make_date_from = make_date_from;
	}

	public String getMake_date_to() {
		return make_date_to;
	}

	public void setMake_date_to(String make_date_to) {
		this.make_date_to = make_date_to;
	}

	public String getEnd_flg() {
		return end_flg;
	}

	public void setEnd_flg(String end_flg) {
		this.end_flg = end_flg;
	}


}
