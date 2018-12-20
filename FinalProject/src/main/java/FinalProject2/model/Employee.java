package FinalProject2.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="mst_employees")
//EmploymentInfoとの１対１マッピング実装済
public class Employee implements Serializable {
	
	@Id
	@Column(name = "employee_id")
	@NotNull
	private String employee_id;
	
	@Column(name = "employee_name")
	@NotNull
	private String employee_name;
	
	@Column(name = "employee_name_kana")
	@NotNull
	private String employee_name_kana;
	
	@Column(name = "sex")
	@NotNull
	private char sex;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "birthday")
	@Past
	@NotNull
	private LocalDate birthday;
	
	@Column(name = "phone")
	@NotNull
	private String phone;
	
	@Column(name = "postal")
	@NotNull
	private String postal;
	
	@Column(name = "prefecture")
	@NotNull
	private String prefecture;
	
	@Column(name = "address1")
	@NotNull
	private String address1;
	
	@Column(name = "address2")
	@NotNull
	private String address2;
	
	@Column(name = "hire_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	private LocalDate hire_date;
	
	@Column(name = "quit_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate quit_date;
	
	@Column(name = "admin_type")
	@NotNull
	private char admin_type;
	
	@Column(name = "password")
	@NotNull
	private String password;
	
	@Column(name = "password_update")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	private LocalDate password_update;
	
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
	
	@OneToOne
	@JoinColumn(name="employee_id")
	private EmploymentInfo employmentInfo;

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	public String getEmployee_name_kana() {
		return employee_name_kana;
	}

	public void setEmployee_name_kana(String employee_name_kana) {
		this.employee_name_kana = employee_name_kana;
	}

	public char getSex() {
		return sex;
	}

	public void setSex(char sex) {
		this.sex = sex;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPostal() {
		return postal;
	}

	public void setPostal(String postal) {
		this.postal = postal;
	}

	public String getPrefecture() {
		return prefecture;
	}

	public void setPrefecture(String prefecture) {
		this.prefecture = prefecture;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public LocalDate getHire_date() {
		return hire_date;
	}

	public void setHire_date(LocalDate hire_date) {
		this.hire_date = hire_date;
	}

	public LocalDate getQuit_date() {
		return quit_date;
	}

	public void setQuit_date(LocalDate quit_date) {
		this.quit_date = quit_date;
	}

	public char getAdmin_type() {
		return admin_type;
	}

	public void setAdmin_type(char admin_type) {
		this.admin_type = admin_type;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getPassword_update() {
		return password_update;
	}

	public void setPassword_update(LocalDate password_update) {
		this.password_update = password_update;
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

	public EmploymentInfo getEmploymentInfo() {
		return employmentInfo;
	}

	public void setEmploymentInfo(EmploymentInfo employmentInfo) {
		this.employmentInfo = employmentInfo;
	}
	
}
