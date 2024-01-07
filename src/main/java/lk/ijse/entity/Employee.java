package lk.ijse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class Employee {
    private String id;
    private String name;
    private String email;
    private String tel;
    private String jobTitle;
    private Double salary;
    private String date;
}
