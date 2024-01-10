package lk.ijse.dao.custom;

import lk.ijse.dto.UserDto;

import java.sql.SQLException;

public interface UserDAO {
    boolean ischecked(String username, String password) throws SQLException, ClassNotFoundException;

    public boolean updatePassword(String username, String text) throws SQLException, ClassNotFoundException;

    public UserDto getEmail(String username) throws SQLException, ClassNotFoundException;
}
