package lk.ijse.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.bo.custom.ItemBO;
import lk.ijse.bo.custom.impl.CustomerBOImpl;
import lk.ijse.bo.custom.impl.ItemBOImpl;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.ItemtDto;
import lk.ijse.dto.RawMaterialDto;
import lk.ijse.dto.tm.ItemTm;
import lk.ijse.entity.Item;
import lk.ijse.model.ItemModel;
import lk.ijse.model.RawMaterialModel;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class ItemFormController {

    @FXML
    private ComboBox<String> cmbRawMaterialId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colItemId;

    @FXML
    private TableColumn<?, ?> colItemName;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private TableColumn<?, ?> colRawMaterialId;

    @FXML
    private AnchorPane pagingPane;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<ItemTm> tblItem;

    @FXML
    private TextField txtItemId;

    @FXML
    private TextField txtItemName;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private Label lblRawMaterialId;

    ItemBO itemBO=new ItemBOImpl();


    private final ItemModel itemModel = new ItemModel();



    public void initialize() throws SQLException {
        setCellValueFactory();
        LoadAllItems();
        setListener();
        loadRawMaterialsIds();

    }

    private void setCellValueFactory() {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colRawMaterialId.setCellValueFactory(new PropertyValueFactory<>("rawMaterialId"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void LoadAllItems()  {
        try {
            List<Item> dtoList = itemBO.getAllItems();

            ObservableList<ItemTm> obList = FXCollections.observableArrayList();

            for(Item dto : dtoList) {
                Button btn = new Button("remove");
                btn.setCursor(Cursor.HAND);

                btn.setOnAction((e) -> {
                    ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                    Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                    if(type.orElse(no) == yes) {
                        int selectedIndex = tblItem.getSelectionModel().getSelectedIndex();
                        //String itemId = (String) colItemId.getCellData(selectedIndex);

                        //deleteItem(itemId);   //delete item from the database

                        obList.remove(selectedIndex);   //delete item from the JFX-Table
                        tblItem.refresh();
                    }
                });

                var tm = new ItemTm(
                        dto.getItemId(),
                        dto.getItemName(),
                        dto.getUnitPrice(),
                        dto.getQtyOnHand(),
                        dto.getRawMaterialId(),
                        btn
                );
                obList.add(tm);
            }
            tblItem.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadRawMaterialsIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<RawMaterialDto> rawList = RawMaterialModel.loadAllRawMaterials();

            for (RawMaterialDto dto : rawList) {
                obList.add(dto.getRawMaterialId());
            }
            cmbRawMaterialId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteItem(String ItemId) {
        /*try {
            boolean isDeleted = itemModel.deleteItem(ItemId);
            if(isDeleted)
                new Alert(Alert.AlertType.CONFIRMATION, "item deleted!").show();
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR, ex.getMessage()).show();
        }*/

        String id = tblItem.getSelectionModel().getSelectedItem().getItemId();
        try {
            if (!existItem(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
            }
            itemBO.deleteItem(id);
            tblItem.getItems().remove(tblItem.getSelectionModel().getSelectedItem());
            tblItem.getSelectionModel().clearSelection();
            // initUI();
            clearFields();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the customer " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        /*String itemId = txtItemId.getText();
        String itemName = txtItemName.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        String rawMaterialId = cmbRawMaterialId.getValue();

        boolean isValidate = validateItem();

        if(isValidate) {
            var itemtDto = new ItemtDto(itemId, itemName, unitPrice, qtyOnHand, rawMaterialId);

            try {
                boolean isSaved = itemModel.saveItem(itemtDto);
                if (isSaved) {
                    new Alert(Alert.AlertType.CONFIRMATION, "item saved!").show();
                    LoadAllItems();
                    clearFields();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }*/

        ItemtDto itemtDto = new ItemtDto(txtItemId.getText(),txtItemName.getText(),BigDecimal.valueOf(Long.parseLong(txtUnitPrice.getText())),Integer.parseInt(String.valueOf(txtQtyOnHand.getText())),cmbRawMaterialId.getValue());
        boolean isSave = itemBO.saveItem(itemtDto);

        if (isSave) {
            new Alert(Alert.AlertType.CONFIRMATION, "item saved!").show();
            clearFields();
            initialize();
        }


    }

    private boolean validateItem() {

        String itemIdText = txtItemId.getText();

        boolean isItemIDValidation = Pattern.matches("[I][0-9]{3,}", itemIdText);

        if (!isItemIDValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID ITEM ID").show();
            txtItemId.setStyle("-fx-border-color: Red");

        }


        String itemNameText = txtItemName.getText();

        boolean isItemNameValidation = Pattern.matches("[A-Za-z.]{3,}", itemNameText);

        if (!isItemNameValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID ITEM NAME").show();
            txtItemName.setStyle("-fx-border-color: Red");

        }

        Double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        String unitPriceString = String.format("%.2f",unitPrice);
        boolean isUnitPriceValidation = Pattern.matches("^[1-9]\\d{0,6}\\.\\d{2}$", unitPriceString);

        if (!isUnitPriceValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID ITEM Unit price").show();
            txtUnitPrice.setStyle("-fx-border-color: Red");

        }

        String qtyOnHandText = txtQtyOnHand.getText();

        boolean isQtyOnHandValidation = Pattern.matches("[-+]?[0-9]*\\.?[0-9]+", qtyOnHandText);

        if (!isQtyOnHandValidation) {

            new Alert(Alert.AlertType.ERROR, "INVALID ITEM QTY").show();
            txtQtyOnHand.setStyle("-fx-border-color: Red");
            return false;
        }

        return  true;
    }





    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
       /* String itemId = txtItemId.getText();
        String itemName = txtItemName.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        String rawMaterialIdId = cmbRawMaterialId.getValue();

//        var model = new ItemModel();
        try {
            boolean isUpdated = itemModel.updateItem(new ItemtDto(itemId, itemName, unitPrice, qtyOnHand, rawMaterialIdId));
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "item updated").show();
                initialize();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }*/
        ItemtDto itemtDto = new ItemtDto(txtItemId.getText(),txtItemName.getText(),BigDecimal.valueOf(Long.parseLong(txtUnitPrice.getText())),Integer.parseInt(String.valueOf(txtQtyOnHand.getText())),cmbRawMaterialId.getValue());
        boolean isUpdate = itemBO.updateItem(itemtDto);
        if(isUpdate) {
            new Alert(Alert.AlertType.CONFIRMATION, "item updated!").show();
            initialize();
        }

    }

    boolean existItem(String id) throws SQLException, ClassNotFoundException {
        return itemBO.existItem(id);

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
       /* String ItemId = txtItemId.getText();

        try {
            boolean isDeleted = itemModel.deleteItem(ItemId);

            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "item deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }*/

        String id = tblItem.getSelectionModel().getSelectedItem().getItemId();
        try {
            if (!existItem(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
            }
            itemBO.deleteItem(id);
            tblItem.getItems().remove(tblItem.getSelectionModel().getSelectedItem());
            tblItem.getSelectionModel().clearSelection();
            // initUI();
            clearFields();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the customer " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }




    @FXML
    void productIdSearchOnAction(ActionEvent event) {


    }

    private void setListener() {
        tblItem.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    var dto = new ItemtDto(

                    );
                    setFields(dto);
                });
    }

    private void setFields(ItemtDto dto) {
        txtItemId.setText(dto.getItemId());
        txtItemName.setText(dto.getItemName());
        txtUnitPrice.setText(String.valueOf(dto.getUnitPrice()));
        txtQtyOnHand.setText(String.valueOf(dto.getQtyOnHand()));
        cmbRawMaterialId.setValue(dto.getRawMaterialId());
    }

    private void clearFields() {
        txtItemId.setText("");
        txtItemName.setText("");
        txtUnitPrice.setText("");
        txtQtyOnHand.setText("");
        cmbRawMaterialId.setValue("");
    }


    public void itemIdSearchOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        /*String code = txtItemId.getText();

        try {
            ItemtDto dto = itemModel.searchItem(code);
            if (dto != null) {
                setFields(dto);
            } else {
                new Alert(Alert.AlertType.INFORMATION, "item not found!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }*/

        String itemid = txtItemId.getText();
        ItemtDto itemtDto = itemBO.searchItem(itemid);
        System.out.println("hii");
        if(itemtDto != null) {
            setFields(itemtDto);
        } else {
            new Alert(Alert.AlertType.INFORMATION, "item not found!").show();
        }


    }

    @FXML
    void cmbRawMaterialIdOnAction(ActionEvent event) {
        //String rawMaterialId = cmbRawMaterialId.getValue();
       // RawMaterialDto dto = rawMaterialModel.searchCustomer(rawMaterialId);


    }

}
