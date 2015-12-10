package pojo;

import java.util.Calendar;

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
@Table(name="Call_State")
public class CallState {
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
	@Column(name="status")
	@NotNull
	private  String status;
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public CallState(Calendar createdDate, String ezconnectId, String status) {
		
		this.createdDate = createdDate;
		this.ezconnectId = ezconnectId;
		this.status = status;
	}
	public CallState() {
	
	}
	
}
