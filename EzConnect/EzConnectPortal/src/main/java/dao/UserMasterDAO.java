package dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import pojo.CallData;
import pojo.UserMaster;

public class UserMasterDAO {
	 protected SessionFactory factory = FactoryProvider.getFactory();
		public UserMaster userMasterExists(String securityCode) {

			{
				  Session session = factory.openSession();
					String sql = "FROM UserMaster where securityCode= :securityCode";
					Query query = session.createQuery(sql);
					query.setParameter("securityCode", securityCode);
					List<UserMaster> results=query.list();
					Iterator<UserMaster> iterator = results.iterator();
					session.close();
					if (iterator.hasNext())
						return (UserMaster) iterator.next();
					return null;
				
			  }
		}
		public String getRole(UserMaster userMaster) {
			String role=userMaster.getRole();
			return role;
			
		}
		public UserMaster  enterUserMaster (String name,String role,String code,String phoneNumber) {

			
			UserMaster callDetailsLog = new UserMaster(name,role,code,phoneNumber);
			Session session = factory.openSession();

			Transaction transaction = session.beginTransaction();
			try {
				session.save(callDetailsLog);
				transaction.commit();
			} catch (Exception e) {
					transaction.rollback();
			}
			finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
			return callDetailsLog;
				
			}
		public List<UserMaster> getUserMasterList() {

			
			Session session = factory.openSession();		
			List<UserMaster> userMasterList= session.createQuery("from UserMaster").list();
			session.close();
			return userMasterList;
			}
}
