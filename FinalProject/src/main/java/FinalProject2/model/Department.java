package FinalProject2.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="mst_department")
//EmploymentInfoとの１対多マッピング実装済
public class Department implements Serializable {
	
	@Id
	@Column(name = "department_id")
	@NotNull
	private String department_id;
	
	@Column(name = "department_name")
	@NotNull
	private String department_name;
	
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
	
	@OneToMany(mappedBy="department")
	private List<EmploymentInfo> employmentInfoList;

	public String getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(String department_id) {
		this.department_id = department_id;
	}

	public String getDepartment_name() {
		return department_name;
	}

	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
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
