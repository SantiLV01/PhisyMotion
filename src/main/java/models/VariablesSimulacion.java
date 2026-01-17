package models;

public class VariablesSimulacion {

    public double velocidadInicial;
    public double angulo; // en grados
    public double gravedad = 9.8;

    public VariablesSimulacion(double velocidadInicial, double angulo, double gravedad) {
        this.velocidadInicial = velocidadInicial;
        this.angulo = Math.toRadians(angulo);
        this.gravedad = gravedad;
    }
}
