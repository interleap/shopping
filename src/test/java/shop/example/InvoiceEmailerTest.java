package shop.example;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.Before;
import org.junit.Test;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class InvoiceEmailerTest {

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
    public void testIncoic() throws IOException, MessagingException, InterruptedException {
        ServerSetup setup = new ServerSetup(1111,"localhost","smtp");
        GreenMail greenMail = new GreenMail(setup);
        greenMail.start();
        InvoiceEmailer invoiceEmailer = new InvoiceEmailer();
        invoiceEmailer.createInvoice();

        assertTrue(greenMail.waitForIncomingEmail(5000, 3));

//Retrieve using GreenMail API
        Message[] messages = greenMail.getReceivedMessages();
        assertEquals(3, messages.length);

// Simple message
//        assertEquals("hello", messages[0].getSubject());
//        assertEquals("world", GreenMailUtil.getBody(messages[0]).trim());

//if you send content as a 2 part multipart...
//        assertTrue(messages[1].getContent() instanceof MimeMultipart);
//        MimeMultipart mp = (MimeMultipart) messages[1].getContent();
//        assertEquals(2, mp.getCount());
//        assertEquals("body1", GreenMailUtil.getBody(mp.getBodyPart(0)).trim());
//        assertEquals("body2", GreenMailUtil.getBody(mp.getBodyPart(1)).trim());

        greenMail.stop();
    }
}
