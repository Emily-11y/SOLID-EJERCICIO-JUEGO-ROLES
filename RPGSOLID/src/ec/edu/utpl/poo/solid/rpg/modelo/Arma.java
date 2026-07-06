package ec.edu.utpl.poo.solid.rpg.modelo;

import ec.edu.utpl.poo.solid.rpg.interfaces.IEquipable;

/**
 * Representa un arma equipable que incrementa el ataque del personaje.
 *
 * SOLID - OCP: extiende Objeto e implementa IEquipable sin modificar
 * ninguna clase existente.
 */
public class Arma extends Objeto implements IEquipable {
    private int bonusAtaque;

    public Arma(String nombre, String descripcion, int bonusAtaque) {
        super(nombre, descripcion);
        this.bonusAtaque = bonusAtaque;
    }

    @Override public int getBonusAtaque()  { return bonusAtaque; }
    @Override public int getBonusDefensa() { return 0; }

    @Override
    public String toString() {
        return super.toString() + " [+ATQ: " + bonusAtaque + "]";
    }
}
