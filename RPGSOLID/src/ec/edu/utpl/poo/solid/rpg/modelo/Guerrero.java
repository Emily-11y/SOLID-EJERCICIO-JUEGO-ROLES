package ec.edu.utpl.poo.solid.rpg.modelo;

import ec.edu.utpl.poo.solid.rpg.interfaces.IEfectoHabilidad;
import ec.edu.utpl.poo.solid.rpg.efectos.EfectoSoloMensaje;
import ec.edu.utpl.poo.solid.rpg.estados.EstadoAumentarFuerza;

/**
 * Personaje de tipo Guerrero.
 * Habilidad especial: "Golpe Devastador" — buff de fuerza +20 por 3 turnos.
 *
 * SOLID - LSP: puede sustituir a Personaje en cualquier parte del sistema.
 * SOLID - OCP: agrega comportamiento sin modificar Personaje.
 * SOLID - SRP: solo define las reglas propias del Guerrero.
 */
public class Guerrero extends Personaje {

    private static final int BONUS_ATAQUE_NIVEL  = 5;
    private static final int BONUS_DEFENSA_NIVEL = 2;
    private static final int COSTO_ENERGIA       = 30;
    private static final int COOLDOWN_TURNOS     = 3;

    private int fuerza;
    private int armadura;

    public Guerrero(String nombre, int puntosVida, int nivelExperiencia,
                    int fuerza, int armadura) {
        super(nombre, puntosVida, nivelExperiencia, 100);
        this.fuerza   = fuerza;
        this.armadura = armadura;
    }

    @Override
    public int atacar()  { return fuerza   + (nivelExperiencia * BONUS_ATAQUE_NIVEL); }

    @Override
    public int defender() { return armadura + (nivelExperiencia * BONUS_DEFENSA_NIVEL); }

    /**
     * SOLID - OCP: retorna IEfectoHabilidad en vez de String con prefijo.
     * El Controlador no necesita conocer qué tipo de efecto es.
     */
    @Override
    public IEfectoHabilidad usarHabilidadEspecial() throws SinEnergiaException {
        if (!habilidadDisponible()) {
            return new EfectoSoloMensaje("[" + nombre + "] Golpe Devastador en cooldown ("
                    + getCooldownHabilidad() + " turnos restantes).");
        }
        consumirEnergia(COSTO_ENERGIA);
        setCooldownHabilidad(COOLDOWN_TURNOS);
        agregarEstado(new EstadoAumentarFuerza(20, 3));
        return new EfectoSoloMensaje("[" + nombre + "] usa ¡GOLPE DEVASTADOR! "
                + "Fuerza aumentada +20 por 3 turnos. (Energía restante: " + energia + ")");
    }

    /**
     * SOLID - OCP: equipamiento por defecto definido en la subclase,
     * sin que el ejecutor necesite usar instanceof.
     */
    @Override
    public void equiparDefault() {
        equiparArma(new Arma("Espada de Acero", "Espada estándar de guerrero", 10));
        equiparArmadura(new Armadura("Escudo de Hierro", "Protección básica", 8));
    }

    public int getFuerza()   { return fuerza; }
    public int getArmadura() { return armadura; }

    public void setFuerza(int fuerza)     { if (fuerza   > 0)  this.fuerza   = fuerza; }
    public void setArmadura(int armadura) { if (armadura >= 0) this.armadura = armadura; }

    @Override
    public String toString() {
        return "----- GUERRERO -----\n"
                + super.toString()
                + "\nFuerza: " + fuerza
                + "\nArmadura: " + armadura;
    }
}
