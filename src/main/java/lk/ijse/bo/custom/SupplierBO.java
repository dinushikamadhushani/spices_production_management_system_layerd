package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.SupplierDto;
import lk.ijse.entity.Supplier;

import java.sql.SQLException;
import java.util.List;

public interface SupplierBO extends SuperBO {

    boolean updateSupplier(SupplierDto supplierDto) throws SQLException, ClassNotFoundException;

    List<Supplier> getAllSuppliers() throws SQLException, ClassNotFoundException;

    boolean saveSupplier(SupplierDto supplierDto) throws SQLException, ClassNotFoundException;

    void deleteSupplier(String id) throws SQLException, ClassNotFoundException;

    SupplierDto searchSupplier(String id) throws SQLException, ClassNotFoundException;

    boolean existSupplier(String id) throws SQLException, ClassNotFoundException;
}
