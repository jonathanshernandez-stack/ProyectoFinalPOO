package Proyecto_final.controlador;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputManager implements KeyListener {  //Escuchará los eventos, ya que las teclas de comantos no son condiciones

    //Variables para jugador
    public boolean arriba;
    public boolean abajo;
    public boolean izquierda;
    public boolean derecha;
    public boolean enter;
    public boolean atacar;

    //nuevas variables para un segundo jugador
    public boolean arriba2;
    public boolean abajo2;
    public boolean izquierda2;
    public boolean derecha2;
    public boolean atacar2;

    public boolean poder;
    public boolean poder2;

    public boolean tecla1;
    public boolean tecla2;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int codigo = e.getKeyCode();  //Convierte la tecla en un codigo numerico

        if (codigo == KeyEvent.VK_1) {
            tecla1 = true;
        }

        if (codigo == KeyEvent.VK_2) {
            tecla2 = true;
        }

        if (codigo == KeyEvent.VK_ENTER) {
            enter = true;
        }

        if (codigo == KeyEvent.VK_W) {
            arriba = true;
        }

        if (codigo == KeyEvent.VK_S) {
            abajo = true;
        }

        if (codigo == KeyEvent.VK_A) {
            izquierda = true;
        }

        if (codigo == KeyEvent.VK_D) {
            derecha = true;
        }

        if (codigo == KeyEvent.VK_SPACE) {
            atacar = true;
        }

        //Teclas para manejo de segundo jugador
        if (codigo == KeyEvent.VK_UP) {
            arriba2 = true;
        }

        if (codigo == KeyEvent.VK_DOWN) {
            abajo2 = true;
        }

        if (codigo == KeyEvent.VK_LEFT) {
            izquierda2 = true;
        }

        if (codigo == KeyEvent.VK_RIGHT) {
            derecha2 = true;
        }

        if (codigo == KeyEvent.VK_P) {
            atacar2 = true;
        }

        //poderes

        if (codigo == KeyEvent.VK_Q) {
            poder = true;
        }

        if (codigo == KeyEvent.VK_O) {
            poder2 = true;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        int codigo = e.getKeyCode();

        if (codigo == KeyEvent.VK_1) {
            tecla1 = false;
        }

        if (codigo == KeyEvent.VK_2) {
            tecla2 = false;
        }

        if (codigo == KeyEvent.VK_ENTER) {
            enter = false;
        }

        if (codigo == KeyEvent.VK_W) {
            arriba = false;
        }

        if (codigo == KeyEvent.VK_S) {
            abajo = false;
        }

        if (codigo == KeyEvent.VK_A) {
            izquierda = false;
        }

        if (codigo == KeyEvent.VK_D) {
            derecha = false;
        }

        if (codigo == KeyEvent.VK_SPACE) {
            atacar = false;
        }

        /// //
        if (codigo == KeyEvent.VK_UP) {
            arriba2 = false;
        }

        if (codigo == KeyEvent.VK_DOWN) {
            abajo2 = false;
        }

        if (codigo == KeyEvent.VK_LEFT) {
            izquierda2 = false;
        }

        if (codigo == KeyEvent.VK_RIGHT) {
            derecha2 = false;
        }

        if (codigo == KeyEvent.VK_P) {
            atacar2 = false;
        }

        if (codigo == KeyEvent.VK_Q) {
            poder = false;
        }

        if (codigo == KeyEvent.VK_O) {
            poder2 = false;
        }

    }
}
