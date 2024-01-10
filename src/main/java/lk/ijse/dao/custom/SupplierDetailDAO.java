package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.SupplierDetail;

import java.sql.SQLException;

public interface SupplierDetailDAO extends CrudDAO<SupplierDetail> {
    String generateID() throws SQLException, ClassNotFoundException;
}
