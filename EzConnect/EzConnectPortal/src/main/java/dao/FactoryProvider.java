package dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FactoryProvider {
	 private static SessionFactory factory; 
	 public static SessionFactory getFactory(){
		 if(factory==null)
			 factory=new Configuration().configure()
			 				.addPackage("pojo").buildSessionFactory();
		 return factory;
	 }
}

