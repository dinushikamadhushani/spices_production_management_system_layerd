package lk.ijse.dao.custom;

import java.sql.SQLException;

public interface UserDAO {
    boolean ischecked(String username, String password) throws SQLException, ClassNotFoundException;
}
