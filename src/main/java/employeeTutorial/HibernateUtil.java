package employeeTutorial;

import javax.ejb.Stateless;
import javax.enterprise.inject.Model;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
  
@Model
public class HibernateUtil implements AutoCloseable {
  
    private static SessionFactory sessionFactory;
  
    public HibernateUtil() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            this.sessionFactory =  new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
  
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
  
    public void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }

	@Override
	public void close() throws Exception {
		
	}
  
}