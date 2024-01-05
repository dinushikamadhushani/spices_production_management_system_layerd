package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.RawMaterialBO;
import lk.ijse.dao.custom.RawMaterialDAO;
import lk.ijse.dao.custom.impl.RawMaterialDAOImpl;
import lk.ijse.dto.RawMaterialDto;
import lk.ijse.entity.RawMaterial;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RawMaterialBOImpl implements RawMaterialBO {

    RawMaterialDAO rawMaterialDAO = new RawMaterialDAOImpl();
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
}
