package lk.ijse.model;


import lk.ijse.db.DbConnection;
import lk.ijse.dto.ItemtDto;
//import lk.ijse.dto.tm.CartTm;
import lk.ijse.dto.tm.OrderDetailTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemModel {
    public boolean saveItem(ItemtDto itemtDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "INSERT INTO item VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, itemtDto.getItemId());
        pstm.setString(2, itemtDto.getItemName());
        pstm.setBigDecimal(3, itemtDto.getUnitPrice());
        pstm.setInt(4, itemtDto.getQtyOnHand());
        pstm.setString(5, itemtDto.getRawMaterialId());

        return pstm.executeUpdate() > 0;
    }

    public boolean updateItem(ItemtDto itemtDto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE item SET item_name = ?, unit_price = ?, qty_on_hand = ?, rawMaterial_id = ? WHERE item_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, itemtDto.getItemName());
        pstm.setBigDecimal(2, itemtDto.getUnitPrice());
        pstm.setInt(3, itemtDto.getQtyOnHand());
        pstm.setString(4, itemtDto.getRawMaterialId());
        pstm.setString(5, itemtDto.getItemId());

        return pstm.executeUpdate() > 0;
    }

    public static boolean updateItem(List<OrderDetailTM> tmList) throws SQLException {
        for (OrderDetailTM orderDetailTM : tmList) {
            if(!updateQty(orderDetailTM)) {
                return false;
            }
        }
        return true;
    }



    private static boolean updateQty(OrderDetailTM orderDetailTM) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE item SET qty_on_hand = qty_on_hand - ? WHERE item_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setInt(1, orderDetailTM.getQty());
        pstm.setString(2, orderDetailTM.getItemId());

        return pstm.executeUpdate() > 0; //true
    }

    public ItemtDto searchItem(String ItemId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM item WHERE item_id = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, ItemId);

        ResultSet resultSet = pstm.executeQuery();

        ItemtDto dto = null;

        if(resultSet.next()) {
            dto = new ItemtDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getBigDecimal(3),
                    resultSet.getInt(4),
                    resultSet.getString(5)
            );
        }
        return dto;
    }

    public boolean deleteItem(String ItemId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM item WHERE item_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, ItemId);

        return pstm.executeUpdate() > 0;
    }

    public List<ItemtDto> loadAllItems() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM item";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<ItemtDto> dtoList = new ArrayList<>();

        while (resultSet.next()) {
            var dto = new ItemtDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getBigDecimal(3),
                    resultSet.getInt(4),
                    resultSet.getString(5)
            );

            dtoList.add(dto);
        }

        return dtoList;
    }
}
