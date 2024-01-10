package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.MaterialDetailBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.*;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.*;
import lk.ijse.entity.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MaterialDetailBOImpl implements MaterialDetailBO {

    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);
    RawMaterialDAO rawMaterialDAO = (RawMaterialDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RAWMATERIAL);
    SupplierDetailDAO supplierDetailDAO = (SupplierDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER_DETAIL);
    QueryDAO queryDAO= (QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.QUERY);

   /* @Override
    public boolean getOrder(LocalDate orderDate, String supplierId, List<SupplierDetailDto> supplierDetails) throws SQLException, ClassNotFoundException {

        Connection connection = null;
        connection= DbConnection.getInstance().getConnection();

        //Check order id already exist or not

        boolean b1 = orderDAO.exist(orderId);

        if (b1) {
            return false;
        }

        connection.setAutoCommit(false);



        //Save the Order to the order table
        boolean b2 = orderDAO.save(new Order(orderId, orderDate, customerId));
        // boolean b2 = orderDAO.save(orderId,orderDate,customerId));

        if (!b2) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }


        // add data to the Order Details table

        for (OrderDetailDTO detail : orderDetails) {
            boolean b3 = orderDetailsDAO.save(new OrderDetail(detail.getOrderId(),detail.getItemId(),detail.getQty(),detail.getUnitPrice()));
            if (!b3) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            //Search & Update Item
            ItemtDto item = findItem(detail.getItemId());
            item.setQtyOnHand(item.getQtyOnHand() - detail.getQty());

            //update item
            boolean b = itemDAO.update(new Item(item.getItemId(), item.getItemName(), item.getUnitPrice(), item.getQtyOnHand(),item.getRawMaterialId()));

            if (!b) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }

        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }*/

    @Override

    public boolean getOrder(LocalDate OrderDate, String supplierId, List<SupplierDetailDto> supplierDetail) throws SQLException, ClassNotFoundException {

        Connection connection = null;
        connection= DbConnection.getInstance().getConnection();


        for (SupplierDetailDto detail : supplierDetail) {
            boolean b3 = supplierDetailDAO.save(new SupplierDetail(detail.getSupplierId(),detail.getRawMaterialId(),detail.getDate(),detail.getUnitPrice(),detail.getQtyOnStock()));
            if (!b3) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            //Search & Update Item
            RawMaterialDto rawMaterial = findRawMaterial(detail.getRawMaterialId());
            rawMaterial.setQtyOnStock(rawMaterial.getQtyOnStock() - detail.getQtyOnStock());

            //update item
            boolean b = rawMaterialDAO.update(new RawMaterial(rawMaterial.getRawMaterialId(), rawMaterial.getRawMaterialName(),rawMaterial.getQtyOnStock(), rawMaterial.getUnitPrice() ));

            if (!b) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }

        connection.commit();
        connection.setAutoCommit(true);
        return true;
    }

    @Override
    public SupplierDto searchSupplier(String id) throws SQLException, ClassNotFoundException {
        Supplier supplier=supplierDAO.search(id);
        SupplierDto supplierDto=new SupplierDto(supplier.getSupplierId(),supplier.getSupplierName(),supplier.getAddress(),supplier.getTel());
        return supplierDto;
    }

    @Override
    public RawMaterialDto searchRawMaterial(String id) throws SQLException, ClassNotFoundException {
        RawMaterial rawMaterial = rawMaterialDAO.search(id);
        return new RawMaterialDto(rawMaterial.getRawMaterialId(),rawMaterial.getRawMaterialName(),rawMaterial.getQtyOnStock(),rawMaterial.getUnitPrice());

    }

    @Override
    public boolean existRawMaterial(String id) throws SQLException, ClassNotFoundException {
        return rawMaterialDAO.exist(id);
    }

    @Override
    public boolean existSupplier(String id) throws SQLException, ClassNotFoundException {
        return supplierDAO.exist(id);
    }


    @Override
    public ArrayList<SupplierDto> getAllSupplier() throws SQLException, ClassNotFoundException {
        ArrayList<Supplier> suppliers=supplierDAO.getAll();
        ArrayList<SupplierDto> supplierDTOS=new ArrayList<>();
        for (Supplier supplier:suppliers) {
            supplierDTOS.add(new SupplierDto(supplier.getSupplierId(),supplier.getSupplierName(),supplier.getAddress(),supplier.getTel()));
        }
        return supplierDTOS;
    }

    @Override
    public ArrayList<RawMaterialDto> getAllRawMaterials() throws SQLException, ClassNotFoundException {
        ArrayList<RawMaterial> rawMaterials=rawMaterialDAO.getAll();
        ArrayList<RawMaterialDto> rawMaterialDtos=new ArrayList<>();
        for (RawMaterial rawMaterial:rawMaterials) {
            rawMaterialDtos.add(( new RawMaterialDto(rawMaterial.getRawMaterialId(),rawMaterial.getRawMaterialName(),rawMaterial.getQtyOnStock(),rawMaterial.getUnitPrice())));
        }
        return rawMaterialDtos;


    }
    @Override

    public RawMaterialDto findRawMaterial(String id) {
        try {
            RawMaterial rawMaterial = rawMaterialDAO.search(id);
            return new RawMaterialDto(rawMaterial.getRawMaterialId(),rawMaterial.getRawMaterialName(),rawMaterial.getQtyOnStock(), rawMaterial.getUnitPrice());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Item " + id, e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
