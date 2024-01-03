package lk.ijse.controller;

import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.util.DateTimeUtil;
import lk.ijse.util.Navigation;
import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {

    @FXML
    private AnchorPane pagingPane;

    @FXML
    private Label txtDate;

    @FXML
    private Label txtTime;

    @FXML
    void btncustomerOnAction(ActionEvent event) throws IOException {
        Navigation.ChangePane(pagingPane,"customerForm.fxml");

    }

    @FXML
    void btndeliveryOnAction(ActionEvent event) throws IOException {
        Navigation.ChangePane(pagingPane,"deliveryForm.fxml");


    }

    @FXML
    void btnemployeeOnAction(ActionEvent event) throws IOException {
        Navigation.ChangePane(pagingPane,"employeeForm.fxml");


    }

    @FXML
    void btnhomeOnAction(ActionEvent event) throws IOException {
        Navigation.ChangePane(pagingPane,"homePageForm.fxml");

    }

    @FXML
    void btnhomeOnMouseExited(MouseEvent event) {

    }

    @FXML
    void btnlogoutOnAction(ActionEvent event) throws IOException {
        Navigation.switchNavigation("loginPageForm.fxml" , event);

    }

    @FXML
    void btnordersOnAction(ActionEvent event) throws IOException {
        Navigation.ChangePane(pagingPane,"placeOrderForm.fxml");


    }

    @FXML
    void btnproductOnAction(ActionEvent event) throws IOException {
        Navigation.ChangePane(pagingPane,"itemForm.fxml");


    }

    @FXML
    void btnrawmaterialOnAction(ActionEvent event) throws IOException {
        Navigation.ChangePane(pagingPane,"rawMaterialForm.fxml");


    }

    @FXML
    void btnsalaryOnAction(ActionEvent event) throws IOException {


    }

    @FXML
    void btnsupplierOnAction(ActionEvent event) throws IOException {
        Navigation.ChangePane(pagingPane,"supplierForm.fxml");


    }

    @FXML
    void btnmaterialDetailOnAction(ActionEvent event) throws IOException {
        Navigation.ChangePane(pagingPane,"materialDetailForm.fxml");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        txtDate.setText(DateTimeUtil.dateNow());
        // txtTime.setText(timeNow());
        loadTime();
    }

    private void loadTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
        txtDate.setText(LocalDate.now().toString());
        Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {

            txtTime.setText(DateTimeUtil.timeNow());
            // lblTime.setText(LocalDateTime.now().format(formatter));

        }),
                new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();


    }


}
