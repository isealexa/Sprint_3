package courier;

import client.CourierClient;
import client.ErrorResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class FailedLoginOfCourierWithIncorrectFieldsTest {

    private CourierClient courierClient;
    private int courierId;
    private int expectedStatusCode;
    private String expectedErrorMessage;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        expectedStatusCode = 404;
        expectedErrorMessage = "Учетная запись не найдена";
    }

    @After
    public void teardown(){
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("It's impossible to login with the incorrect courier's login")
    @Description("The test checks that error 404 'Not Found' will return if you try to login with the incorrect courier's login")
    public void postLoginCourierWithIncorrectLoginReturnErrorBadRequest() {
        Courier courier = prepareTestCourier();
        CourierCredentials cred = getCouriersCredentialsWithIncorrectLogin(courier);
        ErrorResponse loginFailed = tryToLogin(cred);
        checkStatusCodeAndMessage(loginFailed.getStatusCode(), loginFailed.getMessage());
    }

    @Test
    @DisplayName("It's impossible to login with the incorrect courier's password")
    @Description("The test checks that error 404 'Not Found' will return if you try to login with the incorrect courier's password")
    public void postLoginCourierWithIncorrectPasswordReturnErrorBadRequest() {
        Courier courier = prepareTestCourier();
        CourierCredentials cred = getCouriersCredentialsWithIncorrectPassword(courier);
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
    public CourierCredentials getCouriersCredentialsWithIncorrectLogin(Courier courier){
        courier.setLogin( courier.getLogin() + RandomStringUtils.randomAlphanumeric(5));
        return CourierCredentials.from(courier);
    }

    @Step("Get courier's password without login")
    public CourierCredentials getCouriersCredentialsWithIncorrectPassword(Courier courier){
        courier.setPassword( courier.getPassword() + RandomStringUtils.randomAlphanumeric(5));
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
        assertEquals("Некорректное состояние ответа", expectedStatusCode, actualStatusCode);
        assertEquals("Некорректный текст ошибки", expectedErrorMessage,  actualErrorMessage);
    }
}
