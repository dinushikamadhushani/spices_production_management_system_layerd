package lk.ijse.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeDto {
    private String id;
    private String name;
    private String email;
    private String tel;
    private String jobTitle;
    private Double salary;
    private String date;
}
