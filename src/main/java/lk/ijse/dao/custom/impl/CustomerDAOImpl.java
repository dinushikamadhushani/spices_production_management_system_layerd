package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.CustomerDto;
import lk.ijse.entity.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public boolean update(Customer customer) throws SQLException,ClassNotFoundException {
      /*  Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE customer SET customer_name = ?, address = ?, tel = ? WHERE customer_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getName());
        pstm.setString(2, dto.getAddress());
        pstm.setString(3, dto.getTel());
        pstm.setString(4, dto.getId());

        return pstm.executeUpdate() > 0;*/

        return SQLUtil.execute("UPDATE customer SET customer_name = ?, address = ?, tel = ? WHERE customer_id = ?",
                customer.getCustomerName(),
                customer.getAddress(),
                customer.getTel(),
                customer.getCustomerId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
       return SQLUtil.execute("DELETE FROM customer WHERE customer_id=?",id);
    }

    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM customer WHERE customer_id=?",id);
       while (rst.next()) {
           // return new Customer(id + "", rst.getString("name"), rst.getString("address"),rst.getString("tel"));
           Customer customer = new Customer(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4));
           return customer;
       }
       return null;
    }

    @Override

    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM customer");
        ArrayList<Customer> customers = new ArrayList<>();

        while (resultSet.next()){
            Customer customer1 = new Customer(
            resultSet.getString("customer_id"),
            resultSet.getString("customer_name"),
            resultSet.getString("address"),
            resultSet.getString("tel"));

            customers.add(customer1);

        }
        return customers;

    }


    @Override

    public boolean save (Customer customer) throws SQLException,ClassNotFoundException{
       /*Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO customer VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getId());
        pstm.setString(2, dto.getName());
        pstm.setString(3, dto.getAddress());
        pstm.setString(4, dto.getTel());

        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;*/

        return SQLUtil.execute("INSERT INTO customer VALUES(?, ?, ?, ?)",
                customer.getCustomerId(),
                customer.getCustomerName(),
                customer.getAddress(),
                customer.getTel()
        );
    }
   /* @Override

    public boolean delate (Customer customer) throws SQLException, ClassNotFoundException {
       *//*case DELIVERY:
                return new DeliveryDAOImpl(); Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM customer WHERE customer_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, customerId);

        return pstm.executeUpdate() > 0;*//*

        return SQLUtil.execute("DELETE FROM customer WHERE customer_id = ?",
                customer.getCustomerId()
        );
    }

    @Override
    public boolean search(Customer customer) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("SELECT * FROM customer WHERE customer_id = ?",
                customer.getCustomerId()
        );
    }

    public boolean searchCustomer(Customer customer) throws SQLException, ClassNotFoundException {
       *//* Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM customer WHERE customer_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();

        CustomerDto dto = null;

        if(resultSet.next()) {
            String cus_id = resultSet.getString(1);
            String cus_name = resultSet.getString(2);
            String cus_address = resultSet.getString(3);
            String cus_tel = resultSet.getString(4);

            dto = new CustomerDto(cus_id, cus_name, cus_address, cus_tel);
        }

        return dto;*//*

        return SQLUtil.execute("SELECT * FROM customer WHERE customer_id = ?",
                customer.getCustomerId()
        );
    }*/

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst=SQLUtil.execute("SELECT * FROM customer WHERE customer_id=?",id);
        return rst.next();

    }


}
