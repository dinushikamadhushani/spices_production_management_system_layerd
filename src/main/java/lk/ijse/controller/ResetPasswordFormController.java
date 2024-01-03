package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lk.ijse.model.UserModel;
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

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        Navigation.switchNavigation("loginPageForm.fxml",event);
    }

    @FXML
    void btnResetPassword(ActionEvent event) throws SQLException {

        UserModel userModel = new UserModel();
        if(txtPassword.getText().equals(txtConfirmPassword.getText())) {
            boolean isUpdated = userModel.updatePassword(ForgotPasswordFormController.username, txtPassword.getText());
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
