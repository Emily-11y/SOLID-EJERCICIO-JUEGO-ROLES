package ec.edu.utpl.poo.solid.rpg.estados;

import ec.edu.utpl.poo.solid.rpg.interfaces.IEstadoAlterado;
import ec.edu.utpl.poo.solid.rpg.modelo.Personaje;

/**
 * Estado de VENENO: el personaje pierde vida cada turno durante N turnos.
 *
 * SOLID - SRP: solo gestiona la lógica de envenenamiento.
 * SOLID - OCP: nuevo estado que no modifica ninguna clase existente.
 */
public class EstadoEnvenenado implements IEstadoAlterado {
    private int danioPorTurno;
    private int turnosRestantes;

    public EstadoEnvenenado(int danioPorTurno, int duracionTurnos) {
        this.danioPorTurno   = danioPorTurno;
        this.turnosRestantes = duracionTurnos;
    }

    @Override public String getNombreEstado() { return "Envenenado"; }

    @Override
    public void aplicar(Personaje objetivo) {
        if (turnosRestantes > 0) {
            objetivo.recibirDanio(danioPorTurno);
            turnosRestantes--;
            System.out.println("  [Veneno] " + objetivo.getNombre()
                    + " pierde " + danioPorTurno + " PV por veneno. "
                    + "(Turnos restantes: " + turnosRestantes + ")");
        }
    }

    @Override public boolean haExpirado()    { return turnosRestantes <= 0; }
    @Override public boolean bloqueaAtaque() { return false; }
    @Override public int     getBonusAtaque(){ return 0; }
}
