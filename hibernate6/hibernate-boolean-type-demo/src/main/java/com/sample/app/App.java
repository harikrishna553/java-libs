package com.sample.app;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.sample.app.entity.BooleanDemo;

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

		BooleanDemo p1 = new BooleanDemo(1, true, true, true, true);
		BooleanDemo p2 = new BooleanDemo(2, false, false, false, false);

		try (final Session session = SESSION_FACTORY.openSession()) {
			session.beginTransaction();

			session.persist(p1);
			session.persist(p2);

			session.getTransaction().commit();

		}

	}
}