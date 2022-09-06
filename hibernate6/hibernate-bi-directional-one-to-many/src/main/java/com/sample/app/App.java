package com.sample.app;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.sample.app.entity.BankAccount;
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

		try (Session session = sessionFactory.openSession()) {

			session.beginTransaction();

			BankAccount account1 = new BankAccount();
			account1.setAccountNumber("number1");
			account1.setAddress("address1");
			account1.setBranch("branch1");
			account1.setId(1);
			account1.setIfscCode("ifsc1");

			BankAccount account2 = new BankAccount();
			account2.setAccountNumber("number2");
			account2.setAddress("address2");
			account2.setBranch("branch2");
			account2.setId(2);
			account2.setIfscCode("ifsc2");

			session.persist(account1);
			session.persist(account2);

			session.getTransaction().commit();

			session.beginTransaction();

			Employee emp1 = new Employee();
			emp1.setFirstName("Krishna");
			emp1.setLastName("Gurram");
			emp1.setId(1);

			BankAccount persistedAccount1 = session.find(BankAccount.class, 1);
			BankAccount persistedAccount2 = session.find(BankAccount.class, 2);
			emp1.addBankAccount(persistedAccount1);
			emp1.addBankAccount(persistedAccount2);

			session.persist(emp1);
			session.flush();
			session.getTransaction().commit();

			List<Employee> emps = loadAllData(Employee.class, session);
			for (Employee empTemp : emps) {
				System.out.println(empTemp);
			}

		}

	}
}
