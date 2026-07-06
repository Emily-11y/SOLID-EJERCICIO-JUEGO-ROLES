package Modelo;

/**
 * Interfaz que define el contrato de cualquier estado alterado (buff o
 * debuff) que puede afectar a un personaje.
 *
 * Principio aplicado: ISP (Interface Segregation Principle).
 * La interfaz es pequeña y cohesiva: solo expone lo que un "estado" necesita
 * exponer. Ningun cliente (GestorEstadosAlterados, habilidades, etc.) se ve
 * obligado a depender de metodos que no usa.
 *
 * Principio aplicado: OCP (Open/Closed Principle).
 * Para crear un nuevo estado (ej. "Aturdido", "Quemado") basta con
 * implementar esta interfaz; no es necesario modificar Personaje,
 * GestorEstadosAlterados ni el ControladorCombate.
 */
public interface IEstadoAlterado {

    /** Nombre descriptivo del estado (p.ej. "Envenenado", "Congelado"). */
    String getNombreEstado();

    /**
     * Aplica el efecto del estado sobre el personaje objetivo.
     * Se llama una vez por turno al inicio del turno del personaje.
     */
    void aplicar(Personaje objetivo);

    /** Retorna true si el estado ya agoto su duracion. */
    boolean haExpirado();

    /** Retorna true si este estado impide atacar durante el turno. */
    boolean bloqueaAtaque();

    /**
     * Bonus (o penalizacion, valor negativo) de ataque que este estado
     * otorga. Usado para calcular el ataque final del turno.
     */
    int getBonusAtaque();
}
