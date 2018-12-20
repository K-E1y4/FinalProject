package FinalProject2.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="mst_dayoff_division")
public class DayoffDivision implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="dayoff_division_id")
	@NotNull
	private int dayoff_division_id;
	
	@Column(name="dayoff_division_name")
	@NotNull
	private String dayoff_division_name;
	
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


	public int getDayoff_division_id() {
		return dayoff_division_id;
	}

	public void setDayoff_division_id(int dayoff_division_id) {
		this.dayoff_division_id = dayoff_division_id;
	}

	public String getDayoff_division_name() {
		return dayoff_division_name;
	}

	public void setDayoff_division_name(String dayoff_division_name) {
		this.dayoff_division_name = dayoff_division_name;
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
