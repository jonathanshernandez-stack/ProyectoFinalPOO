package Proyecto_final.vista;

import java.awt.*;

public class Menu {

    public void draw(Graphics g) {

        g.setColor(Color.BLACK);

        g.fillRect(0, 0, 1500, 1000);

        g.setColor(Color.RED);

        g.setFont(new Font("Arial", Font.BOLD, 60));

        g.drawString("LEGENDS", 610, 200);

        g.setColor(Color.WHITE);

        g.setFont(new Font("Arial", Font.PLAIN, 30));

        g.drawString("PRESIONA ENTER", 610, 300);

        g.setFont(new Font("Arial", Font.PLAIN, 20));

        g.drawString("Proyecto final de poo", 650, 800);
    }

}
