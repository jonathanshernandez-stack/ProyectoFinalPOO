package Proyecto_final.vista;
//Hereda de JPanel, donde se puede dibujar

import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import Proyecto_final.controlador.SoundManager;

import Proyecto_final.controlador.GameLoop;
import Proyecto_final.controlador.GestorRanking;
import Proyecto_final.controlador.InputManager;
import Proyecto_final.modelo.Jugador;
import Proyecto_final.modelo.RegistroRanking;

public class PanelJuego extends JPanel {

    final int anchoPantalla = 1500;
    final int altoPantalla = 1000;

    public static final int MENU = 0; //Estado del menú (0)
    public static final int SELECCION_MODO = 1; //Estado de seleccion
    public static final int SELECCION_PERSONAJE = 2;
    public static final int JUGANDO = 3; //Estado del juego (2)
    public static final int FIN = 4; //Estado final
    public static final int INSTRUCCIONES = 5;



    int estadoJuego = MENU;  //Dice al panel que inicie en el menú

    boolean contraPC = false;

    long tiempoMuerte = 0;  //Para visualizar muerto
    boolean esperandoPantallaFinal = false;

    String nombreJugador1 = "Jugador 1";
    String nombreJugador2 = "Jugador 2";


    String personajeJugador1 = ""; ////////////Seleccion de personajes en otro apartado
    String personajeJugador2 = "";

    int turnoSeleccion = 1;

    //Guardar logica del juego
    GameLoop gameLoop;

    //Hilo que ejecutará el loop
    Thread hiloJuego;

    InputManager teclado = new InputManager();

    Jugador jugador1 = new Jugador(teclado, anchoPantalla, altoPantalla, 200, 500, 1);  //Le damos acceso a los jugadores en 2 posiciones diferentes
    Jugador jugador2 = new Jugador(teclado, anchoPantalla, altoPantalla, 1200, 500, 2);

    Menu menu = new Menu();
    SeleccionModo seleccionModo = new SeleccionModo();
    SeleccionPersonaje seleccionPersonaje = new SeleccionPersonaje();
    Escenario escenario = new Escenario();
    FinalModo finalModo = new FinalModo();
    Instrucciones instrucciones = new Instrucciones();
    SoundManager soundManager = new SoundManager();

    HUD hud = new HUD();  //Head up Display, informacion en pantalla

    GestorRanking gestorRanking = new GestorRanking();
    ArrayList<RegistroRanking> ranking = new ArrayList<>();
    boolean rankingGuardado = false;


    long tiempoInicioPartida;
    int tiempoPartida = 0;

    int puntajeJugador1 = 0;
    int puntajeJugador2 = 0;

    int vidaAnteriorJugador1 = 200;
    int vidaAnteriorJugador2 = 200;

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

        jugador1.reiniciar(80, 500);
        jugador2.reiniciar(1210, 500);

        jugador1.setPersonajeElegido(personajeJugador1); // Pone en batalla el personaje elegido por J1
        jugador2.setPersonajeElegido(personajeJugador2); // Pone en batalla el personaje elegido por J2 o CPU

        puntajeJugador1 = 0;
        puntajeJugador2 = 0;

        vidaAnteriorJugador1 = jugador1.getVida();
        vidaAnteriorJugador2 = jugador2.getVida();

        tiempoInicioPartida = System.currentTimeMillis();
        tiempoPartida = 0;

        rankingGuardado = false;

        SoundManager.reproducir("Proyecto_final/resources/Sonidos/inicio.wav");
        SoundManager.reproducirMusica("Proyecto_final/resources/Sonidos/Legend.wav");

