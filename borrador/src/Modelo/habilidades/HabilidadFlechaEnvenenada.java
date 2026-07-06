package Modelo.habilidades;

import Modelo.EstadoEnvenenado;
import Modelo.Personaje;

/**
 * Habilidad especial del Arquero: "Flecha Envenenada".
 * Aplica el estado EstadoEnvenenado sobre el rival (objetivo).
 */
public class HabilidadFlechaEnvenenada implements IHabilidadEspecial {

    private static final int COSTO_ENERGIA = 25;
    private static final int COOLDOWN_TURNOS = 2;
    private static final int DANIO_POR_TURNO = 8;
    private static final int DURACION_VENENO = 3;

    @Override
    public String getNombre() {
        return "Flecha Envenenada";
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
        objetivo.agregarEstado(new EstadoEnvenenado(DANIO_POR_TURNO, DURACION_VENENO));
        return "[" + origen.getNombre() + "] dispara ¡FLECHA ENVENENADA! "
                + objetivo.getNombre() + " recibira " + DANIO_POR_TURNO
                + " de daño por veneno durante " + DURACION_VENENO + " turnos. "
                + "(Energia restante: " + origen.getEnergia() + ")";
    }
}