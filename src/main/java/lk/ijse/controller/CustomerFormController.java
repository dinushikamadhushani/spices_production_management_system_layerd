package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.bo.custom.impl.CustomerBOImpl;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.tm.CustomerTm;
import lk.ijse.entity.Customer;
import lk.ijse.model.CustomerModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class CustomerFormController {
    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtTel;

    CustomerBO customerBO=new CustomerBOImpl();

    public void initialize() {
        setCellValueFactory();
        loadAllCustomers();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
    }

    private void loadAllCustomers() {
        var model = new CustomerModel();

        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

        try {
            List<Customer> dtoList = customerBO.getAllCustomers();

            for(Customer dto : dtoList) {
                obList.add(
                        new CustomerTm(
                                dto.getCustomerId(),
                                dto.getCustomerName(),
                                dto.getAddress(),
                                dto.getTel()
                        )
                );
            }

            tblCustomer.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
       /* String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String tel = txtTel.getText();

        boolean isValidate = validateCustomer();

        if (isValidate) {
            var dto = new CustomerDto(id, name, address, tel);

            var model = new CustomerModel();
            try {
                boolean isSaved = model.saveCustomer(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
                    clearFields();
                    initialize();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }*/

        CustomerDto customerDto = new CustomerDto(txtId.getText(),txtName.getText(),txtAddress.getText(),txtTel.getText());
        boolean isSave = customerBO.saveCustomer(customerDto);

        if (isSave) {
            new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
            clearFields();
            initialize();
        }

    }

    private boolean validateCustomer() {

        String idText = txtId.getText();

        boolean isCustomerIDValidation = Pattern.matches("[C][0-9]{3,}", idText);

        if (!isCustomerIDValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID CUSTOMER ID").show();
            txtId.setStyle("-fx-border-color: Red");


        }


        String nameText = txtName.getText();

        boolean isCustomerNameValidation = Pattern.matches("[A-Za-z.]{3,}", nameText);

        if (!isCustomerNameValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID CUSTOMER name").show();
            txtName.setStyle("-fx-border-color: Red");

        }

        String addressText = txtAddress.getText();

        boolean isCustomerAddressValidation = Pattern.matches("[A-Za-z0-9/.\\s]{3,}", addressText);

        if (!isCustomerAddressValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID CUSTOMER address").show();
            txtAddress.setStyle("-fx-border-color: Red");

        }

        String telText = txtTel.getText();

        boolean isCustomerTelValidation = Pattern.matches("[0-9]{10}", telText);

        if (!isCustomerTelValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID CUSTOMER tel").show();
            txtTel.setStyle("-fx-border-color: Red");
            return false;
        }

        return  true;
    }




    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
       /* String id = txtId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String tel = txtTel.getText();

        var dto = new CustomerDto(id, name, address, tel);

        var model = new CustomerModel();
        try {
            boolean isUpdated = model.updateCustomer(dto);
            System.out.println(isUpdated);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }*/

        CustomerDto customerDto = new CustomerDto(txtId.getText(),txtName.getText(),txtAddress.getText(),txtTel.getText());
        boolean isUpdate = customerBO.updateCustomer(customerDto);
        if(isUpdate) {
            new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
            initialize();
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String customerid = txtId.getText();
        CustomerDto customerDto = customerBO.searchCustomer(customerid);
        System.out.println("hii");
        if(customerDto != null) {

            fillFields(customerDto);
        } else {
            new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
        }



        /*var model = new CustomerModel();
        try {
            CustomerDto dto = model.searchCustomer(id);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }*/


    }

    private void fillFields(CustomerDto dto) {
        txtId.setText(dto.getId());
        txtName.setText(dto.getName());
        txtAddress.setText(dto.getAddress());
        txtTel.setText(dto.getTel());
    }

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerBO.existCustomer(id);

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        /*String id = txtId.getText();

        var customerModel = new CustomerModel();
        try {
            boolean isDeleted = customerModel.deleteCustomer(id);

            if(isDeleted) {
                tblCustomer.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }*/

        /*CustomerDto customerDto = new CustomerDto(txtId.getText(),txtName.getText(),txtAddress.getText(),txtTel.getText());
        boolean isDelate = customerBO.deleteCustomer(String.valueOf(customerDto));

        if(isDelate) {
            tblCustomer.refresh();
            new Alert(Alert.AlertType.CONFIRMATION, "customer delated!").show();
            initialize();
        }*/

        String id = tblCustomer.getSelectionModel().getSelectedItem().getId();
        try {
            if (!existCustomer(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
            }
            customerBO.deleteCustomer(id);
            tblCustomer.getItems().remove(tblCustomer.getSelectionModel().getSelectedItem());
            tblCustomer.getSelectionModel().clearSelection();
           // initUI();
            clearFields();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the customer " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboardForm.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard");
        stage.centerOnScreen();
    }

    void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtTel.setText("");
    }


}
