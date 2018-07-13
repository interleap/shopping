package shop.example;

import java.io.IOException;

public class OrderSummaryController {

  private CustomerDao customerDao = new CustomerDao();

  public String dailyOrderSummary(Long customerId) throws IOException {
    Customer customer = customerDao.customerById(customerId);
    double totalAmount = 0;
    String orderSummary = getSummaryHeader(customer);
    double totalDeliveryAmount = 0;

    double totalProductAmount = customer.getTotalProductAmount();

    for (PurchaseOrder purchaseOrder : customer.getCurrentDayPurchaseOrders()) {
      Product product = purchaseOrder.getProduct();
      orderSummary += "..........\n";
      orderSummary += "Product: " + product.productDetails() + "\n";
      orderSummary += "Quantity: " + purchaseOrder.getQuantity() + "\n";
      orderSummary += "Total Cost of Product: " + purchaseOrder.getCost() + "\n";

      int currentDeliveryAmount = purchaseOrder.getDeliveryCharges();
      if (currentDeliveryAmount > 0) {
        orderSummary += "Delivery Charges for this product: " + currentDeliveryAmount + "\n";
        totalDeliveryAmount += currentDeliveryAmount;
      }
    }
    totalAmount = totalProductAmount + totalDeliveryAmount;
    orderSummary += "..........\n";
    orderSummary += "Your total amount: " + totalAmount + "\n";
    orderSummary += "Your registered address is: " + customer.getAddress() + "\n";
    orderSummary += "Kindly pay cash to the delivery officer." + "\n";
    orderSummary += "-------------\n\n\n";
    return orderSummary;
  }

  private String getSummaryHeader(Customer customer) {
    String orderSummary = "-------------\n";
    orderSummary += "Dear " + customer.getName() + ", Here is your order summary for the day \n";
    return orderSummary;
  }
}
