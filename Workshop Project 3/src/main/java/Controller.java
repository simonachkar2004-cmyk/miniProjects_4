import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Stock;
import store.StockStore;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Controller {

    @FXML private TableView<Stock> stockTable;
    @FXML private TableColumn<Stock, String> nameColumn;
    @FXML private TableColumn<Stock, Integer> quantityColumn;
    @FXML private TableColumn<Stock, Double> priceColumn;
    @FXML private TableColumn<Stock, String> categoryColumn;
    @FXML private TableColumn<Stock, String> supplierColumn;
    @FXML private TableColumn<Stock, String> dateColumn;

    @FXML private TextField nameField;
    @FXML private TextField quantityField;
    @FXML private TextField priceField;
    @FXML private TextField categoryField;
    @FXML private TextField supplierField;

    private ObservableList<Stock> stockList;
    private StockStore stockStore = new StockStore();

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        categoryColumn.setCellValueFactory(cellData -> cellData.getValue().categoryProperty());
        supplierColumn.setCellValueFactory(cellData -> cellData.getValue().supplierProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().lastUpdatedProperty());

        stockList = stockStore.getAllStocks();
        stockTable.setItems(stockList);
    }

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

            stockList = stockStore.getAllStocks();
            stockTable.setItems(stockList);

            clearFields();

        } catch (NumberFormatException e) {
            showAlert("Invalid input", "Quantity and Price must be numbers.");
        }
    }

    @FXML
    public void updateStock() {
        Stock selected = stockTable.getSelectionModel().getSelectedItem();

        if (selected != null) {
            try {
                selected.setName(nameField.getText());
                selected.setQuantity(Integer.parseInt(quantityField.getText()));
                selected.setPrice(Double.parseDouble(priceField.getText()));
                selected.setCategory(categoryField.getText());
                selected.setSupplier(supplierField.getText());
                selected.setLastUpdated(LocalDate.now().format(DateTimeFormatter.ISO_DATE));

                stockStore.updateStock(selected);

                stockList = stockStore.getAllStocks();
                stockTable.setItems(stockList);

                clearFields();

            } catch (NumberFormatException e) {
                showAlert("Invalid input", "Quantity and Price must be numbers.");
            }
        } else {
            showAlert("Selection Error", "Select a stock to update.");
        }
    }

    @FXML
    public void deleteStock() {
        Stock selected = stockTable.getSelectionModel().getSelectedItem();

        if (selected != null) {
            stockStore.deleteStock(selected.getId());

            stockList = stockStore.getAllStocks();
            stockTable.setItems(stockList);

            clearFields();
        } else {
            showAlert("Selection Error", "Select a stock to delete.");
        }
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}