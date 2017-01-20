package org.dexmedia.titan.persistance;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {/*
	 private static final SessionFactory sessionFactory = buildSessionFactory();

	    private static SessionFactory buildSessionFactory() {
	        try {
	            // Create the SessionFactory from hibernate.cfg.xml
	            return new Configuration().configure().buildSessionFactory();
	        }
	        catch (Throwable ex) {
	            // Make sure you log the exception, as it might be swallowed
	            System.err.println("Initial SessionFactory creation failed." + ex);
	            throw new ExceptionInInitializerError(ex);
	        }
	    	try {

				SessionFactory sessionFactory = new Configuration()
						.configure("/org/config/hibernate.cfg.xml")
						.addResource("/org/config/Product.hbm.xml")
						.buildSessionFactory();

				return sessionFactory;

			} catch (Throwable ex) {
				// Make sure you log the exception, as it might be swallowed
				System.err.println("Initial SessionFactory creation failed." + ex);
				throw new ExceptionInInitializerError(ex);
			}catch(HibernateException exception){
			     System.out.println("Problem creating session factory");
			     exception.printStackTrace();
			     throw new ExceptionInInitializerError(exception);
			}
	    }

	    public static SessionFactory getSessionFactory() {
	        return sessionFactory;
	    }

	    public static void shutdown() {
	    	// Close caches and connection pools
	    	getSessionFactory().close();
	    }

*/
	private static final SessionFactory sessionFactory = buildSessionFactory1();

	private static SessionFactory buildSessionFactory1() {
	Configuration configuration = new Configuration().configure("/org/config/hibernate.cfg.xml"); // configuration
	// settings
	// from
	// hibernate.cfg.xml

	StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();

	serviceRegistryBuilder.applySettings(configuration.getProperties());

	ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
	StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
            .configure("/org/config/hibernate.cfg.xml").build();
	Metadata metadata = new MetadataSources(standardRegistry).getMetadataBuilder().build();
    return metadata.getSessionFactoryBuilder().build();

	//return configuration.buildSessionFactory(serviceRegistry);
	}

	public static SessionFactory getSessionFactory() {
	return sessionFactory;
	}

	public static void shutdown() {
	// Close caches and connection pools
	getSessionFactory().close();
	}
	}
