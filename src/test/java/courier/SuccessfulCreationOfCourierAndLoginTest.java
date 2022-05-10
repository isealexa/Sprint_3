package courier;

import client.CourierClient;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SuccessfulCreationOfCourierAndLoginTest {

    private CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @After
    public void teardown(){
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Create courier with all fields and login")
    @Description("The test checks the creation of a courier with all fields and its authorization")
    public void courierCreateAndLoginWithAllFieldsReturnTrue() {
        Courier courier = generateAllFields();
        boolean created = createNew(courier);
        courierId = login(courier);

        checkCreatedTrue(created);
        checkCourierIdIsNot0(courierId);
    }

    @Test
    @DisplayName("Create courier only without firstName and login")
    @Description("The test checks the creation of a courier only with required fields and its authorization")
    public void courierCreateAndLoginWithEmptyFirstNameReturnTrue() {
        Courier courier = justEmptyFirstName();
        boolean created = createNew(courier);
        courierId = login(courier);

        checkCreatedTrue(created);
        checkCourierIdIsNot0(courierId);
    }

    @Test
    @DisplayName("Create courier only with required fields and login")
    @Description("The test checks the creation of a courier only with required fields and its authorization")
    public void courierCreateAndLoginOnlyWithRequiredFieldsReturnTrue() {
        Courier courier = justRequiredFields();
        boolean created = createNew(courier);
        courierId = login(courier);

        checkCreatedTrue(created);
        checkCourierIdIsNot0(courierId);
    }

    @Step("Generate random courier's login, password and firstName")
    public Courier generateAllFields(){
        return Courier.getRandomCourierAllFields();
    }

    @Step("Generate empty firstName and random courier's login and password")
    public Courier justEmptyFirstName(){
        return Courier.getCourierWithEmptyFirstName();
    }

    @Step("Generate only required fields: random courier's login and password without firstName")
    public Courier justRequiredFields(){
        return Courier.getRandomCourierJustRequiredFields();
    }

    @Step("Create courier: send POST /courier")
    public boolean createNew(Courier courier){
        return courierClient.create(courier);
    }

    @Step("Courier authorization: send POST /courier/login to get courierId")
    public int login(Courier courier){
        CourierCredentials cred = CourierCredentials.from(courier);
        return courierClient.login(cred);
    }

    @Step("Check that courier was created")
    public void checkCreatedTrue(boolean created){
        assertTrue(created);
    }

    @Step("Check that courier's id doesn't equal 0")
    public void checkCourierIdIsNot0(int courierId){
        assertNotEquals(0, courierId);
    }
}