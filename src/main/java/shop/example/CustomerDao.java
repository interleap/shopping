package shop.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class CustomerDao {

    public Customer customerById(Long id) throws IOException {
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("shopping.properties");
        Properties properties = new Properties();
        properties.load(stream);
        stream.close();

        //Database entity manager
        String persistenceUnitName = properties.getProperty("persistence-unit.name");
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager em = emFactory.createEntityManager();

        Query query = em.createQuery("SELECT c FROM Customer c WHERE c.id = :id");
        query.setParameter("id", id);
        Customer customer = (Customer) query.getSingleResult();
        em.close();
        return customer;
    }

    public List<Customer> all() throws IOException {
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("shopping.properties");
        Properties properties = new Properties();
        properties.load(stream);
        stream.close();

        //Database entity manager
        String persistenceUnitName = properties.getProperty("persistence-unit.name");
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
        EntityManager em = emFactory.createEntityManager();

        Query query = em.createQuery("SELECT c FROM Customer c");
        List<Customer> customers = query.getResultList();
        em.close();
        return customers;
    }
}
