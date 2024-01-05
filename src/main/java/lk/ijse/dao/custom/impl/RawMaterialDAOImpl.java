package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.RawMaterialDAO;
import lk.ijse.entity.RawMaterial;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RawMaterialDAOImpl implements RawMaterialDAO {

    @Override

    public ArrayList<RawMaterial> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT * FROM raw_material");
        ArrayList<RawMaterial> rawMaterials = new ArrayList<>();

        while (resultSet.next()) {
            RawMaterial rawMaterial = new RawMaterial(
                    resultSet.getString("rawMaterial_id"),
                    resultSet.getString("material_name"),
                    resultSet.getDouble("qty_on_stock"),
                    resultSet.getDouble("unit_price"));

            rawMaterials.add(rawMaterial);

        }
        return rawMaterials;

    }

    @Override
    public boolean save(RawMaterial dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(RawMaterial dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public RawMaterial search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
//
