package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CustomDto {
    private String customerId;
    private String customerName;
    private String orderId;
    private String itemId;
    private int qty;
    private BigDecimal unitPrice;


}
