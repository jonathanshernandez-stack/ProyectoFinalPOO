package Proyecto_final.vista;

import Proyecto_final.controlador.SoundManager;

import javax.swing.*;
import java.awt.*;

public class SeleccionPersonaje {

    private String[] nombres = {"Brakhan", "Connor", "Yepstorm"}; // Nombres disponibles
    private Image[] imagenes = new Image[3]; // Guarda las imágenes de cada personaje
    private int indiceSeleccionado = 0; // Personaje donde está parado el selector

    public SeleccionPersonaje() {
        imagenes[0] = new ImageIcon("Proyecto_final/resources/SeleccionesP/Brakhan.png").getImage(); // Imagen Brakhan
        imagenes[1] = new ImageIcon("Proyecto_final/resources/SeleccionesP/Connor.png").getImage(); // Imagen Connor
        imagenes[2] = new ImageIcon("Proyecto_final/resources/SeleccionesP/Yepstorm.png").getImage(); // Imagen Yepstorm
    }

    public void moverIzquierda() {
        indiceSeleccionado--; // Se mueve una posición a la izquierda
        SoundManager.reproducir("Proyecto_final/resources/Sonidos/saltar.wav");

        if (indiceSeleccionado < 0) { // Si se pasa del primero
            indiceSeleccionado = nombres.length - 1; // Vuelve al último
        }
    }

    public void moverDerecha() {
        indiceSeleccionado++; // Se mueve una posición a la derecha
        SoundManager.reproducir("Proyecto_final/resources/Sonidos/saltar.wav");


        if (indiceSeleccionado >= nombres.length) { // Si se pasa del último
            indiceSeleccionado = 0; // Vuelve al primero
        }
    }

    public String getPersonajeSeleccionado() {
        return nombres[indiceSeleccionado]; // Devuelve el personaje actual
    }

    public void draw(Graphics g, int anchoPantalla, int altoPantalla, boolean contraPC, int turnoSeleccion) {

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(new Color(20, 15, 10)); // Fondo oscuro
        g2.fillRect(0, 0, anchoPantalla, altoPantalla); // Pintar todo el fondo

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 60));

        String titulo = contraPC ? "SELECCIONA TU PERSONAJE" : "ELIGE PERSONAJE JUGADOR " + turnoSeleccion;
        centrarTexto(g2, titulo, anchoPantalla, 120);

        int separacion = 320; // Espacio entre cartas
        int cartaAncho = 260;
        int cartaAlto = 420;
        int inicioX = (anchoPantalla - (cartaAncho * 3 + separacion * 2)) / 2;
        int y = 230;

        for (int i = 0; i < nombres.length; i++) {

            int x = inicioX + i * (cartaAncho + separacion);

            if (i == indiceSeleccionado) {  //Permite darle brillo a la carta seleccionada
                
                g2.setColor(new Color(255, 220, 80)); // Marco dorado del seleccionado
                g2.fillRoundRect(x - 10, y - 10, cartaAncho + 20, cartaAlto + 20, 25, 25);
            }

            g2.setColor(new Color(35, 35, 35)); // Fondo de carta
            g2.fillRoundRect(x, y, cartaAncho, cartaAlto, 25, 25);

            g2.setColor(Color.WHITE); // Borde blanco
            g2.drawRoundRect(x, y, cartaAncho, cartaAlto, 25, 25);

            g2.drawImage(imagenes[i], x + 45, y + 70, 170, 230, null); // Dibuja personaje

            g2.setFont(new Font("Arial", Font.BOLD, 30));
            centrarTextoEnCaja(g2, nombres[i], x, cartaAncho, y + 350);

            if (i == indiceSeleccionado) {
                g2.setFont(new Font("Arial", Font.BOLD, 22));
                centrarTextoEnCaja(g2, "SELECCIONADO", x, cartaAncho, y + 390);
            }
        }

        g2.setFont(new Font("Arial", Font.PLAIN, 28));
        centrarTexto(g2, "Presiona flechas para navegar       ENTER para elegir       ESC para volver", 1500, 850);
    }

    private void centrarTexto(Graphics2D g2, String texto, int anchoPantalla, int y) {
        FontMetrics fm = g2.getFontMetrics();
        int x = (anchoPantalla - fm.stringWidth(texto)) / 2;
        g2.drawString(texto, x, y);
    }

    private void centrarTextoEnCaja(Graphics2D g2, String texto, int xCaja, int anchoCaja, int y) {
        FontMetrics fm = g2.getFontMetrics();
        int x = xCaja + (anchoCaja - fm.stringWidth(texto)) / 2;
        g2.drawString(texto, x, y);
    }
}
