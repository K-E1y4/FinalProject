package FinalProject2.model;

import java.io.Serializable;
import java.time.LocalDate;

public class TaskManagementSearch implements Serializable {
	
	private String task_id;
	
	private String task_detail_id;

	private String employee_id;
	
	private String due_date_from;
	
	private String due_date_to;
	
	private String end_flg;

	public String getTask_id() {
		return task_id;
	}

	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}

	public String getTask_detail_id() {
		return task_detail_id;
	}

	public void setTask_detail_id(String task_detail_id) {
		this.task_detail_id = task_detail_id;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
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

	public String getEnd_flg() {
		return end_flg;
	}

	public void setEnd_flg(String end_flg) {
		this.end_flg = end_flg;
	}

}
