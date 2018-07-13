package shop.example;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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

  protected PurchaseOrder() {}

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

  public int getDeliveryCharges() {
    int amount = 0;
    if (product.getProductType() == 2) {
      amount = 20;
    } else {
      if (quantity >= 5) {
        amount = 50;
      } else {
        amount = 30;
      }
    }
    return amount;
  }
}
