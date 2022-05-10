package order;

import client.OrderClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class SuccessfulCreationOfOrderTest {

    private OrderClient orderClient;
    private final String[] chooseColor;
    private final String chosenColor;
    private final int index;

    public SuccessfulCreationOfOrderTest(String[] chooseColor, String chosenColor, int index){
        this.chooseColor = chooseColor;
        this.chosenColor = chosenColor;
        this.index = index;
    }

    @Parameterized.Parameters(name = "The test {2} checks creation of order with color {1}")
    public static Object[][] getOrdersWithCorrectData() {
        return new Object[][] {
                {new String[]{"BLACK"}, "is BLACK", 1},
                {new String[]{"WHITE"}, "is WHITE", 2},
                {new String[]{"BLACK", "WHITE"}, "are BLACK and WHITE", 3},
                {new String[]{}, "doesn't choose", 4},
        };
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Create order with correct fields")
    @Description("The test checks the creation of an order with all correct fields")
    public void createOrderWithAllCorrectFieldsReturnTrackNotNull(){
        Order order = Order.getSimpleOrder(chooseColor);
        int track = orderClient.createOrder(order);
        assertNotNull(track);
        assertNotEquals(0, track);
    }
}
