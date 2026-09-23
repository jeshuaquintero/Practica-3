import java.util.ArrayList;

public class Jugador {
    private String nombre;
    private Sala salaActual;
    private ArrayList<Tarea> tareasCompletadas;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.tareasCompletadas = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public Sala getSalaActual() {
        return salaActual;
    }

    public void setSalaActual(Sala salaActual) {
        this.salaActual = salaActual;
    }

    public ArrayList<Tarea> getTareasCompletadas() {
        return tareasCompletadas;
    }

    public void agregarTareaCompletada(Tarea tarea) {
        this.tareasCompletadas.add(tarea);
    }
}