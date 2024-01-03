package lk.ijse.dto;



import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
/*@Getter
@Setter
@ToString*/
public class SupplierDto {
    private String supplierId;
    private String supplierName;
    private String address;
    private String tel;

}