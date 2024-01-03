package lk.ijse.dto.tm;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class RawMaterialTm {
    private String rawMaterialId;
    private String rawMaterialName;
    private Double qtyOnStock;
    private Double unitPrice;
}
