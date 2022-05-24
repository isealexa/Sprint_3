package order;

import client.OrderClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GetOrdersTest {

    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Get list of orders")
    @Description("The test checks that request get orders return response is contained list of orders")
    public void getOrdersResponseContainsOrders(){
        OrdersResponse response = orderClient.getOrdersResponse();
        OrderDetails[] actualOrdersResponse = response.getOrders();
        assertNotNull(actualOrdersResponse);
    }

    @Test
    @DisplayName("Get list of orders containing order's id")
    @Description("The test checks that after create order request get orders return response is contained order's id")
    public void getOrdersAfterCreateOrderResponseContainsOrderId(){
        Order order = Order.getSimpleOrder(null);
        orderClient.createOrder(order);
        OrdersResponse response = orderClient. getOrdersResponse();
        int actualOrdersId = response.getOrders()[0].getId();
        assertNotEquals(0,actualOrdersId);
    }
}