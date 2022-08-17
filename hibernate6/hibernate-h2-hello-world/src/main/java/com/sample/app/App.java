package com.sample.app;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.sample.app.entity.Employee;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

public class App {
	private static SessionFactory sessionFactory = buildSessionFactory();

	private static <T> List<T> loadAllData(Class<T> clazz, Session session) {
		final CriteriaBuilder builder = session.getCriteriaBuilder();
		final CriteriaQuery<T> criteria = builder.createQuery(clazz);
		criteria.from(clazz);
		return session.createQuery(criteria).getResultList();
	}

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

		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			session.persist(emp1);
			session.persist(emp2);
			session.persist(emp3);
			session.persist(emp4);

			List<Employee> emps = loadAllData(Employee.class, session);
			for (Employee emp : emps) {
				System.out.println(emp);
			}

			session.getTransaction().commit();
		}

	}
}
