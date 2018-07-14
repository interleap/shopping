package shop.example;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OrderSummaryControllerTest {

  private OrderSummaryController orderSummaryController;
  private String mukeshSummary = null;
  private String billSummary = null;
  private String rafaSummary = null;

  @Before
  public void setup() {
    EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("product");
    EntityManager em = emFactory.createEntityManager();

    EntityTransaction transaction = em.getTransaction();
    transaction.begin();

    Product product1 = new Product(1, "The Epic of Gilgamesh", 600, null, "author",
        "abc publications", null, null, null, null);
    Product product2 =
        new Product(2, null, 15000, "specs", null, null, "Blue", "Motorola", "G4", null);
    Product product3 =
        new Product(3, null, 10000, null, null, null, "Gold", "Titan", "W3", "analog");
    Product product4 =
        new Product(3, null, 40000, null, null, null, "Black", "Apple", "iWatch", "smart");

    em.merge(product1);
    em.merge(product2);
    em.merge(product3);

    Customer customer1 = new Customer("Mukesh", "m@jio.com", "Antilla, Mumbai");
    Customer customer2 = new Customer("Bill", "bill@outlook.com", "Xanadu 2.0, Medina");
    Customer customer3 = new Customer("Rafa", "topspin@tennis.es", "Sport Residency, Mallorca");

    PurchaseOrder purchaseOrder1 =
        new PurchaseOrder(Date.valueOf(LocalDate.now()), product2, 3, customer2);
    PurchaseOrder purchaseOrder2 =
        new PurchaseOrder(Date.valueOf(LocalDate.now()), product2, 2, customer3);
    PurchaseOrder purchaseOrder3 =
        new PurchaseOrder(Date.valueOf(LocalDate.now()), product3, 2, customer3);
    PurchaseOrder purchaseOrder4 =
        new PurchaseOrder(Date.valueOf(LocalDate.now().minusDays(1)), product4, 2, customer2);

    em.merge(customer1);
    em.merge(customer2);
    em.merge(customer3);

    em.merge(purchaseOrder1);
    em.merge(purchaseOrder2);
    em.merge(purchaseOrder3);
    em.merge(purchaseOrder4);

    transaction.commit();

    em.close();

    mukeshSummary =
        "-------------\nDear Mukesh, Here is your order summary for the day \n" + "..........\n"
            + "Your total amount: 0.0\n" + "Your registered address is: Antilla, Mumbai\n"
            + "Kindly pay cash to the delivery officer.\n" + "-------------\n\n\n";

    billSummary = "-------------\nDear Bill, Here is your order summary for the day \n"
        + "..........\n" + "Product:  Price: 15000, Name: Motorola G4, Specs: specs, Colour: Blue\n"
        + "Quantity: 3\n" + "Total Cost of Product: 45000\n"
        + "Delivery Charges for this product: 20\n" + "..........\n"
        + "Discount on Delivery Charges: 20.0\n" + "Your total amount: 45000.0\n"
        + "Your registered address is: Xanadu 2.0, Medina\n"
        + "Kindly pay cash to the delivery officer.\n" + "-------------\n\n\n";

    rafaSummary = "-------------\nDear Rafa, Here is your order summary for the day \n"
        + "..........\n" + "Product:  Price: 15000, Name: Motorola G4, Specs: specs, Colour: Blue\n"
        + "Quantity: 2\n" + "Total Cost of Product: 30000\n"
        + "Delivery Charges for this product: 20\n" + "..........\n"
        + "Product:  Price: 10000, Name: Titan W3, Colour: Gold, Type: analog\n" + "Quantity: 2\n"
        + "Total Cost of Product: 20000\n" + "Delivery Charges for this product: 30\n"
        + "..........\n" + "Discount on Delivery Charges: 50.0\n" + "Your total amount: 50000.0\n"
        + "Your registered address is: Sport Residency, Mallorca\n"
        + "Kindly pay cash to the delivery officer.\n" + "-------------\n\n\n";
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
    assertEquals(mukeshSummary, orderSummaryController.dailyOrderSummary(customers.get(0).getId()));
    assertEquals(billSummary, orderSummaryController.dailyOrderSummary(customers.get(1).getId()));
    assertEquals(rafaSummary, orderSummaryController.dailyOrderSummary(customers.get(2).getId()));
    em.close();

  }

  @After
  public void tearDown() {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("product");
    EntityManager em = entityManagerFactory.createEntityManager();
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();
    em.createQuery("DELETE from PurchaseOrder o").executeUpdate();
    em.createQuery("DELETE from Customer c").executeUpdate();
    em.createQuery("DELETE from Product p").executeUpdate();
    transaction.commit();
    em.close();
  }
}
