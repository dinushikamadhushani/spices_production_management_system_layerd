package lk.ijse.controller;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.bo.custom.ItemBO;
import lk.ijse.bo.custom.PlaceOrderBO;
import lk.ijse.bo.custom.impl.CustomerBOImpl;
import lk.ijse.bo.custom.impl.ItemBOImpl;
import lk.ijse.bo.custom.impl.PlaceOrderBOImpl;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.CustomerDto;
import lk.ijse.dto.ItemtDto;
import lk.ijse.dto.OrderDetailDTO;
//import lk.ijse.dto.PlaceOrderDto;
//import lk.ijse.dto.tm.CartTm;
import lk.ijse.dto.tm.OrderDetailTM;
//import lk.ijse.model.CustomerModel;
//import lk.ijse.model.ItemModel;
//import lk.ijse.model.OrderModel;
//import lk.ijse.model.PlaceOrderModel;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PlaceOrderFormController {
  //  private final CustomerModel customerModel = new CustomerModel();
   // private final ItemModel itemModel = new ItemModel();
   // private final OrderModel orderModel = new OrderModel();
   // private final ObservableList<CartTm> obList = FXCollections.observableArrayList();
   // private JFXButton btnAddToCart;

    @FXML
    private JFXButton btnAddToCart;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnPlaceorder;

    @FXML
    private JFXComboBox<String> cmbCustomerId;
    @FXML
    private JFXComboBox<String> cmbItemId;
    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colItemName;
    @FXML
    private TableColumn<?, ?> colItemId;
    @FXML
    private TableColumn<?, ?> colQty;
    @FXML
    private TableColumn<?, ?> colTotal;
    @FXML
    private TableColumn<?, ?> colUnitPrice;
    @FXML
    private Label lblCustomerName;
    @FXML
    private Label lblDescription;
    @FXML
    private Label lblOrderDate;
    @FXML
    private Label lblOrderId;
    @FXML
    private Label lblQtyOnHand;
    @FXML
    private Label lblUnitPrice;
    @FXML
    private AnchorPane root;
    @FXML
    private TableView<OrderDetailTM> tblOrderCart;
    @FXML
    private TextField txtQty;
    @FXML
    private Label lblNetTotal;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private TextField txtUnitPrice;


    private String orderId;

   /* private final PlaceOrderModel placeOrderModel = new PlaceOrderModel();

    public void initialize() {
        setCellValueFactory();
        generateNextOrderId();
        setDate();
        loadCustomerIds();
        loadItemIds();
    }

    private void setCellValueFactory() {
        colItemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("tot"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void generateNextOrderId() {
        try {
            String orderId = orderModel.generateNextOrderId();
            lblOrderId.setText(orderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadItemIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<ItemtDto> itemList = itemModel.loadAllItems();

            for (ItemtDto itemDto : itemList) {
                obList.add(itemDto.getItemId());
            }

            cmbItemId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCustomerIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<CustomerDto> cusList = customerModel.loadAllCustomers();

            for (CustomerDto dto : cusList) {
                obList.add(dto.getId());
            }
            cmbCustomerId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setDate() {
        String date = String.valueOf(LocalDate.now());
        lblOrderDate.setText(date);
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String itemId = cmbItemId.getValue();
        String itemName = lblDescription.getText();
        int qty = Integer.parseInt(txtQty.getText());
        int hand = Integer.parseInt(lblQtyOnHand.getText());
        if (qty < hand) {
            double unitPrice = Double.parseDouble(lblUnitPrice.getText());

            double total = qty * unitPrice;
            Button btn = new Button("remove");
            btn.setCursor(Cursor.HAND);

            btn.setOnAction((e) -> {
                ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

                Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

                if (type.orElse(no) == yes) {
                    int index = tblOrderCart.getSelectionModel().getSelectedIndex();
                    obList.remove(index);
                    tblOrderCart.refresh();

                    calculateNetTotal();
                }
            });

            for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
                if (itemId.equals(colItemId.getCellData(i))) {
                    qty += (int) colQty.getCellData(i);
                    total = qty * unitPrice;

                    obList.get(i).setQty(qty);
                    obList.get(i).setTot(total);

                    tblOrderCart.refresh();
                    calculateNetTotal();
                    return;
                }
            }

            obList.add(new CartTm(
                    itemId,
                    itemName,
                    qty,
                    unitPrice,
                    total,
                    btn
            ));

            tblOrderCart.setItems(obList);
            calculateNetTotal();
            txtQty.clear();
        } else {
            new Alert(Alert.AlertType.ERROR, "The stock haven't ").show();
            txtQty.setStyle("-fx-border-color: Red");

        }
    }

    private void calculateNetTotal() {
        double total = 0;
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            total += (double) colTotal.getCellData(i);
        }

        lblNetTotal.setText(String.valueOf(total));
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        String orderId = lblOrderId.getText();
        String customerId = cmbCustomerId.getValue();
        LocalDate date = LocalDate.parse(lblOrderDate.getText());

        List<CartTm> tmList = new ArrayList<>();

        for (CartTm cartTm : obList) {
            tmList.add(cartTm);
        }

        var placeOrderDto = new PlaceOrderDto(
                orderId,
                customerId,
                date,
                tmList
        );

        try {
            boolean isSuccess = placeOrderModel.placeOrder(placeOrderDto);
            if(isSuccess) {
                new Alert(Alert.AlertType.CONFIRMATION, "order completed!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void cmbItemOnAction(ActionEvent event) {
        String itemId = cmbItemId.getValue();

        txtQty.requestFocus();

        try {
            ItemtDto dto = itemModel.searchItem(itemId);

            lblDescription.setText(dto.getItemName());
            lblUnitPrice.setText(String.valueOf(dto.getUnitPrice()));
            lblQtyOnHand.setText(String.valueOf(dto.getQtyOnHand()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbCustomerOnAction(ActionEvent event) throws SQLException {
        String customerId = cmbCustomerId.getValue();
        CustomerDto dto = customerModel.searchCustomer(customerId);

        lblCustomerName.setText(dto.getName());
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        btnAddToCartOnAction(event);
    }

    @FXML
    void btnNewCustomerOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/customerForm.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Customer Manage");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboardForm.fxml"));
        Stage stage = (Stage) root.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard");
        stage.centerOnScreen();
    }

    public void btnPrintOnAction(ActionEvent actionEvent) {
        InputStream resource = this.getClass().getResourceAsStream("/reports/Blank_A4.jrxml");
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(resource);
            HashMap map = new HashMap<>();
            map.put("orderId",lblOrderId.getText());

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, DbConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/

    PlaceOrderBO placeOrderBO=new PlaceOrderBOImpl();

    ItemBO itemBO = new ItemBOImpl();

    CustomerBO customerBO = new CustomerBOImpl();

    public void initialize() throws SQLException, ClassNotFoundException {

        tblOrderCart.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("itemId"));
        tblOrderCart.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("itemName"));
        tblOrderCart.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblOrderCart.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblOrderCart.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("total"));
        TableColumn<OrderDetailTM, Button> lastCol = (TableColumn<OrderDetailTM, Button>) tblOrderCart.getColumns().get(5);

        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Delete");

            btnDelete.setOnAction(event -> {
                tblOrderCart.getItems().remove(param.getValue());
                tblOrderCart.getSelectionModel().clearSelection();
                calculateTotal();
                enableOrDisablePlaceOrderButton();
            });

            return new ReadOnlyObjectWrapper<>(btnDelete);
        });

        orderId = generateNewOrderId();
        lblOrderId.setText("Order id: " + orderId);
        lblOrderDate.setText(LocalDate.now().toString());
        btnPlaceorder.setDisable(true);
        txtCustomerName.setFocusTraversable(false);
        txtCustomerName.setEditable(false);
        txtName.setFocusTraversable(false);
        txtName.setEditable(false);
        txtUnitPrice.setFocusTraversable(false);
        txtUnitPrice.setEditable(false);
        txtQtyOnHand.setFocusTraversable(false);
        txtQtyOnHand.setEditable(false);
        txtQty.setOnAction(event -> btnAddToCart.fire());
        txtQty.setEditable(false);
        btnAddToCart.setDisable(true);

        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            enableOrDisablePlaceOrderButton();
            if (newValue != null) {
                try {
                    try {
                        if (!existCustomer(newValue + "")) {
                            //"There is no such customer associated with the id " + id
                            new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + newValue + "").show();
                        }
                        //Search Customer
                        CustomerDto customerDto = placeOrderBO.searchCustomer(newValue + "");
                        txtCustomerName.setText(customerDto.getName());

                    } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR, "Failed to find the customer " + newValue + "" + e).show();
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                txtCustomerName.clear();
            }
        });


        cmbItemId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newItemId) -> {
            txtQty.setEditable(newItemId != null);
            btnAddToCart.setDisable(newItemId == null);

            if (newItemId != null) {

                /*Find Item*/
                try {
                    if (!existItem(newItemId + "")) {
//                        throw new NotFoundException("There is no such item associated with the id " + code);
                    }

                    //Search Item
                    ItemtDto item = placeOrderBO.searchItem(newItemId + "");

                    txtName.setText(item.getItemName());
                    txtUnitPrice.setText(item.getUnitPrice().setScale(2).toString());

//                    txtQtyOnHand.setText(tblOrderDetails.getItems().stream().filter(detail-> detail.getCode().equals(item.getCode())).<Integer>map(detail-> item.getQtyOnHand() - detail.getQty()).findFirst().orElse(item.getQtyOnHand()) + "");
                    Optional<OrderDetailTM> optOrderDetail = tblOrderCart.getItems().stream().filter(detail -> detail.getItemId().equals(newItemId)).findFirst();
                    txtQtyOnHand.setText((optOrderDetail.isPresent() ? item.getQtyOnHand() - optOrderDetail.get().getQty() : item.getQtyOnHand()) + "");

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            } else {
                txtName.clear();
                txtQty.clear();
                txtQtyOnHand.clear();
                txtUnitPrice.clear();
            }
        });

        tblOrderCart.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedOrderDetail) -> {

            if (selectedOrderDetail != null) {
                cmbItemId.setDisable(true);
                cmbItemId.setValue(selectedOrderDetail.getItemId());
                btnAddToCart.setText("Update");
                lblQtyOnHand.setText(Integer.parseInt(lblQtyOnHand.getText()) + selectedOrderDetail.getQty() + "");
                txtQty.setText(selectedOrderDetail.getQty() + "");
            } else {
                btnAddToCart.setText("Add");
                cmbItemId.setDisable(false);
                cmbItemId.getSelectionModel().clearSelection();
                txtQty.clear();
            }

        });

        loadAllCustomerIds();
        loadAllItemCodes();
    }

    private boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return placeOrderBO.existItem(code);
    }

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return placeOrderBO.existCustomer(id);
    }

    public String generateNewOrderId() {
        try {
            return placeOrderBO.generateOrderID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new order id").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "OID-001";
    }

    private void loadAllCustomerIds() {
        try {
            ArrayList<CustomerDto> allCustomers = placeOrderBO.getAllCustomer();
            for (CustomerDto c : allCustomers) {
                cmbCustomerId.getItems().add(c.getId());
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load customer ids").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAllItemCodes() {
        try {
            /*Get all items*/

            ArrayList<ItemtDto> allItems = placeOrderBO.getAllItems();
            for (ItemtDto i : allItems) {
                cmbItemId.getItems().add(i.getItemId());
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

    public void btnAddToCartOnAction(ActionEvent actionEvent) {

        if (!txtQty.getText().matches("\\d+") || Integer.parseInt(txtQty.getText()) <= 0 ||
                Integer.parseInt(txtQty.getText()) > Integer.parseInt(txtQtyOnHand.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty").show();
            txtQty.requestFocus();
            txtQty.selectAll();
            return;
        }

        String itemCode = cmbItemId.getSelectionModel().getSelectedItem();
        String description = txtName.getText();
        BigDecimal unitPrice = new BigDecimal(txtUnitPrice.getText()).setScale(2);
        int qty = Integer.parseInt(txtQty.getText());
        BigDecimal total = unitPrice.multiply(new BigDecimal(qty)).setScale(2);

        boolean exists = tblOrderCart.getItems().stream().anyMatch(detail -> detail.getItemId().equals(itemCode));

        if (exists) {
            OrderDetailTM orderDetailTM = tblOrderCart.getItems().stream().filter(detail -> detail.getItemId().equals(itemCode)).findFirst().get();

            if (btnAddToCart.getText().equalsIgnoreCase("Update")) {
                orderDetailTM.setQty(qty);
                orderDetailTM.setTotal(total);
                tblOrderCart.getSelectionModel().clearSelection();
            } else {
                orderDetailTM.setQty(orderDetailTM.getQty() + qty);
                total = new BigDecimal(orderDetailTM.getQty()).multiply(unitPrice).setScale(2);
                orderDetailTM.setTotal(total);
            }
            tblOrderCart.refresh();
        } else {
            tblOrderCart.getItems().add(new OrderDetailTM(itemCode, description, qty, unitPrice, total));
        }
        cmbItemId.getSelectionModel().clearSelection();
        cmbItemId.requestFocus();
        calculateTotal();
        enableOrDisablePlaceOrderButton();
    }

    private void calculateTotal() {
        BigDecimal total = new BigDecimal(0);

        for (OrderDetailTM detail : tblOrderCart.getItems()) {
            total = total.add(detail.getTotal());
        }
        lblNetTotal.setText("Total: " + total);
    }

    private void enableOrDisablePlaceOrderButton() {
        btnPlaceorder.setDisable(!(cmbCustomerId.getSelectionModel().getSelectedItem() != null && !tblOrderCart.getItems().isEmpty()));
    }

    public void txtQtyOnAction(ActionEvent actionEvent) {
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        boolean b = saveOrder(orderId, LocalDate.now(), cmbCustomerId.getValue(),
                tblOrderCart.getItems().stream().map(tm -> new OrderDetailDTO(orderId,tm.getItemId(), tm.getQty(), tm.getUnitPrice())).collect(Collectors.toList()));

        if (b) {
            new Alert(Alert.AlertType.INFORMATION, "Order has been placed successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order has not been placed successfully").show();
        }

        orderId = generateNewOrderId();
        lblOrderId.setText("Order id: " + orderId);
        cmbCustomerId.getSelectionModel().clearSelection();
        cmbItemId.getSelectionModel().clearSelection();
        tblOrderCart.getItems().clear();
        txtQty.clear();
        calculateTotal();
    }

    public boolean saveOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails) {
        try {
            return placeOrderBO.placeOrder(orderId,orderDate,customerId,orderDetails);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbItemOnAction(ActionEvent event) {
        String itemId = cmbItemId.getValue();

        txtQty.requestFocus();

        try {
            ItemtDto dto = itemBO.searchItem(itemId);

            txtName.setText(dto.getItemName());
            txtUnitPrice.setText(String.valueOf(dto.getUnitPrice()));
            txtQtyOnHand.setText(String.valueOf(dto.getQtyOnHand()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbCustomerOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String customerId = cmbCustomerId.getValue();
        CustomerDto dto = customerBO.searchCustomer(customerId);

        txtCustomerName.setText(dto.getName());
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
    void btnNewCustomerOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/customerForm.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Customer Manage");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }


    public void btnPrintOnAction(ActionEvent actionEvent) {
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

    }



}
