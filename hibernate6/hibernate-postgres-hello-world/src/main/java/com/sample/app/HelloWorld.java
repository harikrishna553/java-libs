package com.sample.app;

import java.io.IOException;
import java.net.URISyntaxException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.sample.app.entity.Employee;

public class HelloWorld {
	private static SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			if (sessionFactory == null) {
				StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
						.configure("hibernate.cfg.xml").build();

				Metadata metaData = new MetadataSources(standardRegistry).getMetadataBuilder().build();

				sessionFactory = metaData.getSessionFactoryBuilder().build();
			}
			return sessionFactory;
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static void main(String args[]) throws ClassNotFoundException, IOException, URISyntaxException {
		Employee emp1 = new Employee(1, "Krishna", "G", "Senior Software Developer", 26);
		Employee emp2 = new Employee(2, "Shreyas", "Desai", "Team Manager", 35);
		Employee emp3 = new Employee(3, "Piyush", "Rai", "Senior Software Developer", 26);
		Employee emp4 = new Employee(4, "Maruti", "Borker", "Software Developer", 26);

		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.persist(emp1);
		session.persist(emp2);
		session.persist(emp3);
		session.persist(emp4);
		session.getTransaction().commit();
		session.close();
	}
}