package client;

import courier.Courier;
import courier.CourierCredentials;
import io.restassured.response.ValidatableResponse;

public class CourierClient extends RestAssuredClient {

    private final String ROOT = "/courier";
    private final String LOGIN = ROOT + "/login";
    private final String COURIER = ROOT + "/{courierId}";

    public ValidatableResponse createWithoutValidation(Courier courier) {
        return reqSpec
                .body(courier)
                .when()
                .post(ROOT)
                .then().log().all();
    }

    public boolean create(Courier courier){
        return   createWithoutValidation(courier)
                .assertThat()
                .statusCode(201)
                .extract()
                .path("ok");
    }

    public ValidatableResponse loginWithoutValidation(CourierCredentials cred){
        return   reqSpec
                .body(cred)
                .when()
                .post(LOGIN)
                .then().log().all();
    }

    public int login(CourierCredentials cred){
        return   loginWithoutValidation(cred)
                .assertThat()
                .statusCode(200)
                .extract()
                .path("id");
    }

    public void delete(int courierId){
        reqSpec
                .pathParams("courierId", courierId)
                .when()
                .delete(COURIER)
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }
}
