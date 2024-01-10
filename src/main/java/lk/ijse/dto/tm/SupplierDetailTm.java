package lk.ijse.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SupplierDetailTm {

    private String supplierId;
    private String rawMaterialId;
    private String materialName;
    private int qty;
    private BigDecimal unitPrice;
    private BigDecimal tot;
    private Button btn;

    public SupplierDetailTm(String materialId, String materialName, int qty, BigDecimal unitPrice, BigDecimal total) {
     this.rawMaterialId = materialId;
     this.materialName = materialName;
     this.qty =qty;
     this.unitPrice =unitPrice;
     this.tot =total;
    // this.btn =btn;
    }

    public void setTotal(BigDecimal total) {
    }


    public BigDecimal getTotal() {
        return null;
    }
}