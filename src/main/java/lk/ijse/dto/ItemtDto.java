package lk.ijse.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemtDto {
    private String itemId;
    private String itemName;
    private double unitPrice;
    private int qtyOnHand;
    private String rawMaterialId;

}
