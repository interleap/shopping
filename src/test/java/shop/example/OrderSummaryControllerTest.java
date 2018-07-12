package shop.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class OrderSummaryControllerTest {

    private OrderSummaryController orderSummaryController;

    @Before
    public void setup() {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("product");
        EntityManager em = emFactory.createEntityManager();

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Product product1=new Product(1,"The Epic of Gilgamesh",600,null,"author","abc publications",null,null,null,null);
        Product product2=new Product(2,null,15000,"specs",null,null,"Blue","Motorola","G4",null);
        Product product3=new Product(3,null,10000,null,null,null,"Gold","Titan","W3","analog");
        Product product4=new Product(3,null,40000,null,null,null,"Black","Apple","iWatch","smart");

        em.merge(product1);
        em.merge(product2);
        em.merge(product3);

        Customer customer1 = new Customer("Mukesh", "m@jio.com", "Antilla, Mumbai");
        Customer customer2 = new Customer("Bill", "bill@outlook.com", "Xanadu 2.0, Medina");
        Customer customer3 = new Customer("Rafa", "topspin@tennis.es", "Sport Residency, Mallorca");

        PurchaseOrder purchaseOrder1 = new PurchaseOrder(Date.valueOf(LocalDate.now()), product2, 3, customer2);
        PurchaseOrder purchaseOrder2 = new PurchaseOrder(Date.valueOf(LocalDate.now()), product2, 2, customer3);
        PurchaseOrder purchaseOrder3 = new PurchaseOrder(Date.valueOf(LocalDate.now()), product3, 2, customer3);
        PurchaseOrder purchaseOrder4 = new PurchaseOrder(Date.valueOf(LocalDate.now().minusDays(1)), product4, 2, customer2);

        em.merge(customer1);
        em.merge(customer2);
        em.merge(customer3);

        em.merge(purchaseOrder1);
        em.merge(purchaseOrder2);
        em.merge(purchaseOrder3);
        em.merge(purchaseOrder4);

        transaction.commit();

        em.close();
    }

    @Test
    public void testMyCustomer() throws IOException {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("product");
        EntityManager em = emFactory.createEntityManager();
        Query query = em.createQuery("SELECT c FROM Customer c");
        List<Customer> customers = query.getResultList();

        for (Customer customer : customers) {
            orderSummaryController = new OrderSummaryController();
            System.out.println(orderSummaryController.dailyOrderSummary(customer.getId()));
        }
        em.close();

    }

    @After
    public void tearDown(){
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("product");
            EntityManager em
                    = entityManagerFactory.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.createQuery("DELETE from PurchaseOrder o").executeUpdate();
            em.createQuery("DELETE from Customer c").executeUpdate();
            em.createQuery("DELETE from Product p").executeUpdate();
            transaction.commit();
            em.close();
    }
}
