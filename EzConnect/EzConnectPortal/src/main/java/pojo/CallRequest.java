package pojo;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Call_Request")
public class CallRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id", unique=true,nullable = false)
	private  int id;
	@Column(name="created_date")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Calendar createdDate;
	@Column(name="ezconnect_id")
	@NotNull
	private String ezconnectId;
	@Column(name="callee_1")
	@NotNull
	private String callee1;
	@Column(name="callee_2")
	@NotNull
	private String callee2;
	@Column(name="user_id")
	@NotNull
	private  int userId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Calendar getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}
	public String getEzconnectId() {
		return ezconnectId;
	}
	public void setEzconnectId(String ezconnectId) {
		this.ezconnectId = ezconnectId;
	}
	public String getCallee1() {
		return callee1;
	}
	public void setCallee1(String callee1) {
		this.callee1 = callee1;
	}
	public String getCallee2() {
		return callee2;
	}
	public void setCallee2(String callee2) {
		this.callee2 = callee2;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public CallRequest(Calendar createdDate, String ezconnectId, String callee1, String callee2, int userId) {
		
		this.createdDate = createdDate;
		this.ezconnectId = ezconnectId;
		this.callee1 = callee1;
		this.callee2 = callee2;
		this.userId = userId;
	}
	public CallRequest() {
		
	}
	
}
