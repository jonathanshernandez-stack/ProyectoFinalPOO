package Proyecto_final.vista;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Menu {

    private BufferedImage fondo;
    private BufferedImage logoUAM;

    public Menu() {
        try {
            fondo = ImageIO.read(new File("Proyecto_final/resources/escenarios/fondo4.jpeg"));
           //logoUAM = ImageIO.read(new File("/Users/Jonathan/Documents/poo/Proyecto_final/resources/Logos/logo_uam.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

      /*  if (logoUAM != null) {
            g2.drawImage(logoUAM, 50, 40, 150, 150, null);
        } else {
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, 300, 300);
        } */

        g2.setFont(new Font("Arial", Font.BOLD, 90));
        g2.setColor(new Color(255, 60, 60));
        centrarTexto(g2, "LEGENDS", 1500, 220);

        g2.setFont(new Font("Arial", Font.BOLD, 32));
        g2.setColor(Color.WHITE);
        centrarTexto(g2, "Videojuego de pelea desarrollado en Java Swing", 1500, 290);

        g2.setFont(new Font("Arial", Font.PLAIN, 28));
        g2.setColor(new Color(255, 220, 90));
        centrarTexto(g2, "Programación Orientada a Objetos", 1500, 380);

        g2.setFont(new Font("Arial", Font.BOLD, 30));
        g2.setColor(Color.WHITE);
        centrarTexto(g2, "Integrantes", 1500, 470);

        g2.setFont(new Font("Arial", Font.PLAIN, 26));
        centrarTexto(g2, "Jonathan Hernandez", 1500, 520);
        centrarTexto(g2, "Daniel Yepes", 1500, 560);
        centrarTexto(g2, "Brayan Ramirez", 1500, 600);

        g2.setFont(new Font("Arial", Font.BOLD, 34));
        g2.setColor(new Color(180, 220, 255));
        centrarTexto(g2, "PRESIONA ENTER PARA CONTINUAR", 1500, 830);

        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        g2.setColor(Color.LIGHT_GRAY);
        centrarTexto(g2, "Proyecto final de POO - Universidad Autónoma de Manizales", 1500, 900);
    }

    private void centrarTexto(Graphics2D g2, String texto, int anchoPantalla, int y) {
        FontMetrics medidas = g2.getFontMetrics();
        int anchoTexto = medidas.stringWidth(texto);
        int x = (anchoPantalla - anchoTexto) / 2;

        g2.drawString(texto, x, y);
    }
}
