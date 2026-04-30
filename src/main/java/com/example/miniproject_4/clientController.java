package com.example.miniproject_4;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class clientController {

    @FXML private TextField clientName;
    @FXML private TextField cityName;
    @FXML private TextField orderName;
    @FXML private DatePicker dateSelector;
    @FXML private TextField price;

    @FXML private Button deleteButton;
    @FXML private Button insertButton;
    @FXML private Button updateButton;
    @FXML private Button returnButton;

    @FXML private TableView<client> tableView;
    @FXML private TableColumn<client, String> nameCl;
    @FXML private TableColumn<client, String> adressCl;
    @FXML private TableColumn<client, String> orderCl;
    @FXML private TableColumn<client, LocalDate> dataCl;
    @FXML private TableColumn<client, Number> priceCl;

    private int selectedClientId = -1;

    @FXML
    public void initialize() {
        dateSelector.setValue(LocalDate.now());

        nameCl.setCellValueFactory(cell -> cell.getValue().nameProperty());
        adressCl.setCellValueFactory(cell -> cell.getValue().cityProperty());
        orderCl.setCellValueFactory(cell -> cell.getValue().orderNumberProperty());
        dataCl.setCellValueFactory(cell -> cell.getValue().dateProperty());
        priceCl.setCellValueFactory(cell -> cell.getValue().priceProperty());

        updateButton.setDisable(true);
        deleteButton.setDisable(true);

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            boolean selected = newSel != null;
            updateButton.setDisable(!selected);
            deleteButton.setDisable(!selected);

            if (selected) {
                clientName.setText(newSel.getName());
                cityName.setText(newSel.getCity());
                orderName.setText(newSel.getOrderNumber());
                price.setText(Double.toString(newSel.getPrice()));
                dateSelector.setValue(newSel.getDate());

                selectedClientId = getClientIdFromDB(newSel);
            } else {
                clearFields();
                selectedClientId = -1;
            }
        });

        loadClientsFromDatabase();
    }

    @FXML
    public void insertClient() {
        if (!clientName.getText().isEmpty()) {

            double pr = price.getText().isEmpty() ? 0 : Double.parseDouble(price.getText());

            String sql = "INSERT INTO clients (name, city, order_number, date, price) VALUES (?, ?, ?, ?, ?)";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, clientName.getText());
                stmt.setString(2, cityName.getText());
                stmt.setString(3, orderName.getText());
                stmt.setDate(4, Date.valueOf(dateSelector.getValue()));
                stmt.setDouble(5, pr);

                stmt.executeUpdate();

                loadClientsFromDatabase(); // refresh
                clearFields();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void updateClient() {
        if (selectedClientId == -1) return;

        String sql = "UPDATE clients SET name=?, city=?, order_number=?, date=?, price=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, clientName.getText());
            stmt.setString(2, cityName.getText());
            stmt.setString(3, orderName.getText());
            stmt.setDate(4, Date.valueOf(dateSelector.getValue()));
            stmt.setDouble(5, price.getText().isEmpty() ? 0 : Double.parseDouble(price.getText()));
            stmt.setInt(6, selectedClientId);

            stmt.executeUpdate();

            loadClientsFromDatabase();
            clearFields();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void deleteClient() {
        if (selectedClientId == -1) return;

        String sql = "DELETE FROM clients WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, selectedClientId);
            stmt.executeUpdate();

            loadClientsFromDatabase();
            clearFields();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadClientsFromDatabase() {
        tableView.getItems().clear();

        String sql = "SELECT * FROM clients";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                client c = new client(
                        rs.getString("name"),
                        rs.getString("city"),
                        rs.getString("order_number"),
                        rs.getDate("date").toLocalDate(),
                        rs.getDouble("price")
                );

                tableView.getItems().add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getClientIdFromDB(client c) {
        String sql = "SELECT id FROM clients WHERE name=? AND order_number=? LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getName());
            stmt.setString(2, c.getOrderNumber());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    @FXML
    private void goToHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home-view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 500, 500);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void clearFields() {
        clientName.clear();
        cityName.clear();
        orderName.clear();
        price.clear();
        dateSelector.setValue(LocalDate.now());
        selectedClientId = -1;
    }
}