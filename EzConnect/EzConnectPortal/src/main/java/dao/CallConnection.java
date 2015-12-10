package dao;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import pojo.ActiveDevice;
import pojo.CallRequest;
import pojo.UserMaster;

public class CallConnection {
	
	private final static Logger LOGGER = Logger.getLogger(CallConnection.class.getName());
	protected SessionFactory factory = FactoryProvider.getFactory();

	
	public CallRequest placeCall(String clientIp,int clientPort,UserMaster userMaster,String callee1,String callee2) throws UnknownHostException, IOException,SocketException
	{
		Socket socket;
		CallRequest callRequest=new CallRequest();
	
			socket = new Socket(clientIp,clientPort);
		
		OutputStream outstream = socket.getOutputStream(); 
	    PrintWriter out = new PrintWriter(outstream);
	    CallRequestDAO  callRequestdao =new CallRequestDAO();
		
	callRequest= callRequestdao.enterDetails(userMaster,callee1,callee2);
		
	    String toSend =callRequest.getEzconnectId()+" "+callRequest.getCallee1()+" "+callRequest.getCallee2();

	    out.print(toSend );
	  out.flush();
	  return callRequest;

		 /*catch (SocketException e) {
			
			 return callRequest;
			 
		 }
	 catch (UnknownHostException e) {
		 return callRequest;
		
	} catch (IOException e) {
		return callRequest;
	}*/
				
}
	
	
	public boolean createConnection(String clientIMEI,String clientIP,String sPort) {
		LOGGER.info(clientIMEI+" :clientIMEI"+clientIP+"client Ip "+ sPort +" :Port String");

		int port=Integer.parseInt(sPort);
		Session session = factory.openSession();
		ActiveDevice activeDevice=new ActiveDevice(clientIMEI, clientIP+" "+port,Calendar.getInstance());

		Transaction transaction = session.beginTransaction();
		try {
			session.save(activeDevice);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			return false;
		}
		finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}

		return true;


	}	
	
	public ActiveDevice getActiveIp() {
		  Session session=factory.openSession();
		  String sql = "FROM ActiveDevice ";
		  Query query = session.createQuery(sql);
		  
		 
		  List<ActiveDevice> activeDeviceList =query.list();
		  session.close();
		  ActiveDevice activeDevice=activeDeviceList.get(activeDeviceList.size()-1);
		  LOGGER.info(activeDevice.getCreatedDate().getTime()+": object and size of list:"+activeDeviceList.size());
		  return activeDevice;
		  }
		
}
