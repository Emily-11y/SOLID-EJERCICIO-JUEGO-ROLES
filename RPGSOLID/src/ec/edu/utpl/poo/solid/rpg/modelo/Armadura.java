package ec.edu.utpl.poo.solid.rpg.modelo;

import ec.edu.utpl.poo.solid.rpg.interfaces.IEquipable;

/**
 * Representa una armadura equipable que incrementa la defensa del personaje.
 *
 * SOLID - OCP: extiende Objeto e implementa IEquipable sin modificar
 * ninguna clase existente.
 */
public class Armadura extends Objeto implements IEquipable {
    private int bonusDefensa;

    public Armadura(String nombre, String descripcion, int bonusDefensa) {
        super(nombre, descripcion);
        this.bonusDefensa = bonusDefensa;
    }

    @Override public int getBonusAtaque()  { return 0; }
    @Override public int getBonusDefensa() { return bonusDefensa; }

    @Override
    public String toString() {
        return super.toString() + " [+DEF: " + bonusDefensa + "]";
    }
}
