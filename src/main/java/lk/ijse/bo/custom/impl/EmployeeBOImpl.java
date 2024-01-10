package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.EmployeeBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.EmployeeDAO;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.entity.Employee;

import java.sql.SQLException;
import java.util.List;

public class EmployeeBOImpl implements EmployeeBO {

    EmployeeDAO employeeDAO =
            (EmployeeDAO) DAOFactory.getDaoFactory().
                    getDAO(DAOFactory.DAOTypes.EMPLOYEE);

    @Override
    public boolean updateEmployee(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException {

        return employeeDAO.update(new Employee(employeeDto.getId(),employeeDto.getName(),employeeDto.getEmail(),employeeDto.getTel(),employeeDto.getJobTitle(),employeeDto.getSalary(),employeeDto.getDate()));

    }

    @Override
    public List<Employee> getAllEmployees() throws SQLException, ClassNotFoundException {
        return employeeDAO.getAll();
    }
    @Override

    public boolean saveEmployee(EmployeeDto employeeDto) throws SQLException, ClassNotFoundException {
        return employeeDAO.save(new Employee(employeeDto.getId(),employeeDto.getName(),employeeDto.getEmail(),employeeDto.getTel(),employeeDto.getJobTitle(),employeeDto.getSalary(),employeeDto.getDate()));

    }


    @Override
    public void deleteEmployee(String id) throws SQLException, ClassNotFoundException {
        employeeDAO.delete(id);
    }

    @Override
    public EmployeeDto searchEmployee(String id) throws SQLException, ClassNotFoundException {
        Employee employee=employeeDAO.search(id);

        if (employee!=null){
            return new EmployeeDto(employee.getId(),employee.getName(),employee.getEmail(),employee.getTel(),employee.getJobTitle(),employee.getSalary(),employee.getDate());


        }else{
            return  null;
        }
    }

    @Override
    public boolean existEmployee(String id) throws SQLException, ClassNotFoundException {
        return employeeDAO.exist(id);
    }


}
