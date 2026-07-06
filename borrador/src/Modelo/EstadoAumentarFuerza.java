package Modelo;

/**
 * Estado de AUMENTO DE FUERZA (buff): incrementa el ataque del personaje
 * durante N turnos.
 *
 * Principio aplicado: OCP. Una nueva implementacion de IEstadoAlterado que
 * no exige modificar el resto del sistema.
 */
public class EstadoAumentarFuerza implements IEstadoAlterado {

    private final int bonusAtaque;
    private int turnosRestantes;

    public EstadoAumentarFuerza(int bonusAtaque, int duracionTurnos) {
        this.bonusAtaque = bonusAtaque;
        this.turnosRestantes = duracionTurnos;
    }

    @Override
    public String getNombreEstado() {
        return "AumentarFuerza";
    }

    @Override
    public void aplicar(Personaje objetivo) {
        if (turnosRestantes > 0) {
            turnosRestantes--;
            System.out.println("  [Buff] " + objetivo.getNombre()
                    + " tiene fuerza aumentada +" + bonusAtaque
                    + " Ataque (Turnos restantes: " + turnosRestantes + ")");
        }
    }

    @Override
    public boolean haExpirado() {
        return turnosRestantes <= 0;
    }

    @Override
    public boolean bloqueaAtaque() {
        return false;
    }

    @Override
    public int getBonusAtaque() {
        return turnosRestantes > 0 ? bonusAtaque : 0;
    }
}
