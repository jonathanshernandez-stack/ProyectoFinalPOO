package Proyecto_final.modelo;

public class RegistroRanking {

    private String nombre;
    private int puntaje;
    private int tiempo;
    private String modo;

    public RegistroRanking(String nombre, int puntaje, int tiempo, String modo) {
        this.nombre = nombre;
        this.puntaje = puntaje;
        this.tiempo = tiempo;
        this.modo = modo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public int getTiempo() {
        return tiempo;
    }

    public String getModo() {
        return modo;
    }

    public String convertirATexto() {
        return nombre + ";" + puntaje + ";" + tiempo + ";" + modo;
    }

    public static RegistroRanking crearDesdeTexto(String linea) {
        String[] partes = linea.split(";");

        String nombre = partes[0];
        int puntaje = Integer.parseInt(partes[1]);
        int tiempo = Integer.parseInt(partes[2]);
        String modo = partes[3];

        return new RegistroRanking(nombre, puntaje, tiempo, modo);
    }

}
