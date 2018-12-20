package FinalProject2.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="mst_cost_division")
public class CostDivision implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "cost_division_id")
	@NotNull
	private int cost_division_id;
	
	@Column(name = "cost_division_name")
	@NotNull
	private String cost_division_name;
	
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

	public int getCost_division_id() {
		return cost_division_id;
	}

	public void setCost_division_id(int cost_division_id) {
		this.cost_division_id = cost_division_id;
	}

	public String getCost_division_name() {
		return cost_division_name;
	}

	public void setCost_division_name(String cost_division_name) {
		this.cost_division_name = cost_division_name;
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
