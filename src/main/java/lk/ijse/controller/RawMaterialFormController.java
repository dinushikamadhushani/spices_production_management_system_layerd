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
import lk.ijse.bo.custom.RawMaterialBO;
import lk.ijse.bo.custom.impl.RawMaterialBOImpl;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.RawMaterialDto;
import lk.ijse.dto.tm.RawMaterialTm;
//import lk.ijse.model.RawMaterialModel;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class RawMaterialFormController {

    @FXML
    private TableColumn<?, ?> colMaterialName;

    @FXML
    private TableColumn<?, ?> colQtyOnStock;

    @FXML
    private TableColumn<?, ?> colRawId;

    @FXML
    private TableColumn<?, ?> colUnitPrice;


    @FXML
    private AnchorPane root;

    @FXML
    private TableView<RawMaterialTm> tblRawMaterial;

    @FXML
    private TextField txtQtyOnStock;

    @FXML
    private TextField txtRawId;

    @FXML
    private TextField txtRawName;

    @FXML
    private TextField txtUnitPrice;

    RawMaterialBO rawMaterialBO = new RawMaterialBOImpl();


    public void initialize() throws SQLException, ClassNotFoundException {
        setCellValueFactory();
        loadAllMaterials();
    }

    private void setCellValueFactory() {
        colRawId.setCellValueFactory(new PropertyValueFactory<>("rawMaterialId"));
        colMaterialName.setCellValueFactory(new PropertyValueFactory<>("rawMaterialName"));
        colQtyOnStock.setCellValueFactory(new PropertyValueFactory<>("qtyOnStock"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

    }

    private void loadAllMaterials() throws SQLException, ClassNotFoundException {
       // var model = new RawMaterialModel();

        ObservableList<RawMaterialTm> obList = FXCollections.observableArrayList();
        List<RawMaterialDto> dtoList = rawMaterialBO.getAllMaterials();

        for(RawMaterialDto dto : dtoList) {
            obList.add(
                    new RawMaterialTm(
                            dto.getRawMaterialId(),
                            dto.getRawMaterialName(),
                            dto.getQtyOnStock(),
                            dto.getUnitPrice()

                    )
            );
        }

        tblRawMaterial.setItems(obList);

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

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        /*String rawId = txtRawId.getText();

        var rawMaterialModel = new RawMaterialModel();
        try {
            boolean isDeleted = rawMaterialModel.deleteRawMaterial(rawId);

            if(isDeleted) {
                tblRawMaterial.refresh();
                new Alert(Alert.AlertType.CONFIRMATION, "material deleted!").show();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }*/

        String id = tblRawMaterial.getSelectionModel().getSelectedItem().getRawMaterialId();
        try {
            if (!existRawMaterial(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
            }
            rawMaterialBO.deleteRawMaterial(id);
            tblRawMaterial.getItems().remove(tblRawMaterial.getSelectionModel().getSelectedItem());
            tblRawMaterial.getSelectionModel().clearSelection();
            // initUI();
            clearFields();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the customer " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    boolean existRawMaterial(String id) throws SQLException, ClassNotFoundException {
        return rawMaterialBO.existRawMaterial(id);

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        /*String id = txtRawId.getText();
        String name = txtRawName.getText();
        Double qtyOnStock = Double.valueOf(txtQtyOnStock.getText());
        Double unitPrice = Double.valueOf(txtUnitPrice.getText());

        boolean isValidate = validateRawMaterial();

        if (isValidate) {

            var dto = new RawMaterialDto(id, name, qtyOnStock, unitPrice);

            var model = new RawMaterialModel();
            try {
                boolean isSaved = model.saveRawMaterial(dto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "material saved!").show();
                    clearFields();
                    initialize();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
*/

        boolean isValidate = validateRawMaterial();
        if(isValidate){
            String id = txtRawId.getText();
            String name = txtRawName.getText();
            Double qtyOnStock = Double.valueOf(txtQtyOnStock.getText());
            BigDecimal unitPrice = BigDecimal.valueOf(Long.parseLong(txtUnitPrice.getText()));

            var dto = new RawMaterialDto(id,name,qtyOnStock,unitPrice);
        }

        RawMaterialDto rawMaterialDto = new RawMaterialDto(txtRawId.getText(),txtRawName.getText(),Double.parseDouble(String.valueOf(txtQtyOnStock.getText())), BigDecimal.valueOf(Long.parseLong(txtUnitPrice.getText())));
        boolean isSave = rawMaterialBO.saveRawMaterial(rawMaterialDto);

        if (isSave) {
            new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
            clearFields();
            initialize();
        }
    }

    private boolean validateRawMaterial() {

        String rawMaterialIdText = txtRawId.getText();

        boolean isRawMaterialIDValidation = Pattern.matches("[R][0-9]{3,}", rawMaterialIdText);

        if (!isRawMaterialIDValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID RAW MATERIAL ID").show();
            txtRawId.setStyle("-fx-border-color: Red");

        }


        String rawMaterialNameText = txtRawName.getText();

        boolean isRawMaterialNameValidation = Pattern.matches("[A-Za-z.]{5,}", rawMaterialNameText);

        if (!isRawMaterialNameValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID RAW MATERIAL name").show();
            txtRawName.setStyle("-fx-border-color: Red");

        }

        Double qtyOnStock = Double.parseDouble(txtQtyOnStock.getText());
        String qtyOnStockString = String.format("%.2f",qtyOnStock);
        boolean isQtyOnStockValidation = Pattern.matches("[-+]?[0-9]*\\.?[0-9]+", qtyOnStockString);

        if (!isQtyOnStockValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID QTY").show();
            txtQtyOnStock.setStyle("-fx-border-color: Red");

        }

        Double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        String unitPriceString = String.format("%.2f",unitPrice);
        boolean isUnitPriceValidation = Pattern.matches("^[1-9]\\d{0,6}\\.\\d{2}$", unitPriceString);

        if (!isUnitPriceValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID UNIT PRICE").show();
            txtUnitPrice.setStyle("-fx-border-color: Red");
            return false;
        }

        return true;
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
       /* String rawMaterialId = txtRawId.getText();
        String rawMaterialName = txtRawName.getText();
        Double qtyOnStock = Double.valueOf(txtQtyOnStock.getText());
        Double unitPrice = Double.valueOf(txtUnitPrice.getText());


        var dto = new RawMaterialDto(rawMaterialId, rawMaterialName, qtyOnStock, unitPrice);

        var model = new RawMaterialModel();
        try {
            boolean isUpdated = model.updateRawMaterial(dto);
            System.out.println(isUpdated);
            if(isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "material updated!").show();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }*/

        RawMaterialDto rawMaterialDto = new RawMaterialDto(txtRawId.getText(),txtRawName.getText(),Double.parseDouble(String.valueOf(txtQtyOnStock.getText())),BigDecimal.valueOf(Long.parseLong(txtUnitPrice.getText())));
        boolean isUpdate = rawMaterialBO.updateRawMaterial(rawMaterialDto);
        if(isUpdate) {
            new Alert(Alert.AlertType.CONFIRMATION, "rawmaterial updated!").show();
            initialize();
        }

    }

    @FXML
    void txtRawIdSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        /*String rawId = txtRawId.getText();

        var model = new RawMaterialModel();
        try {
            RawMaterialDto dto = model.searchRawMaterial(rawId);

            if(dto != null) {
                fillFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "material not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }*/

        String rawMaterialid = txtRawId.getText();
        RawMaterialDto rawMaterialDto = rawMaterialBO.searchRawMaterial(rawMaterialid);
        System.out.println("hii");
        if(rawMaterialDto != null) {

            fillFields(rawMaterialDto);
        } else {
            new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
        }

    }

    private void fillFields(RawMaterialDto dto) {
        txtRawId.setText(dto.getRawMaterialId());
        txtRawName.setText(dto.getRawMaterialName());
        txtQtyOnStock.setText(String.valueOf(dto.getQtyOnStock()));
        txtUnitPrice.setText(String.valueOf(dto.getUnitPrice()));

    }

    void clearFields() {
        txtRawId.setText("");
        txtRawName.setText("");
        txtQtyOnStock.setText("");
        txtUnitPrice.setText("");

    }

}
