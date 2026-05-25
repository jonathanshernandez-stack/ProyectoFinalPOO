package Proyecto_final.modelo;

import java.awt.*;

import Proyecto_final.controlador.InputManager;

public class Jugador {

    int x;
    int y;

    int velocidad;

    int ancho;
    int alto;

    Hitbox hurtbox;

    Ataque ataqueActual;

    int vida = 200;

    int numeroJugador;

    String direccion;

    InputManager teclado;

    int anchoPantalla;
    int altoPantalla;

    public Jugador(InputManager teclado, int anchoPantalla, int altoPantalla, int xInicial, int yInicial, int numeroJugador) {

        this.teclado = teclado;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        this.numeroJugador = numeroJugador;

        x = xInicial; //Posicion de prueba de personajes en pantalla, esto permite crear jugadores en posiciones diferentes, diferente a dar valor fijo
        y = yInicial;

        velocidad = 5;

        ancho = 50;
        alto = 50;

        direccion = "abajo";

        hurtbox = new Hitbox(
                x,
                y,
                ancho,
                alto
        );

    }

    public void update() {

        boolean moverArriba;
        boolean moverAbajo;
        boolean moverIzquierda;
        boolean moverDerecha;
        boolean botonAtacar;

        if (numeroJugador == 1) {    //Darle valor a movimientos para compartir entre multiples jugadores
            moverArriba = teclado.arriba;
            moverAbajo = teclado.abajo;
            moverIzquierda = teclado.izquierda;
            moverDerecha = teclado.derecha;
            botonAtacar = teclado.atacar;
        } else {
            moverArriba = teclado.arriba2;
            moverAbajo = teclado.abajo2;
            moverIzquierda = teclado.izquierda2;
            moverDerecha = teclado.derecha2;
            botonAtacar = teclado.atacar2;
        }

        if (moverArriba) {

            if (y > 0) {
                y -= velocidad;
            }

            direccion = "arriba";
        }

        if (moverAbajo) {

            if (y + alto < altoPantalla) {
                y += velocidad;
            }

            direccion = "abajo";
        }

        if (moverIzquierda) {

            if (x > ancho ) {
                x -= velocidad;
            }

            direccion = "izquierda";
        }

        if (moverDerecha) {

            if (x + ancho < anchoPantalla) {
                x += velocidad;
            }

            direccion = "derecha";
        }

        hurtbox.setPosicion(x, y); //Cuando el jugador se mueve, la hitbox debe perseguirlo

        if (botonAtacar) {
            atacar();
        }
    }

    public void recibirDano(int dano) {

        vida -= dano;

        if (vida < 0) {

            vida = 0;
        }

        System.out.println("Vida: " + vida);
    }

    public void verificarGolpe(Jugador enemigo) {

        if (ataqueActual != null &&
                ataqueActual.isActivo()) {

            if (ataqueActual.getHitbox()
                    .colisiona(enemigo.hurtbox)) {

                enemigo.recibirDano(
                        ataqueActual.getDano()
                );

                ataqueActual.desactivar();

                System.out.println("GOLPE!");
            }
        }
    }

    public void atacar() {

        if (direccion.equals("derecha")) {

            ataqueActual = new Ataque(
                    10,
                    new Hitbox(x + ancho, y + 10, 40, 30)
            );
        }

        if (direccion.equals("izquierda")) {

            ataqueActual = new Ataque(
                    10,
                    new Hitbox(x - 40, y + 10, 40, 30)
            );
        }

        if (direccion.equals("arriba")) {

            ataqueActual = new Ataque(
                    10,
                    new Hitbox(x + 10, y - 40, 30, 40)
            );
        }

        if (direccion.equals("abajo")) {

            ataqueActual = new Ataque(
                    10,
                    new Hitbox(x + 10, y + alto, 30, 40)
            );
        }

        ataqueActual.activar();
    }

    public void draw(Graphics g) {

        if (direccion.equals("arriba")) {
            g.setColor(Color.CYAN);
        }

        if (direccion.equals("abajo")) {
            g.setColor(Color.WHITE);
        }

        if (direccion.equals("izquierda")) {
            g.setColor(Color.GREEN);
        }

        if (direccion.equals("derecha")) {
            g.setColor(Color.YELLOW);
        }

        g.fillRect(x, y, ancho, alto);

        g.setColor(Color.RED);   //color de hurtbox

        g.drawRect(x, y, ancho, alto);

        if (ataqueActual != null &&
                ataqueActual.isActivo()) {

            Rectangle r =
                    ataqueActual.getHitbox().getBounds();

            g.setColor(Color.BLUE);

            g.drawRect(
                    r.x,
                    r.y,
                    r.width,
                    r.height
            );
        }
    }
}
