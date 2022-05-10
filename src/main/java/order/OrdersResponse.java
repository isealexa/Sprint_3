package order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrdersResponse {

    private OrderDetails[] orders;
    private PageInfo pageInfo;
    private AvailableStations[] availableStations;
}
