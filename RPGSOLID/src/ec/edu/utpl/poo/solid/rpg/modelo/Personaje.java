package ec.edu.utpl.poo.solid.rpg.modelo;

import ec.edu.utpl.poo.solid.rpg.interfaces.IEstadoAlterado;
import ec.edu.utpl.poo.solid.rpg.interfaces.IEquipable;
import ec.edu.utpl.poo.solid.rpg.interfaces.IEfectoHabilidad;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase abstracta que representa un personaje del juego.
 *
 * SOLID - SRP (Single Responsibility Principle):
 *   Personaje gestiona únicamente su estado interno (vida, energía, cooldowns,
 *   inventario y estados alterados). La lógica de combate reside en el Controlador.
 *
 * SOLID - OCP (Open/Closed Principle):
 *   Nuevos tipos de personaje se crean extendiendo esta clase sin modificarla.
 *
 * SOLID - LSP (Liskov Substitution Principle):
 *   Cualquier subclase puede usarse donde se espere un Personaje sin romper
 *   el comportamiento del sistema.
 *
 * SOLID - DIP (Dependency Inversion Principle):
 *   Depende de las abstracciones IEstadoAlterado, IEquipable e IEfectoHabilidad,
 *   no de implementaciones concretas.
 */
public abstract class Personaje {

    // ── Atributos base ────────────────────────────────────────────────────────
    protected String nombre;
    protected int    puntosVida;
    protected int    nivelExperiencia;

    // ── Inventario y equipamiento ─────────────────────────────────────────────
    private List<Objeto>  inventario       = new ArrayList<>();
    private IEquipable    armaEquipada     = null;
    private IEquipable    armaduraEquipada = null;

    // ── Estados alterados ─────────────────────────────────────────────────────
    private List<IEstadoAlterado> estados = new ArrayList<>();

    // ── Energía y cooldowns ───────────────────────────────────────────────────
    protected int energia;
    protected int energiaMaxima;
    private   int cooldownHabilidad = 0;

    // ─────────────────────────────────────────────────────────────────────────

    public Personaje(String nombre, int puntosVida, int nivelExperiencia,
                     int energiaMaxima) {
        this.nombre           = nombre;
        this.puntosVida       = puntosVida;
        this.nivelExperiencia = nivelExperiencia;
        this.energiaMaxima    = energiaMaxima;
        this.energia          = energiaMaxima;
    }

    // ── Métodos abstractos ────────────────────────────────────────────────────

    /** Ataque base del personaje (sin equipamiento ni estados). */
    public abstract int atacar();

    /** Defensa base del personaje (sin equipamiento ni estados). */
    public abstract int defender();

    /**
     * Usa la habilidad especial del personaje.
     *
     * SOLID - OCP: retorna IEfectoHabilidad en vez de un String con prefijos,
     * permitiendo que el Controlador aplique el efecto sin conocer su tipo.
     *
     * @return efecto a aplicar sobre el rival (o sin efecto si es buff propio).
     * @throws SinEnergiaException si no hay energía suficiente.
     */
    public abstract IEfectoHabilidad usarHabilidadEspecial() throws SinEnergiaException;

    /**
     * SOLID - OCP / Template Method:
     * Retorna el equipamiento por defecto para este tipo de personaje.
     * Cada subclase define su propio equipamiento inicial sin que el
     * ejecutor necesite usar instanceof.
     */
    public abstract void equiparDefault();

    // ── Ataque y defensa con equipamiento ────────────────────────────────────

    public int calcularAtaque() {
        int bonus = (armaEquipada != null) ? armaEquipada.getBonusAtaque() : 0;
        return atacar() + bonus;
    }

    public int calcularDefensa() {
        int bonus = (armaduraEquipada != null) ? armaduraEquipada.getBonusDefensa() : 0;
        return defender() + bonus;
    }

    // ── Inventario y equipamiento ─────────────────────────────────────────────

    public void agregarAlInventario(Objeto objeto) {
        inventario.add(objeto);
        System.out.println("  [Inventario] " + nombre + " obtuvo: " + objeto.getNombre());
    }

