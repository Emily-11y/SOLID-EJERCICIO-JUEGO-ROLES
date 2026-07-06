package Modelo;

/**
 * Estado de CONGELACION: el personaje no puede atacar durante N turnos.
 *
 * Principio aplicado: OCP / LSP. Cualquier IEstadoAlterado puede sustituir a
 * otro sin romper al consumidor (GestorEstadosAlterados), ya que todos
 * cumplen el mismo contrato de forma consistente.
 */
public class EstadoCongelado implements IEstadoAlterado {

    private int turnosRestantes;

    public EstadoCongelado(int duracionTurnos) {
        this.turnosRestantes = duracionTurnos;
    }

    @Override
    public String getNombreEstado() {
        return "Congelado";
    }

    @Override
    public void aplicar(Personaje objetivo) {
        if (turnosRestantes > 0) {
            turnosRestantes--;
            System.out.println("  [Hielo] " + objetivo.getNombre()
                    + " esta congelado y no puede atacar. "
                    + "(Turnos restantes: " + turnosRestantes + ")");
        }
    }

    @Override
    public boolean haExpirado() {
        return turnosRestantes <= 0;
    }

    @Override
    public boolean bloqueaAtaque() {
        return turnosRestantes > 0;
    }

    @Override
    public int getBonusAtaque() {
        return 0;
    }
}

