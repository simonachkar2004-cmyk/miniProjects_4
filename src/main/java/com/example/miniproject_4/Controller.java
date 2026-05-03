package com.example.miniproject_4;

import com.example.miniproject_4.model.Stock;
import com.example.miniproject_4.store.StockStore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class Controller {

    @FXML private TableView<Stock> stockTable;

    @FXML private TableColumn<Stock, String> nameColumn;
    @FXML private TableColumn<Stock, Integer> quantityColumn;
    @FXML private TableColumn<Stock, Double> priceColumn;
    @FXML private TableColumn<Stock, String> categoryColumn;
    @FXML private TableColumn<Stock, String> supplierColumn;
    @FXML private TableColumn<Stock, LocalDate> dateColumn;

    @FXML private TextField nameField;
    @FXML private TextField quantityField;
    @FXML private TextField priceField;
    @FXML private TextField categoryField;
    @FXML private TextField supplierField;

    private final StockStore stockStore = new StockStore();
    private ObservableList<Stock> stockList = FXCollections.observableArrayList();

    // ================= INIT =================
    @FXML
    public void initialize() {

        nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        quantityColumn.setCellValueFactory(data -> data.getValue().quantityProperty().asObject());
        priceColumn.setCellValueFactory(data -> data.getValue().priceProperty().asObject());
        categoryColumn.setCellValueFactory(data -> data.getValue().categoryProperty());
        supplierColumn.setCellValueFactory(data -> data.getValue().supplierProperty());

        dateColumn.setCellValueFactory(data -> data.getValue().lastUpdatedProperty());

        loadTable();
    }

    // ================= LOAD TABLE =================
    private void loadTable() {
        stockList.setAll(stockStore.getAllStocks());
        stockTable.setItems(stockList);
    }

    // ================= ADD =================
    @FXML
    public void addStock() {

        try {
            Stock stock = new Stock(
                    nameField.getText(),
                    Integer.parseInt(quantityField.getText()),
                    Double.parseDouble(priceField.getText()),
                    categoryField.getText(),
                    supplierField.getText(),
                    LocalDate.now()
            );

            stockStore.addStock(stock);
            loadTable();
            clearFields();

        } catch (Exception e) {
            showAlert("Error", "Invalid input! Check numbers.");
        }
    }

    // ================= UPDATE =================
    @FXML
    public void updateStock() {

        Stock selected = stockTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Warning", "Select a stock first!");
            return;
        }

        try {
            selected.setName(nameField.getText());
            selected.setQuantity(Integer.parseInt(quantityField.getText()));
            selected.setPrice(Double.parseDouble(priceField.getText()));
            selected.setCategory(categoryField.getText());
            selected.setSupplier(supplierField.getText());
            selected.setLastUpdated(LocalDate.now()); // FIXED

            stockStore.updateStock(selected);
            loadTable();
            clearFields();

        } catch (Exception e) {
            showAlert("Error", "Invalid input! Check numbers.");
        }
    }

    @FXML
    public void deleteStock() {

        Stock selected = stockTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showAlert("Warning", "Select a stock first!");
            return;
        }

        stockStore.deleteStock(selected.getId());
        loadTable();
        clearFields();
    }

    @FXML
    public void handleRowSelect() {

        Stock selected = stockTable.getSelectionModel().getSelectedItem();

        if (selected != null) {
            nameField.setText(selected.getName());
            quantityField.setText(String.valueOf(selected.getQuantity()));
            priceField.setText(String.valueOf(selected.getPrice()));
            categoryField.setText(selected.getCategory());
            supplierField.setText(selected.getSupplier());
        }
    }

    private void clearFields() {
        nameField.clear();
        quantityField.clear();
        priceField.clear();
        categoryField.clear();
        supplierField.clear();
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @FXML
    private void handleReturn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home-view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 500, 500);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}