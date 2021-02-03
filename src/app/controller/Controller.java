package app.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


import static javafx.application.Application.launch;


public class Controller {


    @FXML
    Button generateButton;

    GridPane tablero = new GridPane();
    Scene tableroScene;


    @FXML
    void generarTablero(ActionEvent event) {
        int columns = 6;
        int rows = 7;
        for (int y = 0; y < columns; y++) {
            for (int x = 0; x < rows; x++) {

                TextField tf = new TextField();
                tf.setPrefHeight(50);
                tf.setPrefWidth(50);
                tablero.setRowIndex(tf, y);
                tablero.setColumnIndex(tf, x);
                tablero.getChildren().add(tf);
            }
        }
        tableroScene = new Scene(tablero, 500, 500);
        Stage stage = ((Stage)((Button)(event.getSource())).getScene().getWindow());
        stage.setScene(tableroScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

