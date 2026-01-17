package utils;

import java.util.ArrayList;

public class SimulacionData {

    public static double velocidad = 0;
    public static double aceleracion = 0;
    public static double angulo = 0;
    public static double gravedad = 9.8;
    public static double masa = 1;

    public static boolean resistenciaAire = false;

    public static ArrayList<Double> tiempo = new ArrayList<>();

    public static ArrayList<Double> posicionX = new ArrayList<>();
    public static ArrayList<Double> posicionY = new ArrayList<>();
    public static ArrayList<Double> posicion = new ArrayList<>(); 

    public static ArrayList<Double> velocidadX = new ArrayList<>();
    public static ArrayList<Double> velocidadY = new ArrayList<>();
    public static ArrayList<Double> velocidadReal = new ArrayList<>();

    public static ArrayList<Double> aceleracionReal = new ArrayList<>();

    public static ArrayList<Double> fuerza = new ArrayList<>();

    public static ArrayList<Double> energiaCinetica = new ArrayList<>();
    public static ArrayList<Double> energiaPotencial = new ArrayList<>();

    public static ArrayList<Double> altura = new ArrayList<>();
public static String tipoMovimiento = "MRU";

    public static void agregarDatos(double t, double x, double y, double vx, double vy, double ax) {

        tiempo.add(t);
        posicionX.add(x);
        posicionY.add(y);
        posicion.add(y);  

        velocidadX.add(vx);
        velocidadY.add(vy);
        velocidadReal.add(Math.sqrt(vx * vx + vy * vy));

        aceleracionReal.add(ax);

        fuerza.add(masa * ax);

        energiaCinetica.add(0.5 * masa * Math.pow(Math.sqrt(vx*vx + vy*vy), 2));
        energiaPotencial.add(masa * gravedad * y);

        altura.add(y);
    }

    public static void limpiarDatos() {
        tiempo.clear();
        posicionX.clear();
        posicionY.clear();
        posicion.clear();
        velocidadX.clear();
        velocidadY.clear();
        velocidadReal.clear();
        aceleracionReal.clear();
        fuerza.clear();
        energiaCinetica.clear();
        energiaPotencial.clear();
        altura.clear();
    }
}
