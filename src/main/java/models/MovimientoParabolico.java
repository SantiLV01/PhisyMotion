package models;

public class MovimientoParabolico {

    private double v0;
    private double angulo; // en radianes
    private double g;

    public MovimientoParabolico(double velocidadInicial, double anguloGrados, double gravedad) {
        this.v0 = velocidadInicial;
        this.angulo = Math.toRadians(anguloGrados);
        this.g = gravedad;
    }

    public double posX(double t) {
        return v0 * Math.cos(angulo) * t;
    }

    public double posY(double t) {
        return v0 * Math.sin(angulo) * t - 0.5 * g * t * t;
    }

    public double velocidadX() {
        return v0 * Math.cos(angulo);
    }

    public double velocidadY(double t) {
        return v0 * Math.sin(angulo) - g * t;
    }

    public double tiempoTotal() {
        return (2 * v0 * Math.sin(angulo)) / g;
    }

    public double alturaMaxima() {
        return (Math.pow(v0 * Math.sin(angulo), 2)) / (2 * g);
    }

    public double alcance() {
        return Math.pow(v0, 2) * Math.sin(2 * angulo) / g;
    }
}
