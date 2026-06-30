package Modelo;

import Modelo.habilidades.IHabilidadEspecial;

/**
 * Clase abstracta que representa un personaje del juego.
 *
 * Refactor SOLID respecto a la version original:
 *
 *  - SRP: Personaje ya NO administra directamente listas de inventario,
 *    listas de estados, energia y cooldown. Esas responsabilidades se
 *    delegaron a Inventario, GestorEstadosAlterados y GestorEnergia
 *    respectivamente (composicion). Personaje pasa a ser un orquestador
 *    delgado: su unica razon de cambio es "como se calculan ataque/defensa/
 *    vida de un personaje", no "como se guardan objetos" o "como expira un
 *    buff".
 *
 *  - OCP: agregar un nuevo tipo de personaje (ej. Sacerdote) no requiere
 *    modificar esta clase: basta con heredar de Personaje e inyectar su
 *    propia IHabilidadEspecial.
 *
 *  - DIP: Personaje depende de la abstraccion IHabilidadEspecial, nunca de
 *    una habilidad concreta. La habilidad concreta es inyectada por la
 *    subclase via el constructor.
 *
 *  - LSP: usarHabilidadEspecial() ya no exige que las subclases retornen
 *    Strings con prefijos magicos ("CONGELAR_RIVAL:", etc.) para que el
 *    ControladorCombate "adivine" que efecto aplicar. El contrato es simple
 *    y consistente para cualquier habilidad: aplica su efecto y devuelve un
 *    mensaje descriptivo.
 */
public abstract class Personaje {

    protected String nombre;
    protected int puntosVida;
    protected int nivelExperiencia;

    private final Inventario inventario;
    private final GestorEstadosAlterados gestorEstados = new GestorEstadosAlterados();
    private final GestorEnergia gestorEnergia;
    private final IHabilidadEspecial habilidadEspecial;

    protected Personaje(String nombre, int puntosVida, int nivelExperiencia,
                         int energiaMaxima, IHabilidadEspecial habilidadEspecial) {
        this.nombre = nombre;
        this.puntosVida = puntosVida;
        this.nivelExperiencia = nivelExperiencia;
        this.inventario = new Inventario(nombre);
        this.gestorEnergia = new GestorEnergia(energiaMaxima);
        this.habilidadEspecial = habilidadEspecial;
    }

    // ── Métodos abstractos (polimorfismo) ───────────────────────────────────
    public abstract int atacar();

    public abstract int defender();

    // ── Cálculo de ataque/defensa con equipamiento ─────────────────────────
    public int calcularAtaque() {
        return atacar() + inventario.getBonusAtaqueEquipo();
    }

    public int calcularDefensa() {
        return defender() + inventario.getBonusDefensaEquipo();
    }

    // ── Inventario (delegado) ───────────────────────────────────────────────
    public void agregarAlInventario(Objeto objeto) {
        inventario.agregar(objeto);
    }

    public void equiparArma(Arma arma) {
        inventario.equipar(arma);
    }

    public void equiparArmadura(Armadura armadura) {
        inventario.equipar(armadura);
    }

    public Arma getArmaEquipada() {
        return inventario.getArmaEquipada();
    }

    public Armadura getArmaduraEquipada() {
        return inventario.getArmaduraEquipada();
    }

    public java.util.List<Objeto> getInventario() {
        return inventario.getObjetos();
    }

    // ── Estados alterados (delegado) ────────────────────────────────────────
    public void agregarEstado(IEstadoAlterado estado) {
        gestorEstados.agregar(estado, nombre);
    }

    public void procesarEstados() {
        gestorEstados.procesar(this);
    }

    public boolean estaBloqueado() {
        return gestorEstados.bloqueaAtaque();
    }

    public int getBonusAtaqueEstados() {
        return gestorEstados.getBonusAtaque();
    }

    public java.util.List<IEstadoAlterado> getEstados() {
        return gestorEstados.getEstados();
    }

    // ── Energía / Cooldown / Habilidad especial (delegado) ──────────────────
    public int getEnergia() {
        return gestorEnergia.getEnergia();
    }

    public int getEnergiaMaxima() {
        return gestorEnergia.getEnergiaMaxima();
    }

    public int getCooldownHabilidad() {
        return gestorEnergia.getCooldownHabilidad();
    }

    public boolean habilidadDisponible() {
        return gestorEnergia.habilidadDisponible();
    }

    public void reducirCooldown() {
        gestorEnergia.reducirCooldown();
    }

    public void regenerarEnergia() {
        gestorEnergia.regenerar();
    }

    /**
     * Intenta usar la habilidad especial de este personaje contra un
     * objetivo. Personaje solo coordina cooldown/energia; la habilidad
     * concreta decide e implementa su propio efecto de juego.
     */
    public String usarHabilidadEspecial(Personaje objetivo) throws SinEnergiaException {
        if (!gestorEnergia.habilidadDisponible()) {
            return "[" + nombre + "] " + habilidadEspecial.getNombre() + " en cooldown ("
                    + gestorEnergia.getCooldownHabilidad() + " turnos restantes).";
        }
        gestorEnergia.consumir(habilidadEspecial.getCostoEnergia(), nombre);
        gestorEnergia.activarCooldown(habilidadEspecial.getCooldownTurnos());
        return habilidadEspecial.aplicarEfecto(this, objetivo);
    }

    // ── Métodos base ─────────────────────────────────────────────────────────
    public void recibirDanio(int danio) {
        if (danio <= 0) {
            return;
        }
        puntosVida -= danio;
        if (puntosVida < 0) {
            puntosVida = 0;
        }
    }

    public void subirNivel() {
        nivelExperiencia++;
        puntosVida += 20;
        gestorEnergia.restaurarAlMaximo();
    }

    public boolean estaVivo() {
        return puntosVida > 0;
    }

    // ── Getters básicos ───────────────────────────────────────────────────────
    public String getNombre() {
        return nombre;
    }

    public int getPuntosVida() {
        return puntosVida;
    }

    public int getNivelExperiencia() {
        return nivelExperiencia;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nombre: ").append(nombre)
                .append("\nPuntos de vida: ").append(puntosVida)
                .append("\nNivel de experiencia: ").append(nivelExperiencia)
                .append("\nEnergia: ").append(getEnergia()).append("/").append(getEnergiaMaxima());

        if (getArmaEquipada() != null) {
            sb.append("\nArma equipada: ").append(getArmaEquipada().getNombre());
        }
        if (getArmaduraEquipada() != null) {
            sb.append("\nArmadura equipada: ").append(getArmaduraEquipada().getNombre());
        }
        if (!gestorEstados.getEstados().isEmpty()) {
            sb.append("\nEstados activos: ");
            for (IEstadoAlterado e : gestorEstados.getEstados()) {
                sb.append(e.getNombreEstado()).append(" ");
            }
        }
        return sb.toString();
    }
}