package Proyecto_final.modelo;

import java.awt.*;

public class Hitbox {

    private int x, y, ancho, alto;

    public Hitbox(int x, int y, int ancho, int alto) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
    }

    public Rectangle getBounds() {

        return new Rectangle(x, y, ancho, alto);
    }

    public boolean colisiona(Hitbox otra) {

        return getBounds().intersects(otra.getBounds());
    }

    public void setPosicion(int x, int y) {
        this.x = x;
        this.y = y;
    }

}
