package ec.edu.utpl.poo.solid.rpg.efectos;

import ec.edu.utpl.poo.solid.rpg.interfaces.IEfectoHabilidad;
import ec.edu.utpl.poo.solid.rpg.modelo.Personaje;
import ec.edu.utpl.poo.solid.rpg.estados.EstadoCongelado;

/**
 * Efecto de habilidad: congela al rival durante N turnos.
 *
 * SOLID - OCP: el ControladorCombate llama a aplicarSobreObjetivo()
 * sin necesidad de analizar strings ni usar instanceof.
 * Para agregar nuevos efectos basta crear otra clase que implemente IEfectoHabilidad.
 *
 * SOLID - DIP: el controlador depende de la abstracción IEfectoHabilidad,
 * no de esta clase concreta.
 */
public class EfectoCongelarRival implements IEfectoHabilidad {
    private final String mensaje;
    private final int duracionTurnos;

    public EfectoCongelarRival(String mensaje, int duracionTurnos) {
        this.mensaje       = mensaje;
        this.duracionTurnos = duracionTurnos;
    }

    @Override public String getMensaje() { return mensaje; }

    @Override
    public void aplicarSobreObjetivo(Personaje objetivo) {
        objetivo.agregarEstado(new EstadoCongelado(duracionTurnos));
    }
}
