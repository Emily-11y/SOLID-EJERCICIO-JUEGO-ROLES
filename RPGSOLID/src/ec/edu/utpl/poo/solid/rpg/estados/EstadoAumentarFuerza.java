package ec.edu.utpl.poo.solid.rpg.estados;

import ec.edu.utpl.poo.solid.rpg.interfaces.IEstadoAlterado;
import ec.edu.utpl.poo.solid.rpg.modelo.Personaje;

/**
 * Estado de AUMENTO DE FUERZA (buff): incrementa el ataque del personaje
 * durante N turnos.
 *
 * SOLID - SRP: solo gestiona la lógica del buff de fuerza.
 * SOLID - OCP: nuevo estado que no modifica ninguna clase existente.
 */
public class EstadoAumentarFuerza implements IEstadoAlterado {
    private int bonusAtaque;
    private int turnosRestantes;

    public EstadoAumentarFuerza(int bonusAtaque, int duracionTurnos) {
        this.bonusAtaque     = bonusAtaque;
        this.turnosRestantes = duracionTurnos;
    }

    @Override public String getNombreEstado() { return "AumentarFuerza"; }

    @Override
    public void aplicar(Personaje objetivo) {
        if (turnosRestantes > 0) {
            turnosRestantes--;
            System.out.println("  [Buff] " + objetivo.getNombre()
                    + " tiene fuerza aumentada +" + bonusAtaque
                    + " ATQ. (Turnos restantes: " + turnosRestantes + ")");
        }
    }

    @Override public boolean haExpirado()    { return turnosRestantes <= 0; }
    @Override public boolean bloqueaAtaque() { return false; }
    @Override public int     getBonusAtaque(){ return turnosRestantes > 0 ? bonusAtaque : 0; }
}
