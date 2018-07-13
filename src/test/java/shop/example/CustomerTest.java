package shop.example;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class CustomerTest {

  private Customer customer1;

  @Before
  public void setup() {

    customer1 = new Customer("Mukesh", "m@jio.com", "Antilla, Mumbai");

    PurchaseOrder purchaseOrder2 = mock(PurchaseOrder.class);
    when(purchaseOrder2.getDate()).thenReturn(Date.valueOf(LocalDate.now()));
    PurchaseOrder purchaseOrder4 = mock(PurchaseOrder.class);
    when(purchaseOrder4.getDate()).thenReturn(Date.valueOf(LocalDate.now().minusDays(1)));
    customer1.addOrder(purchaseOrder2);
    customer1.addOrder(purchaseOrder4);
  }

  @Test
  public void testCurrentDayPurchaseOrders() {
    List<PurchaseOrder> list = customer1.getCurrentDayPurchaseOrders();
    System.out.println(list);
    assertEquals(1, list.size());
  }
}
