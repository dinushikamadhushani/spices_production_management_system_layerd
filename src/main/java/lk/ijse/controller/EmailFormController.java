package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.mail.MessagingException;

public class EmailFormController {

    @FXML
    private JFXButton btnsend;

    @FXML
    private TextField txtmassage;

    @FXML
    private TextField txtsubject;

    @FXML
    void btnSendOnAction(ActionEvent event) throws MessagingException, MessagingException {
        //  void btnSendOnAction(ActionEvent event) throws MessagingException {
        String email = txtsubject.getText();
        String body = txtmassage.getText();
        EmailController.sendEmail(email, "", body);
    }

}
