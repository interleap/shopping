package shop.example;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private String address;

    @OneToMany(targetEntity = PurchaseOrder.class, mappedBy = "customer")
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
        String sm = "Dear "+ name +", Here is your order summary for the day \n";

        for(PurchaseOrder ord : purchaseOrders){
            if(removeTime(ord.getDate()).equals(removeTime(Calendar.getInstance().getTime()))){
                Product product = ord.getProduct();
                sm += "..........\n";
                sm += "Product: " + product.productDetails() + "\n";
                sm += "Quantity: " + ord.getQuantity() + "\n";
                sm += "Total Cost of Product: " + product.getPrice() * ord.getQuantity() + "\n";
                totalAmount += product.getPrice() * ord.getQuantity();
                if(product.getProductType()==2){
                    sm += " Delivery Charges for this product: " + 20 + "\n";
                    totalAmount += 20;
                } else{
                    if(ord.getQuantity() >= 5){
                        sm += " Delivery Charges for this product: " + 50 + "\n";
                        totalAmount += 50;
                    } else {
                        sm += " Delivery Charges for this product: " + 30 + "\n";
                        totalAmount += 30;
                    }
                }
            }
        }

        sm += "..........\n";
        sm += " Your total amount: " + totalAmount + "\n";
        sm += " Your registered address is: " + address + "\n" ;
        sm += " Kindly pay cash to the delivery officer." + "\n" ;
        sm += "-------------\n\n\n";
        return sm;
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
