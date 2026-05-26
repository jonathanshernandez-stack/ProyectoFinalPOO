package Proyecto_final.modelo;

import java.awt.*;

import Proyecto_final.controlador.InputManager;

import javax.swing.*;

import Proyecto_final.controlador.SoundManager;

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
    private String personajeElegido = ""; // Guarda el nombre del personaje seleccionado
    private Image imagenPersonaje; // Guarda la imagen que se dibujará en batalla

    private Image spriteQuieto;
    private Image spriteCorriendo;
    private Image spritePunio;
    private Image spriteSalto;
    private Image spritePiso;
    private Image spriteGolpeado;

    private Image spriteActual;
    private int tiempoAccion = 0;
    private String accionVisual = "quieto";

    private int contadorRespiracion = 0; // Cuenta frames para animación idle
    private int offsetRespiracion = 6; // Movimiento visual arriba/abajo

    String direccion;

    InputManager teclado;

    int anchoPantalla;
    int altoPantalla;

    //Atributos para aplicar gravedad
    private double velocidadY = 0;
    private double gravedad = 0.8;
    private double fuerzaSalto = -24; // Más negativo = salta más alto
    private boolean enSuelo = false;
    private int sueloY;

    private int ajusteSpriteX = 0;    //UNICAMENTE PARA ACOMODAR SPRITES FACIL
    private int ajusteSpriteY = 0;
    private int ajusteSpriteAncho = 0;
    private int ajusteSpriteAlto = 0;

    public Jugador(InputManager teclado, int anchoPantalla, int altoPantalla, int xInicial, int yInicial, int numeroJugador) {

        this.teclado = teclado;
        this.anchoPantalla = anchoPantalla;
        this.altoPantalla = altoPantalla;
        this.numeroJugador = numeroJugador;

        x = xInicial; //Posicion de prueba de personajes en pantalla, esto permite crear jugadores en posiciones diferentes, diferente a dar valor fijo
        y = yInicial;

        velocidad = 5;

        ancho = 230; // Ancho real del cuerpo para golpes
        alto = 500;  // Alto real del cuerpo para golpes

        sueloY = altoPantalla - alto; //Hacer que siempre comience en el suelo
        y = sueloY;

        direccion = "abajo";

        hurtbox = new Hitbox(x, y, ancho, alto);

    }

    public void setPersonajeElegido(String personajeElegido) {
        this.personajeElegido = personajeElegido;

        if (personajeElegido.equals("Brakhan")) {
            spriteQuieto = new ImageIcon("Proyecto_final/resources/Sprites/Brakhan/BR_PELEA.png").getImage();
            spriteCorriendo = new ImageIcon("Proyecto_final/resources/Sprites/Brakhan/BR_CORRIENDO.png").getImage();
            spritePunio = new ImageIcon("Proyecto_final/resources/Sprites/Brakhan/BR_TIRA_PUNO.png").getImage();
            spriteSalto = new ImageIcon("Proyecto_final/resources/Sprites/Brakhan/BR_SALTO.png").getImage();
            spritePiso = new ImageIcon("Proyecto_final/resources/Sprites/Brakhan/BR_PISO.png").getImage();
            spriteGolpeado = spritePiso;
        }

        if (personajeElegido.equals("Yepstorm")) {
            spriteQuieto = new ImageIcon("Proyecto_final/resources/Sprites/Yepstorm/DY_PELEA.png").getImage();
            spriteCorriendo = new ImageIcon("Proyecto_final/resources/Sprites/Yepstorm/DY_CORRE.png").getImage();
            spritePunio = new ImageIcon("Proyecto_final/resources/Sprites/Yepstorm/DY_TIRA_PUNO.png").getImage();
            spriteSalto = new ImageIcon("Proyecto_final/resources/Sprites/Yepstorm/DY_TIRA_PATADA.png").getImage();
            spritePiso = new ImageIcon("Proyecto_final/resources/Sprites/Yepstorm/DY_PISO.png").getImage();
        }

        if (personajeElegido.equals("Connor")) {
            spriteQuieto = new ImageIcon("Proyecto_final/resources/Sprites/Connor/CONNOR_POSE.png").getImage();
            spriteCorriendo = new ImageIcon("Proyecto_final/resources/Sprites/Connor/CONNOR_POSE.png").getImage();
            spritePunio = new ImageIcon("Proyecto_final/resources/Sprites/Connor/CONNOR_PUNO.png").getImage();
            spriteSalto = new ImageIcon("Proyecto_final/resources/Sprites/Connor/CONNOR_SALTO.png").getImage();
            spritePiso = new ImageIcon("Proyecto_final/resources/Sprites/Connor/CONNOR_PISO.png").getImage();
            spriteGolpeado = new ImageIcon("Proyecto_final/resources/Sprites/Connor/CONNOR_GOLPEADO.png").getImage();
        }

        if (personajeElegido.equals("Brakhan")) {       //Ajustar sprites mas exactamente en sus hitboxes
            ajusteSpriteX = 15;
            ajusteSpriteY = -67;
            ajusteSpriteAncho = -30;
            ajusteSpriteAlto = 28;
        }

        if (personajeElegido.equals("Yepstorm")) {
            ajusteSpriteX = 0;   // mueve visualmente a la izquierda
            ajusteSpriteY = -2;    // baja los pies
            ajusteSpriteAncho = 0;
            ajusteSpriteAlto = 0;
        }

        if (personajeElegido.equals("Connor")) {
            ajusteSpriteX = -300;      // lo corre para que el aumento quede centrado
            ajusteSpriteY = -40;       // mantiene altura
            ajusteSpriteAncho = 600;   // lo vuelve más ancho
            ajusteSpriteAlto = 0;      // no altera altura
        }

        spriteActual = spriteQuieto;
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
            SoundManager.reproducir("Proyecto_final/resources/Sonidos/poder.wav");
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

        accionVisual = "quieto";

        contadorRespiracion++; // Avanza frames de respiración

        if (contadorRespiracion >= 30) { // Cada 30 frames cambia
            contadorRespiracion = 0;

            if (offsetRespiracion == 0) {
                offsetRespiracion = 4; // Baja un poquito
            } else {
                offsetRespiracion = 0; // Vuelve arriba
            }
        }

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
            SoundManager.reproducir("Proyecto_final/resources/Sonidos/saltar.wav");
            //direccion = "arriba";
        }

       /* if (moverAbajo) {

            if (y + alto < altoPantalla) {
                y += velocidad;
            }

            direccion = "abajo";
        } */

        if (moverIzquierda) {

            if (x > 0 ) {  //Limite izquierdo
                x -= velocidad;
            }

            direccion = "izquierda";
            accionVisual = "corriendo";
        }

        if (moverDerecha) {

            if (x + ancho < anchoPantalla) {
                x += velocidad;
            }

            direccion = "derecha";
            accionVisual = "corriendo";
        }

        velocidadY += gravedad;
        y += (int) velocidadY;

        if (y >= sueloY) {
            y = sueloY;
            velocidadY = 0;
            enSuelo = true;
        }

        //hurtbox.setPosicion(x, y); //Cuando el jugador se mueve, la hitbox debe perseguirlo

      /*if (botonAtacar) {
            atacar();
        }*/

        if (botonAtacar && (ataqueActual == null || !ataqueActual.isActivo()) && tiempoAccion <= 0) {
            atacar(); // Solo crea un golpe si no hay otro golpe activo
        }

        if (!botonAtacar && ataqueActual != null) {
            ataqueActual.desactivar(); // Al soltar la tecla, se apaga la hitbox de daño
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

        //VOLVEMOS A VALIDAR SUELO DESPUES DEL EMPUJE
        if (y >= sueloY) {
            y = sueloY;        // Obliga a quedar exactamente en el piso
            velocidadY = 0;    // Apaga caída
            empujeY = 0;       // Apaga empuje vertical
            enSuelo = true;    // Confirma que ya tocó suelo
        }

        if (tiempoGolpe > 0) {
            tiempoGolpe--;
        } else {
            mostrarGolpe = false;
        }

        if (tiempoAccion > 0) {
            tiempoAccion--; // Baja duración visual del golpe   //Ponemos este condicional para evitar que el sprite "corriendo" se elimine por el bloque comentado

            if (tiempoAccion <= 0 && ataqueActual != null) {
                ataqueActual.desactivar(); // Apaga daño al terminar ataque
            }
        }

       /* if (tiempoAccion > 0) {                                       //////////////////////////////////////////////////
            tiempoAccion--; // Baja duración visual del golpe
        } else {
            accionVisual = "quieto"; // Vuelve a pose normal

            if (ataqueActual != null) {
                ataqueActual.desactivar(); // Evita que la caja azul quede haciendo daño
            }
        }*/

        //Activar poder especial
        if (poderEspecial != null && poderEspecial.isActivo()) {
            poderEspecial.update();
        }

        hurtbox.setPosicion(x, y); // Actualiza la caja después de corregir suelo
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
                SoundManager.reproducir("Proyecto_final/resources/Sonidos/golpe.wav");
            }
        }
    }

    public void atacar() {

        accionVisual = "punio";
        tiempoAccion = 15;

        if (direccion.equals("derecha")) {

            ataqueActual = new Ataque(
                    10,
                    new Hitbox(x + ancho - 20, y + 70, 90, 80)            );
        }

        if (direccion.equals("izquierda")) {

            ataqueActual = new Ataque(10, new Hitbox(x - 70, y + 70, 90, 80)            );
        }

        if (direccion.equals("arriba")) {

            ataqueActual = new Ataque(10, new Hitbox(x + 10, y - 40, 30, 40));
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

        // CPU: a veces salta, pero sin mover Y manualmente
        if (y >= sueloY - 2 && Math.random() < 0.02) {
            velocidadY = fuerzaSalto; // Salto real de la CPU
            enSuelo = false;          // Ya no está en el piso
        }

        // Física vertical de la CPU: salto + gravedad
        velocidadY += gravedad;      // La gravedad afecta el salto
        y += (int) velocidadY;       // Mueve la CPU en Y

        if (y >= sueloY) {
            y = sueloY;              // Vuelve al suelo exacto
            velocidadY = 0;          // Frena caída
            enSuelo = true;          // Puede saltar otra vez
        }

       /* if (Math.abs(distanciaY) > 40) {
            if (distanciaY > 0) {
                y += velocidad;  //Hace que la cpu flote y caiga desalineada, por eso se eliminó
                direccion = "abajo";
            } else {
                y -= velocidad;
                direccion = "arriba";
            }
        } */

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

        if (poderEspecial != null && poderEspecial.isActivo()) {
            poderEspecial.update();
        }

        hurtbox.setPosicion(x, y);
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

        Image spriteActual = spriteQuieto; // Por defecto está quieto  /////////////////////////

        if (vida <= 0) {        /// ///////
            spriteActual = spritePiso;
        } else if (mostrarGolpe && spriteGolpeado != null) {
            spriteActual = spriteGolpeado;
        } else if (!enSuelo && spriteSalto != null) {
            spriteActual = spriteSalto;
        } else if (accionVisual.equals("punio") && tiempoAccion > 0) {
            spriteActual = spritePunio;
        } else if (accionVisual.equals("corriendo")) {
            spriteActual = spriteCorriendo;
        }

        Graphics2D g2 = (Graphics2D) g;  /////////////////////////////

        int spriteAncho = 500; // Tamaño visual del personaje
        int spriteAlto = 700;  // Altura visual del personaje

        //int spriteX = x + (ancho / 2) - (spriteAncho / 2); // Centra sprite sobre la caja roja
        //int spriteY = y + alto - spriteAlto; // Pega los pies del sprite al piso de la caja

        int spriteX = x - 130 + ajusteSpriteX;
        int spriteY = y - 160 + ajusteSpriteY + offsetRespiracion;
        int spriteW = spriteAncho + ajusteSpriteAncho;
        int spriteH = spriteAlto + ajusteSpriteAlto;

        spriteY += 25 + offsetRespiracion; // Baja visualmente el sprite sin mover la hitbox ahora con efecto de respiración

        if (spriteActual != null) {

            if (direccion.equals("izquierda")) {
                g2.drawImage(spriteActual, spriteX + spriteW, spriteY, -spriteW, spriteH, null);
            } else {
                g2.drawImage(spriteActual, spriteX, spriteY, spriteW, spriteH, null);
            }

        } else {
            g.fillRect(x, y, ancho, alto);
        }

        g.setColor(Color.RED);   //color de hurtbox

        g.drawRect(x, y, ancho, alto); //Hitbox para colisiones, poderes y empujes

        if (ataqueActual != null && ataqueActual.isActivo()) {

            Rectangle r = ataqueActual.getHitbox().getBounds();

            g.setColor(Color.BLUE);

            g.drawRect(r.x, r.y, r.width, r.height);
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

        if (numeroJugador == 1) {
            direccion = "derecha"; // Jugador 1 mira hacia el rival
        } else {
            direccion = "izquierda"; // Jugador 2/CPU mira hacia el rival
        }

        mostrarGolpe = false;
        tiempoGolpe = 0;

        empujeX = 0;
        empujeY = 0;

        ataqueActual = null;
        poderEspecial = null;

        hurtbox.setPosicion(x, y);
    }

}
