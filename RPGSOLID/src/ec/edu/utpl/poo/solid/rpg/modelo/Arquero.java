package ec.edu.utpl.poo.solid.rpg.modelo;

import ec.edu.utpl.poo.solid.rpg.interfaces.IEfectoHabilidad;
import ec.edu.utpl.poo.solid.rpg.efectos.EfectoSoloMensaje;
import ec.edu.utpl.poo.solid.rpg.efectos.EfectoEnvenenarRival;

/**
 * Personaje de tipo Arquero.
 * Habilidad especial: "Flecha Envenenada" — envenena al rival 3 turnos.
 *
 * SOLID - LSP / OCP / SRP: ver Guerrero.java para explicación.
 */
public class Arquero extends Personaje {

    private static final int BONUS_ATAQUE_NIVEL  = 4;
    private static final int BONUS_DEFENSA_NIVEL = 4;
    private static final int COSTO_ENERGIA       = 25;
    private static final int COOLDOWN_TURNOS     = 2;

    private int precision;
    private int agilidad;

    public Arquero(String nombre, int puntosVida, int nivelExperiencia,
                   int precision, int agilidad) {
        super(nombre, puntosVida, nivelExperiencia, 90);
        this.precision = precision;
        this.agilidad  = agilidad;
    }

    @Override
    public int atacar()  { return precision + (nivelExperiencia * BONUS_ATAQUE_NIVEL); }

    @Override
    public int defender() { return agilidad  + (nivelExperiencia * BONUS_DEFENSA_NIVEL); }

    /**
     * SOLID - OCP: retorna EfectoEnvenenarRival (IEfectoHabilidad).
     */
    @Override
    public IEfectoHabilidad usarHabilidadEspecial() throws SinEnergiaException {
        if (!habilidadDisponible()) {
            return new EfectoSoloMensaje("[" + nombre + "] Flecha Envenenada en cooldown ("
                    + getCooldownHabilidad() + " turnos restantes).");
        }
        consumirEnergia(COSTO_ENERGIA);
        setCooldownHabilidad(COOLDOWN_TURNOS);
        return new EfectoEnvenenarRival(
                "[" + nombre + "] dispara ¡FLECHA ENVENENADA! "
                + "El rival recibirá 8 de daño por veneno durante 3 turnos. "
                + "(Energía restante: " + energia + ")",
                8, 3
        );
    }

    @Override
    public void equiparDefault() {
        equiparArma(new Arma("Arco de Madera", "Arco confiable", 8));
        equiparArmadura(new Armadura("Chaleco de Cuero", "Ligero y ágil", 5));
    }

    public int getPrecision() { return precision; }
    public int getAgilidad()  { return agilidad;  }

    public void setPrecision(int precision) { if (precision > 0) this.precision = precision; }
    public void setAgilidad(int agilidad)   { if (agilidad >= 0) this.agilidad  = agilidad; }

    @Override
    public String toString() {
        return "----- ARQUERO -----\n"
                + super.toString()
                + "\nPrecisión: " + precision
                + "\nAgilidad: " + agilidad;
    }
}
