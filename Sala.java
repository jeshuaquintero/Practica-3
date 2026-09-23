import java.util.ArrayList;

public class Sala {
    private String nombre;
    private String descripcion;
    private ArrayList<Tarea> tareas;

    public Sala(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tareas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public ArrayList<Tarea> getTareas() {
        return tareas;
    }

    public void agregarTarea(Tarea tarea) {
        this.tareas.add(tarea);
    }

    // Obtiene únicamente las tareas no completadas de la sala
    public ArrayList<Tarea> getTareasPendientes() {
        ArrayList<Tarea> pendientes = new ArrayList<>();
        for (Tarea tarea : tareas) {
            if (!tarea.isCompletada()) {
                pendientes.add(tarea);
            }
        }
        return pendientes;
    }
}