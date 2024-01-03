package lk.ijse.model;

import lk.ijse.db.DbConnection;
import lk.ijse.dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {
    public static boolean verifyCredential(String user_name,String password){
        try {
            DbConnection instance = DbConnection.getInstance();
            Connection connection = instance.getConnection();

            String sql="SELECT password FROM user WHERE user_name = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1,user_name);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                if (password.equals(resultSet.getString(1))){
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;

    }

   /* public static boolean createaccount(String email,String create_password, String confirm_password){
        try {
            DbConnection instance = DbConnection.getInstance();
            Connection connection = instance.getConnection();

            String sql = "SELECT create_password, confirm_password FROM user WHERE email = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                if (create_password.equals(resultSet.getString(1))) {
                    return true;
                }
            }

            if (resultSet.next()) {
                if (confirm_password.equals(resultSet.getString(2))) {
                    return true;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;

    }

    */

    public UserDto getEmail(String username) throws SQLException {

        String sql = "SELECT * FROM user WHERE user_name=?";
        ResultSet resultSet = null;

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,username);
        try {
            resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                return new UserDto(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5)

                );


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    public boolean updatePassword(String username, String text) throws SQLException {

        String sql = "UPDATE user SET password=? WHERE user_name=?";
        try (PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql)) {
            pstm.setString(1,text);
            pstm.setString(2,username);
            int rows = pstm.executeUpdate();
            if (rows > 0) {
                return true;
            }
        }
        return false;
    }



}
