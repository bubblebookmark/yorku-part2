package com.yorku.gui;

import com.yorku.coordinator.LabManager;
import com.yorku.equipment.Equipment;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LabManagerScreen {

    private Stage stage;
    private LabManager manager;
    private LoginScreen loginScreen; // back button

    public LabManagerScreen(Stage stage, LabManager manager, LoginScreen loginScreen) {
        this.stage = stage;
        this.manager = manager;
        this.loginScreen = loginScreen;
    }

    public void show() {

        Label title = new Label("Lab Manager Dashboard: " + manager.getName());

        TextField idField = new TextField();
        idField.setPromptText("Equipment ID");

        TextField descField = new TextField();
        descField.setPromptText("Description");

        TextField locField = new TextField();
        locField.setPromptText("Lab Location");

        Button addBtn = new Button("Add Equipment");
        ComboBox<String> equipmentList = new ComboBox<>();
        // Populate the ComboBox with existing equipment
        for (Equipment eq : manager.getAllEquipment()) {
            equipmentList.getItems().add(eq.getId() + " - " + eq.getDescription());
        }

        Button enableBtn = new Button("Enable");
        Button disableBtn = new Button("Disable");
        Button maintenanceBtn = new Button("Mark for Maintenance");

        addBtn.setOnAction(e -> {
            String id = idField.getText();
            String desc = descField.getText();
            String loc = locField.getText();

            if (id.isEmpty() || desc.isEmpty() || loc.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Please fill all fields").show();
                return;
            }

            Equipment eq = new Equipment(id, desc, loc);
            manager.addEquipment(eq);
            new Alert(Alert.AlertType.INFORMATION, "Equipment added: " + desc).show();
            equipmentList.getItems().add(eq.getId() + " - " + eq.getDescription());
        });

        enableBtn.setOnAction(e -> {
            String selected = equipmentList.getValue();
            if (selected == null) {
                new Alert(Alert.AlertType.ERROR, "Select equipment").show();
                return;
            }
            manager.enableEquipment(selected.split(" - ")[0]);
            new Alert(Alert.AlertType.INFORMATION, "Equipment enabled").show();
        });

        disableBtn.setOnAction(e -> {
            String selected = equipmentList.getValue();
            if (selected == null) {
                new Alert(Alert.AlertType.ERROR, "Select equipment").show();
                return;
            }
            manager.disableEquipment(selected.split(" - ")[0]);
            new Alert(Alert.AlertType.INFORMATION, "Equipment disabled").show();
        });

        maintenanceBtn.setOnAction(e -> {
            String selected = equipmentList.getValue();
            if (selected == null) {
                new Alert(Alert.AlertType.ERROR, "Select equipment").show();
                return;
            }
            manager.markForMaintenance(selected.split(" - ")[0]);
            new Alert(Alert.AlertType.INFORMATION, "Equipment marked for maintenance").show();
        });

        Button backBtn = new Button("Back");
        backBtn.setOnAction(e -> loginScreen.show());

        VBox layout = new VBox(10, title,
                new Label("Add New Equipment:"), idField, descField, locField, addBtn,
                new Label("Manage Equipment:"), equipmentList, enableBtn, disableBtn, maintenanceBtn,
                backBtn);
        layout.setPadding(new Insets(15));

        stage.setScene(new Scene(layout, 400, 500));
        stage.show();
    }
}