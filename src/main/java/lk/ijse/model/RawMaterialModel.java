package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.RawMaterialDto;
import lk.ijse.dto.tm.MaterialCartTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RawMaterialModel {
    /*public boolean saveRawMaterial(final RawMaterialDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "INSERT INTO raw_material VALUES(?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, dto.getRawMaterialId());
        pstm.setString(2, dto.getRawMaterialName());
        pstm.setDouble(3, dto.getQtyOnStock());
        pstm.setDouble(4, dto.getUnitPrice());


        boolean isSaved = pstm.executeUpdate() > 0;

        return isSaved;
    }

    public boolean updateRawMaterial(final RawMaterialDto dto) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE raw_material SET material_name = ?, qty_on_stock = ?, unit_price = ? WHERE rawMaterial_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);


        pstm.setString(1, dto.getRawMaterialName());
        pstm.setDouble(2, dto.getQtyOnStock());
        pstm.setDouble(3, dto.getUnitPrice());
        pstm.setString(4, dto.getRawMaterialId());



        return pstm.executeUpdate() > 0;
    }
*/

    public RawMaterialDto searchRawMaterial(String rawMaterialId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM raw_material WHERE rawMaterial_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, rawMaterialId);

        ResultSet resultSet = pstm.executeQuery();

        RawMaterialDto dto = null;

        if(resultSet.next()) {
            String raw_id = resultSet.getString(1);
            String raw_name = resultSet.getString(2);
            Double qty = resultSet.getDouble(3);
            Double unit_price = resultSet.getDouble(4);


            dto = new RawMaterialDto(raw_id, raw_name, qty, unit_price);
        }

        return dto;
    }

   /* public List<RawMaterialDto> getAllMaterials() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM raw_material";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<RawMaterialDto> dtoList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()) {
            String raw_id = resultSet.getString(1);
            String raw_name = resultSet.getString(2);
            Double qty = resultSet.getDouble(3);
            Double unit_price = resultSet.getDouble(4);


            var dto = new RawMaterialDto(raw_id, raw_name, qty, unit_price);
            dtoList.add(dto);
        }
        return dtoList;
    }

    public boolean deleteRawMaterial(String rawMaterialId) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "DELETE FROM raw_material WHERE rawMaterial_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, rawMaterialId);

        return pstm.executeUpdate() > 0;
    }*/

    public static List<RawMaterialDto> loadAllRawMaterials() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "SELECT * FROM raw_material";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<RawMaterialDto> rawList = new ArrayList<>();

        while (resultSet.next()) {
            rawList.add(new RawMaterialDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getDouble(4)

            ));
        }
        return rawList;
    }

    public static boolean updateRawMaterial(List<MaterialCartTm> tmList) throws SQLException {
        for (MaterialCartTm materialCartTm : tmList) {
            if(!updateQty(materialCartTm)) {
                return false;
            }
        }
        return true;
    }



    private static boolean updateQty(MaterialCartTm materialCartTm) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();

        String sql = "UPDATE raw_material SET qty_on_stock = qty_on_stock + ? WHERE rawMaterial_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setInt(1, materialCartTm.getQty());
        pstm.setString(2, materialCartTm.getRawMaterialId());

        return pstm.executeUpdate() > 0; //true
    }

}
