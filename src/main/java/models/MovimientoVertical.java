package models;

public class MovimientoVertical {

    private double velocidadInicial;
    private double gravedad;

    public MovimientoVertical(double velocidadInicial, double gravedad) {
        this.velocidadInicial = velocidadInicial;
        this.gravedad = gravedad;
    }

    public double posicionY(double t) {
        return velocidadInicial * t - 0.5 * gravedad * t * t;
    }

    public double velocidadY(double t) {
        return velocidadInicial - gravedad * t;
    }

    public double tiempoMaximo() {
        return velocidadInicial / gravedad;
    }

    public double alturaMaxima() {
        return (velocidadInicial * velocidadInicial) / (2 * gravedad);
    }
}
