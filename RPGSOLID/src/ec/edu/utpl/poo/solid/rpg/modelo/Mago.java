package ec.edu.utpl.poo.solid.rpg.modelo;

import ec.edu.utpl.poo.solid.rpg.interfaces.IEfectoHabilidad;
import ec.edu.utpl.poo.solid.rpg.efectos.EfectoSoloMensaje;
import ec.edu.utpl.poo.solid.rpg.efectos.EfectoCongelarRival;

/**
 * Personaje de tipo Mago.
 * Habilidad especial: "Ventisca Gélida" — congela al rival 1 turno.
 *
 * SOLID - LSP / OCP / SRP: ver Guerrero.java para explicación.
 */
public class Mago extends Personaje {

    private static final int BONUS_ATAQUE_NIVEL  = 6;
    private static final int BONUS_DEFENSA_NIVEL = 3;
    private static final int COSTO_ENERGIA       = 40;
    private static final int COOLDOWN_TURNOS     = 2;

    private int poderMagico;
    private int mana;

    public Mago(String nombre, int puntosVida, int nivelExperiencia,
                int poderMagico, int mana) {
        super(nombre, puntosVida, nivelExperiencia, 120);
        this.poderMagico = poderMagico;
        this.mana        = mana;
    }

    @Override
    public int atacar()  { return poderMagico + (nivelExperiencia * BONUS_ATAQUE_NIVEL); }

    @Override
    public int defender() { return mana       + (nivelExperiencia * BONUS_DEFENSA_NIVEL); }

    /**
     * SOLID - OCP: retorna EfectoCongelarRival (IEfectoHabilidad).
     * El controlador llama aplicarSobreObjetivo(rival) sin analizar Strings.
     */
    @Override
    public IEfectoHabilidad usarHabilidadEspecial() throws SinEnergiaException {
        if (!habilidadDisponible()) {
            return new EfectoSoloMensaje("[" + nombre + "] Ventisca Gélida en cooldown ("
                    + getCooldownHabilidad() + " turnos restantes).");
        }
        consumirEnergia(COSTO_ENERGIA);
        setCooldownHabilidad(COOLDOWN_TURNOS);
        return new EfectoCongelarRival(
                "[" + nombre + "] lanza ¡VENTISCA GÉLIDA! "
                + "El rival quedará congelado 1 turno. (Energía restante: " + energia + ")",
                1
        );
    }

    @Override
    public void equiparDefault() {
        equiparArma(new Arma("Vara Mágica", "Canaliza la magia", 12));
        equiparArmadura(new Armadura("Túnica Rúnica", "Repele hechizos", 6));
    }

    public int getPoderMagico() { return poderMagico; }
    public int getMana()        { return mana; }

    public void setPoderMagico(int poderMagico) { if (poderMagico > 0) this.poderMagico = poderMagico; }
    public void setMana(int mana)               { if (mana >= 0)       this.mana        = mana; }

    @Override
    public String toString() {
        return "----- MAGO -----\n"
                + super.toString()
                + "\nPoder mágico: " + poderMagico
                + "\nManá: " + mana;
    }
}
