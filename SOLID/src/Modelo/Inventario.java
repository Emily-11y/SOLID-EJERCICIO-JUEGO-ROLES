package Modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona el inventario y el equipamiento de un personaje.
 *
 * Principio aplicado: SRP (Single Responsibility Principle).
 * En el diseño original, la clase Personaje mezclaba en una sola clase: datos
 * basicos, inventario/equipamiento, estados alterados y energia/cooldown
 * (una "clase Dios" con multiples razones para cambiar). Aqui se extrae la
 * responsabilidad de inventario a su propia clase: si mañana cambia la regla
 * de equipamiento (p.ej. permitir dos armas), solo se modifica esta clase.
 */
public class Inventario {

    private final List<Objeto> objetos = new ArrayList<>();
    private Arma armaEquipada;
    private Armadura armaduraEquipada;
    private final String nombrePropietario;

    public Inventario(String nombrePropietario) {
        this.nombrePropietario = nombrePropietario;
    }

    public void agregar(Objeto objeto) {
        objetos.add(objeto);
        System.out.println("  [Inventario] " + nombrePropietario + " obtuvo: " + objeto.getNombre());
    }

    public void equipar(Arma arma) {
        if (!objetos.contains(arma)) {
            agregar(arma);
        }
        this.armaEquipada = arma;
        System.out.println("  [Equipo] " + nombrePropietario + " equipo arma: "
                + arma.getNombre() + " (+" + arma.getBonusAtaque() + " ATQ)");
    }

    public void equipar(Armadura armadura) {
        if (!objetos.contains(armadura)) {
            agregar(armadura);
        }
        this.armaduraEquipada = armadura;
        System.out.println("  [Equipo] " + nombrePropietario + " equipo armadura: "
                + armadura.getNombre() + " (+" + armadura.getBonusDefensa() + " DEF)");
    }

    public int getBonusAtaqueEquipo() {
        return armaEquipada != null ? armaEquipada.getBonusAtaque() : 0;
    }

    public int getBonusDefensaEquipo() {
        return armaduraEquipada != null ? armaduraEquipada.getBonusDefensa() : 0;
    }

    public Arma getArmaEquipada() {
        return armaEquipada;
    }

    public Armadura getArmaduraEquipada() {
        return armaduraEquipada;
    }

    public List<Objeto> getObjetos() {
        return objetos;
    }
}
