package FinalProject2.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="tbl_dayoff")
public class DayOff implements Serializable {
	
	@Id
	@Column(name = "dayoff_id")
	@NotNull
	private String dayoff_id;
	
	@Column(name = "dayoff_division_id")
	@NotNull
	private int dayoff_division_id;
	
	@Column(name = "employee_id")
	@NotNull
	private String employee_id;
	
	@Column(name = "detail")
	@NotNull
	private String detail;
	
	@Column(name = "dayoff_date")
	@NotNull
	private LocalDate dayoff_date;
	
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

	public String getDayoff_id() {
		return dayoff_id;
	}

	public void setDayoff_id(String dayoff_id) {
		this.dayoff_id = dayoff_id;
	}

	public int getDayoff_division_id() {
		return dayoff_division_id;
	}

	public void setDayoff_division_id(int dayoff_division_id) {
		this.dayoff_division_id = dayoff_division_id;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public LocalDate getDayoff_date() {
		return dayoff_date;
	}

	public void setDayoff_date(LocalDate dayoff_date) {
		this.dayoff_date = dayoff_date;
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
