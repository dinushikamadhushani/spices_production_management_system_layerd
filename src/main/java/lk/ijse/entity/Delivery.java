package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Delivery {
    private String deliveryId;
    private String orderId;
    private String employeeId;
    private String location;
    private String deliveryStatus;
    private String email;
}
