package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.RawMaterial;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RawMaterialDAO extends CrudDAO<RawMaterial> {
    @Override
    ArrayList<RawMaterial> getAll() throws SQLException, ClassNotFoundException;
}
