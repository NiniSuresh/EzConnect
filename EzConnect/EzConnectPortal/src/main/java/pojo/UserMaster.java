package pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

@Entity
@Table(name="User_Master")
public class UserMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id", unique=true,nullable = false)
	private  int id;
	@Column(name="name",nullable = false)
	private String name;
	@Column(name="role",nullable = false)
	private String role;
	@Column(name="security_code", unique=true,nullable = false)
	private String securityCode;
	@Column(name="phone_number",unique=true)
	@Pattern(regexp="(^$|[0-9]{10})")
	private String phoneNumber;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getSecurityCode() {
		return securityCode;
	}
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public UserMaster(String name, String role, String securityCode, String phoneNumber) {
		this.name = name;
		this.role = role;
		this.securityCode = securityCode;
		this.phoneNumber = phoneNumber;
	}
	public UserMaster() {
	}
	
}
