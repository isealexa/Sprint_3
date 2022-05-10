package courier;

import client.CourierClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static courier.Courier.getCourierWithTheSameLogin;
import static org.junit.Assert.*;

public class FailedCreationOfCourierWithTheSameValueTest {

    private CourierClient courierClient;
    private int courierId;
    private int expectedStatusCode;
    private String expectedErrorMessage;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        expectedStatusCode = 409;
        expectedErrorMessage = "Этот логин уже используется. Попробуйте другой.";
    }

    @After
    public void teardown(){
        courierClient.delete(courierId);
    }

    public int login(Courier courier){
        CourierCredentials cred = CourierCredentials.from(courier);
        return courierClient.login(cred);
    }

    @Test
    @DisplayName("It's impossible to create two the same couriers")
    @Description("The test checks that error 409 'Conflict' will return if you try to create two couriers with the same couriers")
    public void postCreateTwoTheSameCouriersReturnErrorConflict() {
        Courier courier = Courier.getRandomCourierAllFields();
        Courier theSameCourier = courier;

        courierClient.create(courier);
        courierId = login(courier);
        ValidatableResponse creationFailed = courierClient.createWithoutValidation(theSameCourier);
        int actualStatusCode = creationFailed.extract().statusCode();
        String actualErrorMessage = creationFailed.extract().path("message").toString();

        assertEquals("Некорректное состояние ответа", expectedStatusCode, actualStatusCode);
        assertEquals("Некорректный текст ошибки", expectedErrorMessage,  actualErrorMessage);
    }

    @Test
    @DisplayName("It's impossible to create two couriers with the same login")
    @Description("The test checks that error 409 'Conflict' will return if you try to create two couriers with the same login")
    public void postCreateTwoCouriersWithTheSameLoginReturnErrorConflict() {
        Courier courier = Courier.getRandomCourierAllFields();
        Courier courierWithTheSameLogin = getCourierWithTheSameLogin(courier.getLogin());

        courierClient.create(courier);
        courierId = login(courier);
        ValidatableResponse creationFailed = courierClient.createWithoutValidation(courierWithTheSameLogin);
        int actualStatusCode = creationFailed.extract().statusCode();
        String actualErrorMessage = creationFailed.extract().path("message").toString();

        assertEquals("Некорректное состояние ответа", expectedStatusCode, actualStatusCode);
        assertEquals("Некорректный текст ошибки", expectedErrorMessage,  actualErrorMessage);
    }
}
