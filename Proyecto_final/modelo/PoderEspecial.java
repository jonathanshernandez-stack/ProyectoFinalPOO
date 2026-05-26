package Proyecto_final.modelo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class PoderEspecial {

    private int x;
    private int y;
    private int ancho;
    private int alto;
    private int velocidad;
    private int dano;
    private String direccion;
    private boolean activo;

    public PoderEspecial(int x, int y, String direccion) {
        this.x = x;
        this.y = y;
        this.ancho = 80;
        this.alto = 40;
        this.velocidad = 12;
        this.dano = 30;
        this.direccion = direccion;
        this.activo = true;
    }

    public void update() {
        if (direccion.equals("derecha")) {
            x += velocidad;
        }

        if (direccion.equals("izquierda")) {
            x -= velocidad;
        }

        if (direccion.equals("arriba")) {
            y -= velocidad;
        }

        if (direccion.equals("abajo")) {
            y += velocidad;
        }

        if (x < -100 || x > 1600 || y < -100 || y > 1100) {
            activo = false;
        }
    }

    public void draw(Graphics g) {
        if (activo) {
            g.setColor(Color.CYAN);
            g.fillOval(x, y, ancho, alto);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, ancho, alto);
    }

    public boolean isActivo() {
        return activo;
    }

    public void desactivar() {
        activo = false;
    }

    public int getDano() {
        return dano;
    }
}