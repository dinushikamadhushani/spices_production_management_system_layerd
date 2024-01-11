package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.RawMaterialDto;
import lk.ijse.entity.RawMaterial;

import java.sql.SQLException;
import java.util.List;

public interface RawMaterialBO extends SuperBO {
    List<RawMaterialDto> getAllMaterials() throws SQLException, ClassNotFoundException;

    public boolean saveRawMaterial(RawMaterialDto rawMaterialDto) throws SQLException, ClassNotFoundException;

    boolean updateRawMaterial(RawMaterialDto rawMaterialDto) throws SQLException, ClassNotFoundException;

    void deleteRawMaterial(String id) throws SQLException, ClassNotFoundException;

    boolean existRawMaterial(String id) throws SQLException, ClassNotFoundException;

    public RawMaterialDto searchRawMaterial(String id) throws SQLException, ClassNotFoundException;

}
