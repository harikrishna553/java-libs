package com.sample.app;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.sample.app.dto.Employee;
import com.sample.app.entity.SerializedData;
import com.sample.app.util.SerializationUtil;

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

	public static void main(String args[]) throws ClassNotFoundException, IOException, URISyntaxException {

		Employee emp1 = new Employee(1, "Krishna");

		byte[] empByteArray = SerializationUtil.serialize(emp1);

		SerializedData d1 = new SerializedData(1, Employee.class, empByteArray);

		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			session.persist(d1);
			session.flush();
			session.getTransaction().commit();

			List<SerializedData> serializedElements = loadAllData(SerializedData.class, session);
			for (SerializedData serializedData : serializedElements) {

				Class clazz = serializedData.getClazz();
				byte[] serializedByteArray = serializedData.getSerializedData();

				System.out.println("id : " + serializedData.getId());
				System.out.println("class : " + clazz);
				System.out.println("object : " + SerializationUtil.deserialize(serializedByteArray, clazz));
			}

		}

	}
}
