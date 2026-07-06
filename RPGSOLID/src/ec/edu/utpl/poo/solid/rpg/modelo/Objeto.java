package ec.edu.utpl.poo.solid.rpg.modelo;

/**
 * Clase base para todos los objetos del inventario.
 *
 * SOLID - SRP: solo modela los datos comunes de un objeto (nombre, descripción).
 * La lógica de equipamiento la delega a las subclases mediante IEquipable.
 */
public abstract class Objeto {
    protected String nombre;
    protected String descripcion;

    public Objeto(String nombre, String descripcion) {
        this.nombre      = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre()      { return nombre; }
    public String getDescripcion() { return descripcion; }

    @Override
    public String toString() {
        return nombre + " - " + descripcion;
    }
}
