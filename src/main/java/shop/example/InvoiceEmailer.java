package shop.example;

import com.sun.mail.smtp.SMTPTransport;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class InvoiceEmailer {

    public void createInvoice() throws IOException, MessagingException {
        //Load configuration
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


        for(Customer customer:customers){

            Session session = Session.getInstance(properties);
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("no-reply@example.shop"));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(customer.getEmail(), false));
            msg.setSubject("Daily PurchaseOrder Summary");
            msg.setText(OrderSummaryController.dailyOrderSummary(customer));
            msg.setSentDate(new Date());
            SMTPTransport t =
                    (SMTPTransport) session.getTransport("smtp");
            t.connect();
            t.sendMessage(msg, msg.getAllRecipients());
            t.close();
        }





    }
}
