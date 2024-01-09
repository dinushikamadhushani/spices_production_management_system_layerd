package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Supplier {
    private String supplierId;
    private String supplierName;
    private String address;
    private String tel;
}
