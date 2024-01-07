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
import lk.ijse.bo.custom.EmployeeBO;
import lk.ijse.bo.custom.impl.CustomerBOImpl;
import lk.ijse.bo.custom.impl.EmployeeBOImpl;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.tm.EmployeeTm;
import lk.ijse.entity.Employee;
import lk.ijse.model.EmployeeModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class EmployeeFormController {

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colEmployeeName;

    @FXML
    private TableColumn<?, ?> colJobTitle;

    @FXML
    private TableColumn<?, ?> colTel;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableColumn<?, ?> colDate;


    @FXML
    private TableView<EmployeeTm> tblEmployee;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    private TextField txtEmployeeName;

    @FXML
    private TextField txtJobTitle;

    @FXML
    private TextField txtTel;

    @FXML
    private TextField txtSalary;

    @FXML
    private TextField txtDate;



    @FXML
    private AnchorPane root;

    EmployeeBO employeeBO=new EmployeeBOImpl();

    public void initialize() {
        setCellValueFactory();
        loadAllEmployee();
    }

    private void setCellValueFactory() {
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colEmployeeName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colJobTitle.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void loadAllEmployee() {
        var model = new EmployeeModel();

        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();

        try {
            List<Employee> dtoList = employeeBO.getAllEmployees();

            for(Employee dto : dtoList) {
                obList.add(
                        new EmployeeTm(
                                dto.getId(),
                                dto.getName(),
                                dto.getEmail(),
                                dto.getTel(),
                                dto.getJobTitle(),
                                dto.getSalary(),
                                dto.getDate()
                        )
                );
            }

            tblEmployee.setItems(obList);
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

    @FXML
    void btnDeleteOnAction(ActionEvent event) {


        String id = tblEmployee.getSelectionModel().getSelectedItem().getId();
        try {
            if (!existEmployee(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
            }
            employeeBO.deleteEmployee(id);
            tblEmployee.getItems().remove(tblEmployee.getSelectionModel().getSelectedItem());
            tblEmployee.getSelectionModel().clearSelection();
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


        EmployeeDto employeeDto = new EmployeeDto(txtEmployeeId.getText(),txtEmployeeName.getText(),txtEmail.getText(),txtTel.getText(),txtJobTitle.getText(),Double.parseDouble(String.valueOf(txtSalary.getText())),txtDate.getText());
        boolean isSave = employeeBO.saveEmployee(employeeDto);

        if (isSave) {
            new Alert(Alert.AlertType.CONFIRMATION, "employee saved!").show();
            clearFields();
            initialize();
        }
    }

    private boolean validateEmployee() {

        String employeeIdText = txtEmployeeId.getText();

        boolean isEmployeeIDValidation = Pattern.matches("[E][0-9]{3,}", employeeIdText);

        if (!isEmployeeIDValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID EMPLOYEE ID").show();
            txtEmployeeId.setStyle("-fx-border-color: Red");

        }


        String employeeNameText = txtEmployeeName.getText();

        boolean isEmployeeNameValidation = Pattern.matches("[A-Za-z.]{3,}", employeeNameText);

        if (!isEmployeeNameValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID EMPLOYEE NAME").show();
            txtEmployeeName.setStyle("-fx-border-color: Red");

        }

        String email = txtEmail.getText();

        boolean isEmailValidation = Pattern.matches("[a-z].*(com|lk)", email);

        if (!isEmailValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID EMAIL").show();
            txtEmail.setStyle("-fx-border-color: Red");

        }

        String telText = txtTel.getText();

        boolean isCustomerTelValidation = Pattern.matches("[0-9]{10}", telText);

        if (!isCustomerTelValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID EMPLOYEE TEL").show();
            txtTel.setStyle("-fx-border-color: Red");

        }


        Double salary = Double.parseDouble(txtSalary.getText());
        String salaryString = String.format("%.2f",salary);
        boolean isSalaryValidation = Pattern.matches("^[1-9]\\d{0,6}\\.\\d{2}$", salaryString);

        if (!isSalaryValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID SALARY").show();
            txtSalary.setStyle("-fx-border-color: Red");
            return false;
        }


        return  true;
    }


    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        EmployeeDto employeeDto = new EmployeeDto(txtEmployeeId.getText(),txtEmployeeName.getText(),txtEmail.getText(),txtTel.getText(),txtJobTitle.getText(),Double.parseDouble(String.valueOf(txtSalary.getText())),txtDate.getText());
        boolean isUpdate = employeeBO.updateEmployee(employeeDto);
        if(isUpdate) {
            new Alert(Alert.AlertType.CONFIRMATION, "employee updated!").show();
            initialize();
        }

    }

    boolean existEmployee(String id) throws SQLException, ClassNotFoundException {
        return employeeBO.existEmployee(id);

    }


    @FXML
    void txtSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        String employeeid = txtEmployeeId.getText();
        EmployeeDto employeeDto = employeeBO.searchEmployee(employeeid);
        System.out.println("hii");
        if(employeeDto != null) {

            fillFields(employeeDto);
        } else {
            new Alert(Alert.AlertType.INFORMATION, "employee not found!").show();
        }

    }

    private void fillFields(EmployeeDto dto) {
        txtEmployeeId.setText(dto.getId());
        txtEmployeeName.setText(dto.getName());
        txtEmail.setText(dto.getEmail());
        txtTel.setText(dto.getTel());
        txtJobTitle.setText(dto.getJobTitle());
        txtSalary.setText(String.valueOf(dto.getSalary()));
        txtDate.setText(dto.getDate());

    }

    void clearFields() {
        txtEmployeeId.setText("");
        txtEmployeeName.setText("");
        txtEmail.setText("");
        txtTel.setText("");
        txtJobTitle.setText("");
        txtSalary.setText("");
        txtDate.setText("");
    }

}
