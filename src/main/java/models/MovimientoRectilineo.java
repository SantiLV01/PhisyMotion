package models;

public class MovimientoRectilineo {

    private double velocidadInicial;
    private double aceleracion;

    public MovimientoRectilineo(double velocidadInicial, double aceleracion) {
        this.velocidadInicial = velocidadInicial;
        this.aceleracion = aceleracion;
    }

    public double posicion(double t) {
        return velocidadInicial * t + 0.5 * aceleracion * t * t;
    }

    public double velocidad(double t) {
        return velocidadInicial + aceleracion * t;
    }
}
