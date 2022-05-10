package courier;

import client.CourierClient;
import client.ErrorResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FailedLoginOfCourierWithoutRequiredFieldsTest {

    private CourierClient courierClient;
    private int courierId;
    private int expectedCode;
    private String expectedErrorMessage;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        expectedCode = 400;
        expectedErrorMessage = "Недостаточно данных для входа";
    }

    @After
    public void teardown(){
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("It's impossible to login without the courier's login")
    @Description("The test checks that error 400 'Bad Request' will return if you try to login without the courier's login")
    public void postLoginCourierWithoutLoginReturnErrorBadRequest() {
        Courier courier = prepareTestCourier();
        CourierCredentials cred = getCouriersPasswordWithoutLogin(courier);
        ErrorResponse loginFailed = tryToLogin(cred);
        checkStatusCodeAndMessage(loginFailed.getStatusCode(), loginFailed.getMessage());
    }

    @Test
    @DisplayName("It's impossible to login without the courier's password")
    @Description("The test checks that error 400 'Bad Request' will return if you try to login without the courier's password")
    public void postLoginCourierWithoutPasswordReturnErrorBadRequest() {
        Courier courier = prepareTestCourier();
        CourierCredentials cred = getCouriersLoginWithoutPassword(courier);
        ErrorResponse loginFailed = tryToLogin(cred);
        checkStatusCodeAndMessage(loginFailed.getStatusCode(), loginFailed.getMessage());
    }

    @Test
    @DisplayName("It's impossible to login with the empty courier's login")
    @Description("The test checks that error 400 'Bad Request' will return if you try to login with the empty courier's login")
    public void postLoginCourierWithEmptyLoginReturnErrorBadRequest() {
        Courier courier = prepareTestCourier();
        CourierCredentials cred = getCouriersPasswordWithEmptyLogin(courier);
        ErrorResponse loginFailed = tryToLogin(cred);
        checkStatusCodeAndMessage(loginFailed.getStatusCode(), loginFailed.getMessage());
    }

    @Test
    @DisplayName("It's impossible to login  with the empty courier's password")
    @Description("The test checks that error 400 'Bad Request' will return if you try to login with the empty courier's password")
    public void postLoginCourierWithEmptyPasswordReturnErrorBadRequest() {
        Courier courier = prepareTestCourier();
        CourierCredentials cred = getCouriersLoginWithEmptyPassword(courier);
        ErrorResponse loginFailed = tryToLogin(cred);
        checkStatusCodeAndMessage(loginFailed.getStatusCode(), loginFailed.getMessage());
    }

    @Step("Prepare test courier")
    public Courier prepareTestCourier(){
        Courier courier = Courier.getRandomCourierAllFields();
        courierClient.create(courier);
        CourierCredentials cred = CourierCredentials.from(courier);
        courierId = courierClient.login(cred);
        return courier;
    }

    @Step("Get courier's password without login")
    public CourierCredentials getCouriersPasswordWithoutLogin(Courier courier){
        courier.setLogin(null);
        return CourierCredentials.from(courier);
    }

    @Step("Get courier's login without password")
    public CourierCredentials getCouriersLoginWithoutPassword(Courier courier){
        courier.setPassword(null);
        return CourierCredentials.from(courier);
    }

    @Step("Get courier's password and empty login")
    public CourierCredentials getCouriersPasswordWithEmptyLogin(Courier courier){
        courier.setLogin("");
        return CourierCredentials.from(courier);
    }

    @Step("Get courier's login and empty password")
    public CourierCredentials getCouriersLoginWithEmptyPassword(Courier courier){
        courier.setPassword("");
        return CourierCredentials.from(courier);
    }

    @Step("Try to login to get error response")
    public ErrorResponse tryToLogin(CourierCredentials cred) {
        ValidatableResponse loginFailed = courierClient.loginWithoutValidation(cred);
        int statusCode = loginFailed.extract().statusCode();
        String errorMessage = loginFailed.extract().path("message").toString();
        return new ErrorResponse(statusCode, errorMessage);
    }

    @Step("Check status code and message")
    public void checkStatusCodeAndMessage(int actualStatusCode, String actualErrorMessage){
        assertEquals("Некорректное состояние ответа", expectedCode, actualStatusCode);
        assertEquals("Некорректный текст ошибки", expectedErrorMessage,  actualErrorMessage);
    }
}
