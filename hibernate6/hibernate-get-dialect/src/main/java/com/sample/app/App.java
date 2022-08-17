package com.sample.app;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.NationalizationSupport;
import org.hibernate.engine.spi.SessionFactoryImplementor;

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
	
	private static void printNationalizationSupportInfo(Dialect dialect) {
		NationalizationSupport nationalizationSupport = dialect.getNationalizationSupport();
		
		System.out.println("\n\nnationalizationSupport.getCharVariantCode() : " + nationalizationSupport.getCharVariantCode());
		System.out.println("nationalizationSupport.getClobVariantCode() : " + nationalizationSupport.getClobVariantCode());
		System.out.println("nationalizationSupport.getLongVarcharVariantCode() : " + nationalizationSupport.getLongVarcharVariantCode());
		System.out.println("nationalizationSupport.getVarcharVariantCode() : " + nationalizationSupport.getVarcharVariantCode());

	}

	public static void main(final String args[]) {
		Dialect dialect1 = ((SessionFactoryImplementor) SESSION_FACTORY).getJdbcServices().getDialect();
		printNationalizationSupportInfo(dialect1);
		
		try(Session session = SESSION_FACTORY.openSession()){
			Dialect dialect2 = ((SessionFactoryImplementor) session.getSessionFactory()).getJdbcServices().getDialect();
			printNationalizationSupportInfo(dialect2);
		}
		

	}
}