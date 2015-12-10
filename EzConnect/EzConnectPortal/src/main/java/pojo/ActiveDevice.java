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
	@Table(name = "Active_Device")
	
	public class ActiveDevice {
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name = "id")
		private int id;
		
		@Column(name = "IMEI")
		@NotNull
		private String IMEI;
		@Column(name = "IP_address ")
		private String IpAddress;
		@Column(name="created_date")
		@Temporal(TemporalType.TIMESTAMP)
		@NotNull
		private Calendar createdDate;
		
		
		
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getIMEI() {
			return IMEI;
		}
		public void setIMEI(String iMEI) {
			IMEI = iMEI;
		}
		public String getIpAddress() {
			return IpAddress;
		}
		public void setIpAddress(String ipAddress) {
			IpAddress = ipAddress;
		}
		public Calendar getCreatedDate() {
			return createdDate;
		}
		public void setCreatedDate(Calendar createdDate) {
			this.createdDate = createdDate;
		}
		public ActiveDevice(String iMEI, String ipAddress, Calendar createdDate) {
		

			IMEI = iMEI;
			IpAddress = ipAddress;
			this.createdDate = createdDate;
		}
		public ActiveDevice() {
			super();
		}

		

	

	}
	


