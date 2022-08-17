package com.sample.app;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.sample.app.entity.Demo;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

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

	private static <T> List<T> loadAllData(Class<T> clazz, Session session) {
		final CriteriaBuilder builder = session.getCriteriaBuilder();
		final CriteriaQuery<T> criteria = builder.createQuery(clazz);
		criteria.from(clazz);
		return session.createQuery(criteria).getResultList();
	}

	public static void main(final String args[]) throws IOException, SQLException {
		String msg = "Hello World";
		byte[] data = msg.getBytes();

		Demo e1 = new Demo(1, data);

		try (final Session session = SESSION_FACTORY.openSession()) {
			session.beginTransaction();

			session.persist(e1);

			session.flush();
			session.getTransaction().commit();

			List<Demo> records = loadAllData(Demo.class, session);

			for (Demo record : records) {
				int id = record.getId();
				System.out.println("id : " + id);

				byte[] arr1 = record.getArr1();

				System.out.println("arr1 : " + new String(arr1));
			}
		}

	}
}
