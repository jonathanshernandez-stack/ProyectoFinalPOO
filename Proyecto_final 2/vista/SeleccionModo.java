package Proyecto_final.vista;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SeleccionModo {

    private BufferedImage fondo;

    public SeleccionModo() {
        try {
            fondo = ImageIO.read(new File("/Users/Jonathan/Documents/poo/Proyecto_final/resources/escenarios/Seleccionmod.jpeg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Metodo para centrar texto en panel
    private void centrar_texto(Graphics2D g2, String texto, int anchoPantalla, int y) {
        FontMetrics medidas = g2.getFontMetrics();
        int anchoTexto = medidas.stringWidth(texto);
        int x = (anchoPantalla - anchoTexto) / 2;

        g2.drawString(texto, x, y);
    }

    public void draw(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        if (fondo != null) {
            g2.drawImage(fondo, 0, 0, 1500, 1000, null);
        } else {
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, 1500, 1000);
        }



        g2.setColor(new Color(0, 0, 0, 170));
        g2.fillRect(0, 0, 1500, 1000);

        g2.setFont(new Font("Arial", Font.BOLD, 60));
        g2.setColor(Color.WHITE);
        centrar_texto(g2, "SELECCIONE MODO DE JUEGO", 1500, 200);

        g2.setFont(new Font("Arial", Font.BOLD, 28));
        g2.setColor(Color.WHITE);
        centrar_texto(g2, "PRESIONE 1 PARA ELEGIR: JUGADOR 1 -------- VS ------- PC ", 1500, 500);
        centrar_texto(g2, "PRESIONE 2 PARA ELEGIR: JUGADOR 1 -------- VS ------- J2 ", 1500, 580);


    }

}
