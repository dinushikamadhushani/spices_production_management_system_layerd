package lk.ijse.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemtDto implements Serializable {
    private String itemId;
    private String itemName;
    private BigDecimal unitPrice;
    private int qtyOnHand;
    private String rawMaterialId;

    @Override
    public String toString() {
        return "ItemDto{" +
                "code='" + itemId + '\'' +
                ", description='" + itemName + '\'' +
                ", unitPrice=" + unitPrice +
                ", qtyOnHand=" + qtyOnHand +
                '}';
    }

}
