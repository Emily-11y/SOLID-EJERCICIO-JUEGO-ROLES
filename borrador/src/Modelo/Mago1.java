package Modelo;

import Modelo.habilidades.HabilidadVentiscaGelida;

/**
 * Representa un personaje de tipo Mago.
 * Habilidad especial: "Ventisca Gelida" (inyectada via constructor).
 */
public class Mago1 extends Personaje {

    private static final int BONUS_ATAQUE = 6;
    private static final int BONUS_DEFENSA = 3;
    private static final int ENERGIA_MAXIMA = 120;

    private int poderMagico;
    private int mana;

    public Mago1(String nombre, int puntosVida, int nivelExperiencia,
                  int poderMagico, int mana) {
        super(nombre, puntosVida, nivelExperiencia, ENERGIA_MAXIMA, new HabilidadVentiscaGelida());
        this.poderMagico = poderMagico;
        this.mana = mana;
    }

    @Override
    public int atacar() {
        return poderMagico + (nivelExperiencia * BONUS_ATAQUE);
    }

    @Override
    public int defender() {
        return mana + (nivelExperiencia * BONUS_DEFENSA);
    }

    public int getPoderMagico() {
        return poderMagico;
    }

    public int getMana() {
        return mana;
    }

    public void setPoderMagico(int poderMagico) {
        if (poderMagico > 0) {
            this.poderMagico = poderMagico;
        }
    }

    public void setMana(int mana) {
        if (mana >= 0) {
            this.mana = mana;
        }
    }

    @Override
    public String toString() {
        return "----- MAGO -----\n"
                + super.toString()
                + "\nPoder magico: " + poderMagico
                + "\nMana: " + mana;
    }
}

