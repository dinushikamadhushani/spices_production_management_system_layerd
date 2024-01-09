package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.ItemtDto;
import lk.ijse.dto.OrderDetailDTO;
//import lk.ijse.dto.PlaceOrderDto;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface PlaceOrderBO extends SuperBO {

   // public boolean PlaceOrder(String orderId, LocalDate orderDate, String customerId, List<PlaceOrderDto> orderDetails) throws SQLException;

    boolean placeOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException ;
    CustomerDto searchCustomer(String id) throws SQLException, ClassNotFoundException;
    ItemtDto searchItem(String code) throws SQLException, ClassNotFoundException;

    boolean existItem(String code)throws SQLException, ClassNotFoundException;

    boolean existCustomer(String id)throws SQLException, ClassNotFoundException;

    String generateOrderID()throws SQLException, ClassNotFoundException;

    ArrayList<CustomerDto> getAllCustomer()throws SQLException, ClassNotFoundException;

    ArrayList<ItemtDto> getAllItems()throws SQLException, ClassNotFoundException;

}
