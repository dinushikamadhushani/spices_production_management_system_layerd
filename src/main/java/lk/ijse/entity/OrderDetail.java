package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class OrderDetail {
    private String orderId;
    private String itemId;
    private int qty;
    private BigDecimal unitPrice;


}
