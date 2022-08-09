package com.sample.app;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.sample.app.entity.Account;

public class App {
	private static final SessionFactory SESSION_FACTORY = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {

			final StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
					.configure("hibernate.cfg.xml").build();

			final Metadata metaData = new MetadataSources(standardRegistry).getMetadataBuilder().build();

			return metaData.getSessionFactoryBuilder().build();

		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}

	}

	public static void main(final String args[]) {

		Account account = new Account(1, 1000000.0, 2.3, 2.0);

		try (final Session session = SESSION_FACTORY.openSession()) {
			session.beginTransaction();

			session.persist(account);

			// Since entity is cached on persist, lets flush and refresh the entity to
			// refetch the data from database.
			session.flush();
			session.refresh(account);

			session.getTransaction().commit();

			Account persistedAccount = session.find(Account.class, 1);

			System.out.println("prinicpal : " + persistedAccount.getPrincipal());
			System.out.println("timeInYears : " + persistedAccount.getTimeInYears());
			System.out.println("rateOfInrest : " + persistedAccount.getRateOfIntrest());
			System.out.println("interestToPay : " + persistedAccount.getInterestToPay());

		}

	}
}