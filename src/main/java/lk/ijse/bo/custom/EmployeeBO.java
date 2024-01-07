package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.entity.Employee;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeBO extends SuperBO {
    // CustomerDAO customerDAO = new CustomerDAOImpl();
    boolean updateEmployee(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException;

    List<Employee> getAllEmployees() throws SQLException, ClassNotFoundException;

    boolean saveEmployee(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException;

    void deleteEmployee(String id) throws SQLException, ClassNotFoundException;

    EmployeeDto searchEmployee(String id) throws SQLException, ClassNotFoundException;

    boolean existEmployee(String id) throws SQLException, ClassNotFoundException;
}
