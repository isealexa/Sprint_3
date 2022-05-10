package courier;

import client.CourierClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FailedCreationOfCourierWithoutRequiredFieldsTest {

    private CourierClient courierClient;
    private int expectedStatusCode;
    private String expectedErrorMessage;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        expectedStatusCode = 400;
        expectedErrorMessage = "Недостаточно данных для создания учетной записи";
    }

    @Test
    @DisplayName("It's impossible to create the courier without login")
    @Description("The test checks that error 400 'Bad Request' will return if you try to create the courier without login")
    public void postCreateCourierWithoutLoginReturnErrorBadRequest() {
        Courier courier = Courier.getCourierWithoutLogin();

        ValidatableResponse creationFailed = courierClient.createWithoutValidation(courier);
        int actualStatusCode = creationFailed.extract().statusCode();
        String actualErrorMessage = creationFailed.extract().path("message").toString();

        assertEquals("Некорректное состояние ответа", expectedStatusCode, actualStatusCode);
        assertEquals("Некорректный текст ошибки", expectedErrorMessage,  actualErrorMessage);
    }

    @Test
    @DisplayName("It's impossible to create the courier without password")
    @Description("The test checks that error 400 'Bad Request' will return if you try to create the courier without password")
    public void postCreateCourierWithoutPasswordReturnErrorBadRequest() {
        Courier courier = Courier.getCourierWithoutPassword();

        ValidatableResponse creationFailed = courierClient.createWithoutValidation(courier);
        int actualStatusCode = creationFailed.extract().statusCode();
        String actualErrorMessage = creationFailed.extract().path("message").toString();

        assertEquals("Некорректное состояние ответа", expectedStatusCode, actualStatusCode);
        assertEquals("Некорректный текст ошибки", expectedErrorMessage,  actualErrorMessage);
    }

    @Test
    @DisplayName("It's impossible to create the courier with empty login")
    @Description("The test checks that error 400 'Bad Request' will return if you try to create the courier with empty login")
    public void postCreateCourierWithEmptyLoginReturnErrorBadRequest() {
        Courier courier = new Courier("", "Qwerty123", "Test");

        ValidatableResponse creationFailed = courierClient.createWithoutValidation(courier);
        int actualStatusCode = creationFailed.extract().statusCode();
        String actualErrorMessage = creationFailed.extract().path("message").toString();

        assertEquals("Некорректное состояние ответа", expectedStatusCode, actualStatusCode);
        assertEquals("Некорректный текст ошибки", expectedErrorMessage,  actualErrorMessage);
    }

    @Test
    @DisplayName("It's impossible to create the courier with empty password")
    @Description("The test checks that error 400 'Bad Request' will return if you try to create the courier with empty password")
    public void postCreateCourierWithEmptyPasswordReturnErrorBadRequest() {
        Courier courier = new Courier(RandomStringUtils.randomAlphanumeric(10), "", "Test");

        ValidatableResponse creationFailed = courierClient.createWithoutValidation(courier);
        int actualStatusCode = creationFailed.extract().statusCode();
        String actualErrorMessage = creationFailed.extract().path("message").toString();

        assertEquals("Некорректное состояние ответа", expectedStatusCode, actualStatusCode);
        assertEquals("Некорректный текст ошибки", expectedErrorMessage,  actualErrorMessage);
    }
}
