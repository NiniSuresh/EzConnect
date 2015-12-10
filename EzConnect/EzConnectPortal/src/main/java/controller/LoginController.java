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

import dao.CallConnection;
import dao.CallStateDAO;
import dao.UserMasterDAO;
import pojo.ActiveDevice;
import pojo.CallRequest;
import pojo.CallState;
import pojo.UserMaster;


@Controller
public class LoginController {
	private final Logger LOGGER = Logger.getLogger(LoginController.class.getName());
	@RequestMapping(value = "/connect", method = RequestMethod.POST)
	public ModelAndView uploadNumber(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = null;
		HttpSession session = request.getSession();
		String contactNumber1 = request.getParameter("contactNumber1");
		String contactNumber2 = request.getParameter("contactNumber2");
		UserMaster userMaster = (UserMaster) session.getAttribute("userMaster");
        
		try {
			CallConnection callConnection = new CallConnection();
			ActiveDevice activeDevice= callConnection.getActiveIp();
			StringTokenizer tk=new StringTokenizer(activeDevice.getIpAddress());
			String activeIp=tk.nextToken();
			String activePort=tk.nextToken();
			if(activeDevice.getId()!=0){
			
				CallRequest callRequest = callConnection.placeCall(activeIp,Integer.parseInt(activePort),userMaster, contactNumber1, contactNumber2);
				model = new ModelAndView("callPage.jsp");
				model.addObject("callRequest", callRequest);
			}else {
				String errorMessage = "There is no any active device!! Sorry for the inconvenience!!";
				model = new ModelAndView("input.jsp");
				model.addObject("errorMessage", errorMessage);
			}

		}

		catch (Exception e) {
			String errorMessage = "Phone is busy!! Sorry for the inconvenience!!";
			model = new ModelAndView("input.jsp");
	model.addObject("errorMessage", errorMessage);

		}
		return model;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView authenticator(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = null;
		String securityCode = request.getParameter("securityCode");
		HttpSession session = request.getSession();
		try {
			UserMasterDAO dao = new UserMasterDAO();
			UserMaster userMaster = null;
			userMaster = dao.userMasterExists(securityCode);
			if (userMaster != null) {
				String role;
				role = dao.getRole(userMaster);
				if (role.equals("HR")) {
					List<UserMaster> userMasterList = dao.getUserMasterList();
					model = new ModelAndView("input.jsp");
					model.addObject("userMaster",userMaster);
					session.setAttribute("userMaster",userMaster);
					session.setAttribute("userMasterId",userMaster.getId() );
					model.addObject("userMasterList",userMasterList);
				} else
				{String errorMessage = "You are not allowed!";
					model = new ModelAndView("index.jsp");
					model.addObject("errorMessage", errorMessage);
				}
			}
			else
			{
				String errorMessage = "Invalid Security Code";
				model = new ModelAndView("index.jsp");
				model.addObject("errorMessage", errorMessage);
			}
		} catch (Exception e) {
			String errorMessage = "Invalid Security Code";
			model = new ModelAndView("index.jsp");
			model.addObject("errorMessage", errorMessage);

		}
		return model;

	}

	@RequestMapping(value = "/status_update", method = RequestMethod.GET)
	public @ResponseBody String showStatus(@RequestParam("ezconnectId") String ezconnectId) {
		LOGGER.info("Within status update");
		CallStateDAO callStateDAO=new CallStateDAO();
		String status="Call connecting..";
		List<CallState> CallStateList=callStateDAO.getCallStateList(ezconnectId);
		if(CallStateList.size()>1)
		 status=CallStateList.get(CallStateList.size()-1).getStatus();
			return status;
	}

	


	@RequestMapping(value="/logout" , method=RequestMethod.GET/*,consumes=MediaType.APPLICATION_JSON_VALUE*/)
	  @ModelAttribute("userMaster")
	 public  ModelAndView loggingOut(HttpSession session){
	   ModelAndView mav = new ModelAndView("index.jsp");
	         session.invalidate();
	         return mav;
	 }
	
}
