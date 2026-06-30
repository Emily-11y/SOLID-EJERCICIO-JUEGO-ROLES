package Modelo;

/**
 * Excepcion lanzada cuando un personaje intenta usar una habilidad especial
 * sin tener suficiente energia disponible.
 */
public class SinEnergiaException extends Exception {
    public SinEnergiaException(String mensaje) {
        super(mensaje);
    }
}
