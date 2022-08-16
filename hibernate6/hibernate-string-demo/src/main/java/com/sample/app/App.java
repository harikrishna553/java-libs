package com.sample.app;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.sample.app.entity.StringTypeDemo;

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

	private static <T> List<T> loadAllData(Class<T> type, Session session) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(type);
		criteria.from(type);
		List<T> data = session.createQuery(criteria).getResultList();
		return data;
	}

	public static void main(final String args[]) {

		String str1 = "Hello!!!";
		StringTypeDemo p1 = new StringTypeDemo(1, str1, str1, str1, str1, str1, str1);

		try (final Session session = SESSION_FACTORY.openSession()) {
			session.beginTransaction();

			session.persist(p1);
			session.flush();
			session.getTransaction().commit();

			List<StringTypeDemo> records = loadAllData(StringTypeDemo.class, session);
			records.forEach(System.out::println);
		}

	}
}