package shop.example;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Customer {

    private Long id;
    private String name;
    private String email;
    private String address;

    private List<PurchaseOrder> purchaseOrders;

    protected Customer(){}

    public Customer(String name, String email, String address) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.purchaseOrders = new ArrayList<>();
    }

    public String dailyOrderSummary(){
        double totalAmount = 0;
        String orderSummary = "Here is your order summary for the day \n";

        for(PurchaseOrder purchaseOrder : purchaseOrders){
            if(removeTime(purchaseOrder.getDate()).equals(removeTime(Calendar.getInstance().getTime()))){
                Product product = purchaseOrder.getProduct();
                orderSummary += "Product: " + product.productDetails() + "\n";
                orderSummary += "Quantity: " + purchaseOrder.getQuantity() + "\n";
                totalAmount += product.getPrice() * purchaseOrder.getQuantity();
                if(product.getProductType()==2){
                    orderSummary += " Delivery Charges for this product: " + 20 + "\n" + "\n";
                    totalAmount += 20;
                } else{
                    orderSummary += " Delivery Charges for this product: " + 30 + "\n" + "\n";
                    totalAmount += 30;
                }
            }
        }

        orderSummary += " Your total amount: " + totalAmount + "\n";
        orderSummary += " Kindly pay cash to the delivery officer." + "\n" + "\n";
        orderSummary += "-------------\n\n";
        return orderSummary;
    }

    public Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public String getEmail() {
        return email;
    }

    public void addOrder(PurchaseOrder purchaseOrder) {
        purchaseOrders.add(purchaseOrder);
    }
}
