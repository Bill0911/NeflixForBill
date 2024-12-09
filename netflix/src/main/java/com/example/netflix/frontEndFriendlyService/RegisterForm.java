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

public class RegisterForm extends GridPane {

    public RegisterForm() {
        setPadding(new Insets(10, 10, 10, 10));
        setVgap(8);
        setHgap(10);

        // Username Label
        Label usernameLabel = new Label("Username:");
        GridPane.setConstraints(usernameLabel, 0, 0);

        // Username Input
        TextField usernameInput = new TextField();
        GridPane.setConstraints(usernameInput, 1, 0);

        // Email Label
        Label emailLabel = new Label("Email:");
        GridPane.setConstraints(emailLabel, 0, 1);

        // Email Input
        TextField emailInput = new TextField();
        GridPane.setConstraints(emailInput, 1, 1);

        // Password Label
        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 2);

        // Password Input
        PasswordField passwordInput = new PasswordField();
        GridPane.setConstraints(passwordInput, 1, 2);

        // Register Button
        Button registerButton = new Button("Register");
        GridPane.setConstraints(registerButton, 1, 3);

        registerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String username = usernameInput.getText();
                String email = emailInput.getText();
                String password = passwordInput.getText();
                registerUser(username, email, password);
            }
        });

        getChildren().addAll(usernameLabel, usernameInput, emailLabel, emailInput, passwordLabel, passwordInput, registerButton);
    }

    private void registerUser(String username, String email, String password) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String requestBody = String.format("{\"username\":\"%s\",\"email\":\"%s\",\"password\":\"%s\"}", username, email, password);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8081/api/users/register"))
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
