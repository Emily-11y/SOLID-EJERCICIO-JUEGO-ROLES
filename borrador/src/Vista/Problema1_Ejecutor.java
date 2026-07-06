package Vista;

import Controlador.ControladorCombate;
import Modelo.Arma;
import Modelo.Armadura;
import Modelo.Arquero;
import Modelo.EstadoEnvenenado;
import Modelo.Guerrero;
import Modelo.Mago1;
import Modelo.Objeto;
import Modelo.Personaje;
import java.util.Scanner;

/**
 * Clase principal del juego.
 * Demuestra las tres funcionalidades:
 *   1. Inventario y Equipamiento (Arma / Armadura).
 *   2. Estados Alterados (veneno, congelacion, fuerza aumentada).
 *   3. Habilidades especiales con Energia y Cooldown.
 *
 * Puede ejecutarse en modo DEMO (automatico) o modo INTERACTIVO.
 *
 * Esta capa no cambio en su logica de presentacion: el refactor SOLID se
 * concentro en Modelo y Controlador. Eso en si mismo demuestra el beneficio
 * de aplicar bien SRP/OCP/DIP: la Vista pudo mantenerse intacta porque solo
 * depende de la abstraccion Personaje y de ControladorCombate.
 */
public class Problema1_Ejecutor {

