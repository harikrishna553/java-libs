package com.sample.app;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import com.sample.app.entity.Author;
import com.sample.app.entity.Book;
import com.sample.app.entity.Publisher;

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

		Author author1 = new Author(1, "Krishna");
		Publisher publisher1 = new Publisher(1, "blogger");

		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			session.persist(author1);
			session.persist(publisher1);
			session.flush();
			session.getTransaction().commit();

			Author persistedAuthor = session.find(Author.class, 1);
			Publisher persistedPublisher = session.find(Publisher.class, 1);

			session.beginTransaction();
			Book book = new Book("Programming for beginners", persistedAuthor, persistedPublisher);
			session.persist(book);
			session.flush();
			session.getTransaction().commit();

			List<Book> books = loadAllData(Book.class, session);
			for (Book book1 : books) {
				System.out.println(book1);
				System.out.println(book1.getAuthor());
				System.out.println(book1.getPublisher());
			}
		}

	}
}
