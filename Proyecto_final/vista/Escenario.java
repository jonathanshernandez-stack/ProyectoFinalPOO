package Proyecto_final.vista;

import java.awt.*;

public class Escenario {

    public void draw(Graphics g) {

        // CIELO

        g.setColor(new Color(15, 15, 30));

        g.fillRect(0, 0, 1500, 1000);

        // LUNA

        g.setColor(Color.WHITE);

        g.fillOval(600, 50, 100, 100);

        // EDIFICIOS

        g.setColor(new Color(20, 20, 20));

        g.fillRect(50, 200, 100, 300);

        g.fillRect(200, 150, 120, 350);

        g.fillRect(400, 180, 150, 320);

        g.fillRect(620, 220, 130, 280);

        // PISO

        g.setColor(new Color(40, 40, 40));

        g.fillRect(0, 500, 800, 100);

        // LINEA DEL PISO

        g.setColor(Color.YELLOW);

        g.fillRect(0, 495, 800, 5);
    }
}
