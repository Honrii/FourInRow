package app.controller;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


import java.lang.reflect.Array;

import static javafx.application.Application.launch;


public class Controller {


    @FXML
    Button generateButton;

    GridPane tablero = new GridPane();
    Scene tableroScene;
    int columns = 6;
    int rows = 7;
    int [][] matriz = new int[7][6];
    int[][] tableroJuego = new int[rows][columns];



    @FXML
    void generarTablero(ActionEvent event) {
        llenarTablero();
        int buttonSize = 70;
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {

                Button button = new Button();
                button.setMinSize(buttonSize, buttonSize);
                button.setPrefSize(buttonSize, buttonSize);
                button.setMaxSize(buttonSize, buttonSize);
                button.setId(y+","+x);

                if(y==0) {
                    button.setOnAction(
                            new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {
                                    System.out.println(((Button) actionEvent.getSource()).getId());


                                    Button buttontoChange = (Button) tableroScene.lookup("#"+espacioLibre(((Button) actionEvent.getSource()).getId()));
                                    buttontoChange.setStyle("-fx-background-color: #008000;");



                                }
                            });
                }
                tablero.setRowIndex(button, y);
                tablero.setColumnIndex(button, x);
                tablero.getChildren().add(button);

            }


        }

        tableroScene = new Scene(tablero, 500, 500);
        Stage stage = ((Stage)((Button)(event.getSource())).getScene().getWindow());
        stage.setScene(tableroScene);
    }

    private void llenarTablero(){
        for (int i = 0; i <tableroJuego.length ; i++) {
            for (int j = 0; j <tableroJuego[i].length ; j++) {
              tableroJuego[i][j]=0;
            }

        }
    }
    private String espacioLibre(String idButn){
        int colum = Integer.parseInt( idButn.split(",")[1]);
        for (int i = 0; i < tableroJuego.length; i++) {
            if (tableroJuego[i][colum]==1){
                tableroJuego[i-1][colum]=1;
                return (i-1)+","+colum;
            }
        }
        tableroJuego[rows-1][colum]=1;
        return (rows-1)+","+colum;

    }





    public static void main(String[] args) {
        launch(args);
    }
}

