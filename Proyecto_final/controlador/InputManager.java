package Proyecto_final.controlador;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputManager implements KeyListener {  //Escuchará los eventos, ya que las teclas de comantos no son condiciones

    public boolean arriba;
    public boolean abajo;
    public boolean izquierda;
    public boolean derecha;
    public boolean enter;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int codigo = e.getKeyCode();  //Convierte la tecla en un codigo numerico

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

    }

    @Override
    public void keyReleased(KeyEvent e) {

        int codigo = e.getKeyCode();

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

    }
}
