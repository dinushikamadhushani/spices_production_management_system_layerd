package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class OrderDetailDTO implements Serializable {

    private String orderId;
    private String itemId;
    private int qty;
    private BigDecimal unitPrice;

    @Override
    public String toString() {
        return "OrderDetailDTO{" +
                "itemCode='" + itemId + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                '}';
    }


}
