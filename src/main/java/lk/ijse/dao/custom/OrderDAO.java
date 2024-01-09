package lk.ijse.dao.custom;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Order;

import java.sql.SQLException;

public interface OrderDAO extends CrudDAO<Order> {
    String generateID() throws SQLException, ClassNotFoundException;
   /* boolean save(Order entity) throws SQLException, ClassNotFoundException;

    boolean update(Order dto) throws SQLException, ClassNotFoundException;*/

}
