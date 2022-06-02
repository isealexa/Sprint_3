package courier;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Courier {

    private String login;
    private String password;
    private String firstName;

    public static Courier getRandomCourierAllFields(){
        String login = RandomStringUtils.randomAlphanumeric(10);
        String password = RandomStringUtils.randomAlphanumeric(10);
        String firstName = RandomStringUtils.randomAlphanumeric(10);

        return new Courier(login, password, firstName);
    }

    public static Courier getRandomCourierJustRequiredFields(){
        String login = RandomStringUtils.randomAlphanumeric(10);
        String password = RandomStringUtils.randomAlphanumeric(10);

        return new Courier(login, password, null);
    }

    public static Courier getCourierWithEmptyFirstName(){
        String login = RandomStringUtils.randomAlphanumeric(10);
        String password = RandomStringUtils.randomAlphanumeric(10);

        return new Courier(login, password, "");
    }

    public static Courier getCourierWithTheSameLogin (String theSameCourierLogin){
        String courierPassword = RandomStringUtils.randomAlphanumeric(10);
        String courierFirstName = RandomStringUtils.randomAlphanumeric(10);

        return new Courier(theSameCourierLogin, courierPassword, courierFirstName);
    }

    public static Courier getCourierWithoutLogin(){
        String password = RandomStringUtils.randomAlphanumeric(10);
        String firstName = RandomStringUtils.randomAlphanumeric(10);

        return new Courier(null, password, firstName);
    }

    public static Courier getCourierWithoutPassword(){
        String login = RandomStringUtils.randomAlphanumeric(10);
        String firstName = RandomStringUtils.randomAlphanumeric(10);

        return new Courier(login, null, firstName);
    }
}
