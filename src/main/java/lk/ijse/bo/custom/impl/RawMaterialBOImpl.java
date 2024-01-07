package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.RawMaterialBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.RawMaterialDAO;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.RawMaterialDto;
import lk.ijse.entity.Customer;
import lk.ijse.entity.RawMaterial;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RawMaterialBOImpl implements RawMaterialBO {

   // RawMaterialDAO rawMaterialDAO = new RawMaterialDAOImpl();

    RawMaterialDAO rawMaterialDAO =
            (RawMaterialDAO) DAOFactory.getDaoFactory().
                    getDAO(DAOFactory.DAOTypes.RAWMATERIAL);
    @Override
    public List<RawMaterialDto> getAllMaterials() throws SQLException, ClassNotFoundException {
        List<RawMaterial> rawMaterials = rawMaterialDAO.getAll();
        List<RawMaterialDto> rawMaterialDtos = new ArrayList<>();

        for (RawMaterial rawMaterial : rawMaterials){
            RawMaterialDto rawMaterialDto = new RawMaterialDto();
            rawMaterialDto.setRawMaterialId(rawMaterial.getRawMaterialId());
            rawMaterialDto.setRawMaterialName(rawMaterial.getRawMaterialName());
            rawMaterialDto.setQtyOnStock(rawMaterial.getQtyOnStock());
            rawMaterialDto.setUnitPrice(rawMaterial.getUnitPrice());
            rawMaterialDtos.add(rawMaterialDto);
        }
        return rawMaterialDtos;

    }

    @Override
    public boolean saveRawMaterial(RawMaterialDto rawMaterialDto) throws SQLException, ClassNotFoundException {
        return rawMaterialDAO.save(new RawMaterial(rawMaterialDto.getRawMaterialId(),rawMaterialDto.getRawMaterialName(),rawMaterialDto.getQtyOnStock(),rawMaterialDto.getUnitPrice()));

    }

    public boolean updateRawMaterial(RawMaterialDto rawMaterialDto) throws SQLException, ClassNotFoundException {

        return rawMaterialDAO.update(new RawMaterial(rawMaterialDto.getRawMaterialId(),rawMaterialDto.getRawMaterialName(),rawMaterialDto.getQtyOnStock(),rawMaterialDto.getUnitPrice()));

    }

    public void deleteRawMaterial(String id) throws SQLException, ClassNotFoundException {
        rawMaterialDAO.delete(id);

    }

    @Override
    public boolean existRawMaterial(String id) throws SQLException, ClassNotFoundException {
        return rawMaterialDAO.exist(id);
    }

    public RawMaterialDto searchRawMaterial(String id) throws SQLException, ClassNotFoundException {
        RawMaterial rawMaterial=rawMaterialDAO.search(id);
        // CustomerDto customerDTO=new CustomerDto(customer.getCustomerId(),customer.getCustomerName(),customer.getAddress(),customer.getTel());
        //  return customerDTO;
        if (rawMaterial!=null){
            return new RawMaterialDto(rawMaterial.getRawMaterialId(), rawMaterial.getRawMaterialName(), rawMaterial.getQtyOnStock(), rawMaterial.getUnitPrice());
        }else{
            return  null;
        }
    }

}
