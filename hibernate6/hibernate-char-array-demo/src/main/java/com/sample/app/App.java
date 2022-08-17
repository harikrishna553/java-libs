package com.sample.app;

import java.sql.Types;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.NationalizationSupport;
import org.hibernate.engine.spi.SessionFactoryImplementor;

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

    public static void main(final String args[]) {
        Dialect dialect = ((SessionFactoryImplementor) SESSION_FACTORY).getJdbcServices().getDialect();
        NationalizationSupport nationalizationSupport = dialect.getNationalizationSupport();

        System.out.println("Types.CHAR : " + Types.CHAR);
        System.out.println("Types.VARCHAR : " + Types.VARCHAR);
        System.out.println("Types.LONGVARCHAR : " + Types.LONGVARCHAR);
        System.out.println("Types.CLOB : " + Types.CLOB);

        System.out.println("Types.NCHAR : " + Types.NCHAR);
        System.out.println("Types.NVARCHAR : " + Types.NVARCHAR);
        System.out.println("Types.LONGNVARCHAR : " + Types.LONGNVARCHAR);
        System.out.println("Types.NCLOB : " + Types.NCLOB);
        
        System.out.println("nationalizationSupport.getCharVariantCode() : " + nationalizationSupport.getCharVariantCode());
        System.out.println("nationalizationSupport.getClobVariantCode() : " + nationalizationSupport.getClobVariantCode());
        System.out.println("nationalizationSupport.getLongVarcharVariantCode() : " + nationalizationSupport.getLongVarcharVariantCode());
        System.out.println("nationalizationSupport.getVarcharVariantCode() : " + nationalizationSupport.getVarcharVariantCode());


        char[] arr = {'h', 'e', 'l', 'l', 'o'};
        
        Demo e1 = new Demo(1, arr, arr, arr, arr);
  
        try (final Session session = SESSION_FACTORY.openSession()) {
            session.beginTransaction();

            session.persist(e1);

            session.flush();
            session.getTransaction().commit();

            List<Demo> records = loadAllData(Demo.class, session);
            records.forEach(System.out::println);
        }

    }
}
