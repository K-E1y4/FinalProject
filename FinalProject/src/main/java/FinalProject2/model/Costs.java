package FinalProject2.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="tbl_costs")
public class Costs implements Serializable {
	
	@Id
	@Column(name = "cost_id")
	@NotNull
	private String cost_id;
	
	@Column(name = "cost_name")
	@NotNull
	private String cost_name;
	
	@Column(name = "cost_division_id")
	@NotNull
	private int cost_division_id;
	
	@Column(name = "cost")
	@NotNull
	private int cost;
	
	@Column(name = "cost_date")
	@NotNull
	private LocalDate cost_date;
	
	@Column(name = "employee_id")
	@NotNull
	private String employee_id;
	
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

	public String getCost_id() {
		return cost_id;
	}

	public void setCost_id(String cost_id) {
		this.cost_id = cost_id;
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

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public LocalDate getCost_date() {
		return cost_date;
	}

	public void setCost_date(LocalDate cost_date) {
		this.cost_date = cost_date;
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
