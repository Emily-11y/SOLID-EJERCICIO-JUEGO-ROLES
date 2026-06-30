package Modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona la coleccion de estados alterados (buffs/debuffs) activos sobre
 * un personaje.
 *
 * Principio aplicado: SRP. Esta logica (agregar, procesar, limpiar
 * expirados, calcular bonus de ataque acumulado, saber si el personaje esta
 * bloqueado) ya no vive dentro de Personaje, sino en su propia clase
 * especializada.
 */
public class GestorEstadosAlterados {

    private final List<IEstadoAlterado> estados = new ArrayList<>();

    public void agregar(IEstadoAlterado estado, String nombrePersonaje) {
        estados.add(estado);
        System.out.println("  [Estado] " + nombrePersonaje + " recibe estado: "
                + estado.getNombreEstado());
    }

    /**
     * Aplica todos los estados activos y elimina los que ya expiraron.
     */
    public void procesar(Personaje objetivo) {
        List<IEstadoAlterado> expirados = new ArrayList<>();
        for (IEstadoAlterado e : estados) {
            e.aplicar(objetivo);
            if (e.haExpirado()) {
                expirados.add(e);
                System.out.println("  [Estado] El estado '" + e.getNombreEstado()
                        + "' en " + objetivo.getNombre() + " ha expirado.");
            }
        }
        estados.removeAll(expirados);
    }

    public boolean bloqueaAtaque() {
        for (IEstadoAlterado e : estados) {
            if (e.bloqueaAtaque()) {
                return true;
            }
        }
        return false;
    }

    public int getBonusAtaque() {
        int bonus = 0;
        for (IEstadoAlterado e : estados) {
            bonus += e.getBonusAtaque();
        }
        return bonus;
    }

    public List<IEstadoAlterado> getEstados() {
        return estados;
    }
}