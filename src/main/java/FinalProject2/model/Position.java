package FinalProject2.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="mst_position")
//EmploymentInfoとの１対多マッピング実装済
public class Position implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="position_id")
	@NotNull
	private int position_id;
	
	@Column(name="position_name")
	@NotNull
	private String position_name;
	
	@Column(name="bonus_rate")
	@NotNull
	private float bonus_rate;
	
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
	
	@OneToMany(mappedBy="position")
	private List<EmploymentInfo> employmentInfoList;


	public int getPosition_id() {
		return position_id;
	}

	public void setPosition_id(int position_id) {
		this.position_id = position_id;
	}

	public String getPosition_name() {
		return position_name;
	}

	public void setPosition_name(String position_name) {
		this.position_name = position_name;
	}

	public float getBonus_rate() {
		return bonus_rate;
	}

	public void setBonus_rate(float bonus_rate) {
		this.bonus_rate = bonus_rate;
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

	public List<EmploymentInfo> getEmploymentInfoList() {
		return employmentInfoList;
	}

	public void setEmploymentInfoList(List<EmploymentInfo> employmentInfoList) {
		this.employmentInfoList = employmentInfoList;
	}
	
}
