package Modelo;

/**
 * Gestiona la energia y el cooldown de la habilidad especial de un
 * personaje.
 *
 * Principio aplicado: SRP. Antes esta logica (consumir energia, regenerarla,
 * controlar cooldown) estaba mezclada dentro de Personaje junto con
 * inventario y estados. Ahora es una responsabilidad propia y reutilizable.
 */
public class GestorEnergia {

    private final int energiaMaxima;
    private int energia;
    private int cooldownHabilidad = 0;

    public GestorEnergia(int energiaMaxima) {
        this.energiaMaxima = energiaMaxima;
        this.energia = energiaMaxima;
    }

    public boolean habilidadDisponible() {
        return cooldownHabilidad == 0;
    }

    public void consumir(int cantidad, String nombrePersonaje) throws SinEnergiaException {
        if (energia < cantidad) {
            throw new SinEnergiaException(nombrePersonaje + " no tiene suficiente energia. "
                    + "Necesita " + cantidad + " pero solo tiene " + energia + ".");
        }
        energia -= cantidad;
    }

    public void activarCooldown(int turnos) {
        this.cooldownHabilidad = turnos;
    }

    public void reducirCooldown() {
        if (cooldownHabilidad > 0) {
            cooldownHabilidad--;
        }
    }

    /** Regenera un porcentaje de energia por turno (10 %). */
    public void regenerar() {
        int regen = Math.max(1, energiaMaxima / 10);
        energia = Math.min(energiaMaxima, energia + regen);
    }

    public void restaurarAlMaximo() {
        energia = energiaMaxima;
    }

    public int getEnergia() {
        return energia;
    }

    public int getEnergiaMaxima() {
        return energiaMaxima;
    }

    public int getCooldownHabilidad() {
        return cooldownHabilidad;
    }
}