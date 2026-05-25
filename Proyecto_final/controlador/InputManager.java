package Proyecto_final.controlador;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputManager implements KeyListener {  //Escuchará los eventos, ya que las teclas de comantos no son condiciones

    public boolean arriba;
    public boolean abajo;
    public boolean izquierda;
    public boolean derecha;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int codigo = e.getKeyCode();  //Codigo hace referencia a la orden por teclado

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

    }
}
