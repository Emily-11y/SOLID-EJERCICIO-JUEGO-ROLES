package Modelo.habilidades;

import Modelo.Personaje;

/**
 * Contrato para cualquier habilidad especial de un personaje.
 *
 * Esta interfaz es la pieza central del refactor SOLID de este ejercicio,
 * siguiendo el mismo enfoque que "ICalculadorDescuento" del ejemplo del
 * profesor: en vez de que el codigo cliente (ControladorCombate) tenga que
 * reconocer "tipos" de habilidad mediante texto o instanceof, cada habilidad
 * sabe aplicar su propio efecto.
 *
 * Principio aplicado: SRP (Single Responsibility Principle).
 * Cada habilidad concreta (HabilidadGolpeDevastador, HabilidadVentiscaGelida,
 * HabilidadFlechaEnvenenada...) tiene una unica responsabilidad: describir su
 * costo/cooldown y aplicar su propio efecto de juego.
 *
 * Principio aplicado: OCP (Open/Closed Principle).
 * Agregar un nuevo personaje con una habilidad nueva (ej. "Sacerdote" con
 * "Curacion Divina") solo requiere crear una nueva clase que implemente esta
 * interfaz. Ni Personaje ni ControladorCombate necesitan modificarse.
 *
 * Principio aplicado: LSP (Liskov Substitution Principle).
 * Cualquier implementacion puede sustituir a otra en Personaje sin alterar
 * el comportamiento esperado por quien la invoca (Personaje.usarHabilidadEspecial),
 * porque todas devuelven un mensaje descriptivo y aplican su efecto por su
 * cuenta (a diferencia del diseño original, que dependia de que el String de
 * retorno tuviera un prefijo "magico" como "CONGELAR_RIVAL:").
 *
 * Principio aplicado: DIP (Dependency Inversion Principle).
 * Personaje y ControladorCombate dependen de esta abstraccion, nunca de una
 * habilidad concreta.
 */
public interface IHabilidadEspecial {

    /** Nombre de la habilidad, usado solo para mensajes informativos. */
    String getNombre();

    /** Costo en energia para poder ejecutar la habilidad. */
    int getCostoEnergia();

    /** Turnos de cooldown que la habilidad deja tras usarse. */
    int getCooldownTurnos();

    /**
     * Aplica el efecto de la habilidad. La propia habilidad decide si el
     * efecto recae sobre quien la usa (origen) o sobre el rival (objetivo),
     * sin que quien la invoque necesite saberlo.
     *
     * @param origen   el personaje que ejecuta la habilidad.
     * @param objetivo el rival en el combate.
     * @return mensaje descriptivo del efecto producido, listo para mostrarse.
     */
    String aplicarEfecto(Personaje origen, Personaje objetivo);
}