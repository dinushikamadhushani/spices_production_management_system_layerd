package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.PlaceOrderBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.*;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.*;
import lk.ijse.entity.Customer;
import lk.ijse.entity.Item;
import lk.ijse.entity.Order;
import lk.ijse.entity.OrderDetail;
import lk.ijse.model.ItemModel;
//import lk.ijse.model.OrderModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderBOImpl implements PlaceOrderBO {



    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    OrderDetaiDAO orderDetailsDAO = (OrderDetaiDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAIL);
    QueryDAO queryDAO= (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERY);


    @Override
    public boolean placeOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException {

                Connection connection = null;
        connection= DbConnection.getInstance().getConnection();

        //Check order id already exist or not

        boolean b1 = orderDAO.exist(orderId);

        if (b1) {
            return false;
        }

        connection.setAutoCommit(false);



        //Save the Order to the order table
        boolean b2 = orderDAO.save(new Order(orderId, orderDate, customerId));
       // boolean b2 = orderDAO.save(orderId,orderDate,customerId));

        if (!b2) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }


        // add data to the Order Details table

        for (OrderDetailDTO detail : orderDetails) {
            boolean b3 = orderDetailsDAO.save(new OrderDetail(detail.getOrderId(),detail.getItemId(),detail.getQty(),detail.getUnitPrice()));
            if (!b3) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            //Search & Update Item
            ItemtDto item = findItem(detail.getItemId());
            item.setQtyOnHand(item.getQtyOnHand() - detail.getQty());

            //update item
            boolean b = itemDAO.update(new Item(item.getItemId(), item.getItemName(), item.getUnitPrice(), item.getQtyOnHand(),item.getRawMaterialId()));

            if (!b) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }

        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }

    @Override
    public CustomerDto searchCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer customer=customerDAO.search(id);
        CustomerDto customerDTO=new CustomerDto(customer.getCustomerId(),customer.getCustomerName(),customer.getAddress(),customer.getTel());
        return customerDTO;
    }

    @Override
    public ItemtDto searchItem(String code) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.search(code);
        return new ItemtDto(item.getItemId(),item.getItemName(),item.getUnitPrice(), item.getQtyOnHand(),item.getRawMaterialId());

    }

    @Override
    public boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.exist(code);
    }

    @Override
    public boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exist(id);
    }

    @Override
    public String generateOrderID() throws SQLException, ClassNotFoundException {
        return orderDAO.generateID();
    }

    @Override
    public ArrayList<CustomerDto> getAllCustomer() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customers=customerDAO.getAll();
        ArrayList<CustomerDto> customerDTOS=new ArrayList<>();
        for (Customer customer:customers) {
            customerDTOS.add(new CustomerDto(customer.getCustomerId(),customer.getCustomerName(),customer.getAddress(),customer.getTel()));
        }
        return customerDTOS;
    }

    @Override
    public ArrayList<ItemtDto> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<Item> items=itemDAO.getAll();
        ArrayList<ItemtDto> itemDTOS=new ArrayList<>();
        for (Item item:items) {
            itemDTOS.add(new ItemtDto(item.getItemId(),item.getItemName(),item.getUnitPrice(),item.getQtyOnHand(),item.getRawMaterialId()));
        }
        return itemDTOS;
    }

    public ItemtDto findItem(String code) {
        try {
            Item item = itemDAO.search(code);
            return new ItemtDto(item.getItemId(),item.getItemName(),item.getUnitPrice(), item.getQtyOnHand(),item.getRawMaterialId());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Item " + code, e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}


