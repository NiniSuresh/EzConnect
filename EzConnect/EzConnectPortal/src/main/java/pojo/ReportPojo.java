package pojo;

import java.util.Calendar;

public class ReportPojo {
	String ezConnectId;
	String Callee1;
	String Callee2;
	String createdDate;
	String updatedStatus;
	int callDuration;
	int recording;

	public String getEzConnectId() {
		return ezConnectId;
	}

	public void setEzConnectId(String ezConnectId) {
		this.ezConnectId = ezConnectId;
	}

	public String getCallee1() {
		return Callee1;
	}

	public void setCallee1(String callee1) {
		Callee1 = callee1;
	}

	public String getCallee2() {
		return Callee2;
	}

	public void setCallee2(String callee2) {
		Callee2 = callee2;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedStatus() {
		return updatedStatus;
	}

	public void setUpdatedStatus(String updatedStatus) {
		this.updatedStatus = updatedStatus;
	}

	public int getCallDuration() {
		return callDuration;
	}

	public void setCallDuration(int callDuration) {
		this.callDuration = callDuration;
	}

	public int getRecording() {
		return recording;
	}

	public void setRecording(int recording) {
		this.recording = recording;
	}

	/*
	 * public ReportPojo(String ezConnectId, String callee1, String callee2,
	 * Calendar connectionTime, String updatedStatus, int callDuration, int
	 * recording) {
	 * 
	 * this.ezConnectId = ezConnectId; Callee1 = callee1; Callee2 = callee2;
	 * this.connectionTime = connectionTime; this.updatedStatus = updatedStatus;
	 * this.callDuration = callDuration; this.recording = recording; }
	 */
	public ReportPojo(String ezConnectId, String callee1, String callee2, Calendar createdDate, String staus,
			int callDuration, int recording) {
		this.ezConnectId = ezConnectId;
		Callee1 = callee1;
		Callee2 = callee2;
		this.createdDate = createdDate.get(Calendar.DATE) + "-" + createdDate.get(Calendar.MONTH) + "-"
				+ createdDate.get(Calendar.YEAR) + " " + + createdDate.get(Calendar.HOUR)+
				":"+createdDate.get(Calendar.MINUTE) +":"+createdDate.get(Calendar.SECOND);
		this.updatedStatus = staus;
		this.callDuration = callDuration;
		this.recording = recording;
	}

	public ReportPojo() {

	}

	public ReportPojo(String ezconnectId, String callee1, String callee2, Calendar createdDate, String staus) {
		this.ezConnectId = ezconnectId;
		Callee1 = callee1;
		Callee2 = callee2;
		this.updatedStatus = staus;
		this.createdDate = createdDate.get(Calendar.DATE) + "-" + createdDate.get(Calendar.MONTH) + "-"
				+ createdDate.get(Calendar.YEAR) + " " + + createdDate.get(Calendar.HOUR)+
				":"+createdDate.get(Calendar.MINUTE) +":"+createdDate.get(Calendar.SECOND);
		// TODO Auto-generated constructor stub
	}

}
