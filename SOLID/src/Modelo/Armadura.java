package Modelo;

/**
 * Representa una armadura equipable que incrementa la defensa del personaje.
 */
public class Armadura extends Objeto {
    private final int bonusDefensa;

    public Armadura(String nombre, String descripcion, int bonusDefensa) {
        super(nombre, descripcion);
        this.bonusDefensa = bonusDefensa;
    }

    public int getBonusDefensa() {
        return bonusDefensa;
    }

    @Override
    public String toString() {
        return super.toString() + " [+Defensa: " + bonusDefensa + "]";
    }
}
