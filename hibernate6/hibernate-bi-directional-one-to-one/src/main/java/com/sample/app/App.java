package com.sample.app;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.sample.app.entity.Employee;
import com.sample.app.entity.EmployeeDetails;

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

		try (Session session = sessionFactory.openSession()) {

			Employee emp = new Employee(1, "Krishna");

			session.beginTransaction();
			session.persist(emp);
			session.flush();
			session.getTransaction().commit();

			session.beginTransaction();
			Employee persistedEmployee = session.find(Employee.class, 1);

			EmployeeDetails empDetails1 = new EmployeeDetails(123, "Aerospace", persistedEmployee);
			persistedEmployee.setEmployeeDetails(empDetails1);
			session.persist(empDetails1);
			session.flush();
			session.getTransaction().commit();

			List<Employee> employees = loadAllData(Employee.class, session);
			for (Employee emp1 : employees) {
				System.out.println(emp1);
			}

		}

	}
}
