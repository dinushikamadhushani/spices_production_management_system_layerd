package lk.ijse.bo.custom;

import lk.ijse.dto.RawMaterialDto;
import lk.ijse.entity.RawMaterial;

import java.sql.SQLException;
import java.util.List;

public interface RawMaterialBO {
    List<RawMaterialDto> getAllMaterials() throws SQLException, ClassNotFoundException;

}
