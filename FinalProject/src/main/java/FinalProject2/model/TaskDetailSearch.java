package FinalProject2.model;

import java.io.Serializable;
import java.time.LocalDate;

public class TaskDetailSearch implements Serializable {
	
	private String task_id;
	
	private String task_detail_id;

	private String sekininsya;
	
	private String due_date_from;
	
	private String due_date_to;
	
	private String make_date_from;
	
	private String make_date_to;
	
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

	public String getSekininsya() {
		return sekininsya;
	}

	public void setSekininsya(String sekininsya) {
		this.sekininsya = sekininsya;
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
