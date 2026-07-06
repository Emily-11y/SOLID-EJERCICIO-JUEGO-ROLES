package ec.edu.utpl.poo.solid.rpg.interfaces;

import ec.edu.utpl.poo.solid.rpg.modelo.Personaje;

/**
 * SOLID - ISP (Interface Segregation Principle):
 * Interfaz cohesiva que define el contrato mínimo de un estado alterado.
 * No obliga a implementar métodos que no correspondan a todos los estados.
 *
 * SOLID - OCP (Open/Closed Principle):
 * Nuevos estados (ej. Paralizado, Quemado) se agregan implementando esta
 * interfaz sin modificar el código existente.
 */
public interface IEstadoAlterado {

    /** Nombre descriptivo del estado (ej. "Envenenado", "Congelado"). */
    String getNombreEstado();

    /**
     * Aplica el efecto del estado sobre el personaje objetivo.
     * Se llama una vez por turno al inicio del turno del personaje.
     */
    void aplicar(Personaje objetivo);

    /** @return true si el estado ya agotó su duración. */
    boolean haExpirado();

    /** @return true si este estado impide atacar durante el turno. */
    boolean bloqueaAtaque();

    /**
     * Bonus de ataque que este estado otorga (positivo = buff, 0 = neutro).
     */
    int getBonusAtaque();
}
