package mx.itson.parchis;

import java.util.List;

public interface RepositorioEstadisticas {
    void crearTabla() throws RepositorioEstadisticasException;

    void guardar(Jugador ganador) throws RepositorioEstadisticasException;

    List<RegistroEstadistica> leer() throws RepositorioEstadisticasException;
}
