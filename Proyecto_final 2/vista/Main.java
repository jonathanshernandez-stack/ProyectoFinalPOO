package Proyecto_final.vista;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        //Crea ventana en memoria de clase swing, es la base donde vivirá el panel
        JFrame ventana = new JFrame();

        ventana.setTitle("Legends - POO");

        //Permite el cierre de la ventana
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ventana.setResizable(false);

        PanelJuego panelJuego = new PanelJuego();

        //Metemos el panel de juego dentro del JFrame
        ventana.add(panelJuego);

        //Se llama el metodo que inicia el hilo para darle vida al juego
        panelJuego.iniciarJuego();

        //Hace que el JFrame adopte el tamaño del panel
        ventana.pack();

        ventana.setLocationRelativeTo(null);

        //La muestra visible
        ventana.setVisible(true);

    }

}
