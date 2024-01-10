package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.RawMaterialDAO;
import lk.ijse.entity.Customer;
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
                    resultSet.getBigDecimal("unit_price"));

            rawMaterials.add(rawMaterial);

        }
        return rawMaterials;

    }

    @Override
    public boolean save(RawMaterial rawMaterial) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO raw_material VALUES(?, ?, ?, ?)",
                rawMaterial.getRawMaterialId(),
                rawMaterial.getRawMaterialName(),
                rawMaterial.getQtyOnStock(),
                rawMaterial.getUnitPrice()
        );
    }

    @Override
    public boolean update(RawMaterial rawMaterial) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("UPDATE raw_material SET material_name = ?, qty_on_stock = ?, unit_price = ? WHERE rawMaterial_id = ?",
                rawMaterial.getRawMaterialName(),
                rawMaterial.getQtyOnStock(),
                rawMaterial.getUnitPrice(),
                rawMaterial.getRawMaterialId()
        );
    }

    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        //return false;
        ResultSet rst=SQLUtil.execute("SELECT * FROM raw_material WHERE rawMaterial_id=?",id);
        return rst.next();

    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("DELETE FROM raw_material WHERE rawMaterial_id=?",id);
    }

    @Override
    public RawMaterial search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT * FROM raw_material WHERE rawMaterial_id=?",id);
        while (rst.next()) {
            // return new Customer(id + "", rst.getString("name"), rst.getString("address"),rst.getString("tel"));
            RawMaterial rawMaterial = new RawMaterial(rst.getString(1), rst.getString(2), rst.getDouble(3), rst.getBigDecimal(4));
            return rawMaterial;
        }
        return null;
    }


}
//
