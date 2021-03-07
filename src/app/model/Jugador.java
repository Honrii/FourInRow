package app.model;

import com.sun.prism.paint.Color;

public  class Jugador {

    private  String name;
    private int posicionTirada;
    private String  color ="-fx-background-color:  ;";


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosicionTirada() {
        return posicionTirada;
    }

    public void setPosicionTirada(int posicionTirada) {
        this.posicionTirada = posicionTirada;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String  color) {
        color = color.replace("0x","#");
        this.color = "-fx-background-color:"+color+"  ;";
    }
}
