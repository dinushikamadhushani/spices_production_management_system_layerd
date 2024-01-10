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
import lk.ijse.bo.custom.SupplierBO;
import lk.ijse.bo.custom.impl.CustomerBOImpl;
import lk.ijse.bo.custom.impl.SupplierBOImpl;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.SupplierDto;
import lk.ijse.dto.tm.SupplierTm;
import lk.ijse.entity.Supplier;
//import lk.ijse.model.SupplierModel;
//import lk.ijse.dto.tm.SupplierTm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class SupplierFormController {

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTel;


    @FXML
    private TableColumn<?, ?> colRawId;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<SupplierTm> tblSupplier;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtSupAddress;

    @FXML
    private TextField txtSupId;

    @FXML
    private TextField txtSupName;

    @FXML
    private TextField txtSupTel;

    SupplierBO supplierBO=new SupplierBOImpl();

    public void initialize() {
        setCellValueFactory();
        loadAllSuppliers();
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));

    }

    private void loadAllSuppliers() {
       // var model = new SupplierModel();

        ObservableList<SupplierTm> obList = FXCollections.observableArrayList();

        try {
            List<Supplier> dtoList = supplierBO.getAllSuppliers();

            for(Supplier dto : dtoList) {
                obList.add(
                        new SupplierTm(
                                dto.getSupplierId(),
                                dto.getSupplierName(),
                                dto.getAddress(),
                                dto.getTel()

                        )
                );
            }

            tblSupplier.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }




    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboardForm.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard");
        stage.centerOnScreen();


    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();

    }

    boolean existSupplier(String id) throws SQLException, ClassNotFoundException {
        return supplierBO.existSupplier(id);

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {


        String id = tblSupplier.getSelectionModel().getSelectedItem().getSupplierId();
        try {
            if (!existSupplier(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such supplier associated with the id " + id).show();
            }
            supplierBO.deleteSupplier(id);
            tblSupplier.getItems().remove(tblSupplier.getSelectionModel().getSelectedItem());
            tblSupplier.getSelectionModel().clearSelection();
            // initUI();
            clearFields();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the supplier " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        boolean isValidate = validateSupplier();
        if(isValidate){
            String id = txtSupId.getText();
            String name = txtSupName.getText();
            String address = txtSupAddress.getText();
            String tel = txtSupTel.getText();

            var dto = new SupplierDto(id,name,address,tel);
        }

        SupplierDto supplierDto = new SupplierDto(txtSupId.getText(),txtSupName.getText(),txtSupAddress.getText(),txtSupTel.getText());
        boolean isSave = supplierBO.saveSupplier(supplierDto);

        if (isSave) {
            new Alert(Alert.AlertType.CONFIRMATION, "supplier saved!").show();
            clearFields();
            initialize();
        }

    }

    private boolean validateSupplier() {

        String idText = txtSupId.getText();

        boolean isCustomerIDValidation = Pattern.matches("[S][0-9]{3,}", idText);

        if (!isCustomerIDValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID SUPPLIER ID").show();
            txtSupId.setStyle("-fx-border-color: Red");

        }


        String nameText = txtSupName.getText();

        boolean isSupplierNameValidation = Pattern.matches("[A-Za-z.]{3,}", nameText);

        if (!isSupplierNameValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID SUPPLIER NAME").show();
            txtSupName.setStyle("-fx-border-color: Red");

        }

        String addressText = txtSupAddress.getText();

        boolean isCustomerAddressValidation = Pattern.matches("[A-Za-z0-9/.\\s]{3,}", addressText);

        if (!isCustomerAddressValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID SUPPLIER ADDRESS").show();
            txtSupAddress.setStyle("-fx-border-color: Red");

        }

        String telText = txtSupTel.getText();

        boolean isCustomerTelValidation = Pattern.matches("[0-9]{10}", telText);

        if (!isCustomerTelValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID SUPPLIER TEL").show();
            txtSupTel.setStyle("-fx-border-color: Red");
            return false;
        }

        return  true;
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {


        SupplierDto supplierDto = new SupplierDto(txtSupId.getText(),txtSupName.getText(),txtSupAddress.getText(),txtSupTel.getText());
        boolean isUpdate = supplierBO.updateSupplier(supplierDto);
        if(isUpdate) {
            new Alert(Alert.AlertType.CONFIRMATION, "supplier updated!").show();
            initialize();
        }

    }

    @FXML
    void txtSupIdSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {


        String supplierid = txtSupId.getText();
        SupplierDto supplierDto = supplierBO.searchSupplier(supplierid);
        System.out.println("hii");
        if(supplierDto != null) {

            fillFields(supplierDto);
        } else {
            new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
        }

    }

    private void fillFields(SupplierDto dto) {
        txtSupId.setText(dto.getSupplierId());
        txtSupName.setText(dto.getSupplierName());
        txtSupAddress.setText(dto.getAddress());
        txtSupTel.setText(dto.getTel());
    }

    void clearFields() {
        txtSupId.setText("");
        txtSupName.setText("");
        txtSupAddress.setText("");
        txtSupTel.setText("");
    }

}