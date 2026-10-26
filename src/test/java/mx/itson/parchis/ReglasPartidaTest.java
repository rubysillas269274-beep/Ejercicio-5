package mx.itson.parchis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class ReglasPartidaTest {

    private final ReglasPartida reglas = new ReglasPartida();

    @Test
    void fichaEnBaseSoloPuedeSalirConCinco() {
        Jugador jugador = new Jugador("Rojo", ColorJugador.ROJO);

        assertEquals(ReglasPartida.EstadoMovimiento.REQUIERE_CINCO,
                reglas.validarMovimiento(jugador, 0, 4));
        assertTrue(reglas.puedeMoverFicha(jugador, 0, 5));
    }

    @Test
    void fichaNecesitaElNumeroExactoParaLlegarALaMeta() {
        Jugador jugador = new Jugador("Rojo", ColorJugador.ROJO);
        jugador.getFichas()[0] = ReglasPartida.META - 2;

        assertEquals(ReglasPartida.EstadoMovimiento.EXCEDE_META,
                reglas.validarMovimiento(jugador, 0, 3));
        assertEquals(ReglasPartida.EstadoMovimiento.VALIDO,
                reglas.validarMovimiento(jugador, 0, 2));
    }

    @Test
    void capturaFichaRivalEnLaMismaCasillaYLaRegresaALaBase() {
        Jugador rojo = new Jugador("Rojo", ColorJugador.ROJO);
        Jugador azul = new Jugador("Azul", ColorJugador.AZUL);
        rojo.getFichas()[0] = 4;
        azul.getFichas()[0] = 55;

        List<Integer> capturadas = reglas.capturarFichas(rojo, azul, 0);

        assertEquals(List.of(0), capturadas);
        assertEquals(-1, azul.getFichas()[0]);
    }
}
