package com.example.miniproject_4;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;

import java.io.IOException;

public class HomeController {

    @FXML
    private Button clientBtn;

    @FXML
    private Button exitBtn;

    @FXML
    private Button button1;

    @FXML
    protected void onClientClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/miniproject_4/client-view.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.getIcons().add(icon);
            stage.setTitle("Clients");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Image icon = new Image(
            HelloApplication.class.getResource("/image/2083417.png").toExternalForm()
    );

    @FXML
    protected void onExitClick(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    protected void onButton1Click(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/miniproject_4/StockScene.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.getIcons().add(icon);
            stage.setTitle("Stock");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}