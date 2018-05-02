package shop.example;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date date;

    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
    private int quantity;
    @ManyToOne
    private Customer customer;

    protected PurchaseOrder(){}

    public PurchaseOrder(Date date, Product product, int quantity, Customer customer) {
        this.date = date;
        this.product = product;
        this.quantity = quantity;
        this.customer = customer;
        customer.addOrder(this);
    }

    public Date getDate() {
        return date;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
