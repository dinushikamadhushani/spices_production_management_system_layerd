package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.SupplierBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.SupplierDAO;
import lk.ijse.dto.SupplierDto;
import lk.ijse.entity.Supplier;

import java.sql.SQLException;
import java.util.List;

public class SupplierBOImpl implements SupplierBO {
    SupplierDAO supplierDAO =
            (SupplierDAO) DAOFactory.getDaoFactory().
                    getDAO(DAOFactory.DAOTypes.SUPPLIER);
    // CustomerDAO customerDAO = new CustomerDAOImpl();
    @Override
    public boolean updateSupplier(SupplierDto supplierDto) throws SQLException, ClassNotFoundException {

        return supplierDAO.update(new Supplier(supplierDto.getSupplierId(),supplierDto.getSupplierName(),supplierDto.getAddress(),supplierDto.getTel()));

    }

    @Override
    public List<Supplier> getAllSuppliers() throws SQLException, ClassNotFoundException {
        return supplierDAO.getAll();
    }
    @Override

    public boolean saveSupplier(SupplierDto supplierDto) throws SQLException, ClassNotFoundException {
        return supplierDAO.save(new Supplier(supplierDto.getSupplierId(),supplierDto.getSupplierName(),supplierDto.getAddress(),supplierDto.getTel()));

    }


    @Override
    public void deleteSupplier(String id) throws SQLException, ClassNotFoundException {
        supplierDAO.delete(id);


    }


    @Override
    public SupplierDto searchSupplier(String id) throws SQLException, ClassNotFoundException {
        Supplier supplier=supplierDAO.search(id);

        if (supplier!=null){
            return new SupplierDto(supplier.getSupplierId(), supplier.getSupplierName(), supplier.getAddress(), supplier.getTel());


        }else{
            return  null;
        }
    }

    @Override
    public boolean existSupplier(String id) throws SQLException, ClassNotFoundException {
        return supplierDAO.exist(id);
    }


}
