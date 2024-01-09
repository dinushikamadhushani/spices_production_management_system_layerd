package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.ItemDAO;
import lk.ijse.entity.Customer;
import lk.ijse.entity.Item;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM item");
        ArrayList<Item> items = new ArrayList<>();

        while (resultSet.next()){
            Item item = new Item(
                    resultSet.getString("item_id"),
                    resultSet.getString("item_name"),
                    resultSet.getBigDecimal("unit_price"),
                    resultSet.getInt("qty_on_hand"),
                    resultSet.getString("rawMaterial_id")
                    );

            items.add(item);

        }
        return items;
    }

    @Override
    public boolean save(Item item) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO item VALUES(?, ?, ?, ?, ?)",
                item.getItemId(),
                item.getItemName(),
                item.getQtyOnHand(),
                item.getUnitPrice(),
                item.getRawMaterialId()
        );
    }

    @Override
    public boolean update(Item item) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE item SET item_name = ?, qty_on_hand = ?, unit_price = ? , rawMaterial_id = ? WHERE item_id = ?",

                item.getItemName(),
                item.getQtyOnHand(),
                item.getUnitPrice(),
                item.getRawMaterialId() ,
                item.getItemId()
        );
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst=SQLUtil.execute("SELECT * FROM item WHERE item_id=?",id);
        return rst.next();
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM item WHERE item_id=?",id);
    }

    @Override
    public Item search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM item WHERE item_id=?",id);
        while (rst.next()) {
            // return new Customer(id + "", rst.getString("name"), rst.getString("address"),rst.getString("tel"));
            Item item = new Item(rst.getString(1), rst.getString(2), rst.getBigDecimal(3), rst.getInt(4),rst.getString(5));
            return item;
        }
        return null;
    }
}
