package com.yorku.gui;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.yorku.coordinator.HeadLabCoordinator;
import com.yorku.coordinator.LabManager;
import com.yorku.users.User;
import com.yorku.users.UserFactory;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginScreen {

    private Stage stage;
    private LabManager labManager;

    public LoginScreen(Stage stage, LabManager labManager) {
        this.stage = stage;
        this.labManager = labManager;
    }

    public void show() {
        Label title = new Label("YorkU Lab Booking System");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        TextField idField = new TextField();
        idField.setPromptText("Student/Staff ID or Certification");

        ComboBox<String> userType = new ComboBox<>();
        userType.getItems().addAll("student", "faculty", "researcher", "guest", "lab_manager", "head coordinator");
        userType.setPromptText("Select User Type");

        // Sample Users Dropdown
        ComboBox<String> sampleUsers = new ComboBox<>();
        sampleUsers.setPromptText("Load Sample User");

        // Read CSV directly
        List<String[]> users = new ArrayList<>();
        try (InputStream is = getClass().getResourceAsStream("/com/yorku/sample_users.csv")) {
            if (is == null) throw new Exception("sample_users.csv not found in resources");
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String line;
                br.readLine(); // skip header
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",", -1);
                    users.add(parts);
                    sampleUsers.getItems().add(parts[0]); // add Name as display
                }
            }

            // Auto-fill fields when a sample user is selected
            sampleUsers.setOnAction(ev -> {
                int index = sampleUsers.getSelectionModel().getSelectedIndex();
                if (index >= 0) {
                    String[] selectedUser = users.get(index);
                    // CSV columns: Name, UserType, Email, Password, ID_Certification
                    userType.setValue(selectedUser[1]);
                    emailField.setText(selectedUser[2]);
                    passwordField.setText(selectedUser[3]);
                    idField.setText(selectedUser[4]);
                }
            });
        } catch (Exception ex) {
            new Alert(Alert.AlertType.ERROR, "Failed to load sample users: " + ex.getMessage()).show();
        }

        Button loginBtn = new Button("Login");
        loginBtn.setOnAction(e -> {
            try {
                String type = userType.getValue();
                String email = emailField.getText();
                String password = passwordField.getText();
                String id = idField.getText();

                // Validation
                if (email == null || email.trim().isEmpty()) {
                    new Alert(Alert.AlertType.ERROR, "Email is required").show();
                    return;
                }
                if (password == null || password.trim().isEmpty()) {
                    new Alert(Alert.AlertType.ERROR, "Password is required").show();
                    return;
                }
                if (id == null || id.trim().isEmpty()) {
                    new Alert(Alert.AlertType.ERROR, "ID is required").show();
                    return;
                }
                if (type == null) {
                    new Alert(Alert.AlertType.ERROR, "Select a user type").show();
                    return;
                }

                // Login handling
                if (type.equals("head coordinator") && email.equals("Alice@yorku.ca")) {
                    HeadCoordinatorApprovalScreen approvalScreen =
                            new HeadCoordinatorApprovalScreen(stage, HeadLabCoordinator.getInstance(), this);
                    approvalScreen.show();

                } else if (type.equals("lab_manager")) {
                    LabManagerScreen labScreen = new LabManagerScreen(stage, labManager, this);
                    labScreen.show();

                } else {
                    User user = UserFactory.createUser(type, email, password, id);
                    ReservationScreen reservation = new ReservationScreen(stage, user, labManager, this);
                    reservation.show();
                }

            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Invalid login or missing information").show();
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(title, emailField, passwordField, idField, userType, sampleUsers, loginBtn);
        layout.setStyle("-fx-padding: 15;");

        stage.setScene(new Scene(layout, 400, 350));
        stage.setTitle("Login");
        stage.show();
    }
}