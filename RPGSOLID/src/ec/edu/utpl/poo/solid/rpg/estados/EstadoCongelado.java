package ec.edu.utpl.poo.solid.rpg.estados;

import ec.edu.utpl.poo.solid.rpg.interfaces.IEstadoAlterado;
import ec.edu.utpl.poo.solid.rpg.modelo.Personaje;

/**
 * Estado de CONGELACIÓN: el personaje no puede atacar durante N turnos.
 *
 * SOLID - SRP: solo gestiona la lógica de congelamiento.
 * SOLID - OCP: nuevo estado que no modifica ninguna clase existente.
 */
public class EstadoCongelado implements IEstadoAlterado {
    private int turnosRestantes;

    public EstadoCongelado(int duracionTurnos) {
        this.turnosRestantes = duracionTurnos;
    }

    @Override public String getNombreEstado() { return "Congelado"; }

    @Override
    public void aplicar(Personaje objetivo) {
        if (turnosRestantes > 0) {
            turnosRestantes--;
            System.out.println("  [Hielo] " + objetivo.getNombre()
                    + " está congelado y no puede atacar. "
                    + "(Turnos restantes: " + turnosRestantes + ")");
        }
    }

    @Override public boolean haExpirado()    { return turnosRestantes <= 0; }
    @Override public boolean bloqueaAtaque() { return turnosRestantes > 0; }
    @Override public int     getBonusAtaque(){ return 0; }
}
