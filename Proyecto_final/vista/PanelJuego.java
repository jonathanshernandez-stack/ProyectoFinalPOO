package Proyecto_final.vista;
//Hereda de JPanel, donde se puede dibujar

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import Proyecto_final.controlador.GameLoop;
import Proyecto_final.controlador.InputManager;
import Proyecto_final.modelo.Jugador;

public class PanelJuego extends JPanel {

    final int anchoPantalla = 1500;
    final int altoPantalla = 1000;

    public static final int MENU = 0; //Estado del menú (0)
    public static final int JUGANDO = 1; //Estado del juego (1)
    int estadoJuego = MENU;  //Dice al panel que inicie en el menú


    //Guardar logica del juego
    GameLoop gameLoop;

    //Hilo que ejecutará el loop
    Thread hiloJuego;

    InputManager teclado = new InputManager();

    Jugador jugador = new Jugador(teclado, anchoPantalla, altoPantalla); // Le damos acceso al jugador al teclado

    Menu menu = new Menu();
    Escenario escenario = new Escenario();


    public PanelJuego () {

        //Medir el total de la pantalla
        this.setPreferredSize(new Dimension(anchoPantalla, altoPantalla));


        this.setBackground(Color.black);

        //El doble buffer evita lageos en el juego, procesa en memoria y despues dibuja en el frame, hace que se vea fluido
        this.setDoubleBuffered(true);

        this.addKeyListener(teclado); //Dice al panel "Usa esta clase para escuchar teclado"

        this.setFocusable(true);//Recibir foco para el objeto que recibe entradas de teclado

        gameLoop = new GameLoop(this); //El this permite decir especificamente al loop que controle este objeto actual (PanelJuego)

    }

    public void iniciarJuego () {
        hiloJuego = new Thread(gameLoop);
        hiloJuego.start();
    }

    public void update () { //consulta estados al teclado

         /*if (teclado.arriba) {
            System.out.println("ARRIBA");
        }

        if (teclado.abajo) {
            System.out.println("ABAJO");
        }

        if (teclado.derecha) {
            System.out.println("DERECHA");
        }

        if (teclado.izquierda) {
            System.out.println("IZQUIERDA");
        }*/

        if (estadoJuego == MENU) {
            if (teclado.enter) {  //Revisa evento "enter" para iniciar el juego
                estadoJuego = JUGANDO;
            }
        }

        if (estadoJuego == JUGANDO) {
            jugador.update();
        }
    }

    @Override //Para sobreescribir el paintComponent que ya tiene JPanel
    protected void paintComponent(Graphics g) {
        //Limpia el frame anterior para evitar una mala animacion
        super.paintComponent(g);

        if (estadoJuego == MENU) {

            menu.draw(g);
        }

        if (estadoJuego == JUGANDO) {

            escenario.draw(g);

            jugador.draw(g);  // orden fundamental para dibujar personaje encima del escenario
        }

        /*
        //Color del pincel
        g.setColor(Color.WHITE);
        //Parametros
        g.fillRect(100, 100, 50, 50);*/

    }
}
