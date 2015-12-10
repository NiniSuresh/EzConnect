package controller;

import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import dao.CallConnection;
import dao.CallStateDAO;
import dao.ReportAccess;
import dao.UserMasterDAO;
import pojo.ActiveDevice;
import pojo.CallRequest;
import pojo.CallState;
import pojo.ReportPojo;
import pojo.UserMaster;


@Controller
public class UserProfileController {
	private final Logger LOGGER = Logger.getLogger(LoginController.class.getName());
	@RequestMapping(value = "/userProfile", method = RequestMethod.GET)
public ModelAndView authenticator(HttpServletRequest request, HttpServletResponse response) {
		

		ModelAndView model = null;
		model = new ModelAndView("pages/userTable.html");
		return model;

		} 
	
	@RequestMapping(value="/UserJsonReturn", method=RequestMethod.GET)
	 public @ResponseBody String byParameter(HttpServletRequest request, HttpServletResponse response) {
	     //Perform logic with foo
	     /*return "Mapped by path + me" + name;*/
		ReportAccess reportAccess=new ReportAccess();
		HttpSession session = request.getSession();
		LOGGER.info(Integer.parseInt(session.getAttribute("userMasterId").toString())+":userMASTERid ");
		 List<ReportPojo> reportPojoList=reportAccess.userReportAccessing(Integer.parseInt(session.getAttribute("userMasterId").toString()));
		// if(reportPojoList.size()>0)LOGGER.info( reportPojoList.get(1).getCallee1()+ "Callee1 " + reportPojoList.get(1).getCallDuration() + " :CallDuration");
		Gson gson = new Gson();
		
		
		String json = gson.toJson(reportPojoList);
		// LOGGER.warning( "JSON return"+reportPojoList.get(1).getCallee1()+ "Callee1 " + reportPojoList.get(1).getCallDuration() + " :CallDuration");
		 return json;
	 }
	
}
