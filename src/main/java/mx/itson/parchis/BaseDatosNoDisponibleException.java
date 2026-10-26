package mx.itson.parchis;

public class BaseDatosNoDisponibleException extends RepositorioEstadisticasException {
    public BaseDatosNoDisponibleException(Throwable causa) {
        super(causa.getMessage(), causa);
    }
}
