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
@Table(name = "Call_Data")
public class CallData {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	@Column(name = "created_date", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar createdDate;
	@Column(name = "ezconnect_id")
	@NotNull
	private String ezconnectId;
	@Column(name = "call_duration")
	@NotNull
	private int callDuration;
	@Column(name = "recording ")
	private byte[] recording;

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

	public int getCallDuration() {
		return callDuration;
	}

	public void setCallDuration(int callDuration) {
		this.callDuration = callDuration;
	}

	public byte[] getRecording() {
		return recording;
	}

	public void setRecording(byte[] recording) {
		this.recording = recording;
	}

	public CallData(Calendar createdDate, String ezconnectId, int callDuration, byte[] recording) {

		this.createdDate = createdDate;
		this.ezconnectId = ezconnectId;
		this.callDuration = callDuration;
		this.recording = recording;
	}

	public CallData() {

	}

}