
/*
package lk.ijse.model;


import lk.ijse.db.DbConnection;
import lk.ijse.dto.tm.CartTm;
import lk.ijse.dto.tm.OrderDetailTM;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailModel {
    public boolean saveOrderDetail(String orderId, List<OrderDetailTM> tmList) throws SQLException {
        for (OrderDetailTM orderDetailTM : tmList) {
            if(!saveOrderDetail(orderId, orderDetailTM)) {
                return false;
            }
        }
        return true;
    }

    private boolean saveOrderDetail(String orderId, CartTm cartTm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO Order_detail VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, orderId);
        pstm.setString(2, cartTm.getItemId());
        pstm.setInt(3, cartTm.getQty());
        pstm.setDouble(4, cartTm.getUnitPrice());

        return pstm.executeUpdate() > 0;
    }
}

*/
