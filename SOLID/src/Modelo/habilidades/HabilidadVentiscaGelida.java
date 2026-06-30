package Modelo.habilidades;

import Modelo.EstadoCongelado;
import Modelo.Personaje;

/**
 * Habilidad especial del Mago: "Ventisca Gelida".
 * Aplica el estado EstadoCongelado sobre el rival (objetivo).
 *
 * Antes, el ControladorCombate debia interpretar el texto
 * "CONGELAR_RIVAL:..." devuelto por usarHabilidadEspecial() para enterarse
 * de que debia crear un EstadoCongelado y aplicarselo al rival. Ahora la
 * propia habilidad se encarga de eso (SRP + DIP).
 */
public class HabilidadVentiscaGelida implements IHabilidadEspecial {

    private static final int COSTO_ENERGIA = 40;
    private static final int COOLDOWN_TURNOS = 2;
    private static final int DURACION_CONGELAMIENTO = 1;

    @Override
    public String getNombre() {
        return "Ventisca Gelida";
    }

    @Override
    public int getCostoEnergia() {
        return COSTO_ENERGIA;
    }

    @Override
    public int getCooldownTurnos() {
        return COOLDOWN_TURNOS;
    }

    @Override
    public String aplicarEfecto(Personaje origen, Personaje objetivo) {
        objetivo.agregarEstado(new EstadoCongelado(DURACION_CONGELAMIENTO));
        return "[" + origen.getNombre() + "] lanza ¡VENTISCA GELIDA! "
                + objetivo.getNombre() + " quedara congelado " + DURACION_CONGELAMIENTO
                + " turno(s). (Energia restante: " + origen.getEnergia() + ")";
    }
}
