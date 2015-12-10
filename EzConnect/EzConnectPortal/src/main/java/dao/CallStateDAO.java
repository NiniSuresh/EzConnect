package dao;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import controller.ReportController;
import pojo.CallState;

public class CallStateDAO {
	protected SessionFactory factory = FactoryProvider.getFactory();
	private final static Logger LOGGER = Logger.getLogger(ReportController.class.getName());

	 public CallState saveStatus(String ezconnectId,String status)
	 {
		 Session session = factory.openSession();
				 CallState callState=new CallState(Calendar.getInstance(),ezconnectId,status);
	
	 Transaction transaction = session.beginTransaction();
	 try {
			session.save(callState);
			transaction.commit();
		} catch (Exception e) {
			    e.printStackTrace();
				transaction.rollback();
		}
		finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	
	 return callState;
		 
	 }
	 public List<CallState> getCallStateList(String ezconnectId) {
		  Session session=factory.openSession();
		  String sql = "FROM CallState  where ezconnectId= :ezconnectId ";
		  Query query = session.createQuery(sql);
		  query.setParameter("ezconnectId", ezconnectId);
		 
		  List<CallState> callStateList =query.list();
		  session.close();
		  return callStateList;
		  }
	 
	 
		
	 public String getLastState(String ezconnectId) {
		    Session session=factory.openSession();
		    String sql = "FROM CallState  where ezconnectId= :ezconnectId ";
		    Query query = session.createQuery(sql);
		    query.setParameter("ezconnectId", ezconnectId);
		   
		    List<CallState> callStateList =query.list();
		    session.close();
		    if(callStateList.isEmpty())return ("STATE_NOT_FOUND");
		    LOGGER.info(callStateList.size()-1+": callStateList.size()"+callStateList.get(callStateList.size()-1)+":callStateList.get(callStateList.size()-1)");
		    return callStateList.get(callStateList.size()-1).getStatus();
		    }
}
