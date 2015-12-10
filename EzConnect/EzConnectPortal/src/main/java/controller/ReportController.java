package controller;



import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


import com.google.gson.Gson;
import com.google.gson.JsonObject;

import dao.CallDataDAO;
import dao.ReportAccess;
import pojo.CallData;
import pojo.ReportPojo;


@Controller
public class ReportController {
	private final static Logger LOGGER = Logger.getLogger(ReportController.class.getName());
	@RequestMapping(value = "/report", method = RequestMethod.POST)
	public ModelAndView authenticator(HttpServletRequest request, HttpServletResponse response) {
		

		ModelAndView model = null;
		ReportAccess reportAccess=new ReportAccess();
		 List<ReportPojo> reportPojoList=reportAccess.reportAccessing();
		 LOGGER.info( reportPojoList.get(1).getCallee1()+ "Callee1 " + reportPojoList.get(1).getCallDuration() + " :CallDuration");
		 model = new ModelAndView("pages/tables.html");
		// model = new ModelAndView("report.jsp");
			model.addObject("reportPojoList",reportPojoList);
			
			return model;

		} 
	
	@RequestMapping(value = "/report", method = RequestMethod.GET)
	public ModelAndView dataList() {
		
		 Gson gson = new Gson();
		 
		ModelAndView model = null;
		//ReportAccess reportAccess=new ReportAccess();
		 //List<ReportPojo> reportPojoList=reportAccess.reportAccessing();
		 //String json = gson.toJson(reportPojoList);
		 //LOGGER.info( reportPojoList.get(1).getCallee1()+ "Callee1 " + reportPojoList.get(1).getCallDuration() + " :CallDuration"+json);
		 model = new ModelAndView("pages/tables.html");
		 //model.addObject(json);
			//model.addObject("reportPojoList",reportPojoList);
			
			return model;

		}
	
	
	
	@RequestMapping(value = "/report/downloadMedia/{callId}", method = RequestMethod.GET)
	public void downLoad(@PathVariable (value="callId") String callId,HttpServletRequest request, HttpServletResponse response) {
		
		  
		 LOGGER.info(callId+ ": callId ");
		 CallDataDAO callDataDAO=new CallDataDAO();
		 
		 
		 //LOGGER.info(callData.getCallDuration()+": call duration for entry");
		 if(callDataDAO.getCallDataList(callId).size() != 0){
			    response.setContentType("audio/mpeg3");
			    CallData callData=callDataDAO.getCallDataList(callId).get(0);
			    response.setHeader("Content-Disposition", "attachment;filename=" + callId+".mp3");
			    try {
			    	LOGGER.warning(callData.getRecording()+ ": callData Recording content ");
					response.getOutputStream().write(callData.getRecording());
				} catch (IOException e) {
					LOGGER.warning(callData.getRecording()+ ": callData Recording content ");
					e.printStackTrace();
				}
		  }else{
//			  JOptionPane.showMessageDialog(null,
//					    "media files not found");
			 			  JOptionPane.showMessageDialog(null,
					    "Media file not found",
					    "warning",
					    JOptionPane.WARNING_MESSAGE);
			  
					try {
						response.sendRedirect("/EzConnectPortal/report");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						LOGGER.warning("not redirected");
					}
				
				
			  
		  }
	}

		
	
	
	@RequestMapping(value="/jsonReturn", method=RequestMethod.GET)
	 public @ResponseBody String byParameter() {
	     //Perform logic with foo
	     /*return "Mapped by path + me" + name;*/
		 
		Gson gson = new Gson();
		 ReportAccess reportAccess=new ReportAccess();
		 List<ReportPojo> reportPojoList=reportAccess.reportAccessing();
		String json = gson.toJson(reportPojoList);
		// LOGGER.warning( "JSON return"+reportPojoList.get(1).getCallee1()+ "Callee1 " + reportPojoList.get(1).getCallDuration() + " :CallDuration");
		 return json;
	 }
	
	

	}

