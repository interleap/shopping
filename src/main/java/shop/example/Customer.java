package shop.example;

import javax.persistence.*;
import java.util.ArrayList;
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

    public String getEmail() {
        return email;
    }

    public void addOrder(PurchaseOrder purchaseOrder) {
        purchaseOrders.add(purchaseOrder);
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public List<PurchaseOrder> getPurchaseOrders() {
        return purchaseOrders;
    }

    public Long getId() {
        return id;
    }
}
