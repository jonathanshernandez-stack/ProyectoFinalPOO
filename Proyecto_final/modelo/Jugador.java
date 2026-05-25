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

    boolean mostrarGolpe = false;
    int tiempoGolpe = 0;

    private double empujeX = 0;
    private double empujeY = 0;

    private PoderEspecial poderEspecial;

    private boolean modoSolo = false;

    String direccion;

    InputManager teclado;

    int anchoPantalla;
    int altoPantalla;

    //Atributos para aplicar gravedad
    private double velocidadY = 0;
    private double gravedad = 0.8;
    private double fuerzaSalto = -18;
    private boolean enSuelo = false;
    private int sueloY;

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

        sueloY = altoPantalla - alto; //Hacer que siempre comience en el suelo
        y = sueloY;

        direccion = "abajo";

        hurtbox = new Hitbox(
                x,
                y,
                ancho,
                alto
        );

    }

    public void lanzarPoder() {
        if (poderEspecial == null || !poderEspecial.isActivo()) { //Significa, no se puede lanzar un poder si hay otro activo

            int poderX = x;
            int poderY = y + alto / 2;

            if (direccion.equals("derecha")) {
                poderX = x + ancho;
            }

            if (direccion.equals("izquierda")) {
                poderX = x - 80;
            }

            if (direccion.equals("arriba")) {
                poderY = y - 40;
            }

            if (direccion.equals("abajo")) {
                poderY = y + alto;
            }

            poderEspecial = new PoderEspecial(poderX, poderY, direccion);
        }
    }

    private void aplicarLimitesPantalla() {

        if (x < 0) {
            x = 0;
            empujeX = 0;
        }

        if (y < 0) {
            y = 0;
            empujeY = 0;
        }

        if (x + ancho > anchoPantalla) {
            x = anchoPantalla - ancho;
            empujeX = 0;
        }

        if (y + alto > altoPantalla) {
            y = altoPantalla - alto;
            empujeY = 0;
        }
    }

    public void update() {

        boolean moverArriba;
        boolean moverAbajo;
        boolean moverIzquierda;
        boolean moverDerecha;
        boolean botonAtacar;
        boolean botonPoder;

         /*if (numeroJugador == 1) {    //Darle valor a movimientos para compartir entre multiples jugadores
            moverArriba = teclado.arriba;
            moverAbajo = teclado.abajo;
            moverIzquierda = teclado.izquierda;
            moverDerecha = teclado.derecha;
            botonAtacar = teclado.atacar;
            botonPoder = teclado.poder;
        } else {
            moverArriba = teclado.arriba2;
            moverAbajo = teclado.abajo2;
            moverIzquierda = teclado.izquierda2;
            moverDerecha = teclado.derecha2;
            botonAtacar = teclado.atacar2;
            botonPoder = teclado.poder2;
        }*/

        if (numeroJugador == 1) {

            //Condiciones nuevas de juego, si entra al modo solo para disponer del teclado
            if (modoSolo) {
                moverArriba = teclado.arriba2;
                moverAbajo = teclado.abajo2;
                moverIzquierda = teclado.izquierda2;
                moverDerecha = teclado.derecha2;
                botonAtacar = teclado.atacar;
                botonPoder = teclado.poder;
            } else {
                moverArriba = teclado.arriba;
                moverAbajo = teclado.abajo;
                moverIzquierda = teclado.izquierda;
                moverDerecha = teclado.derecha;
                botonAtacar = teclado.atacar;
                botonPoder = teclado.poder;
            }
        } else {
            moverArriba = teclado.arriba2;
            moverAbajo = teclado.abajo2;
            moverIzquierda = teclado.izquierda2;
            moverDerecha = teclado.derecha2;
            botonAtacar = teclado.atacar2;
            botonPoder = teclado.poder2;
        }

        aplicarLimitesPantalla();

        if (moverArriba && enSuelo) {
            velocidadY = fuerzaSalto;
            enSuelo = false;
            direccion = "arriba";
        }

       /* if (moverAbajo) {

            if (y + alto < altoPantalla) {
                y += velocidad;
            }

            direccion = "abajo";
        } */

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

        velocidadY += gravedad;
        y += (int) velocidadY;

        if (y >= sueloY) {
            y = sueloY;
            velocidadY = 0;
            enSuelo = true;
        }

        hurtbox.setPosicion(x, y); //Cuando el jugador se mueve, la hitbox debe perseguirlo

        if (botonAtacar) {
            atacar();
        }

        if (botonPoder) {
            lanzarPoder();
        }

        // Aplicar empuje cuando recibe un golpe
        x += (int) empujeX;
        y += (int) empujeY;

        // Reducir poco a poco el empuje
        empujeX *= 0.8;
        empujeY *= 0.8;

        // Si el empuje ya es muy pequeño, lo apagamos
        if (Math.abs(empujeX) < 1) {
            empujeX = 0;
        }

        if (Math.abs(empujeY) < 1) {
            empujeY = 0;
        }

        if (tiempoGolpe > 0) {
            tiempoGolpe--;
        } else {
            mostrarGolpe = false;
        }

        //Activar poder especial

        if (poderEspecial != null && poderEspecial.isActivo()) {
            poderEspecial.update();
        }
    }

    public void setModoSolo(boolean modoUnJugador) {
        this.modoSolo = modoUnJugador;
    }

    public void recibirDano(int dano) {

        vida -= dano;

        if (vida < 0) {

            vida = 0;
        }

        mostrarGolpe = true;
        tiempoGolpe = 30;

        System.out.println("Vida: " + vida);
    }

    public int getVida() {
        return vida;
    }

    public void verificarGolpe(Jugador enemigo) {

        if (ataqueActual != null && ataqueActual.isActivo()) {

            if (ataqueActual.getHitbox().colisiona(enemigo.hurtbox)) {

                enemigo.recibirDano(ataqueActual.getDano());
                if (this.x < enemigo.x) {                     //Significa, si estoy a la derecha, empujo a la izquiera y viceversa
                    enemigo.recibirEmpuje(20, 0);
                } else {
                    enemigo.recibirEmpuje(-20, 0);
                }

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

    public void recibirEmpuje(int fuerzaX, int fuerzaY) { //Metodo para empuje en el daño
        empujeX = fuerzaX;
        empujeY = fuerzaY;
    }

    public void verificarPoder(Jugador enemigo) {
        if (poderEspecial != null && poderEspecial.isActivo()) {
            if (poderEspecial.getBounds().intersects(enemigo.hurtbox.getBounds())) {
                enemigo.recibirDano(poderEspecial.getDano());
                enemigo.recibirEmpuje(this.x < enemigo.x ? 30 : -30, 0);
                poderEspecial.desactivar();
                System.out.println("PODER ESPECIAL!");
            }
        }
    }

    //Asegurar que la cpu ataque al jugador 1

    public void controlarCPU(Jugador enemigo) {

        int distanciaX = enemigo.x - this.x;
        int distanciaY = enemigo.y - this.y;

        if (Math.abs(distanciaX) > 120) {
            if (distanciaX > 0) {
                x += velocidad;
                direccion = "derecha";
            } else {
                x -= velocidad;
                direccion = "izquierda";
            }
        }

        if (Math.abs(distanciaY) > 40) {
            if (distanciaY > 0) {
                y += velocidad;
                direccion = "abajo";
            } else {
                y -= velocidad;
                direccion = "arriba";
            }
        }

        if (Math.abs(distanciaX) < 140 && Math.abs(distanciaY) < 100) {
            atacar();
        }

        if (Math.abs(distanciaX) > 350) {
            lanzarPoder();
        }

        x += (int) empujeX;
        y += (int) empujeY;

        empujeX *= 0.8;
        empujeY *= 0.8;

        if (Math.abs(empujeX) < 1) {
            empujeX = 0;
        }

        if (Math.abs(empujeY) < 1) {
            empujeY = 0;
        }

        aplicarLimitesPantalla();

        hurtbox.setPosicion(x, y);

        if (poderEspecial != null && poderEspecial.isActivo()) {
            poderEspecial.update();
        }
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

        if (ataqueActual != null && ataqueActual.isActivo()) {

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

        if (mostrarGolpe) {
            g.setColor(Color.YELLOW);
            g.drawString("GOLPE!", x, y - 20);
        }

        if (poderEspecial != null && poderEspecial.isActivo()) {
            poderEspecial.draw(g);
        }
    }

    public void reiniciar(int xInicial, int yInicial) {
        x = xInicial;
        y = sueloY;
        velocidadY = 0;
        enSuelo = true;

        vida = 200;

        direccion = "abajo";

        mostrarGolpe = false;
        tiempoGolpe = 0;

        empujeX = 0;
        empujeY = 0;

        ataqueActual = null;
        poderEspecial = null;

        hurtbox.setPosicion(x, y);
    }

}
