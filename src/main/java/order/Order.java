package order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import java.time.LocalDate;
import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

    private String firstName;
    private String lastName;
    private String address;
    private int metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;

    public static Order getSimpleOrder(String[] choseColor){
        String testEnding = RandomStringUtils.randomAlphabetic(3) + RandomStringUtils.randomNumeric(3);
        Random random = new Random();
        LocalDate randomDate = LocalDate.now().plusDays(random.nextInt(14));

        String firstName = "QAFirstName" + testEnding;
        String lastName = "QALastName" + testEnding;
        String address = "testAddress, " + testEnding;
        int metroStation = random.nextInt(225);
        String phone = "+78" + RandomStringUtils.randomNumeric(9);
        int rentTime = random.nextInt(7);
        String deliveryDate = randomDate.toString();
        String comment = "Test Comment " + testEnding;
        String[] color = choseColor;

        return new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }
}
