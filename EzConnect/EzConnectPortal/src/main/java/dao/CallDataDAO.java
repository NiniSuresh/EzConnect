package dao;
import java.util.Calendar;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import pojo.CallData;


public class CallDataDAO {
	protected SessionFactory factory = FactoryProvider.getFactory();

	public CallData saveRecord(String ezconnectId, int callDuration, byte[] recording) {
		Session session = factory.openSession();
		
		CallData callData = new CallData(Calendar.getInstance(),ezconnectId,callDuration,recording);
		Transaction transaction = session.beginTransaction();
		try {
			session.save(callData);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		return callData;
	}
	public List<CallData> getCallDataList(String ezconnectId) {
		Session session=factory.openSession();
		String sql = "FROM CallData  where ezconnectId= :ezconnectId ";
		Query query = session.createQuery(sql);
		query.setParameter("ezconnectId", ezconnectId);
	
		List<CallData> callDataList =query.list();
		session.close();
		return callDataList;
		}
}
