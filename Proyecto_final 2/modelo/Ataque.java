package Proyecto_final.modelo;

public class Ataque {

    private int dano;
    private Hitbox hitbox;
    public boolean activo;

    public Ataque(int dano, Hitbox hitbox) {
        this.dano = dano;
        this.hitbox = hitbox;
    }

    public void activar() {

        activo = true;
    }

    public void desactivar() {

        activo = false;
    }

    public boolean isActivo() {

        return activo;
    }

    public int getDano() {

        return dano;
    }

    public Hitbox getHitbox() {

        return hitbox;
    }

}
