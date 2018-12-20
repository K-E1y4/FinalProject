package FinalProject2.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="mst_task_division")
public class TaskDivision implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "task_division_id")
	@NotNull
	private int task_division_id;
	
	@Column(name = "task_division_name")
	@NotNull
	private String task_division_name;
	
	@Column(name = "detail")
	@NotNull
	private String detail;
	
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

	public int getTask_division_id() {
		return task_division_id;
	}

	public void setTask_division_id(int task_division_id) {
		this.task_division_id = task_division_id;
	}

	public String getTask_division_name() {
		return task_division_name;
	}

	public void setTask_division_name(String task_division_name) {
		this.task_division_name = task_division_name;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
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
