package ec.edu.utpl.poo.solid.rpg.modelo;

/**
 * Excepción lanzada cuando un personaje intenta usar una habilidad especial
 * sin tener suficiente energía disponible.
 */
public class SinEnergiaException extends Exception {
    public SinEnergiaException(String mensaje) {
        super(mensaje);
    }
}
