package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lk.ijse.bo.custom.LoginBO;
import lk.ijse.bo.custom.impl.LoginBOImpl;
//import lk.ijse.model.UserModel;
import lk.ijse.util.Navigation;

import java.io.IOException;
import java.sql.SQLException;

public class ResetPasswordFormController {
    @FXML
    private Button btnLogin;

    @FXML
    private Button btnReset;


    @FXML
    private TextField txtConfirmPassword;

    @FXML
    private TextField txtPassword;

    LoginBO loginBO = new LoginBOImpl();

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        Navigation.switchNavigation("loginPageForm.fxml",event);
    }

    @FXML
    void btnResetPassword(ActionEvent event) throws SQLException, ClassNotFoundException {

        //UserModel userModel = new UserModel();
        if(txtPassword.getText().equals(txtConfirmPassword.getText())) {
            boolean isUpdated = loginBO.updatePassword(ForgotPasswordFormController.username, txtPassword.getText());
            if (isUpdated) {
                System.out.println("OK");
                new Alert(Alert.AlertType.CONFIRMATION, "OK!").show();
            } else {
                System.out.println("WRONG");

            }
        }else {
            System.out.println("CONFIRMATION NOT MATCHED!");
            new Alert(Alert.AlertType.ERROR,"CONFIRMATION NOT MATCHED!").show();
        }
    }
    public void initialize(){
        System.out.println(ForgotPasswordFormController.username);

    }
}
