package Proyecto_final.controlador;

import javax.sound.sampled.*;
import java.io.File;

public class SoundManager {

    private static Clip musica;

    // SONIDOS NORMALES
    public static void reproducir(String ruta) {

        try {

            AudioInputStream audio =
                    AudioSystem.getAudioInputStream(new File(ruta));

            Clip clip = AudioSystem.getClip();

            clip.open(audio);

            clip.start();

        } catch (Exception e) {

            System.out.println("Error sonido: " + ruta);
        }
    }

    // MUSICA EN LOOP
    public static void reproducirMusica(String ruta) {

        try {

            AudioInputStream audio =
                    AudioSystem.getAudioInputStream(new File(ruta));

            musica = AudioSystem.getClip();

            musica.open(audio);

            musica.loop(Clip.LOOP_CONTINUOUSLY);

            musica.start();

        } catch (Exception e) {

            System.out.println("Error música: " + ruta);
        }
    }

    // DETENER MUSICA
    public static void detenerMusica() {

        if (musica != null) {

            musica.stop();
        }
    }
}