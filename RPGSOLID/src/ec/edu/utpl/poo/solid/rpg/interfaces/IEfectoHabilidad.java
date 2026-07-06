package ec.edu.utpl.poo.solid.rpg.interfaces;

import ec.edu.utpl.poo.solid.rpg.modelo.Personaje;

/**
 * SOLID - OCP (Open/Closed Principle) + DIP (Dependency Inversion Principle):
 *
 * Representa el efecto que produce una habilidad especial al usarse.
 * El ControladorCombate depende de esta abstracción en vez de analizar
 * Strings con prefijos ("CONGELAR_RIVAL:", "ENVENENAR_RIVAL:").
 *
 * Para agregar nuevos efectos (curación, robo de vida, etc.) basta con
 * implementar esta interfaz, sin tocar el controlador ni los personajes.
 */
public interface IEfectoHabilidad {

    /** Descripción del efecto para mostrar en pantalla. */
    String getMensaje();

    /**
     * Aplica el efecto sobre el personaje objetivo (normalmente el rival).
     * Si el efecto es sobre el propio usuario, el personaje origen
     * ya habrá aplicado el efecto antes de retornar este objeto.
     *
     * @param objetivo personaje que recibe el efecto (puede ser el rival o el mismo atacante).
     */
    void aplicarSobreObjetivo(Personaje objetivo);
}
