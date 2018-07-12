package shop.example;

import com.sun.mail.smtp.SMTPTransport;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class InvoiceEmailer {

    public void createInvoice() throws IOException, MessagingException {
        CustomerDao cdo = new CustomerDao();
        OrderSummaryController con = new OrderSummaryController();
        List<Customer> customers = cdo.all();

        //Load configuration
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("shopping.properties");
        Properties properties = new Properties();
        properties.load(stream);
        stream.close();

        for(Customer customer:customers){

            Session session = Session.getInstance(properties);
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("no-reply@example.shop"));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(customer.getEmail(), false));
            msg.setSubject("Daily PurchaseOrder Summary");
            msg.setText(con.dailyOrderSummary(customer.getId()));
            msg.setSentDate(new Date());
            SMTPTransport t =
                    (SMTPTransport) session.getTransport("smtp");
            t.connect();
            t.sendMessage(msg, msg.getAllRecipients());
            t.close();
        }





    }
}
