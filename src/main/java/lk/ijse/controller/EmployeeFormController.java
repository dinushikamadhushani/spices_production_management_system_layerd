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
import lk.ijse.dto.EmployeeDto;
import lk.ijse.dto.tm.EmployeeTm;
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
            List<EmployeeDto> dtoList = model.getAllEmployee();

            for(EmployeeDto dto : dtoList) {
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
        String id = txtEmployeeId.getText();

        var employeeModel = new EmployeeModel();
        try {
            boolean isDeleted = employeeModel.deleteEmployee(id);

            if(isDeleted) {
                tblEmployee.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "employee deleted!").show();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtEmployeeId.getText();
        String name = txtEmployeeName.getText();
        String email = txtEmail.getText();
        String tel = txtTel.getText();
        String jobTitle = txtJobTitle.getText();
        Double salary = Double.valueOf(txtSalary.getText());
        String date = txtDate.getText();

        boolean isValidate = validateEmployee();

        if (isValidate) {
            var dto = new EmployeeDto(id, name, email, tel, jobTitle, salary, date);

            var model = new EmployeeModel();
            try {
                boolean isSaved = model.saveEmployee(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "employee saved!").show();
                    clearFields();
                    initialize();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
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
    void btnUpdateOnAction(ActionEvent event) {

        String id = txtEmployeeId.getText();
        String name = txtEmployeeName.getText();
        String email = txtEmail.getText();
        String tel = txtTel.getText();
        String jobTitle = txtJobTitle.getText();
        Double salary = Double.valueOf(txtSalary.getText());
        String date = txtDate.getText();


        var dto = new EmployeeDto(id, name, email, tel,jobTitle, salary, date);

        var model = new EmployeeModel();
        try {
            boolean isUpdated = model.updateEmployee(dto);
            System.out.println(isUpdated);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "employee updated!").show();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {
        String id = txtEmployeeId.getText();

        var model = new EmployeeModel();
        try {
            EmployeeDto dto = model.searchEmployee(id);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "employee not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
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
