package com.sample.app;

import java.io.IOException;
import java.net.URISyntaxException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.sample.app.entity.Person;

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

	public static void main(final String args[]) throws ClassNotFoundException, IOException, URISyntaxException {

		Person person1 = new Person("Krishna", 23, "I am a traveller");
		Person person2 = new Person("Ram", 31, "blogger, Entrepreneur, content writer");

		try (final Session session = SESSION_FACTORY.openSession()) {
			session.beginTransaction();
			session.persist(person1);
			session.persist(person2);
			session.getTransaction().commit();
		}

	}
}