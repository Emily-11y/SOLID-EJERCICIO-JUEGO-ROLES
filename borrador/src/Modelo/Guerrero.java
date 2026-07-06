package Modelo;

import Modelo.habilidades.HabilidadGolpeDevastador;

/**
 * Representa un personaje de tipo Guerrero.
 * Habilidad especial: "Golpe Devastador" (inyectada via constructor).
 */
public class Guerrero extends Personaje {

    private static final int BONUS_ATAQUE = 5;
    private static final int BONUS_DEFENSA = 2;
    private static final int ENERGIA_MAXIMA = 100;

    private int fuerza;
    private int armadura;

    public Guerrero(String nombre, int puntosVida, int nivelExperiencia,
                     int fuerza, int armadura) {
        super(nombre, puntosVida, nivelExperiencia, ENERGIA_MAXIMA, new HabilidadGolpeDevastador());
        this.fuerza = fuerza;
        this.armadura = armadura;
    }

    @Override
    public int atacar() {
        return fuerza + (nivelExperiencia * BONUS_ATAQUE);
    }

    @Override
    public int defender() {
        return armadura + (nivelExperiencia * BONUS_DEFENSA);
    }

    public int getFuerza() {
        return fuerza;
    }

    public int getArmadura() {
        return armadura;
    }

    public void setFuerza(int fuerza) {
        if (fuerza > 0) {
            this.fuerza = fuerza;
        }
    }

    public void setArmadura(int armadura) {
        if (armadura >= 0) {
            this.armadura = armadura;
        }
    }

    @Override
    public String toString() {
        return "----- GUERRERO -----\n"
                + super.toString()
                + "\nFuerza: " + fuerza
                + "\nArmadura: " + armadura;
    }
}

