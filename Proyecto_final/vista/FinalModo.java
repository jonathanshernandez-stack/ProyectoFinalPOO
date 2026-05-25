package Proyecto_final.vista;

import Proyecto_final.modelo.Jugador;
import Proyecto_final.modelo.RegistroRanking;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FinalModo {

    private BufferedImage fondo;


    public FinalModo() {
        try {
            fondo = ImageIO.read(new File("Proyecto_final/resources/escenarios/fondo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw (Graphics g, String nombreJugador1, String nombreJugador2, int tiempoPartida, int puntajeJugador1, int puntajeJugador2, ArrayList<RegistroRanking> ranking, Jugador jugador1) {

        Graphics2D g2 = (Graphics2D) g;

        if (fondo != null) {
            g2.drawImage(fondo, 0, 0, 1500, 1000, null);
        } else {
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, 1500, 1000);
        }

        g2.setColor(new Color(0, 0, 0, 170));
        g2.fillRect(0, 0, 1500, 1000);


        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 60));

        if (jugador1.getVida() <= 0) {
            centrarTexto(g2, "GANA " + nombreJugador2, 1500, 200);
        } else {
            centrarTexto(g2, "GANA " + nombreJugador1, 1500, 200);
        }

        g.setFont(new Font("Arial", Font.BOLD, 30));
        centrarTexto(g2,"Tiempo final ---------------------- " + tiempoPartida + " segundos", 1500, 300);
        centrarTexto(g2, nombreJugador1 + " ---------------------- " + puntajeJugador1 + " puntos", 1500, 350);
        centrarTexto(g2, nombreJugador2 + " ---------------------- " + puntajeJugador2 + " puntos", 1500, 400);

        g.setFont(new Font("Arial", Font.BOLD, 35));
        centrarTexto(g2, "---------  TOP 3 MEJORES JUGADORES  --------", 1500, 500);

        g.setFont(new Font("Arial", Font.BOLD, 25));

        for (int i = 0; i < ranking.size() && i < 3; i++) {
            RegistroRanking registro = ranking.get(i);

            centrarTexto(g2, (i + 1) + ". " + registro.getNombre() + " - " + registro.getPuntaje() + " pts - " + registro.getTiempo() + "s - " + registro.getModo(), 1500, 600 + (i * 35));
        }

        centrarTexto(g2, "PRESIONE ENTER PARA JUGAR DE NUEVO", 1500, 880);
        centrarTexto(g2, "PRESIONE ESCAPE PARA IR DIRECTO AL MENÚ", 1500, 920);

    }

    private void centrarTexto(Graphics2D g2, String texto, int anchoPantalla, int y) {
        FontMetrics medidas = g2.getFontMetrics();
        int anchoTexto = medidas.stringWidth(texto);
        int x = (anchoPantalla - anchoTexto) / 2;

        g2.drawString(texto, x, y);
    }

}
