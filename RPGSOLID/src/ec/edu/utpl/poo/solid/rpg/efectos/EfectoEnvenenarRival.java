package ec.edu.utpl.poo.solid.rpg.efectos;

import ec.edu.utpl.poo.solid.rpg.interfaces.IEfectoHabilidad;
import ec.edu.utpl.poo.solid.rpg.modelo.Personaje;
import ec.edu.utpl.poo.solid.rpg.estados.EstadoEnvenenado;

/**
 * Efecto de habilidad: envenena al rival durante N turnos.
 *
 * SOLID - OCP / DIP: ver EfectoCongelarRival para explicación.
 */
public class EfectoEnvenenarRival implements IEfectoHabilidad {
    private final String mensaje;
    private final int danioPorTurno;
    private final int duracionTurnos;

    public EfectoEnvenenarRival(String mensaje, int danioPorTurno, int duracionTurnos) {
        this.mensaje        = mensaje;
        this.danioPorTurno  = danioPorTurno;
        this.duracionTurnos = duracionTurnos;
    }

    @Override public String getMensaje() { return mensaje; }

    @Override
    public void aplicarSobreObjetivo(Personaje objetivo) {
        objetivo.agregarEstado(new EstadoEnvenenado(danioPorTurno, duracionTurnos));
    }
}
