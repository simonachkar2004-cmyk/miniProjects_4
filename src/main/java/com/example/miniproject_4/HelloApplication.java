package com.example.miniproject_4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                HelloApplication.class.getResource("hello-view.fxml")
        );

        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Login");

        Image icon = new Image(
                HelloApplication.class.getResource("/image/2083417.png").toExternalForm()
        );
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();


    }
}
