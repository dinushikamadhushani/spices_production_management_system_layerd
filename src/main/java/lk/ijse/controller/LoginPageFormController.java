package lk.ijse.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lk.ijse.model.UserModel;
import lk.ijse.util.Navigation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageFormController implements Initializable {

    @FXML
    private TextField showPassword;

    @FXML
    private PasswordField txtpassword;

    @FXML
    private TextField txtusername;

    @FXML
    void btnLoginOnAction(ActionEvent event) {
        if (UserModel.verifyCredential(txtusername.getText(),txtpassword.getText())){
            try {
                Navigation.switchNavigation("dashboardForm.fxml",event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            new Alert(Alert.AlertType.ERROR,"Invalid Credentials").show();
        }

    }

    @FXML
    void hypforgotpasswordOnAction(ActionEvent event) throws IOException {
        Navigation.switchNavigation("forgotPasswordForm.fxml" , event);


    }

    @FXML
    void hypsignupOnAction(ActionEvent event) throws IOException {
        Navigation.switchNavigation("signUpForm.fxml" , event);

    }

    @FXML
    void check(ActionEvent event) {
        String pass = txtpassword.getText();
        txtpassword.setVisible(false);
        showPassword.setVisible(true);
        showPassword.setText(pass);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showPassword.setVisible(false);
    }



}
