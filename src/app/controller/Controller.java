package app.controller;


import app.model.Jugador;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import javax.swing.text.AsyncBoxView;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;

import static javafx.application.Application.launch;


public class Controller {


    @FXML
    Button generateButton;
    @FXML
    GridPane winPane;

    @FXML
    TextField j1Name;
    @FXML
    TextField j2Name;
    @FXML
    ColorPicker j1Color;
    @FXML
    ColorPicker j2Color;

    GridPane tablero = new GridPane();
    Scene tableroScene;
    int columns = 7;
    int rows = 7;

    int[][] tableroJuego = new int[rows][columns];
    int tieneFicha = 2;
    int turno = 1;

    Jugador player1 = new Jugador() ;


    Jugador player2 = new Jugador() ;
    Jugador playerTirada = null;




    @FXML
    void generarTablero(ActionEvent event) {

        setJugadorColor();


        llenarTablero();

        playerTirada=player1;

        int buttonSize = 70;


        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {

                Button button = new Button();
                button.setMinSize(buttonSize, buttonSize);
                button.setPrefSize(buttonSize, buttonSize);
                button.setMaxSize(buttonSize, buttonSize);
                button.setId(y+","+x);

                if(y==0) {
                    button.setStyle("-fx-background-color: rgba(0,0,0,0), #000000 ;");

                    button.setOnAction(
                            new EventHandler<ActionEvent>() {

                                @Override
                                public void handle(ActionEvent actionEvent) {
                                    String id = ((Button) actionEvent.getSource()).getId();



                                    Button buttontoChange = (Button) tableroScene.lookup("#"+espacioLibre(((Button) actionEvent.getSource()).getId()));
                                    buttontoChange.setStyle(playerTirada.getColor());


                                    System.out.println(id+"="+tableroJuego[ Integer.parseInt( id.split(",")[0])][ Integer.parseInt( id.split(",")[1])]);
                                    boolean resultado =  partidaGanada(Integer.parseInt(buttontoChange.getId().split(",")[0]),Integer.parseInt(buttontoChange.getId().split(",")[1]));
                                    if (resultado==true){
                                        switch (turno){
                                            case 1:
                                                System.out.println("ha ganada Jugador 1 "+player1.getName());
                                                break;
                                            case 2:
                                                System.out.println("ha ganada Jugador 2 "+player2.getName());
                                                break;
                                        }
                                        writteFile();
                                      /*  Stage primaryStage = (Stage)winPane.getScene().getWindow();
                                        Parent root = null;

                                        try {
                                            root = FXMLLoader.load(getClass().getResource("../view/win.fxml"));
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        Scene scene = new Scene(root, 350, 350);
                                        primaryStage.setScene(scene);
                                        primaryStage.show();*/
                                    }
                                    System.out.println(resultado);
                                    playerTirada=tirada();

                                }
                            });
                }
                tablero.setRowIndex(button, y);
                tablero.setColumnIndex(button, x);
                tablero.getChildren().add(button);


            }
        }

            tableroScene = new Scene(tablero, 500, 500);
            Stage stage = ((Stage) ((Button) (event.getSource())).getScene().getWindow());
            stage.setScene(tableroScene);

    }

    private void llenarTablero(){
        for (int i = 0; i <tableroJuego.length ; i++) {
            for (int j = 0; j <tableroJuego[i].length ; j++) {
              tableroJuego[i][j]=0;
            }

        }
    }
    private boolean partidaGanada(int row, int col){

        return chekVertical(row,col) || chekHorizontal(row,col);
    }
    private boolean chekHorizontal(int row, int col){
        int contadorSeguidas = 0;

        for (int j = 0; j <columns-col ; j++) {

            if (contadorSeguidas<4){
                if (tableroJuego[row][j+col]==turno){
                    contadorSeguidas++;
                }else {
                    break;
                }
            }
        }
        if (col!=0){
            for (int j = 1; j < col ; j++) {

                if (contadorSeguidas<4){
                    if (tableroJuego[row][col-j]==turno){
                        contadorSeguidas++;
                    }else {
                        return false;
                    }
                }
            }
        }

        if (contadorSeguidas==4){
            return  true;
        }return false;
    }
    private boolean chekVertical(int row, int col){
        int contadorSeguidas = 0;

            for (int j = 0; j <rows-row ; j++) {

                if (contadorSeguidas<4){
                    if (tableroJuego[j+row][col]==turno){
                        contadorSeguidas++;
                    }else {
                        return false;
                    }
                }
            }
            if (contadorSeguidas==4){
                return  true;
            }return false;
    }
    private String espacioLibre(String idButn){
        int colum = Integer.parseInt( idButn.split(",")[1]);
        for (int i = 0; i < tableroJuego.length; i++) {
            if (tableroJuego[i][colum]==1 || tableroJuego[i][colum]==2){
                tableroJuego[i-1][colum]=turno;
                return (i-1)+","+colum;
            }
        }
        tableroJuego[rows-1][colum]=turno;
        return (rows-1)+","+colum;

    }
   private Jugador tirada() {
       if (turno == 1){
           turno=2;
           return player2;
       }
        turno = 1;
        return player1;
    }
    private void setJugadorColor(){
        player1.setName(j1Name.getText());
        player2.setName(j2Name.getText());
        player1.setColor(j1Color.getValue().toString());
        player2.setColor(j2Color.getValue().toString());

    }
    public void writteFile(){
        try(BufferedWriter bw=new BufferedWriter(new FileWriter("resultado.txt"));){

            bw.write("El Jugador " + turno+ "Ha ganado");
            bw.newLine();
            bw.flush();
        }catch(IOException e){
            System.out.println("NO SE HA PODIDO GUARDAR NADA: "+e);
        }
    }



    public static void main(String[] args) {
        launch(args);
    }
}

