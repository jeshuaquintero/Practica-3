import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Juego {
    private ArrayList<Sala> salas;
    private Jugador jugador;
    private Scanner scanner;
    private Random random;
    private int totalTareasJuego;

    public Juego() {
        this.salas = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.random = new Random();
        this.totalTareasJuego = 0;
        inicializarNave();
    }

    private void inicializarNave() {
        // Creación de las 4 salas con 3 tareas cada una
        Sala electricidad = new Sala("Electricidad", "Sala con tableros de energía e interruptores principales.");
        electricidad.agregarTarea(new Tarea("Calibrar distribuidores"));
        electricidad.agregarTarea(new Tarea("Conectar cables"));
        electricidad.agregarTarea(new Tarea("Desviar energía a Navegación"));

        Sala navegacion = new Sala("Navegación", "Sala de control de rumbo y mapas estelares.");
        navegacion.agregarTarea(new Tarea("Establecer curso"));
        navegacion.agregarTarea(new Tarea("Alinear mapa estelar"));
        navegacion.agregarTarea(new Tarea("Ajustar timón"));

        Sala motores = new Sala("Motores", "Sala de propulsión de la nave espacial.");
        motores.agregarTarea(new Tarea("Alinear salida de motor"));
        motores.agregarTarea(new Tarea("Cargar combustible"));
        motores.agregarTarea(new Tarea("Inspeccionar turbinas"));

        Sala armeria = new Sala("Armería", "Sala de defensa y control de asteroides.");
        armeria.agregarTarea(new Tarea("Destruir asteroides"));
        armeria.agregarTarea(new Tarea("Cargar torpedos"));
        armeria.agregarTarea(new Tarea("Alinear miras láser"));

        salas.add(electricidad);
        salas.add(navegacion);
        salas.add(motores);
        salas.add(armeria);

        for (Sala sala : salas) {
            totalTareasJuego += sala.getTareas().size();
        }
    }

    public void iniciar() {
        System.out.println("=== NAVE ESPACIAL AMONG US ===");
        System.out.print("Ingresa el nombre del jugador: ");
        String nombreJugador = scanner.nextLine();

        jugador = new Jugador(nombreJugador);
        jugador.setSalaActual(salas.get(0));

        boolean ejecutando = true;

        while (ejecutando) {
            System.out.println("\n-------------------------------------------");
            System.out.println("Sala actual: " + jugador.getSalaActual().getNombre());
            System.out.println("Descripción: " + jugador.getSalaActual().getDescripcion());
            System.out.println("-------------------------------------------");
            System.out.println("1. Moverse de sala");
            System.out.println("2. Realizar tarea de la sala actual");
            System.out.println("3. Ver tareas completadas");
            System.out.println("4. Ver progreso");
            System.out.println("5. Salir");
            System.out.print("Selecciona una opción: ");

            String opcionStr = scanner.nextLine();
            int opcion;
            try {
                opcion = Integer.parseInt(opcionStr);
            } catch (NumberFormatException e) {
                System.out.println("Ingresa una opción numérica válida.");
                continue;
            }

            switch (opcion) {
                case 1:
                    moverseDeSala();
                    break;
                case 2:
                    realizarTareaAleatoria();
                    break;
                case 3:
                    verTareasCompletadas();
                    break;
                case 4:
                    verProgreso();
                    break;
                case 5:
                    ejecutando = false;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }

            if (jugador.getTareasCompletadas().size() == totalTareasJuego) {
                System.out.println("\n¡Felicidades! Has completado todas las tareas de la nave.");
                ejecutando = false;
            }
        }

        finalizarPartida();
    }

    private void moverseDeSala() {
        System.out.println("\n--- SALAS DISPONIBLES ---");
        for (int i = 0; i < salas.size(); i++) {
            System.out.println((i + 1) + ". " + salas.get(i).getNombre());
        }
        System.out.print("Selecciona el número de la sala: ");
        try {
            int eleccion = Integer.parseInt(scanner.nextLine());
            if (eleccion >= 1 && eleccion <= salas.size()) {
                jugador.setSalaActual(salas.get(eleccion - 1));
                System.out.println("Te has movido a: " + jugador.getSalaActual().getNombre());
            } else {
                System.out.println("Número de sala fuera de rango.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
        }
    }

    private void realizarTareaAleatoria() {
        Sala salaActual = jugador.getSalaActual();
        ArrayList<Tarea> tareasPendientes = salaActual.getTareasPendientes();

        if (tareasPendientes.isEmpty()) {
            System.out.println("No hay tareas pendientes en " + salaActual.getNombre() + ".");
            return;
        }

        // Selección aleatoria basada en el tamaño del ArrayList de tareas pendientes
        int indiceAleatorio = random.nextInt(tareasPendientes.size());
        Tarea tareaAleatoria = tareasPendientes.get(indiceAleatorio);

        tareaAleatoria.setCompletada(true);
        jugador.agregarTareaCompletada(tareaAleatoria);

        System.out.println(">>> ¡Tarea realizada exitosamente!");
        System.out.println("Tarea completada: " + tareaAleatoria.getNombre() + " (Sala: " + salaActual.getNombre() + ")");
    }

    private void verTareasCompletadas() {
        System.out.println("\n--- TAREAS COMPLETADAS ---");
        ArrayList<Tarea> completadas = jugador.getTareasCompletadas();
        if (completadas.isEmpty()) {
            System.out.println("No has completado ninguna tarea aún.");
        } else {
            for (int i = 0; i < completadas.size(); i++) {
                System.out.println((i + 1) + ". " + completadas.get(i).getNombre());
            }
        }
    }

    private void verProgreso() {
        int completadas = jugador.getTareasCompletadas().size();
        double porcentaje = ((double) completadas / totalTareasJuego) * 100;
        System.out.printf("\nProgreso actual: %d / %d tareas (%.1f%%)\n", completadas, totalTareasJuego, porcentaje);
    }

    private void finalizarPartida() {
        System.out.println("\n===========================================");
        System.out.println("          RESUMEN DE LA PARTIDA           ");
        System.out.println("===========================================");
        System.out.println("Nombre del jugador: " + jugador.getNombre());
        System.out.println("Tareas completadas: " + jugador.getTareasCompletadas().size() + " de " + totalTareasJuego);
        System.out.println("\nDetalle de tareas:");
        if (jugador.getTareasCompletadas().isEmpty()) {
            System.out.println(" - Ninguna.");
        } else {
            for (Tarea t : jugador.getTareasCompletadas()) {
                System.out.println(" - " + t.getNombre());
            }
        }

        String resultado;
        if (jugador.getTareasCompletadas().size() == totalTareasJuego) {
            resultado = "VICTORIA: Se completaron todas las tareas de la nave.";
        } else {
            resultado = "ABANDONO: Se finalizó la partida antes de terminar todas las tareas.";
        }
        System.out.println("\nResultado de la partida: " + resultado);
        System.out.println("===========================================");
    }

    public static void main(String[] args) {
        Juego juego = new Juego();
        juego.iniciar();
    }
}