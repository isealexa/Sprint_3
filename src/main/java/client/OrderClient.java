package client;

import order.Order;
import order.OrdersResponse;

public class OrderClient extends  RestAssuredClient{

    private final String ORDERS = "/orders";
    private final String TRACK = ORDERS + "/orders";

    public int createOrder(Order order){
        return   reqSpec
                .body(order)
                .when()
                .post(ORDERS)
                .then().log().all()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("track");
    }

    public OrdersResponse getOrdersResponse(){
        return   reqSpec
                .get(ORDERS)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .body().as(OrdersResponse.class);
    }
}
