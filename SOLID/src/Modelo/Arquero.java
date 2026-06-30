package Modelo;

import Modelo.habilidades.HabilidadFlechaEnvenenada;

/**
 * Representa un personaje de tipo Arquero.
 * Habilidad especial: "Flecha Envenenada" (inyectada via constructor).
 */
public class Arquero extends Personaje {

    private static final int BONUS_ATAQUE = 4;
    private static final int BONUS_DEFENSA = 4;
    private static final int ENERGIA_MAXIMA = 90;

    private int precision;
    private int agilidad;

    public Arquero(String nombre, int puntosVida, int nivelExperiencia,
                    int precision, int agilidad) {
        super(nombre, puntosVida, nivelExperiencia, ENERGIA_MAXIMA, new HabilidadFlechaEnvenenada());
        this.precision = precision;
        this.agilidad = agilidad;
    }

    @Override
    public int atacar() {
        return precision + (nivelExperiencia * BONUS_ATAQUE);
    }

    @Override
    public int defender() {
        return agilidad + (nivelExperiencia * BONUS_DEFENSA);
    }

    public int getPrecision() {
        return precision;
    }

    public int getAgilidad() {
        return agilidad;
    }

    public void setPrecision(int precision) {
        if (precision > 0) {
            this.precision = precision;
        }
    }

    public void setAgilidad(int agilidad) {
        if (agilidad >= 0) {
            this.agilidad = agilidad;
        }
    }

    @Override
    public String toString() {
        return "----- ARQUERO -----\n"
                + super.toString()
                + "\nPrecision: " + precision
                + "\nAgilidad: " + agilidad;
    }
}