        estadoJuego = JUGANDO;
    }

    //Controla la selección de personajes
    public void seleccionarPersonaje(String personajeElegido) {

        //Si el juego es contra CPU
        if (contraPC) {

            personajeJugador1 = personajeElegido;

            //La CPU escoge automáticamente otro personaje
            personajeJugador2 = elegirPersonajeCPU(personajeJugador1);

            reiniciarPartida(); //inicia pelea

            estadoJuego = JUGANDO;

            return;
        }

        //Turno del jugador 1
        if (turnoSeleccion == 1) {

            personajeJugador1 = personajeElegido;

            turnoSeleccion = 2; //ahora elige jugador 2

            return;
        }

        //Turno jugador 2
        personajeJugador2 = personajeElegido;

        reiniciarPartida();

        estadoJuego = JUGANDO;
    }

    //La CPU elige un personaje aleatorio diferente
    public String elegirPersonajeCPU(String personajeJugador) {

        String[] personajes = {"Brakhan", "Connor", "Yepstorm"};

        ArrayList<String> opcionesCPU = new ArrayList<>();

        //Guardar personajes disponibles
        for (String personaje : personajes) {

            if (!personaje.equals(personajeJugador)) {

                opcionesCPU.add(personaje);
            }
        }

        //Número aleatorio
        int posicionAleatoria =
                (int)(Math.random() * opcionesCPU.size());

        return opcionesCPU.get(posicionAleatoria);
    }

    public void update () { //consulta estados al teclado

        if (teclado.escape) {
            estadoJuego = MENU;
            SoundManager.detenerMusica();
            return;
        }

        if (estadoJuego == MENU) {

            if (teclado.teclaI) { // Si presionas I en el menú
                SoundManager.reproducir("Proyecto_final/resources/Sonidos/presionar_enter.wav");
                estadoJuego = INSTRUCCIONES; // Muestra instrucciones
                teclado.teclaI = false; // Apaga la tecla
                return; // No deja que siga revisando más estados
            }

            if (teclado.enter) { // Si presionas ENTER
                SoundManager.reproducir("Proyecto_final/resources/Sonidos/presionar_enter.wav");
                estadoJuego = SELECCION_MODO; // Sigue normal a selección de modo
                teclado.enter = false; // Apaga enter
                return;
            }
        }

        if (estadoJuego == INSTRUCCIONES) {

            if (teclado.escape) { // ESC vuelve al menú
                SoundManager.reproducir("Proyecto_final/resources/Sonidos/presionar_enter.wav");
                estadoJuego = MENU;
                teclado.escape = false;
                return;
            }
        }

        if (estadoJuego == SELECCION_MODO) {

            if (teclado.tecla1) {

                contraPC = true; //el juego será contra la computadora

                nombreJugador1 = JOptionPane.showInputDialog(this,
                        "Nombre del jugador 1: ");

                //si el jugador no escribe nada
                if (nombreJugador1 == null || nombreJugador1.isEmpty()) {
                    nombreJugador1 = "Jugador 1";
                }

                nombreJugador2 = "CPU";

                jugador1.setModoSolo(true); //jugador humano
                jugador2.setModoSolo(false); //CPU

                turnoSeleccion = 1; //empieza eligiendo jugador 1

                estadoJuego = SELECCION_PERSONAJE; //pasar a elegir personaje

                teclado.tecla1 = false; //reiniciar tecla
                return;
            }


           /* if (teclado.tecla1) {
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
            }*/

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

                turnoSeleccion = 1;
                estadoJuego = SELECCION_PERSONAJE;

                teclado.tecla2 = false;
                return;
            }

           /* if (teclado.tecla2) {
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


            }*/
        }

        //Pantalla selección de personajes
        if (estadoJuego == SELECCION_PERSONAJE) {

            if (teclado.izquierda2) { // Flecha izquierda mueve selección
                seleccionPersonaje.moverIzquierda();
                teclado.izquierda2 = false;
                return;
            }

            if (teclado.derecha2) { // Flecha derecha mueve selección
                seleccionPersonaje.moverDerecha();
                teclado.derecha2 = false;
                return;
            }

            if (teclado.enter) { // Enter confirma personaje
                SoundManager.reproducir("Proyecto_final/resources/Sonidos/Seleccionar_personaje.wav");
                seleccionarPersonaje(seleccionPersonaje.getPersonajeSeleccionado());
                teclado.enter = false;
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

        if (estadoJuego == JUGANDO) {   //Deja visualizar muerte despues de 3 segundos pero sigue guardando ranking

            if (jugador1.getVida() <= 0 || jugador2.getVida() <= 0) {

                if (!esperandoPantallaFinal) {
                    esperandoPantallaFinal = true;
                    tiempoMuerte = System.currentTimeMillis(); // empieza contador de 3 segundos
                }

                if (System.currentTimeMillis() - tiempoMuerte >= 3000) {

                    if (!rankingGuardado) {
                        String modo = contraPC ? "1 Jugador" : "2 Jugadores";

                        gestorRanking.guardarRegistro(
                                new RegistroRanking(nombreJugador1, puntajeJugador1, tiempoPartida, modo)
                        );

                        gestorRanking.guardarRegistro(
                                new RegistroRanking(nombreJugador2, puntajeJugador2, tiempoPartida, modo)
                        );

                        ranking = gestorRanking.leerRanking();

                        rankingGuardado = true;
                    }

                    SoundManager.detenerMusica();

                    SoundManager.reproducir("Proyecto_final/resources/Sonidos/fin_juego.wav");

                    estadoJuego = FIN;

                }
            }
        }

        /* if (estadoJuego == JUGANDO) {
            if (jugador1.getVida() <= 0 || jugador2.getVida() <= 0) {
                estadoJuego = FIN;
            }
        } */

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

        if (estadoJuego == INSTRUCCIONES) {
            instrucciones.draw(g, anchoPantalla, altoPantalla);
        }

        //Se dibujan los apartados de nombres y desicion de uno o dos jugadores
        if (estadoJuego == SELECCION_MODO) {
           seleccionModo.draw(g);
        }

        //Pantalla selección personajes
        if (estadoJuego == SELECCION_PERSONAJE) {
            seleccionPersonaje.draw(g, anchoPantalla, altoPantalla, contraPC, turnoSeleccion);
        }

        if (estadoJuego == JUGANDO) {

            escenario.draw(g);
                                    // orden fundamental para dibujar personaje encima del escenario
            jugador1.draw(g);
            jugador2.draw(g);

            hud.draw(g, jugador1, jugador2, nombreJugador1, nombreJugador2, tiempoPartida, puntajeJugador1, puntajeJugador2); //Dibuja vida solo si esta en el juego
        }

        if (estadoJuego == FIN) {
            finalModo.draw(g, nombreJugador1, nombreJugador2, tiempoPartida, puntajeJugador1, puntajeJugador2, ranking, jugador1);
        }



        /*
        //Color del pincel
        g.setColor(Color.WHITE);
        //Parametros
        g.fillRect(100, 100, 50, 50);*/

    }
}
