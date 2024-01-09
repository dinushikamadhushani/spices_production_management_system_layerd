package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Item {

    private String itemId;
    private String itemName;
    private BigDecimal unitPrice;
    private int qtyOnHand;
    private String rawMaterialId;
}
