package ec.edu.utpl.poo.solid.rpg.interfaces;

/**
 * SOLID - ISP (Interface Segregation Principle):
 * Contrato mínimo que debe cumplir cualquier objeto equipable.
 * Permite que el sistema de equipamiento sea extensible (OCP) sin
 * modificar la clase Personaje.
 */
public interface IEquipable {
    /** Bonus de ataque que aporta este objeto al equiparse (0 si no aplica). */
    int getBonusAtaque();

    /** Bonus de defensa que aporta este objeto al equiparse (0 si no aplica). */
    int getBonusDefensa();
}
