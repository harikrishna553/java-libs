package com.sample.app;

import java.io.IOException;
import java.net.URISyntaxException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.sample.app.entity.Contact;
import com.sample.app.entity.Name;

public class HelloWorld {
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

	public static void main(final String args[]) throws ClassNotFoundException, IOException, URISyntaxException {
		final Contact contact1 = new Contact(1, new Name("Krishna", null, "G"), 34);
		final Contact contact2 = new Contact(2, new Name("Ram", "krishna", "Geeta"), 39);

		try (final Session session = SESSION_FACTORY.openSession()) {
			session.beginTransaction();
			session.persist(contact1);
			session.persist(contact2);
			session.getTransaction().commit();
		}

	}
}