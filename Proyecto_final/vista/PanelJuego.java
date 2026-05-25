package Proyecto_final.vista;
//Hereda de JPanel, donde se puede dibujar

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import Proyecto_final.controlador.GameLoop;

public class PanelJuego extends JPanel {

    //Guardar logica del juego
    GameLoop gameLoop;

    //Hilo que ejecutará el loop
    Thread hiloJuego;

    final int anchoPantalla = 1500;
    final int altoPantalla = 1000;

    public PanelJuego () {

        //Medir el total de la pantalla
        this.setPreferredSize(new Dimension(anchoPantalla, altoPantalla));


        this.setBackground(Color.black);

        //El doble buffer evita lageos en el juego, procesa en memoria y despues dibuja en el frame, hace que se vea fluido
        this.setDoubleBuffered(true);

        gameLoop = new GameLoop();

    }

    public void iniciarJuego () {
        hiloJuego = new Thread(gameLoop);
        hiloJuego.start();
    }

    @Override //Para sobreescribir el paintComponent que ya tiene JPanel
    protected void paintComponent(Graphics g) {
        //Limpia el frame anterior para evitar una mala animacion
        super.paintComponent(g);
        //Color del pincel
        g.setColor(Color.WHITE);
        //Parametros
        g.fillRect(100, 100, 50, 50);
    }
}
