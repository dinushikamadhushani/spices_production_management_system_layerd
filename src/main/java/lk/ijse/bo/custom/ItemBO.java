package lk.ijse.bo.custom;

import lk.ijse.dto.ItemtDto;
import lk.ijse.entity.Item;

import java.sql.SQLException;
import java.util.List;

public interface ItemBO {
    // CustomerDAO customerDAO = new CustomerDAOImpl();
    boolean updateItem(ItemtDto itemtDto) throws SQLException, ClassNotFoundException;

    List<Item> getAllItems() throws SQLException, ClassNotFoundException;

    boolean saveItem(ItemtDto itemtDto) throws SQLException, ClassNotFoundException;

    void deleteItem(String id) throws SQLException, ClassNotFoundException;

    ItemtDto searchItem(String id) throws SQLException, ClassNotFoundException;

    boolean existItem(String id) throws SQLException, ClassNotFoundException;
}
