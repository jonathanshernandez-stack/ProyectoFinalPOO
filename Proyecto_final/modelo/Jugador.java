package Proyecto_final.modelo;

import java.awt.Color;
import java.awt.Graphics;

import Proyecto_final.controlador.InputManager;

public class Jugador {

    int x;
    int y;

    int velocidad;

    int ancho;
    int alto;

    String direccion;

    InputManager teclado;

    int anchoPantalla;
    int altoPantalla;

    public Jugador(InputManager teclado, int anchoPantalla, int altoPantalla) {

        this.teclado = teclado;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;

        x = 100;
        y = 100;

        velocidad = 5;

        ancho = 50;
        alto = 50;

        direccion = "abajo";
    }

    public void update() {

        if (teclado.arriba) {

            if (y > 0) {
                y -= velocidad;
            }

            direccion = "arriba";
        }

        if (teclado.abajo) {

            if (y + alto < altoPantalla) {
                y += velocidad;
            }

            direccion = "abajo";
        }

        if (teclado.izquierda) {

            if (x > ancho ) {
                x -= velocidad;
            }

            direccion = "izquierda";
        }

        if (teclado.derecha) {

            if (x + ancho < anchoPantalla) {
                x += velocidad;
            }

            direccion = "derecha";
        }
    }

    public void draw(Graphics g) {

        if (direccion.equals("arriba")) {
            g.setColor(Color.CYAN);
        }

        if (direccion.equals("abajo")) {
            g.setColor(Color.WHITE);
        }

        if (direccion.equals("izquierda")) {
            g.setColor(Color.GREEN);
        }

        if (direccion.equals("derecha")) {
            g.setColor(Color.YELLOW);
        }

        g.fillRect(x, y, ancho, alto);
    }
}
