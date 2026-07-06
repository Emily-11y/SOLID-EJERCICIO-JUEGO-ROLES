package ec.edu.utpl.poo.solid.rpg.efectos;

import ec.edu.utpl.poo.solid.rpg.interfaces.IEfectoHabilidad;
import ec.edu.utpl.poo.solid.rpg.modelo.Personaje;

/**
 * Efecto de habilidad que solo muestra un mensaje (el efecto ya fue aplicado
 * sobre el propio personaje, por ejemplo un buff de fuerza).
 *
 * SOLID - SRP: separa el caso "efecto sobre sí mismo" del caso "efecto sobre rival".
 */
public class EfectoSoloMensaje implements IEfectoHabilidad {
    private final String mensaje;

    public EfectoSoloMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override public String getMensaje() { return mensaje; }

    /** No aplica nada al objetivo; el efecto ya fue ejecutado. */
    @Override public void aplicarSobreObjetivo(Personaje objetivo) { }
}
