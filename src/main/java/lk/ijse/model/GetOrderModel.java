/*
package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.GetOrderDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class GetOrderModel {
   // private final OrderModel orderModel = new OrderModel();
    // private final ItemModel itemModel = new ItemModel();
    private final SupplierDetailModel supplierDetailModel = new SupplierDetailModel();

    public boolean getOrder(GetOrderDto gDto) throws SQLException {
        boolean result = false;
        Connection connection = null;
        try {
            connection = DbConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isOrderSaved = supplierDetailModel.saveSupplierDetail(gDto.getSupplierId(), gDto.getRawMaterialId(), LocalDate.now(),gDto.getTmList());
            if (isOrderSaved) {
                boolean isUpdated = RawMaterialModel.updateRawMaterial(gDto.getTmList());
                if(isUpdated) {
                    connection.commit();
                    result = true;



                }
            }
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                // connection.close();
            }
        }
        return result;
    }
}
*/
