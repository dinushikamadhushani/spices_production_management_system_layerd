/*
package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.EmployeeDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {
    public boolean saveEmployee(final EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO employee VALUES(?, ?, ?, ?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getEmail());
        pstm.setString(4, dto.getTel());
        pstm.setString(5, dto.getJobTitle());
        pstm.setDouble(6, dto.getSalary());
        pstm.setString(7, dto.getDate());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public boolean updateEmployee(final EmployeeDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE employee SET employee_name = ?, email = ?, tel = ?, job_title = ?, salary = ?, date = ? WHERE employee_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getEmail());
        pstm.setString(3, dto.getTel());
        pstm.setString(4, dto.getJobTitle());
        pstm.setDouble(5, dto.getSalary());
        pstm.setString(6, dto.getDate());
        pstm.setString(7, dto.getId());

        return pstm.executeUpdate() > 0;
    }

    public EmployeeDto searchEmployee(String id) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee WHERE employee_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        EmployeeDto dto = null;

        if(resultSet.next()) {
            String emp_id = resultSet.getString(1);
            String emp_name = resultSet.getString(2);
            String email = resultSet.getString(3);
            String emp_tel = resultSet.getString(4);
            String emp_job_title = resultSet.getString(5);
            Double emp_salary = Double.valueOf(resultSet.getString(6));
            String date = resultSet.getString(7);

            dto = new EmployeeDto(emp_id, emp_name, email, emp_tel, emp_job_title, emp_salary, date);
        }

        return dto;
    }

    public List<EmployeeDto> getAllEmployee() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<EmployeeDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String emp_id = resultSet.getString(1);
            String emp_name = resultSet.getString(2);
            String email = resultSet.getString(3);
            String emp_tel = resultSet.getString(4);
            String emp_job_title = resultSet.getString(5);
            Double emp_salary = Double.valueOf(resultSet.getString(6));
            String date = resultSet.getString(7);

            var dto = new EmployeeDto(emp_id, emp_name, email, emp_tel, emp_job_title, emp_salary, date);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public boolean deleteEmployee(String employeeId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM employee WHERE employee_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, employeeId);

        return pstm.executeUpdate() > 0;
    }

    public static List<EmployeeDto> loadAllEmployee() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<EmployeeDto> empList = new ArrayList<>();

        while (resultSet.next()) {
            empList.add(new EmployeeDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getDouble(6),
                    resultSet.getString(7)
            ));
        }
        return empList;
    }


}
*/
