package ec.edu.utpl.poo.solid.rpg.controlador;

import ec.edu.utpl.poo.solid.rpg.interfaces.IEfectoHabilidad;
import ec.edu.utpl.poo.solid.rpg.modelo.Personaje;
import ec.edu.utpl.poo.solid.rpg.modelo.SinEnergiaException;

/**
 * Controlador de combate.
 *
 * SOLID - SRP: solo orquesta el flujo de combate.
 * SOLID - OCP: no necesita modificarse para nuevos efectos de habilidad.
 * SOLID - DIP: depende de Personaje e IEfectoHabilidad (abstracciones),
 *              no de Guerrero, Mago ni de strings mágicos.
 */
public class ControladorCombate {

    private static final int TURNOS_HABILIDAD = 3;

    public Personaje combatir(Personaje p1, Personaje p2) {
        int turno = 1;
        while (p1.estaVivo() && p2.estaVivo()) {
            System.out.println("\n══════════════ TURNO " + turno + " ══════════════");
            mostrarEstado(p1, p2);
            ejecutarTurno(p1, p2, turno);
            if (!p2.estaVivo()) break;
            ejecutarTurno(p2, p1, turno);
            turno++;
        }
        Personaje ganador = p1.estaVivo() ? p1 : p2;
        ganador.subirNivel();
        System.out.println("\n " + ganador.getNombre() + " ha ganado el combate y sube de nivel!");
        return ganador;
    }

    private void ejecutarTurno(Personaje atacante, Personaje defensor, int turno) {
        System.out.println("\n▶ Turno de " + atacante.getNombre() + ":");
        atacante.procesarEstados();
        if (!atacante.estaVivo()) {
            System.out.println("  " + atacante.getNombre() + " murió por los efectos de estado.");
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
     * SOLID - OCP / DIP:
     * Ya no analiza Strings con prefijos. Llama aplicarSobreObjetivo()
     * sobre la abstracción IEfectoHabilidad. Funciona con cualquier
     * efecto futuro sin modificar este método.
     */
    private void intentarHabilidadEspecial(Personaje atacante, Personaje defensor) {
        try {
            IEfectoHabilidad efecto = atacante.usarHabilidadEspecial();
            System.out.println("  " + efecto.getMensaje());
            efecto.aplicarSobreObjetivo(defensor);
        } catch (SinEnergiaException e) {
            System.out.println("  ⚠ " + e.getMessage());
        }
    }

    private void realizarAtaque(Personaje atacante, Personaje defensor) {
        int ataque  = atacante.calcularAtaque() + atacante.getBonusAtaqueEstados();
        int defensa = defensor.calcularDefensa();
        int danio   = Math.max(0, ataque - defensa);
        defensor.recibirDanio(danio);
        System.out.printf("  %s ataca con %d (ATQ:%d - DEF:%d) → %s pierde %d PV. Vida restante: %d%n",
                atacante.getNombre(), danio, ataque, defensa,
                defensor.getNombre(), danio, defensor.getPuntosVida());
    }

    private void mostrarEstado(Personaje p1, Personaje p2) {
        System.out.printf("  %-20s PV:%-5d E:%-4d | %-20s PV:%-5d E:%-4d%n",
                p1.getNombre(), p1.getPuntosVida(), p1.getEnergia(),
                p2.getNombre(), p2.getPuntosVida(), p2.getEnergia());
    }
}
