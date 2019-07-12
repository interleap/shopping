package shop.example;

import org.junit.Test;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class OrderSummaryControllerTest {

    @Test
    public void shouldCreateDailyOrderSummary() {
        Customer customer = new Customer("n1", "e1", "a1");
        Date now =  Date.valueOf(LocalDate.now());
        Product product1=new Product(1,"The Epic of Gilgamesh",100,null,"author","abc publications",null,null,null,null);
        Product product2=new Product(2,null,100,"specs",null,null,"Blue","Motorola","G4",null);
        Product product3=new Product(3,null,100,null,null,null,"Gold","Titan","W3","analog");
        customer.addOrder(new PurchaseOrder(now, product1,10, customer));
        customer.addOrder(new PurchaseOrder(now, product2,11, customer));
        customer.addOrder(new PurchaseOrder(now, product3,5, customer));
        assertEquals(expectedOrderSummary(), OrderSummaryController.dailyOrderSummary(customer));
    }

    public String expectedOrderSummary(){
        return "-------------\n" +
                "Dear n1, Here is your order summary for the day \n" +
                "..........\n" +
                "Product:  Price: 100, Name: The Epic of Gilgamesh, Author: author, Publisher: abc publications\n" +
                "Quantity: 10\n" +
                "Total Cost of Product: 1000\n" +
                "Delivery Charges for this product: 50\n" +
                "..........\n" +
                "Product:  Price: 100, Name: The Epic of Gilgamesh, Author: author, Publisher: abc publications\n" +
                "Quantity: 10\n" +
                "Total Cost of Product: 1000\n" +
                "Delivery Charges for this product: 50\n" +
                "..........\n" +
                "Product:  Price: 100, Name: Motorola G4, Specs: specs, Colour: Blue\n" +
                "Quantity: 11\n" +
                "Total Cost of Product: 1100\n" +
                "Delivery Charges for this product: 20\n" +
                "..........\n" +
                "Product:  Price: 100, Name: Motorola G4, Specs: specs, Colour: Blue\n" +
                "Quantity: 11\n" +
                "Total Cost of Product: 1100\n" +
                "Delivery Charges for this product: 20\n" +
                "..........\n" +
                "Product:  Price: 100, Name: Titan W3, Colour: Gold, Type: analog\n" +
                "Quantity: 5\n" +
                "Total Cost of Product: 500\n" +
                "Delivery Charges for this product: 50\n" +
                "..........\n" +
                "Product:  Price: 100, Name: Titan W3, Colour: Gold, Type: analog\n" +
                "Quantity: 5\n" +
                "Total Cost of Product: 500\n" +
                "Delivery Charges for this product: 50\n" +
                "..........\n" +
                "Your total amount: 5440.0\n" +
                "Your registered address is: a1\n" +
                "Kindly pay cash to the delivery officer.\n" +
                "-------------\n" +
                "\n" +
                "\n";
    }
}