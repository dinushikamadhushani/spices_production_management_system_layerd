package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.custom.LoginBO;
import lk.ijse.bo.custom.impl.LoginBOImpl;
import lk.ijse.dto.UserDto;
//import lk.ijse.model.UserModel;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

public class ForgotPasswordFormController {

    @FXML
    private TextField txtuserName;

    @FXML
    private JFXButton btnreset;


    static String username;
    static int otp;

    @FXML
    private AnchorPane pane;


    private Stage stage;

    LoginBO loginBO = new LoginBOImpl();



    @FXML
    void btnResetpasswordOnAction(ActionEvent event) throws MessagingException, SQLException, IOException {
        username = txtuserName.getText();

        System.out.println(username);
       // UserModel userModel = new UserModel();
        Random random = new Random();
        otp = random.nextInt(9000);
        otp += 1000;

        try {

            UserDto user = loginBO.getEmail(username);
            System.out.println(user.getEmail());

            EmailController.sendEmail(user.getEmail(), "cafe au lait verification", otp + "");

        }catch (SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/otpForm.fxml"));
            Scene scene = new Scene(rootNode);
            Stage stage1 = new Stage();
            stage1.setScene(scene);
            stage1.centerOnScreen();
            stage1.setResizable(false);
            stage1.show();

    }




    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/loginPageForm.fxml"));
        Stage stage = (Stage) pane.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard");
        stage.centerOnScreen();


    }


}