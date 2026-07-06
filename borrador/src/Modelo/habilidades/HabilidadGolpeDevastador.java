package Modelo.habilidades;

import Modelo.EstadoAumentarFuerza;
import Modelo.Personaje;

/**
 * Habilidad especial del Guerrero: "Golpe Devastador".
 * Se aplica el estado de buff EstadoAumentarFuerza sobre si mismo (origen).
 *
 * Principio aplicado: SRP. Esta clase solo conoce las reglas de ESTA
 * habilidad (costo, cooldown y efecto); no conoce nada de combate, energia
 * ni del resto de personajes.
 */
public class HabilidadGolpeDevastador implements IHabilidadEspecial {

    private static final int COSTO_ENERGIA = 30;
    private static final int COOLDOWN_TURNOS = 3;
    private static final int BONUS_FUERZA = 20;
    private static final int DURACION_BUFF = 3;

    @Override
    public String getNombre() {
        return "Golpe Devastador";
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
        origen.agregarEstado(new EstadoAumentarFuerza(BONUS_FUERZA, DURACION_BUFF));
        return "[" + origen.getNombre() + "] usa ¡GOLPE DEVASTADOR! "
                + "Fuerza aumentada +" + BONUS_FUERZA + " por " + DURACION_BUFF
                + " turnos. (Energia restante: " + origen.getEnergia() + ")";
    }
}

