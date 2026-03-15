package com.yorku.gui;

import com.yorku.booking.BookingFacade;
import com.yorku.command.Command;
import com.yorku.command.ReserveCommand;
import com.yorku.equipment.Equipment;
import com.yorku.users.User;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ReservationScreen {

    private Stage stage;
    private User user;

    public ReservationScreen(Stage stage, User user) {
        this.stage = stage;
        this.user = user;
    }

    public void show() {

        Label title = new Label("Reserve Equipment");
        Label cancel = new Label("cancel Equipment usage");
        Label extend = new Label("extend Equipment time");

        ComboBox<String> equipmentList = new ComboBox<>();
        equipmentList.getItems().addAll(
                "Microscope",
                "Spectrometer",
                "3D Printer"
        );

        TextField hoursField = new TextField();
        hoursField.setPromptText("Hours");

        Button reserveBtn = new Button("Reserve");
        Button cancelBtn = new Button("Cancel");
        Button extendBtn = new Button("Extend");

        reserveBtn.setOnAction(e -> {

            try {

                Equipment equipment = new Equipment(
                        "EQ001",
                        equipmentList.getValue(),
                        "Lab A"
                );

                String hoursText = hoursField.getText();
                if (hoursText == null || hoursText.isEmpty()) {
                    new Alert(Alert.AlertType.ERROR,"Please enter number of hours").show();
                    return;
                }

                int hours;
                try {
                    hours = Integer.parseInt(hoursText);
                } catch (NumberFormatException ex) {
                    new Alert(Alert.AlertType.ERROR,"Invalid number of hours").show();
                    return;
                }

                BookingFacade booking = new BookingFacade();
                Command reserveCommand = new ReserveCommand(booking, user, equipment, hours);
                reserveCommand.execute();
                

                new Alert(Alert.AlertType.INFORMATION,
                        "Reservation successful!"
                ).show();

            } catch (Exception ex) {

                new Alert(Alert.AlertType.ERROR,
                        "Reservation failed"
                ).show();
            }
        });
        cancelBtn.setOnAction(e -> {
            try {

            } catch (Exception ex) {

                new Alert(Alert.AlertType.ERROR,
                        "cancel Reservation failed"
                ).show();
            }
        });

        extendBtn.setOnAction(e -> {
            try {

            } catch (Exception ex) {

                new Alert(Alert.AlertType.ERROR,
                        "cancel Reservation failed"
                ).show();
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(title,cancel,extend,cancelBtn,extendBtn, equipmentList, hoursField, reserveBtn);

        stage.setScene(new Scene(layout, 400, 250));
    }
}