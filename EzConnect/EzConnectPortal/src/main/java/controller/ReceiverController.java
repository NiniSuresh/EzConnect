package controller;
import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import dao.CallConnection;
import dao.CallDataDAO;
import dao.CallStateDAO;



@Controller
public class ReceiverController {
	
	private final Logger LOGGER = Logger.getLogger(ReceiverController.class.getName());

	@RequestMapping(value = "/ipReceive", method = RequestMethod.POST)
	public  void listenIp(HttpServletRequest request, HttpServletResponse response) {
		
		String clientIP = request.getParameter("clientIp");
		String clientPort = request.getParameter("clientPort");
		String clientIMEI = request.getParameter("clientImei");
		LOGGER.info(clientIP + ":ipAddress " + clientIMEI + " :IMEI"+clientPort + " :port"+request.getRemoteAddr()+":Received ip");
	    CallConnection callConnection=new CallConnection();
		//callConnection.createConnection(clientIMEI,clientIP,clientPort);
	    callConnection.createConnection(clientIMEI,request.getRemoteAddr(),clientPort);
	}
	
	@RequestMapping(value = "/stateReceive", method = RequestMethod.POST)
	public void listenStatus(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = null;
		String ezconnectId= request.getParameter("uuid");
		String status = request.getParameter("state");
		 CallStateDAO dao=new  CallStateDAO();
		dao.saveStatus(ezconnectId, status);
		
		
	}

	@RequestMapping(value = "/callData", method = RequestMethod.POST)
	public void storeData(HttpServletRequest request, HttpServletResponse response, @RequestParam(value="recording",required=false) MultipartFile recording) throws IOException {
	
		String ezconnectId= request.getParameter("uuid");
		String timeDuration = request.getParameter("callDuration");
		byte[] input=null;
		if(recording!=null){
		input=recording.getBytes();
		}
		
		int callDuration=Integer.parseInt(timeDuration);
		CallDataDAO callDatadao=new CallDataDAO();
		callDatadao.saveRecord(ezconnectId,callDuration,input);
		
	}
}