package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class RawMaterial {
    private String rawMaterialId;
    private String rawMaterialName;
    private Double qtyOnStock;
    private Double unitPrice;
}
