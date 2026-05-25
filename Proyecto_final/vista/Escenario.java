package Proyecto_final.vista;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Escenario {

    // Imagen del fondo
    private BufferedImage fondo;

    // Constructor
    public Escenario() {

        try {

            fondo = ImageIO.read(
                    new File("/Users/Jonathan/Documents/poo/Proyecto_final/resources/escenarios/fondo.png,")
            );

        } catch (IOException e) {

            e.printStackTrace();

        }
    }

    // Método para dibujar
    public void draw(Graphics g) {

        g.drawImage(fondo, 0, 0, 1500, 1000, null);

    }
}
