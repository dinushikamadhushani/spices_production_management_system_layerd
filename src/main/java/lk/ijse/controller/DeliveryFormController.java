package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.custom.DeliveryBO;
import lk.ijse.bo.custom.EmployeeBO;
import lk.ijse.bo.custom.impl.DeliveryBOImpl;
import lk.ijse.bo.custom.impl.EmployeeBOImpl;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.DeliveryDto;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.tm.DeliveryTm;
import lk.ijse.entity.Delivery;
//import lk.ijse.model.DeliveryModel;
import lk.ijse.entity.Employee;
//import lk.ijse.model.EmployeeModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class DeliveryFormController {

    @FXML
    private TableColumn<?, ?> colDeliveryId;

    @FXML
    private TableColumn<?, ?> colDeliveryStatus;

    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colLocation;

    @FXML
    private TableColumn<?, ?> colOrderId;
    @FXML
    private TableColumn<?, ?> colEmail;


    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<DeliveryTm> tblDelivery;

    @FXML
    private TextField txtDeliveryId;

    @FXML
    private TextField txtDeliveryStatus;

    @FXML
    private TextField txtEmail;

    @FXML
    private ComboBox<String> cmbEmployeeId;


    @FXML
    private ComboBox<String> cmbEmail;

    @FXML
    private ComboBox<String> cmbOrderId;

    @FXML
    private TextField txtLocation;

    @FXML
    private TextField txtOrderId;

    @FXML
    private TextField txtTel;

  //  private final EmployeeModel employeeModel = new EmployeeModel();

    DeliveryBO deliveryBO = new DeliveryBOImpl();

    EmployeeBO employeeBO = new EmployeeBOImpl();

    public void initialize() {
        setCellValueFactory();
        loadAllDelivery();
        loadEmployeessIds();
        loadEmployeeEmails();

    }

    private void loadEmployeeEmails() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<Employee> empList = employeeBO.getAllEmployees();

            for (Employee dto : empList) {
                obList.add(dto.getEmail());
            }
            cmbEmail.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void setCellValueFactory() {
        colDeliveryId.setCellValueFactory(new PropertyValueFactory<>("deliveryId"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        colDeliveryStatus.setCellValueFactory(new PropertyValueFactory<>("deliveryStatus"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void loadAllDelivery() {
       // var model = new DeliveryModel();

        ObservableList<DeliveryTm> obList = FXCollections.observableArrayList();

        try {
            List<Delivery> dtoList = deliveryBO.getAllDeliveries();

            for(Delivery dto : dtoList) {
                obList.add(
                        new DeliveryTm(
                                dto.getDeliveryId(),
                                dto.getOrderId(),
                                dto.getEmployeeId(),
                                dto.getLocation(),
                                dto.getDeliveryStatus(),
                                dto.getEmail()
                        )
                );
            }

            tblDelivery.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadEmployeessIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<Employee> empList = employeeBO.getAllEmployees();

            for (Employee dto : empList) {
                obList.add(dto.getId());
            }
            cmbEmployeeId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }




    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard");
        stage.centerOnScreen();

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();

    }

    boolean existDelivery(String id) throws SQLException, ClassNotFoundException {
        return deliveryBO.existDelivery(id);

    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
       /* String deliveryId = txtDeliveryId.getText();

        var deliveryModel = new DeliveryModel();
        try {
            boolean isDeleted = deliveryModel.deleteDelivery(deliveryId);

            if(isDeleted) {
                tblDelivery.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "delivery deleted!").show();
                initialize();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
*/
        String id = tblDelivery.getSelectionModel().getSelectedItem().getDeliveryId();
        try {
            if (!existDelivery(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
            }
            deliveryBO.deleteDelivery(id);
            tblDelivery.getItems().remove(tblDelivery.getSelectionModel().getSelectedItem());
            tblDelivery.getSelectionModel().clearSelection();
            // initUI();
            clearFields();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the customer " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        /*String deliveryId = txtDeliveryId.getText();
        String orderId = txtOrderId.getText();
        String employeeId = cmbEmployeeId.getValue();
        String location = txtLocation.getText();
        String deliveryStatus = txtDeliveryStatus.getText();
       // String tel = txtTel.getText();
        String email = cmbEmail.getValue();

        boolean isValidate = validateDelivery();
        if (isValidate) {
            var dto = new DeliveryDto(deliveryId, orderId, employeeId, location, deliveryStatus, email);

            var model = new DeliveryModel();
            try {
                boolean isSaved = model.saveDelivery(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "delivery saved!").show();
                    //clearFields();
                    initialize();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
*/

        boolean isValidate = validateDelivery();
        if (isValidate){
            String deliveryId = txtDeliveryId.getText();
            String orderId = txtOrderId.getText();
            String employeeId = cmbEmployeeId.getValue();
            String location = txtLocation.getText();
            String deliveryStatus = txtDeliveryStatus.getText();
            String email = cmbEmail.getValue();

            var dto = new DeliveryDto(deliveryId,orderId,employeeId,location,deliveryStatus,email);
        }
        DeliveryDto deliveryDto = new DeliveryDto(txtDeliveryId.getText(),txtOrderId.getText(),cmbEmployeeId.getValue(),txtLocation.getText(),txtDeliveryStatus.getText(),cmbEmail.getValue());
        boolean isSave = deliveryBO.saveDelivery(deliveryDto);

        if (isSave) {
            new Alert(Alert.AlertType.CONFIRMATION, "delivery saved!").show();
            clearFields();
            initialize();
        }

    }

    private boolean validateDelivery() {

        String deliveryIdText = txtDeliveryId.getText();

        boolean isDeliveryIDValidation = Pattern.matches("[D][0-9]{3,}", deliveryIdText);

        if (!isDeliveryIDValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID DELIVERY ID").show();
            txtDeliveryId.setStyle("-fx-border-color: Red");

        }


        String orderIdText = txtOrderId.getText();

        boolean isOrderIdValidation = Pattern.matches("[O][0-9]{3,}", orderIdText);

        if (!isOrderIdValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID ORDER ID").show();
            txtOrderId.setStyle("-fx-border-color: Red");

        }

        String employeeIdText = cmbEmployeeId.getValue();

        boolean isEmployeeIdValidation = Pattern.matches("[E][0-9]{3,}", employeeIdText);

        if (!isEmployeeIdValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID EMPLOYEE ID").show();
            cmbEmployeeId.setStyle("-fx-border-color: Red");

        }


       /* String telText = txtTel.getText();

        boolean isCustomerTelValidation = Pattern.matches("[0-9]{10}", telText);

        if (!isCustomerTelValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID DELIVERY TEL").show();
            txtTel.setStyle("-fx-border-color: Red");
            return false;
        }

        */

        return  true;
    }




    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        /*String deliveryId = txtDeliveryId.getText();
        String orderId = txtOrderId.getText();
        String employeeId = cmbEmployeeId.getValue();
        String location = txtLocation.getText();
        String deliveryStatus = txtDeliveryStatus.getText();
        String email = cmbEmail.getValue();

        var dto = new DeliveryDto(deliveryId, orderId, employeeId, location, deliveryStatus, email);

        var model = new DeliveryModel();
        try {
            boolean isUpdated = model.updateDelivery(dto);
            System.out.println(isUpdated);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "delivery updated!").show();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }*/

        DeliveryDto deliveryDto = new DeliveryDto(txtDeliveryId.getText(),txtOrderId.getText(),cmbEmployeeId.getValue(),txtLocation.getText(),txtDeliveryStatus.getText(),cmbEmail.getValue());
        boolean isUpdate = deliveryBO.updateDelivery(deliveryDto);
        if(isUpdate) {
            new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
            initialize();
        }
    }


    @FXML
    void txtDeliveryIdSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        /*String deliveryId = txtDeliveryId.getText();

        var model = new DeliveryModel();
        try {
            DeliveryDto dto = model.searchDelivery(deliveryId);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
*/

        String deliveryid = txtDeliveryId.getText();
        DeliveryDto deliveryDto = deliveryBO.searchDelivery(deliveryid);
        System.out.println("hii");
        if(deliveryDto != null) {

            fillFields(deliveryDto);
        } else {
            new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
        }
    }
    private void fillFields(DeliveryDto dto) {
        txtDeliveryId.setText(dto.getDeliveryId());
        txtOrderId.setText(dto.getOrderId());
        cmbEmployeeId.setValue(dto.getEmployeeId());
        txtLocation.setText(dto.getLocation());
        txtDeliveryStatus.setText(dto.getDeliveryStatus());
        cmbEmail.setValue(dto.getEmail());
    }
    void clearFields() {
        txtDeliveryId.setText("");
        txtOrderId.setText("");
        cmbEmployeeId.setValue("");
        txtLocation.setText("");
        txtDeliveryStatus.setText("");
       // txtTel.setText("");
        cmbEmail.setValue("");
    }

    @FXML
    void cmbEmployeeIdOnAction(ActionEvent event) {

    }

    @FXML
    void cmbOrderIdOnAction(ActionEvent event) {

    }



    @FXML
    void btnEmailOnAction(ActionEvent event) throws IOException {
        //switchPaging(emailpane, "emailForm.fxml", "Customer Form");

        Parent rootNode = FXMLLoader.load(getClass().getResource("/view/emailForm.fxml"));
        Scene scene = new Scene(rootNode);
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.centerOnScreen();
        stage1.setResizable(false);
        stage1.show();
    }

    @FXML
    void cmbEmailOnAction(ActionEvent event) {

    }


}