    /**
     * SOLID - DIP: recibe IEquipable en vez de Arma concreta.
     * Cualquier objeto que implemente IEquipable puede equiparse como arma.
     */
    public void equiparArma(Arma arma) {
        if (!inventario.contains(arma)) agregarAlInventario(arma);
        this.armaEquipada = arma;
        System.out.println("  [Equipo] " + nombre + " equipó arma: "
                + arma.getNombre() + " (+" + arma.getBonusAtaque() + " ATQ)");
    }

    public void equiparArmadura(Armadura armadura) {
        if (!inventario.contains(armadura)) agregarAlInventario(armadura);
        this.armaduraEquipada = armadura;
        System.out.println("  [Equipo] " + nombre + " equipó armadura: "
                + armadura.getNombre() + " (+" + armadura.getBonusDefensa() + " DEF)");
    }

    public List<Objeto> getInventario() { return inventario; }

    // ── Estados alterados ─────────────────────────────────────────────────────

    public void agregarEstado(IEstadoAlterado estado) {
        estados.add(estado);
        System.out.println("  [Estado] " + nombre + " recibe estado: "
                + estado.getNombreEstado());
    }

    public void procesarEstados() {
        List<IEstadoAlterado> expirados = new ArrayList<>();
        for (IEstadoAlterado e : estados) {
            e.aplicar(this);
            if (e.haExpirado()) {
                expirados.add(e);
                System.out.println("  [Estado] El estado '" + e.getNombreEstado()
                        + "' en " + nombre + " ha expirado.");
            }
        }
        estados.removeAll(expirados);
    }

    public boolean estaBloqueado() {
        for (IEstadoAlterado e : estados) {
            if (e.bloqueaAtaque()) return true;
        }
        return false;
    }

    public int getBonusAtaqueEstados() {
        int bonus = 0;
        for (IEstadoAlterado e : estados) bonus += e.getBonusAtaque();
        return bonus;
    }

    public List<IEstadoAlterado> getEstados() { return estados; }

    // ── Energía y cooldowns ───────────────────────────────────────────────────

    public int  getEnergia()           { return energia; }
    public int  getEnergiaMaxima()     { return energiaMaxima; }
    public int  getCooldownHabilidad() { return cooldownHabilidad; }

    public void setCooldownHabilidad(int turnos) { this.cooldownHabilidad = turnos; }
    public boolean habilidadDisponible()          { return cooldownHabilidad == 0; }

    public void reducirCooldown() {
        if (cooldownHabilidad > 0) cooldownHabilidad--;
    }

    public void regenerarEnergia() {
        int regen = Math.max(1, energiaMaxima / 10);
        energia = Math.min(energiaMaxima, energia + regen);
    }

    protected void consumirEnergia(int cantidad) throws SinEnergiaException {
        if (energia < cantidad) {
            throw new SinEnergiaException(nombre + " no tiene suficiente energía. "
                    + "Necesita " + cantidad + " pero solo tiene " + energia + ".");
        }
        energia -= cantidad;
    }

    // ── Métodos de estado ─────────────────────────────────────────────────────

    public void recibirDanio(int danio) {
        if (danio <= 0) return;
        puntosVida -= danio;
        if (puntosVida < 0) puntosVida = 0;
    }

    public void subirNivel() {
        nivelExperiencia++;
        puntosVida += 20;
        energia     = energiaMaxima;
    }

    public boolean estaVivo()           { return puntosVida > 0; }
    public String  getNombre()          { return nombre; }
    public int     getPuntosVida()      { return puntosVida; }
    public int     getNivelExperiencia(){ return nivelExperiencia; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre: ").append(nombre)
          .append("\nPuntos de vida: ").append(puntosVida)
          .append("\nNivel de experiencia: ").append(nivelExperiencia)
          .append("\nEnergía: ").append(energia).append("/").append(energiaMaxima);

        if (armaEquipada     != null) sb.append("\nArma equipada: ").append(((Objeto) armaEquipada).getNombre());
        if (armaduraEquipada != null) sb.append("\nArmadura equipada: ").append(((Objeto) armaduraEquipada).getNombre());
        if (!estados.isEmpty()) {
            sb.append("\nEstados activos: ");
            for (IEstadoAlterado e : estados) sb.append(e.getNombreEstado()).append(" ");
        }
        return sb.toString();
    }
}
