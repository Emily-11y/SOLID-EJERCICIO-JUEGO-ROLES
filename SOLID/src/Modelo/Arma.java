package Modelo;

/**
 * Representa un arma equipable que incrementa el ataque del personaje.
 *
 * Principio aplicado: LSP. Arma puede usarse en cualquier lugar donde se
 * espere un Objeto sin alterar el comportamiento esperado por el cliente
 * (Inventario solo invoca getNombre()/getDescripcion() via Objeto).
 */
public class Arma extends Objeto {
    private final int bonusAtaque;

    public Arma(String nombre, String descripcion, int bonusAtaque) {
        super(nombre, descripcion);
        this.bonusAtaque = bonusAtaque;
    }

    public int getBonusAtaque() {
        return bonusAtaque;
    }

    @Override
    public String toString() {
        return super.toString() + " [+Ataque: " + bonusAtaque + "]";
    }
}
