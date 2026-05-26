package Proyecto_final.vista;

import Proyecto_final.modelo.Jugador;
import java.awt.*;

public class HUD {

    public void draw(Graphics g, Jugador jugador1, Jugador jugador2, String nombreJugador1, String nombreJugador2, int tiempo, int puntajeJugador1, int puntajeJugador2) {

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 25));

        g.drawString(nombreJugador1, 50, 40);
        g.drawString(nombreJugador2, 1050, 40);

        g.setColor(Color.RED);
        g.fillRect(50, 50, jugador1.getVida() * 2, 30);

        g.setColor(Color.BLUE);
        g.fillRect(1050, 50, jugador2.getVida() * 2, 30);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 35));
        g.drawString("Tiempo: " + tiempo + "s", 650, 60);

        g.setFont(new Font("Arial", Font.BOLD, 25));
        g.drawString("Puntos: " + puntajeJugador1, 50, 110);
        g.drawString("Puntos: " + puntajeJugador2, 1050, 110);
    }
}