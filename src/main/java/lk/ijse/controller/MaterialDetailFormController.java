package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.custom.*;
import lk.ijse.bo.custom.impl.*;
import lk.ijse.dao.custom.RawMaterialDAO;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.*;
import lk.ijse.dto.tm.OrderDetailTM;
import lk.ijse.dto.tm.SupplierDetailTm;
//import lk.ijse.model.GetOrderModel;
//import lk.ijse.model.RawMaterialModel;
//import lk.ijse.model.SupplierModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MaterialDetailFormController {

    public Label lblQtyOnHand;
    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXComboBox<String> cmbMaterialId;

    @FXML
    private JFXComboBox<String> cmbSupplierId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colMaterialId;

    @FXML
    private TableColumn<?, ?> colMaterialName;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblMaterialName;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblOrderDate;

  
    @FXML
    private Label lblSupplierName;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private AnchorPane root;


    @FXML
    private TableView<SupplierDetailTm> tblMaterialCart;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtMaterialName;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private TextField txtSupplierName;

    @FXML
    private JFXButton txtGetOrder;

    @FXML
    private JFXButton btnGetOrder;



  //  private final GetOrderModel getOrderModel = new GetOrderModel();

    //private final RawMaterialModel rawMaterialModel = new RawMaterialModel();

   // private  final SupplierModel supplierModel=new SupplierModel();
    ObservableList<SupplierDetailTm> obList1 = FXCollections.observableArrayList();

    MaterialDetailBO materialDetailBO=new MaterialDetailBOImpl();

    RawMaterialBO rawMaterialBO = new RawMaterialBOImpl();

    SupplierBO supplierBO = new SupplierBOImpl();


  /*  public void initialize() {
        setCellValueFactory();
        setDate();
        loadSupplierIds();
        loadRawMaterialIds();
    }

    private void setCellValueFactory() {
        colMaterialId.setCellValueFactory(new PropertyValueFactory<>("rawMaterialId"));
        colMaterialName.setCellValueFactory(new PropertyValueFactory<>("materialName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("tot"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void loadRawMaterialIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<RawMaterialDto> rawList = rawMaterialBO.getAllMaterials();

            for (RawMaterialDto rawMaterialDto : rawList) {
                obList.add(rawMaterialDto.getRawMaterialId());
            }

            cmbMaterialId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadSupplierIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<SupplierDto> supList = supplierModel.loadAllSuppliers();

            for (SupplierDto dto : supList) {
                obList.add(dto.getSupplierId());
            }
            cmbSupplierId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setDate() {
        String date = String.valueOf(LocalDate.now());
        lblOrderDate.setText(date);
    }


    @FXML
    void btnAddOnAction(ActionEvent event) {
        String materialId = cmbMaterialId.getValue();
        String materialName = lblMaterialName.getText();
        int qty = Integer.parseInt(txtQty.getText());
        double hand = Double.parseDouble(lblQtyOnHand.getText());
        if (qty < hand) {
            BigDecimal unitPrice =  new BigDecimal(lblUnitPrice.getText());

            double total = qty * unitPrice;
            Button btn = new Button("remove");
            btn.setCursor(Cursor.HAND);

            btn.setOnAction((e) -> {
                ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                if (type.orElse(no) == yes) {
                    int index = tblMaterialCart.getSelectionModel().getSelectedIndex();
                    obList1.remove(index);
                    tblMaterialCart.refresh();

                    calculateNetTotal();
                }
            });

            for (int i = 0; i < tblMaterialCart.getItems().size(); i++) {
                if (materialId.equals(colMaterialId.getCellData(i))) {
                    qty += (int) colQty.getCellData(i);
                    total = qty * unitPrice;

                    obList1.get(i).setQty(qty);
                    obList1.get(i).setTot(total);

                    tblMaterialCart.refresh();
                    calculateNetTotal();
                    return;
                }
            }

            obList1.add(new SupplierDetailTm(
                    materialId,
                    materialName,
                    qty,
                    unitPrice,
                    total,
                    btn
            ));

            tblMaterialCart.setItems(obList1);
            calculateNetTotal();
            txtQty.clear();
        }

    }

    private void calculateNetTotal() {
        double total = 0;
        for (int i = 0; i < tblMaterialCart.getItems().size(); i++) {
            total += (double) colTotal.getCellData(i);
        }

        lblNetTotal.setText(String.valueOf(total));
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboardForm.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard");
        stage.centerOnScreen();

    }

    @FXML
    void btnNewSupplierOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/supplierForm.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Supplier Manage");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }

    @FXML
    void btnGetOrderOnAction(ActionEvent event) {

        String supplierId = cmbSupplierId.getValue();
        String rawMaterialId = cmbMaterialId.getValue();
        LocalDate date = LocalDate.parse(lblOrderDate.getText());

        List<SupplierDetailTm> tmList = new ArrayList<>();

        for (SupplierDetailTm materialCartTm : obList1) {
            tmList.add(materialCartTm);
        }

        var getOrderDto = new GetOrderDto(

                supplierId,
                rawMaterialId,
                date,
                tmList
        );

        try {
            boolean isSuccess = getOrderModel.getOrder(getOrderDto);
            if(isSuccess) {
                new Alert(Alert.AlertType.CONFIRMATION, "order completed!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }



    }

    @FXML
    void cmbSupplierOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String supplierId = cmbSupplierId.getValue();
        SupplierDto dto = supplierBO.searchSupplier(supplierId);

        lblSupplierName.setText(dto.getSupplierName());


    }

    @FXML
    void cmbMaterialIdOnAction(ActionEvent event) {
        String rawMaterialId = cmbMaterialId.getValue();

        txtQty.requestFocus();

        try {
            RawMaterialDto dto = rawMaterialBO.searchRawMaterial(rawMaterialId);

            lblMaterialName.setText(dto.getRawMaterialName());
            lblUnitPrice.setText(String.valueOf(dto.getUnitPrice()));
            lblQtyOnHand.setText(String.valueOf(dto.getQtyOnStock()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        btnAddOnAction(event);

    }

    @FXML
    void btnPrintOnAction(ActionEvent event) {
        InputStream resource = this.getClass().getResourceAsStream("/reports/Material.jrxml");
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

    public void initialize() throws SQLException, ClassNotFoundException {

        tblMaterialCart.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("rawMaterialId"));
        tblMaterialCart.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("materialName"));
        tblMaterialCart.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblMaterialCart.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblMaterialCart.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));
        TableColumn<SupplierDetailTm, Button> lastCol = (TableColumn<SupplierDetailTm, Button>) tblMaterialCart.getColumns().get(5);

        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Delete");

            btnDelete.setOnAction(event -> {
                tblMaterialCart.getItems().remove(param.getValue());
                tblMaterialCart.getSelectionModel().clearSelection();
                calculateTotal();
                enableOrDisableGetOrderButton();
            });

            return new ReadOnlyObjectWrapper<>(btnDelete);
        });

        //orderId = generateNewOrderId();
       // lblOrderId.setText("Order id: " + orderId);
        lblOrderDate.setText(LocalDate.now().toString());
        btnGetOrder.setDisable(true);
        txtMaterialName.setFocusTraversable(false);
        txtSupplierName.setEditable(false);
        txtMaterialName.setFocusTraversable(false);
        txtMaterialName.setEditable(false);
        txtUnitPrice.setFocusTraversable(false);
        txtUnitPrice.setEditable(false);
        txtQtyOnHand.setFocusTraversable(false);
        txtQtyOnHand.setEditable(false);
        txtQty.setOnAction(event -> btnAdd.fire());
        txtQty.setEditable(false);
        btnAdd.setDisable(true);

        cmbSupplierId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            enableOrDisableGetOrderButton();
            if (newValue != null) {
                try {
                    try {
                        if (!existSupplier(newValue + "")) {
                            //"There is no such customer associated with the id " + id
                            new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + newValue + "").show();
                        }
                        //Search Customer
                        SupplierDto supplierDto = materialDetailBO.searchSupplier(newValue + "");
                        txtSupplierName.setText(supplierDto.getSupplierName());

                    } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR, "Failed to find the customer " + newValue + "" + e).show();
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                txtSupplierName.clear();
            }
        });


        cmbMaterialId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newItemId) -> {
            txtQty.setEditable(newItemId != null);
            btnAdd.setDisable(newItemId == null);

            if (newItemId != null) {

                /*Find Item*/
                try {
                    if (!existRawMaterial(newItemId + "")) {
//                        throw new NotFoundException("There is no such item associated with the id " + code);
                    }

                    //Search Item
                    RawMaterialDto item = materialDetailBO.searchRawMaterial(newItemId + "");

                    txtSupplierName.setText(item.getRawMaterialName());
                    txtUnitPrice.setText(item.getUnitPrice().setScale(2).toString());

//                    txtQtyOnHand.setText(tblOrderDetails.getItems().stream().filter(detail-> detail.getCode().equals(item.getCode())).<Integer>map(detail-> item.getQtyOnHand() - detail.getQty()).findFirst().orElse(item.getQtyOnHand()) + "");
                    Optional<SupplierDetailTm> optOrderDetail = tblMaterialCart.getItems().stream().filter(detail -> detail.getRawMaterialId().equals(newItemId)).findFirst();
                    txtQtyOnHand.setText((optOrderDetail.isPresent() ? item.getQtyOnStock() - optOrderDetail.get().getQty() : item.getQtyOnStock()) + "");

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            } else {
                txtMaterialName.clear();
                txtQty.clear();
                txtQtyOnHand.clear();
                txtUnitPrice.clear();
            }
        });

        tblMaterialCart.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedOrderDetail) -> {

            if (selectedOrderDetail != null) {
                cmbMaterialId.setDisable(true);
                cmbMaterialId.setValue(selectedOrderDetail.getRawMaterialId());
                btnAdd.setText("Update");
                lblQtyOnHand.setText(Integer.parseInt(lblQtyOnHand.getText()) + selectedOrderDetail.getQty() + "");
                txtQty.setText(selectedOrderDetail.getQty() + "");
            } else {
                btnAdd.setText("Add");
                cmbMaterialId.setDisable(false);
                cmbMaterialId.getSelectionModel().clearSelection();
                txtQty.clear();
            }

        });

        loadAllSupplierIds();
        loadAllRawMaterials();
    }

    private boolean existRawMaterial(String id) throws SQLException, ClassNotFoundException {
        return materialDetailBO.existRawMaterial(id);
    }

    boolean existSupplier(String id) throws SQLException, ClassNotFoundException {
        return materialDetailBO.existSupplier(id);
    }

  /*  public String generateNewOrderId() {
        try {
            return placeOrderBO.generateOrderID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new order id").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "OID-001";
    }*/

    private void loadAllSupplierIds() {
        try {
            ArrayList<SupplierDto> allSuppliers = materialDetailBO.getAllSupplier();
            for (SupplierDto c : allSuppliers) {
                cmbSupplierId.getItems().add(c.getSupplierId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load suppliers ids").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAllRawMaterials() {
        try {
            /*Get all items*/

            ArrayList<RawMaterialDto> allRawMaterial = materialDetailBO.getAllRawMaterials();
            for (RawMaterialDto i : allRawMaterial) {
                cmbMaterialId.getItems().add(i.getRawMaterialId());
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

   /* @FXML
    private void navigateToHome(MouseEvent event) throws IOException {
        URL resource = this.getClass().getResource("/com/example/layeredarchitecture/main-form.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.root.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }*/

    public void btnAddOnAction(ActionEvent actionEvent) {

        if (!txtQty.getText().matches("\\d+") || Integer.parseInt(txtQty.getText()) <= 0 ||
                Integer.parseInt(txtQty.getText()) > Integer.parseInt(txtQtyOnHand.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty").show();
            txtQty.requestFocus();
            txtQty.selectAll();
            return;
        }

        String materialId = cmbMaterialId.getSelectionModel().getSelectedItem();
        String materialName = txtMaterialName.getText();
        BigDecimal unitPrice = new BigDecimal(txtUnitPrice.getText()).setScale(2);
        int qty = Integer.parseInt(txtQty.getText());
        BigDecimal total = unitPrice.multiply(new BigDecimal(qty)).setScale(2);

        boolean exists = tblMaterialCart.getItems().stream().anyMatch(detail -> detail.getRawMaterialId().equals(materialId));

        if (exists) {
            SupplierDetailTm supplierDetailTm = tblMaterialCart.getItems().stream().filter(detail -> detail.getRawMaterialId().equals(materialId)).findFirst().get();

            if (btnAdd.getText().equalsIgnoreCase("Update")) {
                supplierDetailTm.setQty(qty);
                supplierDetailTm.setTotal(total);
                tblMaterialCart.getSelectionModel().clearSelection();
            } else {
                supplierDetailTm.setQty(supplierDetailTm.getQty() + qty);
                total = new BigDecimal(supplierDetailTm.getQty()).multiply(unitPrice).setScale(2);
                supplierDetailTm.setTotal(total);
            }
            tblMaterialCart.refresh();
        } else {
            tblMaterialCart.getItems().add(new SupplierDetailTm(materialId, materialName, qty, unitPrice, total));
        }
        cmbMaterialId.getSelectionModel().clearSelection();
        cmbMaterialId.requestFocus();
        calculateTotal();
        enableOrDisableGetOrderButton();
    }

    private void calculateTotal() {
        BigDecimal total = new BigDecimal(0);

        for (SupplierDetailTm detail : tblMaterialCart.getItems()) {
            total = total.add(detail.getTotal());
        }
        lblNetTotal.setText("Total: " + total);
    }

    private void enableOrDisableGetOrderButton() {
        btnGetOrder.setDisable(!(cmbSupplierId.getSelectionModel().getSelectedItem() != null && !tblMaterialCart.getItems().isEmpty()));
    }

    public void txtQtyOnAction(ActionEvent actionEvent) {
    }

    public void btnGetOrderOnAction(ActionEvent actionEvent) {
        boolean b = saveOrder(LocalDate.now(), cmbSupplierId.getValue(),
                tblMaterialCart.getItems().stream().map(tm -> new SupplierDetailDto(tm.getSupplierId(), tm.getRawMaterialId(),LocalDate.now(), tm.getUnitPrice(),tm.getQty())).collect(Collectors.toList()));

        if (b) {
            new Alert(Alert.AlertType.INFORMATION, "Order has been placed successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order has not been placed successfully").show();
        }

       // orderId = generateNewOrderId();
       // lblOrderId.setText("Order id: " + orderId);
        cmbSupplierId.getSelectionModel().clearSelection();
        cmbMaterialId.getSelectionModel().clearSelection();
        tblMaterialCart.getItems().clear();
        txtQty.clear();
        calculateTotal();
    }

    public boolean saveOrder(LocalDate orderDate, String supplierId, List<SupplierDetailDto> supplierDetails) {
        try {
            return materialDetailBO.getOrder(orderDate,supplierId,supplierDetails);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbMaterialIdOnAction(ActionEvent event) {
        String rawMaterialId = cmbMaterialId.getValue();

        txtQty.requestFocus();

        try {
            RawMaterialDto dto = rawMaterialBO.searchRawMaterial(rawMaterialId);

            txtMaterialName.setText(dto.getRawMaterialName());
            txtUnitPrice.setText(String.valueOf(dto.getUnitPrice()));
            txtQtyOnHand.setText(String.valueOf(dto.getQtyOnStock()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbSupplierOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String supplierId = cmbSupplierId.getValue();
        SupplierDto dto = supplierBO.searchSupplier(supplierId);

        txtSupplierName.setText(dto.getSupplierName());
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboardForm.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard");
        stage.centerOnScreen();
    }

    @FXML
    void btnNewSupplierOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/supplierForm.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Customer Manage");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }


    /*public void btnPrintOnAction(ActionEvent actionEvent) {
        InputStream resource = this.getClass().getResourceAsStream("/reports/Blank_A4.jrxml");
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
            HashMap map = new HashMap<>();
            map.put("orderId", lblOrderId.getText());

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

    @FXML
    void btnPrintOnAction(ActionEvent event) {
        InputStream resource = this.getClass().getResourceAsStream("/reports/Material.jrxml");
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
