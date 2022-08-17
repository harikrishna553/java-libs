package com.sample.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.NClob;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.engine.jdbc.BlobProxy;
import org.hibernate.engine.jdbc.ClobProxy;
import org.hibernate.engine.jdbc.NClobProxy;

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
		// Create a Clob using ClobProxy.
		String msg = "Hello World";
		Clob clob = ClobProxy.generateProxy(msg);
		NClob nclob = NClobProxy.generateProxy(msg);

		byte[] byteMsg = "Hello World".getBytes();
		Blob blob = BlobProxy.generateProxy(byteMsg);

		Demo e1 = new Demo(1, clob, nclob, blob);

		try (final Session session = SESSION_FACTORY.openSession()) {
			session.beginTransaction();

			session.persist(e1);

			session.flush();
			session.getTransaction().commit();

			List<Demo> records = loadAllData(Demo.class, session);

			for (Demo record : records) {
				int id = record.getId();
				System.out.println("id : " + id);

				try (InputStream is = record.getBlob().getBinaryStream()) {
					String blobStr = new BufferedReader(new InputStreamReader(is)).lines()
							.collect(Collectors.joining("\n"));
					System.out.println("blobStr : " + blobStr);
				}

				try (Reader reader = record.getClob().getCharacterStream()) {
					String clobStr = new BufferedReader(reader).lines().collect(Collectors.joining("\n"));
					System.out.println("clobStr : " + clobStr);
				}

				try (Reader reader = record.getNclob().getCharacterStream()) {
					String nclobStr = new BufferedReader(reader).lines().collect(Collectors.joining("\n"));
					System.out.println("nclobStr : " + nclobStr);
				}
			}
		}

	}
}
