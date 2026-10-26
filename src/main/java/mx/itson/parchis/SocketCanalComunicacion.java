package mx.itson.parchis;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

/**
 * Implementación concreta del canal basado en sockets TCP.
 */
public class SocketCanalComunicacion implements CanalComunicacion {

    private static final String HOST_RIVAL = "localhost";
    static final int PUERTO_RIVAL = 5000;

    private final Consumer<String> observadorErrores;
    private boolean avisoSinRed = false;

    public SocketCanalComunicacion(Consumer<String> observadorErrores) {
        this.observadorErrores = observadorErrores;
    }

    @Override
    public void enviar(String mensaje) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(HOST_RIVAL, PUERTO_RIVAL), 300);
            PrintWriter salida = new PrintWriter(
                    new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
            salida.println(mensaje);
        } catch (IOException e) {
            if (!avisoSinRed) {
                avisoSinRed = true;
                observadorErrores.accept("Sin conexión con " + HOST_RIVAL + ":" + PUERTO_RIVAL
                        + ". Se sigue jugando en esta computadora.");
            }
        }
    }
}
