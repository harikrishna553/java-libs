package com.sample.app;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.sample.app.entity.Address;
import com.sample.app.entity.Name;
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

		final List<Address> person1Addresses = Arrays.asList(
				new Address("Chowdeswari Street", "Bangalore", "Karnataka", "12345", "India"),
				new Address("Auto Nagar", "Vijayawada", "Andhra Pradesh", "234567", "India"));

		final List<Address> person2Addresses = Arrays.asList(
				new Address("Panchayat street", "Chennai", "Tamilanadu", "345678", "India"),
				new Address("Gandhi Nagar", "Hyderabad", "Telangana", "456789", "India"));

		final Person person1 = new Person(1, new Name("Krishna", null, "G"), 34, person1Addresses);
		final Person person2 = new Person(2, new Name("Ram", "krishna", "Geeta"), 39, person2Addresses);

		try (final Session session = SESSION_FACTORY.openSession()) {
			session.beginTransaction();
			session.persist(person1);
			session.persist(person2);
			session.getTransaction().commit();
		}

	}
}