package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.CustomerDto;
import lk.ijse.entity.Customer;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO {
    boolean updateCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException;

    List<Customer> getAllCustomers() throws SQLException, ClassNotFoundException;

    public boolean saveCustomer(CustomerDto customerDto) throws SQLException, ClassNotFoundException;

    void deleteCustomer(String id) throws SQLException, ClassNotFoundException;
    public CustomerDto searchCustomer(String id) throws SQLException, ClassNotFoundException;

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException;
}
