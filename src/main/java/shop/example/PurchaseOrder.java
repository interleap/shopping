package shop.example;

import java.sql.Date;

public class PurchaseOrder {

    private Long id;
    private Date date;

    private Product product;
    private int quantity;
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
