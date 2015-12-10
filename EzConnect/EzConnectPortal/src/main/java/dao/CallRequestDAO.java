package dao;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pojo.CallRequest;
import pojo.UserMaster;

public class CallRequestDAO {
	 protected SessionFactory factory = FactoryProvider.getFactory();
public CallRequest enterDetails(UserMaster userMaster, String callee1, String callee2) {

	CallRequest callRequest = new CallRequest(Calendar.getInstance(),UUID.randomUUID().toString(),callee1,callee2,userMaster.getId());
	Session session = factory.openSession();

	Transaction transaction = session.beginTransaction();
	try {
		session.save(callRequest);
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
	return callRequest;
		
	}
public List<CallRequest> getCallRequestList() {

	
Session session = factory.openSession();		
List<CallRequest> callRequestList= session.createQuery("from CallRequest ").list();
session.close();
return callRequestList;
}

public List<CallRequest> getUserCallRequestList(int userMasterId) {

	
	Session session = factory.openSession();		
	List<CallRequest> callRequestList= session.createQuery("from CallRequest where userId= "+userMasterId).list();
	session.close();
	return callRequestList;
}

}

