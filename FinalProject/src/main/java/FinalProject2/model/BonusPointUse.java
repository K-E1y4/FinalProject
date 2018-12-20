package FinalProject2.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="tbl_bonus_point_use")
public class BonusPointUse implements Serializable {
	
	@Id
	@Column(name = "bonus_point_use_id")
	@NotNull
	private String bonus_point_use_id;
	
	@Column(name = "cost_name")
	@NotNull
	private String cost_name;
	
	@Column(name = "cost_division_id")
	@NotNull
	private int cost_division_id;
	
	@Column(name = "employee_id")
	@NotNull
	private String employee_id;
	
	@Column(name = "bonus_point_use")
	@NotNull
	private int bonus_point_use;
	
	@Column(name = "detail")
	@NotNull
	private String detail;
	
	@Column(name = "use_date")
	@NotNull
	private LocalDate use_date;
	
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

	public String getBonus_point_use_id() {
		return bonus_point_use_id;
	}

	public void setBonus_point_use_id(String bonus_point_use_id) {
		this.bonus_point_use_id = bonus_point_use_id;
	}

	public String getCost_name() {
		return cost_name;
	}

	public void setCost_name(String cost_name) {
		this.cost_name = cost_name;
	}

	public int getCost_division_id() {
		return cost_division_id;
	}

	public void setCost_division_id(int cost_division_id) {
		this.cost_division_id = cost_division_id;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public int getBonus_point_use() {
		return bonus_point_use;
	}

	public void setBonus_point_use(int bonus_point_use) {
		this.bonus_point_use = bonus_point_use;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public LocalDate getUse_date() {
		return use_date;
	}

	public void setUse_date(LocalDate use_date) {
		this.use_date = use_date;
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