    public static void main(String[] args) {
        System.out.println("============================================");
        System.out.println("       JUEGO DE ROLES - COMBATE RPG        ");
        System.out.println("   (Sistema Expandido - Version 2.0 SOLID) ");
        System.out.println("============================================\n");

        System.out.println("Seleccione modo:");
        System.out.println("  1. Demo automatico (recomendado para ver todas las funcionalidades)");
        System.out.println("  2. Modo interactivo");
        System.out.print("Opcion: ");

        Scanner sc = new Scanner(System.in);
        int opcion = 0;
        try {
            opcion = Integer.parseInt(sc.nextLine().trim());
        } catch (Exception ignored) {
            // opcion invalida -> se usa el modo demo por defecto
        }

        if (opcion == 2) {
            modoInteractivo(sc);
        } else {
            modoDemo();
        }

        sc.close();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // MODO DEMO
    // ─────────────────────────────────────────────────────────────────────────
    private static void modoDemo() {
        System.out.println("\n============================================");
        System.out.println("              MODO DEMO");
        System.out.println("============================================");

        Guerrero thor = new Guerrero("Thor", 150, 2, 40, 25);
        Mago1 merlin = new Mago1("Merlin", 110, 2, 45, 20);
        Arquero legolas = new Arquero("Legolas", 120, 2, 38, 30);

        System.out.println("\n===== FUNCIONALIDAD 1: INVENTARIO Y EQUIPAMIENTO ====");
        Arma espadaLegendaria = new Arma("Espada Legendaria", "Forjada en el volcan", 15);
        Arma arcoElfico = new Arma("Arco Elfico", "Madera de roble magico", 12);
        Arma bastionArcano = new Arma("Bastion Arcano", "Amplifica la magia", 18);
        Armadura pielDragon = new Armadura("Piel de Dragon", "Escamas irrompibles", 10);
        Armadura capaMago = new Armadura("Capa del Archimago", "Tejida con runas", 8);
        Armadura armaduraArquero = new Armadura("Chaleco de Cuero Reforzado", "Ligero y resistente", 6);

        thor.equiparArma(espadaLegendaria);
        thor.equiparArmadura(pielDragon);

        merlin.equiparArma(bastionArcano);
        merlin.equiparArmadura(capaMago);

        legolas.equiparArma(arcoElfico);
        legolas.equiparArmadura(armaduraArquero);

        Objeto espadaRepuesto = new Arma("Espada Oxidada", "Espada de repuesto", 2);
        thor.agregarAlInventario(espadaRepuesto);

        mostrarPersonaje(thor);
        mostrarPersonaje(merlin);
        mostrarPersonaje(legolas);

        System.out.println("\n==== FUNCIONALIDAD 2: ESTADOS ALTERADOS ====");
        System.out.println("(Los estados se aplicaran durante el combate via habilidades especiales)");
        System.out.println("  -> Merlin puede CONGELAR al rival (1 turno sin atacar).");
        System.out.println("  -> Legolas puede ENVENENAR al rival (8 daño x 3 turnos).");
        System.out.println("  -> Thor puede AUMENTAR SU FUERZA (+20 ATQ x 3 turnos).");

        System.out.println("\nDemo de veneno fuera de combate:");
        Guerrero dummy = new Guerrero("Maniqui", 50, 1, 10, 5);
        dummy.agregarEstado(new EstadoEnvenenado(5, 2));
        System.out.println("  Estado inicial: " + dummy.getPuntosVida() + " PV");
        dummy.procesarEstados();
        System.out.println("  Despues de 1 procesamiento: " + dummy.getPuntosVida() + " PV");
        dummy.procesarEstados();
        System.out.println("  Despues de 2 procesamientos: " + dummy.getPuntosVida() + " PV");

        System.out.println("\n==== FUNCIONALIDAD 3: HABILIDADES Y COOLDOWN ====");
        System.out.println("  Thor    -> Golpe Devastador  (costo: 30 energia, cooldown: 3 turnos)");
        System.out.println("  Merlin  -> Ventisca Gelida   (costo: 40 energia, cooldown: 2 turnos)");
        System.out.println("  Legolas -> Flecha Envenenada (costo: 25 energia, cooldown: 2 turnos)");

        System.out.println("\n============================================");
        System.out.println("       COMBATE 1: THOR  vs  MERLIN         ");
        System.out.println("============================================");

        ControladorCombate controlador = new ControladorCombate();
        Personaje ganador1 = controlador.combatir(thor, merlin);

        System.out.println("\n==== GANADOR del Combate 1 ====");
        System.out.println(ganador1);

        System.out.println("\n============================================");
        System.out.println("     COMBATE 2: LEGOLAS  vs  GANADOR 1     ");
        System.out.println("============================================");

        Personaje ganadorFinal = controlador.combatir(legolas, ganador1);

        System.out.println("\n============================================");
        System.out.println("           CAMPEON FINAL                ");
        System.out.println("============================================");
        System.out.println(ganadorFinal);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // MODO INTERACTIVO
    // ─────────────────────────────────────────────────────────────────────────
    private static void modoInteractivo(Scanner sc) {
        System.out.println("\n============================================");
        System.out.println("           MODO INTERACTIVO");
        System.out.println("============================================\n");

        System.out.println("===== PERSONAJE 1 ====");
        Personaje p1 = crearPersonaje(sc);
        equiparPersonaje(p1);

        System.out.println("\n==== PERSONAJE 2 ====");
        Personaje p2 = crearPersonaje(sc);
        equiparPersonaje(p2);

        System.out.println("\n==== Personajes creados ====");
        System.out.println(p1);
        System.out.println();
        System.out.println(p2);

        System.out.println("\n========= INICIANDO COMBATE =======\n");
        ControladorCombate controlador = new ControladorCombate();
        Personaje ganador = controlador.combatir(p1, p2);

        System.out.println("\n===================");
        System.out.println("    GANADOR     ");
        System.out.println("====================");
        System.out.println(ganador);
    }

    private static Personaje crearPersonaje(Scanner sc) {
        int opcion;
        do {
            System.out.println("\nTipo de personaje:");
            System.out.println("  1. Guerrero  (Habilidad: Golpe Devastador)");
            System.out.println("  2. Mago      (Habilidad: Ventisca Gelida)");
            System.out.println("  3. Arquero   (Habilidad: Flecha Envenenada)");
            System.out.print("Opcion: ");
            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                opcion = 0;
            }
            if (opcion < 1 || opcion > 3) {
                System.out.println("  Opcion invalida.");
            }
        } while (opcion < 1 || opcion > 3);

        System.out.print("Nombre del personaje: ");
        String nombre = sc.nextLine().trim();
        if (nombre.isEmpty()) {
            nombre = "Heroe";
        }

        return switch (opcion) {
            case 1 -> new Guerrero(nombre, 150, 2, 40, 25);
            case 2 -> new Mago1(nombre, 110, 2, 45, 20);
            default -> new Arquero(nombre, 120, 2, 38, 30);
        };
    }

    private static void equiparPersonaje(Personaje p) {
        System.out.println("\n-- Equipamiento para " + p.getNombre() + " --");
        if (p instanceof Guerrero) {
            p.equiparArma(new Arma("Espada de Acero", "Espada estandar de guerrero", 10));
            p.equiparArmadura(new Armadura("Escudo de Hierro", "Proteccion basica", 8));
        } else if (p instanceof Mago1) {
            p.equiparArma(new Arma("Vara Magica", "Canaliza la magia", 12));
            p.equiparArmadura(new Armadura("Tunica Runica", "Repele hechizos", 6));
        } else {
            p.equiparArma(new Arma("Arco de Madera", "Arco confiable", 8));
            p.equiparArmadura(new Armadura("Chaleco de Cuero", "Ligero y agil", 5));
        }
    }

    private static void mostrarPersonaje(Personaje p) {
        System.out.println("\n" + p);
        System.out.println("  ATQ total: " + p.calcularAtaque()
                + " | DEF total: " + p.calcularDefensa());
        System.out.println("  Inventario (" + p.getInventario().size() + " objetos):");
        for (Objeto o : p.getInventario()) {
            System.out.println("    - " + o);
        }
    }
}