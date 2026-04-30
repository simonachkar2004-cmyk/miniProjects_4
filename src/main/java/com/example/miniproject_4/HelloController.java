package com.example.miniproject_4;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.io.IOException;

public class HelloController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick(ActionEvent event) {

        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            welcomeText.setText("Please fill all fields");
            return;
        }

        String sql = "SELECT * FROM login WHERE username = ? AND password = ?";

        try (Connection conn = loginConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/com/example/miniproject_4/home-view.fxml")
                );
                Parent root = loader.load();

                Image icon = new Image(
                        HelloApplication.class.getResource("/image/2083417.png").toExternalForm()
                );

                Stage homeStage = new Stage();
                homeStage.setScene(new Scene(root, 500, 500));
                homeStage.setTitle("Home");
                homeStage.getIcons().add(icon);
                homeStage.show();

                Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                loginStage.close();

            } else {
                welcomeText.setText("Invalid username or password");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            welcomeText.setText("Database error");
        } catch (IOException e) {
            e.printStackTrace();
            welcomeText.setText("Error loading Home screen");
        }
    }
}