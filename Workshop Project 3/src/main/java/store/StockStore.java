package store;

import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Stock;

import java.sql.*;
import java.time.LocalDate;

public class StockStore {

    public ObservableList<Stock> getAllStocks() {
        ObservableList<Stock> list = FXCollections.observableArrayList();

        String sql = "SELECT * FROM stock";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Stock stock = new Stock(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getString("category"),
                        rs.getString("supplier"),
                        rs.getDate("last_updated").toLocalDate()
                );

                list.add(stock);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public void addStock(Stock stock) {
        String sql = "INSERT INTO stock(name, quantity, price, category, supplier, last_updated) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, stock.getName());
            stmt.setInt(2, stock.getQuantity());
            stmt.setDouble(3, stock.getPrice());
            stmt.setString(4, stock.getCategory());
            stmt.setString(5, stock.getSupplier());
            stmt.setDate(6, Date.valueOf(stock.getLastUpdated()));

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateStock(Stock stock) {
        String sql = "UPDATE stock SET name=?, quantity=?, price=?, category=?, supplier=?, last_updated=? WHERE id=?";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, stock.getName());
            stmt.setInt(2, stock.getQuantity());
            stmt.setDouble(3, stock.getPrice());
            stmt.setString(4, stock.getCategory());
            stmt.setString(5, stock.getSupplier());
            stmt.setDate(6, Date.valueOf(stock.getLastUpdated()));
            stmt.setInt(7, stock.getId());

            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteStock(int id) {
        String sql = "DELETE FROM stock WHERE id=?";

        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}