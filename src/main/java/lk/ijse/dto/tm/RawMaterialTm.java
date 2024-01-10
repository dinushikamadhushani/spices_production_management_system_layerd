package lk.ijse.dto.tm;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class RawMaterialTm {
    private String rawMaterialId;
    private String rawMaterialName;
    private Double qtyOnStock;
    private BigDecimal unitPrice;

    @Override
    public String toString() {
        return "RawMaterialTm{" +
                "rawMaterialId='" + rawMaterialId + '\'' +
                ", rawMaterialName='" + rawMaterialName + '\'' +
                ", qtyOnStock=" + qtyOnStock +
                ", unitPrice=" + unitPrice +

                '}';
    }
}
