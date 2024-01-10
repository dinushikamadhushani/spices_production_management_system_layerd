package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {
    @Override
    public boolean ischecked(String username, String password) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT password FROM user WHERE user_name=?",username);
        while (resultSet.next()){
            String pw = resultSet.getString(1);
            if (password.equals(pw)){
                return true;
            }


        }
        return false;
    }

    public boolean updatePassword(String username, String text) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE user SET password=? WHERE user_name=?";
        return SQLUtil.execute(sql,text,username);
       /* try (PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql)) {
            pstm.setString(1,text);
            pstm.setString(2,username);
            int rows = pstm.executeUpdate();
            if (rows > 0) {
                return true;
            }
        }
        return false;*/
    }

    public UserDto getEmail(String username) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM user WHERE user_name=?");


            if (resultSet.next()) {
                return new UserDto(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)

                );
            }

        return null;


    }
}
