package mx.itson.parchis;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsula la lógica para decidir si un movimiento es válido y si el jugador puede seguir.
 */
public class ReglasPartida {

    public static final int META = 74;
    public static final int TOTAL_CASILLAS = 68;
    public static final int ULTIMA_CASILLA_COMUN = 66;
    public static final int PRIMERA_CASILLA_PASILLO = 67;

    public enum EstadoMovimiento {
        VALIDO,
        FICHA_EN_META,
        REQUIERE_CINCO,
        EXCEDE_META
    }

    public boolean puedeMover(Jugador jugador, int dado) {
        int[] fichas = jugador.getFichas();
        for (int i = 0; i < fichas.length; i++) {
            if (puedeMoverFicha(jugador, i, dado)) {
                return true;
            }
        }
        return false;
    }

    public boolean puedeMoverFicha(Jugador jugador, int numeroFicha, int dado) {
        return validarMovimiento(jugador, numeroFicha, dado) == EstadoMovimiento.VALIDO;
    }

    public EstadoMovimiento validarMovimiento(Jugador jugador, int numeroFicha, int dado) {
        int posicion = jugador.getFichas()[numeroFicha];
        if (posicion == META) {
            return EstadoMovimiento.FICHA_EN_META;
        }
        if (posicion == -1) {
            return dado == 5 ? EstadoMovimiento.VALIDO : EstadoMovimiento.REQUIERE_CINCO;
        }
        if (posicion < 0 || posicion + dado > META) {
            return EstadoMovimiento.EXCEDE_META;
        }
        return EstadoMovimiento.VALIDO;
    }

    public boolean debeRepetirTiro(int dado, int seisSeguidos) {
        return dado == 6 && seisSeguidos < 3;
    }

    public List<Integer> capturarFichas(Jugador jugador, Jugador rival, int numeroFicha) {
        int posicion = jugador.getFichas()[numeroFicha];
        if (posicion < 0 || posicion > ULTIMA_CASILLA_COMUN) {
            return List.of();
        }

        int casilla = (jugador.getColor().getCasillaSalida() + posicion) % TOTAL_CASILLAS;
        int[] fichasRival = rival.getFichas();
        List<Integer> capturadas = new ArrayList<>();
        for (int i = 0; i < fichasRival.length; i++) {
            if (fichasRival[i] >= 0 && fichasRival[i] <= ULTIMA_CASILLA_COMUN
                    && (rival.getColor().getCasillaSalida() + fichasRival[i]) % TOTAL_CASILLAS == casilla) {
                fichasRival[i] = -1;
                capturadas.add(i);
            }
        }
        return capturadas;
    }

    public int contarFichasEnMeta(Jugador jugador) {
        int enMeta = 0;
        for (int posicion : jugador.getFichas()) {
            if (posicion == META) {
                enMeta++;
            }
        }
        return enMeta;
    }

    public boolean jugadorGano(Jugador jugador) {
        return contarFichasEnMeta(jugador) == jugador.getFichas().length;
    }
}
