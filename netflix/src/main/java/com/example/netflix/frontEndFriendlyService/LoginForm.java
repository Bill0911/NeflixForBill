package com.example.netflix.frontEndFriendlyService;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;

public class LoginForm extends GridPane {

    public LoginForm() {
        setPadding(new Insets(10, 10, 10, 10));
        setVgap(8);
        setHgap(10);

        // Email Label
        Label emailLabel = new Label("Email:");
        GridPane.setConstraints(emailLabel, 0, 0);

        // Email Input
        TextField emailInput = new TextField();
        GridPane.setConstraints(emailInput, 1, 0);

        // Password Label
        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 1);

        // Password Input
        PasswordField passwordInput = new PasswordField();
        GridPane.setConstraints(passwordInput, 1, 1);

        // Login Button
        Button loginButton = new Button("Login");
        GridPane.setConstraints(loginButton, 1, 2);

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String email = emailInput.getText();
                String password = passwordInput.getText();
                loginUser(email, password);
            }
        });

        getChildren().addAll(emailLabel, emailInput, passwordLabel, passwordInput, loginButton);
    }

    private void loginUser(String email, String password) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String requestBody = String.format("{\"email\":\"%s\",\"password\":\"%s\"}", email, password);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8081/api/users/login"))
                    .header("Content-Type", "application/json")
                    .POST(BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            System.out.println("Response: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
