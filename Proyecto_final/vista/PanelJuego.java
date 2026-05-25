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
    public static final int SELECCION_MODO = 1;
    public static final int JUGANDO = 2; //Estado del juego (2)
    public static final int FIN = 3;


    int estadoJuego = MENU;  //Dice al panel que inicie en el menú

    boolean contraPC = false;

    String nombreJugador1 = "Jugador 1";
    String nombreJugador2 = "Jugador 2";


    //Guardar logica del juego
    GameLoop gameLoop;

    //Hilo que ejecutará el loop
    Thread hiloJuego;

    InputManager teclado = new InputManager();

    Jugador jugador1 = new Jugador(teclado, anchoPantalla, altoPantalla, 200, 500, 1);  //Le damos acceso a los jugadores en 2 posiciones diferentes
    Jugador jugador2 = new Jugador(teclado, anchoPantalla, altoPantalla, 1200, 500, 2);

    Menu menu = new Menu();
    Escenario escenario = new Escenario();

    HUD hud = new HUD();       ////////////////////////////////

    long tiempoInicioPartida;
    int tiempoPartida = 0;

    int puntajeJugador1 = 0;
    int puntajeJugador2 = 0;

    int vidaAnteriorJugador1 = 200;
    int vidaAnteriorJugador2 = 200;
/// //////////////

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

    public void reiniciarPartida() {
        jugador1.reiniciar(200, 500);
        jugador2.reiniciar(1200, 500);

        puntajeJugador1 = 0;
        puntajeJugador2 = 0;

        vidaAnteriorJugador1 = jugador1.getVida();
        vidaAnteriorJugador2 = jugador2.getVida();

        tiempoInicioPartida = System.currentTimeMillis();
        tiempoPartida = 0;

        estadoJuego = JUGANDO;
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
            if (teclado.enter) {
                estadoJuego = SELECCION_MODO;
            }
        }

        if (estadoJuego == SELECCION_MODO) {

            if (teclado.tecla1) {
                contraPC = true;

                nombreJugador1 = JOptionPane.showInputDialog(this, "Nombre del jugador 1:");

                if (nombreJugador1 == null || nombreJugador1.isEmpty()) {
                    nombreJugador1 = "Jugador 1";
                }

                nombreJugador2 = "CPU";

                jugador1.setModoSolo(true);  //Se activa el metodo update para un solo jugador
                jugador2.setModoSolo(false);  //Se ignora juego para cpu (ya existe el metodo para ella)

                reiniciarPartida();
                return;
            }

            if (teclado.tecla2) {
                contraPC = false;

                nombreJugador1 = JOptionPane.showInputDialog(this, "Nombre del jugador 1:");
                nombreJugador2 = JOptionPane.showInputDialog(this, "Nombre del jugador 2:");

                if (nombreJugador1 == null || nombreJugador1.isEmpty()) {
                    nombreJugador1 = "Jugador 1";
                }

                if (nombreJugador2 == null || nombreJugador2.isEmpty()) {
                    nombreJugador2 = "Jugador 2";
                }

                jugador1.setModoSolo(false);
                jugador2.setModoSolo(false);

                reiniciarPartida();
                return;


            }
        }

        if (estadoJuego == JUGANDO) {
            jugador1.update();
            if (contraPC) {
                jugador2.controlarCPU(jugador1);
            } else {
                jugador2.update();
            }

            jugador1.verificarGolpe(jugador2);
            jugador2.verificarGolpe(jugador1);
            jugador1.verificarPoder(jugador2);
            jugador2.verificarPoder(jugador1);


            //Verificar que tanto daño se hizo
            tiempoPartida = (int) ((System.currentTimeMillis() - tiempoInicioPartida) / 1000);

            if (jugador2.getVida() < vidaAnteriorJugador2) {
                int danoHecho = vidaAnteriorJugador2 - jugador2.getVida();
                puntajeJugador1 += danoHecho * 10;  // Jugador ofensivo gana puntos multiplicados por diez
                vidaAnteriorJugador2 = jugador2.getVida();
            }

            if (jugador1.getVida() < vidaAnteriorJugador1) {
                int danoHecho = vidaAnteriorJugador1 - jugador1.getVida();
                puntajeJugador2 += danoHecho * 10;
                vidaAnteriorJugador1 = jugador1.getVida();
            }
        }

       /* if (jugador1.getVida() <= 0 || jugador2.getVida() <= 0) {  //Verificar ganador
            estadoJuego = FIN;
        }*/

        if (estadoJuego == JUGANDO) {
            if (jugador1.getVida() <= 0 || jugador2.getVida() <= 0) {
                estadoJuego = FIN;
            }
        }

        if (estadoJuego == FIN) {
            if (teclado.enter) {
                reiniciarPartida();
                jugador1.setModoSolo(contraPC);
                jugador2.setModoSolo(false);
            }
        }
    }

    @Override //Para sobreescribir el paintComponent que ya tiene JPanel
    protected void paintComponent(Graphics g) {
        //Limpia el frame anterior para evitar una mala animacion
        super.paintComponent(g);

        if (estadoJuego == MENU) {

            menu.draw(g);
        }

        //Se dibujan los apartados de nombres y desicion de uno o dos jugadores
        if (estadoJuego == SELECCION_MODO) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, anchoPantalla, altoPantalla);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 60));
            g.drawString("SELECCIONA MODO DE JUEGO", 320, 250);

            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Presiona 1: Un jugador vs PC", 450, 400);
            g.drawString("Presiona 2: Dos jugadores", 450, 480);
        }

        if (estadoJuego == JUGANDO) {

            escenario.draw(g);
                                    // orden fundamental para dibujar personaje encima del escenario
            jugador1.draw(g);
            jugador2.draw(g);

            hud.draw(g, jugador1, jugador2, nombreJugador1, nombreJugador2, tiempoPartida, puntajeJugador1, puntajeJugador2); //Dibuja vida solo si esta en el juego
        }

        if (estadoJuego == FIN) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 60));

            if (jugador1.getVida() <= 0) {
                g.drawString("GANA JUGADOR 2", 500, 500);
            } else {
                g.drawString("GANA JUGADOR 1", 500, 500);
            }

            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Tiempo final: " + tiempoPartida + " segundos", 520, 560);
            g.drawString(nombreJugador1 + ": " + puntajeJugador1 + " puntos", 520, 610);
            g.drawString(nombreJugador2 + ": " + puntajeJugador2 + " puntos", 520, 660);

            g.setFont(new Font("Arial", Font.BOLD, 35));
            g.drawString("Presiona ENTER para reiniciar", 500, 730);
        }



        /*
        //Color del pincel
        g.setColor(Color.WHITE);
        //Parametros
        g.fillRect(100, 100, 50, 50);*/

    }
}
