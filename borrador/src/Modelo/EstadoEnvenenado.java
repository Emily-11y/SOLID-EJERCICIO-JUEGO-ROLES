package Modelo;

/**
 * Estado de VENENO: el personaje pierde vida cada turno durante N turnos.
 *
 * Esta clase es una "extension" del sistema (Modelo.IEstadoAlterado) y por si
 * sola es un buen ejemplo de OCP: agregar este estado no requirio tocar
 * Personaje ni el GestorEstadosAlterados.
 */
public class EstadoEnvenenado implements IEstadoAlterado {

    private final int danioPorTurno;
    private int turnosRestantes;

    public EstadoEnvenenado(int danioPorTurno, int duracionTurnos) {
        this.danioPorTurno = danioPorTurno;
        this.turnosRestantes = duracionTurnos;
    }

    @Override
    public String getNombreEstado() {
        return "Envenenado";
    }

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
        return 0;
    }
}
