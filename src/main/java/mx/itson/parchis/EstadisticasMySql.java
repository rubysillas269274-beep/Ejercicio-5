package mx.itson.parchis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsable de guardar y consultar las estadísticas de la partida.
 */
public class EstadisticasMySql implements RepositorioEstadisticas {

    private static final String URL_BD = "jdbc:mysql://localhost:3306/parchis?createDatabaseIfNotExist=true&connectTimeout=2000";
    private static final String USUARIO_BD = "root";
    private static final String CLAVE_BD = "";

    private SQLException errorInicializacion;

    @Override
    public void crearTabla() throws RepositorioEstadisticasException {
        try (Connection conexion = DriverManager.getConnection(URL_BD, USUARIO_BD, CLAVE_BD);
                Statement sentencia = conexion.createStatement()) {
            sentencia.executeUpdate("CREATE TABLE IF NOT EXISTS estadisticas ("
                    + "color VARCHAR(10) PRIMARY KEY, "
                    + "jugadas INT NOT NULL DEFAULT 0, "
                    + "ganadas INT NOT NULL DEFAULT 0)");
            errorInicializacion = null;
        } catch (SQLException e) {
            errorInicializacion = e;
            throw new BaseDatosNoDisponibleException(e);
        }
    }

    @Override
    public List<RegistroEstadistica> leer() throws RepositorioEstadisticasException {
        comprobarInicializacion();
        List<RegistroEstadistica> registros = new ArrayList<>();
        try (Connection conexion = DriverManager.getConnection(URL_BD, USUARIO_BD, CLAVE_BD);
                Statement sentencia = conexion.createStatement();
                ResultSet resultado = sentencia.executeQuery(
                        "SELECT color, jugadas, ganadas FROM estadisticas ORDER BY color DESC")) {
            while (resultado.next()) {
                registros.add(new RegistroEstadistica(
                        resultado.getString("color"),
                        resultado.getInt("jugadas"),
                        resultado.getInt("ganadas")));
            }
        } catch (SQLException e) {
            throw new RepositorioEstadisticasException(e.getMessage(), e);
        }
        return registros;
    }

    @Override
    public void guardar(Jugador ganador) throws RepositorioEstadisticasException {
        comprobarInicializacion();
        String sql = "INSERT INTO estadisticas (color, jugadas, ganadas) VALUES (?, 1, ?) "
                + "ON DUPLICATE KEY UPDATE jugadas = jugadas + 1, ganadas = ganadas + ?";
        try (Connection conexion = DriverManager.getConnection(URL_BD, USUARIO_BD, CLAVE_BD);
                PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            for (ColorJugador color : ColorJugador.values()) {
                int gano = color == ganador.getColor() ? 1 : 0;
                sentencia.setString(1, color.getNombre());
                sentencia.setInt(2, gano);
                sentencia.setInt(3, gano);
                sentencia.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RepositorioEstadisticasException(e.getMessage(), e);
        }
    }

    private void comprobarInicializacion() throws RepositorioEstadisticasException {
        if (errorInicializacion != null) {
            throw new BaseDatosNoDisponibleException(errorInicializacion);
        }
    }
}
