package Proyecto_final.controlador;

import Proyecto_final.modelo.RegistroRanking;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GestorRanking {

    private final String rutaArchivo = "Proyecto_final/controlador/ranking.txt";

    public void guardarRegistro(RegistroRanking registro) {
        try {
            FileWriter escritor = new FileWriter(rutaArchivo, true);
            escritor.write(registro.convertirATexto() + "\n");
            escritor.close();
        } catch (IOException e) {
            System.out.println("Error guardando ranking: " + e.getMessage());
        }
    }

    public ArrayList<RegistroRanking> leerRanking() {
        ArrayList<RegistroRanking> ranking = new ArrayList<>();

        try {
            File archivo = new File(rutaArchivo);

            if (!archivo.exists()) {
                return ranking;
            }

            BufferedReader lector = new BufferedReader(new FileReader(archivo));

            String linea;

            while ((linea = lector.readLine()) != null) {
                ranking.add(RegistroRanking.crearDesdeTexto(linea));
            }

            lector.close();

        } catch (IOException e) {
            System.out.println("Error leyendo ranking: " + e.getMessage());
        }

        Collections.sort(ranking, new Comparator<RegistroRanking>() {
            @Override
            public int compare(RegistroRanking r1, RegistroRanking r2) {
                return r2.getPuntaje() - r1.getPuntaje();
            }
        });

        return ranking;
    }

}
/*
FileWriter(rutaArchivo, true) significa: agregue al archivo sin borrar lo anterior.
BufferedReader lee línea por línea.
Collections.sort ordena.
r2.getPuntaje() - r1.getPuntaje() ordena de mayor a menor.
 */