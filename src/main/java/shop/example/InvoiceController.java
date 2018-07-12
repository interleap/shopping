package shop.example;

import java.util.Calendar;
import java.util.Date;

public class InvoiceController {


    public static String dailyOrderSummary(Customer customer){
        double totalAmount = 0;
        String sm = "-------------\n";
        sm += "Dear "+ customer.getName() +", Here is your order summary for the day \n";

        for(PurchaseOrder ord : customer.getPurchaseOrders()){
            if(removeTime(ord.getDate()).equals(removeTime(Calendar.getInstance().getTime()))){
                Product product = ord.getProduct();
                sm += "..........\n";
                sm += "Product: " + product.productDetails() + "\n";
                sm += "Quantity: " + ord.getQuantity() + "\n";
                sm += "Total Cost of Product: " + product.getPrice() * ord.getQuantity() + "\n";
                totalAmount += product.getPrice() * ord.getQuantity();
                if(product.getProductType()==2){
                    sm += "Delivery Charges for this product: " + 20 + "\n";
                    totalAmount += 20;
                } else{
                    if(ord.getQuantity() >= 5){
                        sm += "Delivery Charges for this product: " + 50 + "\n";
                        totalAmount += 50;
                    } else {
                        sm += "Delivery Charges for this product: " + 30 + "\n";
                        totalAmount += 30;
                    }
                }
            }
        }

        sm += "..........\n";
        sm += "Your total amount: " + totalAmount + "\n";
        sm += "Your registered address is: " + customer.getAddress() + "\n" ;
        sm += "Kindly pay cash to the delivery officer." + "\n" ;
        sm += "-------------\n\n\n";
        return sm;
    }

    public static Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
