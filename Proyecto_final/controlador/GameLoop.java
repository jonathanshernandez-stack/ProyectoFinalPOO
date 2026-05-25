package Proyecto_final.controlador;
/*
Corazón del juego
1. Actualiza posiciones
2. Detecta colisiones
3. Actualiza ataques
4. Actualiza enemigos
5. Luego dibuja todo*/

//Ya que el juego nunca se detiene, necesitamos contener motor del juego dentro de un while o do while

import Proyecto_final.vista.PanelJuego;

public class GameLoop implements Runnable{  //Implements Runnable significa que puede ejecutar en un hilo

    PanelJuego panelJuego; //Ahora el loop conoce el panel

    public GameLoop (PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
    }

    @Override
    public void run() {  //el corazon del juego
        while (true) {

            panelJuego.update(); //Actualiza el estado (movimiento, ataques, fisica, etc..)

            panelJuego.repaint(); //Le dice a swing "Vuelve a dibujar el panel"

            try {

                Thread.sleep(16);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
