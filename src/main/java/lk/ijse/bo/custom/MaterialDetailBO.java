package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dao.custom.RawMaterialDAO;
import lk.ijse.dto.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface MaterialDetailBO extends SuperBO {



    boolean getOrder(LocalDate OrderDate, String supplierId, List<SupplierDetailDto> supplierDetail) throws SQLException, ClassNotFoundException;

    SupplierDto searchSupplier(String id) throws SQLException, ClassNotFoundException;

    RawMaterialDto searchRawMaterial(String id) throws SQLException, ClassNotFoundException;

    boolean existRawMaterial(String id) throws SQLException, ClassNotFoundException;

    boolean existSupplier(String id) throws SQLException, ClassNotFoundException;

    ArrayList<SupplierDto> getAllSupplier() throws SQLException, ClassNotFoundException;

   // ArrayList<RawMaterialDAO> getAllRawMaterial() throws SQLException, ClassNotFoundException;


   // ArrayList<RawMaterialDto> getAllRawMaterialIds() throws SQLException, ClassNotFoundException;

    ArrayList<RawMaterialDto> getAllRawMaterials() throws SQLException, ClassNotFoundException;

    RawMaterialDto findRawMaterial(String id);
}
