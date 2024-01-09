package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.OrderDetail;

import java.sql.SQLException;

public interface OrderDetaiDAO extends CrudDAO<OrderDetail> {
    String generateID() throws SQLException, ClassNotFoundException;
}
