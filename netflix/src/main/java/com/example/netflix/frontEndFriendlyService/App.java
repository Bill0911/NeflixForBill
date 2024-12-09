package com.example.netflix.frontEndFriendlyService;

import com.example.netflix.NetflixApplication;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import org.hibernate.id.factory.spi.StandardGenerator;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.application.Application;
public class App extends Application
{
    @Override
    public void start(Stage primaryStage)
    {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("User management");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
