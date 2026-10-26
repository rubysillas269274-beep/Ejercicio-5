package mx.itson.parchis;

/**
 * Abstracción del canal de comunicación para informar la jugada del rival.
 */
public interface CanalComunicacion {
    void enviar(String mensaje);
}
