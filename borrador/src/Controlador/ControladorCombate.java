package Controlador;

import Modelo.Personaje;
import Modelo.SinEnergiaException;

/**
 * Controlador de combate.
 *
 * Refactor SOLID respecto a la version original: el metodo
 * intentarHabilidadEspecial() ya NO interpreta prefijos de texto
 * ("CONGELAR_RIVAL:", "ENVENENAR_RIVAL:") ni instancia clases concretas de
 * estado (EstadoCongelado, EstadoEnvenenado). Esa logica vivia aqui porque el
 * diseño original violaba OCP y DIP: cada vez que se agregaba un personaje
 * con un nuevo tipo de efecto, habia que tocar este controlador.
 *
 * Ahora el controlador solo conoce la abstraccion Personaje y delega
 * totalmente en Personaje.usarHabilidadEspecial(objetivo), que a su vez
 * delega en la habilidad concreta (IHabilidadEspecial) de cada personaje.
 * Esto cumple:
 *
 *  - OCP: se pueden agregar personajes y habilidades nuevas sin tocar esta
 *    clase.
 *  - DIP: el controlador depende unicamente de la abstraccion Personaje, no
 *    de Guerrero, Mago1, Arquero, EstadoCongelado, etc.
 */
public class ControladorCombate {

    private static final int TURNOS_HABILIDAD = 3; // intentar habilidad cada N turnos

    /**
     * Realiza una batalla completa entre dos personajes.
     *
     * @return el personaje ganador.
     */
    public Personaje combatir(Personaje p1, Personaje p2) {
        int turno = 1;

        while (p1.estaVivo() && p2.estaVivo()) {
            System.out.println("\n============ TURNO " + turno + " ===============");
            mostrarEstado(p1, p2);

            ejecutarTurno(p1, p2, turno);
            if (!p2.estaVivo()) {
                break;
            }

            ejecutarTurno(p2, p1, turno);

            turno++;
        }

        Personaje ganador = p1.estaVivo() ? p1 : p2;
        ganador.subirNivel();
        System.out.println("\n " + ganador.getNombre() + " ha ganado el combate y sube de nivel!");
        return ganador;
    }

    /**
     * Ejecuta un turno completo de un personaje:
     *  1. Procesa estados alterados activos.
     *  2. Intenta usar habilidad especial (cada TURNOS_HABILIDAD turnos).
     *  3. Si no esta bloqueado, realiza ataque normal + bonus de estados.
     *  4. Reduce cooldown al final.
     *  5. Regenera energia.
     */
    private void ejecutarTurno(Personaje atacante, Personaje defensor, int turno) {
        System.out.println("\n Turno de " + atacante.getNombre() + ":");

        atacante.procesarEstados();

        if (!atacante.estaVivo()) {
            System.out.println("  " + atacante.getNombre() + " murio por los efectos de estado.");
            return;
        }

        if (turno % TURNOS_HABILIDAD == 0) {
            intentarHabilidadEspecial(atacante, defensor);
        }

        if (atacante.estaBloqueado()) {
            System.out.println("  " + atacante.getNombre() + " no puede atacar este turno.");
        } else {
            realizarAtaque(atacante, defensor);
        }

        atacante.reducirCooldown();
        atacante.regenerarEnergia();
    }

    /**
     * Intenta usar la habilidad especial del atacante. El controlador no
     * necesita saber que efecto produce ni sobre quien recae: eso lo decide
     * la propia habilidad dentro de Personaje.usarHabilidadEspecial(...).
     */
    private void intentarHabilidadEspecial(Personaje atacante, Personaje defensor) {
        try {
            String resultado = atacante.usarHabilidadEspecial(defensor);
            System.out.println("  " + resultado);
        } catch (SinEnergiaException e) {
            System.out.println("  error " + e.getMessage());
        }
    }

    /**
     * Calcula y aplica el daño del atacante al defensor.
     */
    private void realizarAtaque(Personaje atacante, Personaje defensor) {
        int ataque = atacante.calcularAtaque() + atacante.getBonusAtaqueEstados();
        int defensa = defensor.calcularDefensa();
        int danio = Math.max(0, ataque - defensa);

        defensor.recibirDanio(danio);
        System.out.printf("%s ataca con %d (ATQ:%d - DEF:%d) -> %s pierde %d PV. Vida restante: %d%n",
                atacante.getNombre(), danio,
                ataque, defensa,
                defensor.getNombre(), danio,
                defensor.getPuntosVida());
    }

    /** Muestra el estado actual de ambos personajes. */
    private void mostrarEstado(Personaje p1, Personaje p2) {
        System.out.printf("  %-20s PV:%-5d E:%-4d | %-20s PV:%-5d E:%-4d%n",
                p1.getNombre(), p1.getPuntosVida(), p1.getEnergia(),
                p2.getNombre(), p2.getPuntosVida(), p2.getEnergia());
    }
}
