package lk.ijse.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RawMaterialDto {
    private String rawMaterialId;
    private String rawMaterialName;
    private Double qtyOnStock;
    private Double unitPrice;


}
