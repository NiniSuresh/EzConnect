package dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import controller.ReportController;
import pojo.CallData;
import pojo.CallRequest;
import pojo.CallState;
import pojo.ReportPojo;

public class ReportAccess {
	
	private final static Logger LOGGER = Logger.getLogger(ReportController.class.getName());
	public List<ReportPojo> reportAccessing()
	{
		CallRequestDAO callRequestDAO=new CallRequestDAO();
		
		List<CallRequest> callRequestList=callRequestDAO.getCallRequestList();
		List<ReportPojo> reportPojoList = new ArrayList<ReportPojo>();
		LOGGER.info(callRequestList.get(0).getCallee2()+":callRequestList have callee2");
			for(CallRequest m: callRequestList)
			{
			CallDataDAO callDataDAO=new CallDataDAO();
			CallStateDAO callStateDAO= new CallStateDAO();
			List<CallData> callDataList=callDataDAO.getCallDataList(m.getEzconnectId());
			String lastConnectionState=callStateDAO.getLastState(m.getEzconnectId());
				if(!callDataList.isEmpty()){
					CallData callData=callDataList.get(0);
					LOGGER.info(callDataList.get(0).getEzconnectId()+":getEzconnectId from matched callDataList");
					ReportPojo reportPojo=new ReportPojo(m.getEzconnectId(),m.getCallee1(),m.getCallee2(),m.getCreatedDate(),lastConnectionState,callData.getCallDuration(),callData.getRecording().length);
					reportPojoList.add(reportPojo);
					
					LOGGER.info(reportPojo.getEzConnectId()+"reportPojo entry for report list");
				}else{
				
					//LOGGER.info(callDataList.get(0).getEzconnectId()+":getEzconnectId from not found data list");
					ReportPojo reportPojo=new ReportPojo(m.getEzconnectId(),m.getCallee1(),m.getCallee2(),m.getCreatedDate(),lastConnectionState);
					//reportPojo.setCallDuration(0);
					//reportPojo.setCreatedDate(null);
					//reportPojo.setRecording(0);
					System.out.println(reportPojo.getEzConnectId()+"reportPojo entry for report list"+reportPojo.getCallee2()+reportPojo.getRecording()+":recording");
					LOGGER.info(reportPojo.getEzConnectId()+"reportPojo entry for report list without datalist"+reportPojoList.size()+":size of list");
					reportPojoList.add(reportPojo);
					LOGGER.info(reportPojo.getEzConnectId()+"reportPojo entry for report list without datalist");
				}
		}
		LOGGER.info(reportPojoList.size()+":size of list");
		return reportPojoList;

	}
	public List<ReportPojo> userReportAccessing(int userMasterId)
	{
		CallRequestDAO callRequestDAO=new CallRequestDAO();
		
		List<CallRequest> callRequestList=callRequestDAO.getUserCallRequestList(userMasterId);
		List<ReportPojo> reportPojoList = new ArrayList<ReportPojo>();
		if(callRequestList.size()>0)LOGGER.info(callRequestList.get(0).getCallee2()+":callRequestList have callee2");
			for(CallRequest m: callRequestList)
			{
			CallDataDAO callDataDAO=new CallDataDAO();
			CallStateDAO callStateDAO= new CallStateDAO();
			List<CallData> callDataList=callDataDAO.getCallDataList(m.getEzconnectId());
			String lastConnectionState=callStateDAO.getLastState(m.getEzconnectId());
				if(!callDataList.isEmpty()){
					CallData callData=callDataList.get(0);
					LOGGER.info(callDataList.get(0).getEzconnectId()+":getEzconnectId from matched callDataList");
					ReportPojo reportPojo=new ReportPojo(m.getEzconnectId(),m.getCallee1(),m.getCallee2(),m.getCreatedDate(),lastConnectionState,callData.getCallDuration(),callData.getRecording().length);
					reportPojoList.add(reportPojo);
					
					LOGGER.info(reportPojo.getEzConnectId()+"reportPojo entry for report list");
				}else{
				
					//LOGGER.info(callDataList.get(0).getEzconnectId()+":getEzconnectId from not found data list");
					ReportPojo reportPojo=new ReportPojo(m.getEzconnectId(),m.getCallee1(),m.getCallee2(),m.getCreatedDate(),lastConnectionState);
					//reportPojo.setCallDuration(0);
					//reportPojo.setCreatedDate(null);
					//reportPojo.setRecording(0);
					System.out.println(reportPojo.getEzConnectId()+"reportPojo entry for report list"+reportPojo.getCallee2()+reportPojo.getRecording()+":recording");
					LOGGER.info(reportPojo.getEzConnectId()+"reportPojo entry for report list without datalist"+reportPojoList.size()+":size of list");
					reportPojoList.add(reportPojo);
					LOGGER.info(reportPojo.getEzConnectId()+"reportPojo entry for report list without datalist");
				}
		}
		LOGGER.info(reportPojoList.size()+":size of list");
		return reportPojoList;

	}
	
	
}
