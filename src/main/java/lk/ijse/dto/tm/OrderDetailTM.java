package lk.ijse.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data


public class OrderDetailTM {
    private String itemId;
    private String itemName;
    private int qty;
    private BigDecimal unitPrice;
    private BigDecimal total;

    @Override
    public String toString() {
        return "OrderDetailTM{" +
                "code='" + itemId + '\'' +
                ", description='" + itemName + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                ", total=" + total +
                '}';
    }


}
