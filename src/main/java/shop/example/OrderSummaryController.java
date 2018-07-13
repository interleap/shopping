package shop.example;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class OrderSummaryController {

  private CustomerDao customerDao = new CustomerDao();

  public String dailyOrderSummary(Long customerId) throws IOException {
    Customer customer = customerDao.customerById(customerId);
    double totalAmount = 0;
    String sm = "-------------\n";
    sm += "Dear " + customer.getName() + ", Here is your order summary for the day \n";
    double totalProductAmount = 0;
    double totalDeliveryAmount = 0;

    for (PurchaseOrder ord : customer.getPurchaseOrders()) {
      if (removeTime(ord.getDate()).equals(removeTime(Calendar.getInstance().getTime()))) {
        Product product = ord.getProduct();
        sm += "..........\n";
        sm += "Product: " + product.productDetails() + "\n";
        sm += "Quantity: " + ord.getQuantity() + "\n";
        sm += "Total Cost of Product: " + product.getPrice() * ord.getQuantity() + "\n";
        totalProductAmount += product.getPrice() * ord.getQuantity();

        int currentDeliveryAmount = ord.getDeliveryCharges();
        if (currentDeliveryAmount > 0) {
          sm += "Delivery Charges for this product: " + currentDeliveryAmount + "\n";
          totalDeliveryAmount += currentDeliveryAmount;
        }
      }
    }
    totalAmount = totalProductAmount + totalDeliveryAmount;
    sm += "..........\n";
    sm += "Your total amount: " + totalAmount + "\n";
    sm += "Your registered address is: " + customer.getAddress() + "\n";
    sm += "Kindly pay cash to the delivery officer." + "\n";
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
