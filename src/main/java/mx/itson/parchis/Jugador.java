package mx.itson.parchis;

/**
 * Un jugador de la partida: su nombre y la posición de sus cuatro fichas.
 */
public class Jugador {

    private final String nombre;
    private final ColorJugador color;

    /**
     * Posición de cada ficha: -1 en la base, de 0 a 66 en el recorrido, de 67 a 73 en el
     * pasillo de su color y 74 en la meta.
     */
    private final int[] fichas = {-1, -1, -1, -1};

    public Jugador(String nombre, ColorJugador color) {
        this.nombre = nombre;
        this.color = color;
    }

    public String getNombre() {
        return nombre;
    }

    public int[] getFichas() {
        return fichas;
    }

    public ColorJugador getColor() {
        return color;
    }

    public String getNombreColor() {
        return color.getNombre();
    }
}
