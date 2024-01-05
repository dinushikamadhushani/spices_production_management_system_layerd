package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.LoginBO;
import lk.ijse.dao.custom.UserDAO;
import lk.ijse.dao.custom.impl.UserDAOImpl;

import java.sql.SQLException;

public class LoginBOImpl implements LoginBO {

    UserDAO userDAO = new UserDAOImpl();
    @Override

    public boolean verifyCredential(String username, String password) throws SQLException, ClassNotFoundException {
        return userDAO.ischecked(username,password);

    }
}
