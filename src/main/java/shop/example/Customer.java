package shop.example;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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

  protected Customer() {}

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

  public List<PurchaseOrder> getCurrentDayPurchaseOrders() {
    List<PurchaseOrder> currentDayList = new ArrayList<>();
    Date today = removeTime(Calendar.getInstance().getTime());
    for (PurchaseOrder order : purchaseOrders) {
      Date purchaseDate = removeTime(order.getDate());
      if (purchaseDate.equals(today)) {
        currentDayList.add(order);
      }
    }
    return currentDayList;
  }

  public double getTotalProductAmount() {
    double totalProductAmount = 0;
    for (PurchaseOrder purchaseOrder : getCurrentDayPurchaseOrders()) {
      totalProductAmount += purchaseOrder.getCost();
    }
    return totalProductAmount;
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
