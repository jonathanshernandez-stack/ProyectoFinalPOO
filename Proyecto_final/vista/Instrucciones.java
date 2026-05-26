package Proyecto_final.vista; // Dice que esta clase pertenece al paquete vista

import javax.imageio.ImageIO;
import java.awt.*; // Permite usar Graphics, Color, Font, etc.
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Instrucciones { // Clase visual, NO JFrame, porque se dibuja dentro de PanelJuego

    private BufferedImage fondo;

    public Instrucciones() {
        try {
            fondo = ImageIO.read(new File("Proyecto_final/resources/escenarios/instrucciones.jpeg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g, int anchoPantalla, int altoPantalla) { // Método que dibuja instrucciones

        Graphics2D g2 = (Graphics2D) g;

        if (fondo != null) {
            g2.drawImage(fondo, 0, 0, anchoPantalla, 1000, null);
        } else {
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, 1500, 1000);
        }


        g2.setColor(new Color(0, 0, 0, 170));
        g2.fillRect(0, 0, 1500, 1000);

        g.setColor(Color.WHITE); // Cambia color a blanco
        g.setFont(new Font("Arial", Font.BOLD, 60)); // Fuente grande y negrita
        g.drawString("INSTRUCCIONES", 500, 130); // Dibuja el título

        g.setFont(new Font("Arial", Font.PLAIN, 35)); // Fuente normal para texto

        centrarTexto(g2, "Jugador 1 se mueve con WASD", anchoPantalla, 250); // Texto jugador 1
        centrarTexto(g2, "Jugador 2 se mueve con flechas", anchoPantalla, 320); // Texto jugador 2
        centrarTexto(g2, "jugador 1 ataca con ESPACIO", anchoPantalla, 390); // Ataque jugador 1
        centrarTexto(g2, "jugador 2 ataca con P", anchoPantalla, 460); // Ataque jugador 2
        centrarTexto(g2,"jugador 1 tira poderes con Q", anchoPantalla, 530); // Poder jugador 1
        centrarTexto(g2, "jugador 2 tira poderes con O", anchoPantalla, 600); // Poder jugador 2
        centrarTexto(g2, "Gana quien elimine primero al rival", anchoPantalla, 670); // Objetivo
        centrarTexto(g2, "En caso de jugar 1J VS CPU, el jugador 1 ataca con ESPACIO y se mueve con flechas", anchoPantalla, 740); // Objetivo


        g.setFont(new Font("Arial", Font.BOLD, 30)); // Fuente inferior
        g.drawString("Presiona ESC para volver al menú", 520, 850); // Volver
    }

    private void centrarTexto(Graphics2D g2, String texto, int anchoPantalla, int y) {
        FontMetrics medidas = g2.getFontMetrics();
        int anchoTexto = medidas.stringWidth(texto);
        int x = (anchoPantalla - anchoTexto) / 2;

        g2.drawString(texto, x, y);
    }
}
