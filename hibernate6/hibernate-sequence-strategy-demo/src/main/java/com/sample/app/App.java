package com.sample.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	public static void main(String args[]) {

		Map<String, Object> config = new HashMap<>();
		config.put("maxThreads", 10);
		config.put("storagePath", "/Users/krishna/my-docs");

		Employee emp1 = new Employee("Krishna");
		Employee emp2 = new Employee("Ram");

		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			session.persist(emp1);
			session.persist(emp2);
			session.flush();
			session.getTransaction().commit();

			List<Employee> emps = loadAllData(Employee.class, session);
			for (Employee emp : emps) {
				System.out.println(emp);

			}

		}

	}
}
