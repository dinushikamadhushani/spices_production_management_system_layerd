package lk.ijse.bo.custom;

import lk.ijse.dto.UserDto;

import java.sql.SQLException;

public interface LoginBO {
    boolean verifyCredential(String username, String password) throws SQLException, ClassNotFoundException;

    boolean updatePassword(String username, String text) throws SQLException, ClassNotFoundException;


    UserDto getEmail(String username) throws SQLException, ClassNotFoundException;
}
