package mx.itson.parchis;

import java.awt.Color;

public enum ColorJugador {
    ROJO("rojo", 1, 0xC23B2E, 0),
    AZUL("azul", 18, 0x2563A8, 11);

    private final String nombre;
    private final int casillaSalida;
    private final int rgb;
    private final int columnaBase;

    ColorJugador(String nombre, int casillaSalida, int rgb, int columnaBase) {
        this.nombre = nombre;
        this.casillaSalida = casillaSalida;
        this.rgb = rgb;
        this.columnaBase = columnaBase;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCasillaSalida() {
        return casillaSalida;
    }

    public int getRgb() {
        return rgb;
    }

    public Color getColorAwt() {
        return new Color(rgb);
    }

    public int getColumnaBase() {
        return columnaBase;
    }
}
