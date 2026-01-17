package controllers;

import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import utils.SimulacionData;

import java.io.IOException;

public class SimulacionesController {

    public static SimulacionesController instancia;

    @FXML private AnchorPane panelAnimacion;

    @FXML private ImageView objetoMovible;
    @FXML private ImageView trayectoriaImg;
    @FXML private ImageView imgFondoEscena;
    @FXML private Canvas canvasTrayectoria;

    private AnimationTimer animacion;
    private String tipoSimulacion = "MRU";

    private final DoubleProperty velocidad   = new SimpleDoubleProperty();
    private final DoubleProperty aceleracion = new SimpleDoubleProperty();
    private final DoubleProperty angulo      = new SimpleDoubleProperty();
    private final DoubleProperty gravedad    = new SimpleDoubleProperty(9.8);

    @FXML
    private void initialize() {

        instancia = this;

        panelAnimacion.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null && newScene.getWindow() instanceof Stage stage) {
                stage.sizeToScene();
                stage.centerOnScreen();
            }
        });
    }

    @FXML
    private void toggleSonido() {
        System.out.println("🔊 Sonido ON / OFF (Simulación)");
    }

    public void actualizarDesdeVariables() {
if (!tipoSimulacion.equals("PROYECTIL")) {
    SimulacionData.altura.clear();
}

        velocidad.set(SimulacionData.velocidad);
        aceleracion.set(SimulacionData.aceleracion);
        angulo.set(SimulacionData.angulo);
        gravedad.set(SimulacionData.gravedad);

        SimulacionData.limpiarDatos();

        detenerSimulacion();
        configurarVista();
        iniciarSimulacion();
    }

    @FXML
    private void abrirConfig() {
        cambiarPantalla("/fxml/configuracion.fxml");
    }

    public void setTipoSimulacion(String tipo) {
        tipoSimulacion = tipo.toUpperCase();
        actualizarDesdeVariables();
        SimulacionData.tipoMovimiento = tipoSimulacion;

    }

    private void configurarVista() {

        imgFondoEscena.setImage(new Image(getClass().getResourceAsStream("/images/fondito.png")));

        if (trayectoriaImg == null) return;

        switch (tipoSimulacion) {

            case "MRU" -> {
                cargarObjeto("CARRO");
                trayectoriaImg.setVisible(false);
            }

            case "MRUV" -> {
                cargarObjeto("CAJA");
                trayectoriaImg.setVisible(false);
            }

            case "PROYECTIL" -> {
                cargarObjeto("PELOTA");
                trayectoriaImg.setVisible(true);
            }
        }
    }

    private void cargarObjeto(String nombre) {
        objetoMovible.setImage(new Image(getClass().getResourceAsStream("/images/" + nombre + ".png")));
        objetoMovible.setLayoutX(30);
    }
    private void iniciarSimulacion() {

        if (canvasTrayectoria != null) {
            canvasTrayectoria.getGraphicsContext2D().clearRect(
                    0, 0,
                    canvasTrayectoria.getWidth(),
                    canvasTrayectoria.getHeight()
            );
        }

        detenerSimulacion();

        switch (tipoSimulacion) {
            case "MRU"       -> iniciarMRU();
            case "MRUV"      -> iniciarMRUV();
            case "PROYECTIL" -> iniciarProyectil();
        }
    }

    private double calcularSuelo() {
        return imgFondoEscena.getLayoutY()
                + imgFondoEscena.getFitHeight()
                - objetoMovible.getFitHeight();
    }

    private void iniciarMRU() {

        final double suelo = calcularSuelo();
        objetoMovible.setLayoutY(suelo);

        final double[] tiempo = {0};
        final long[]   last   = {0};

        animacion = new AnimationTimer() {
            @Override
            public void handle(long now) {

                if (last[0] > 0) {

                    double dt = (now - last[0]) / 1e9;
                    tiempo[0] += dt;

                    double nuevoX = objetoMovible.getLayoutX() + velocidad.get() * dt;

                    double limiteDer = imgFondoEscena.getLayoutX()
                            + imgFondoEscena.getFitWidth()
                            - objetoMovible.getFitWidth();

                    if (nuevoX >= limiteDer) {
                        nuevoX = limiteDer;
                        detenerSimulacion();
                    }

                    objetoMovible.setLayoutX(nuevoX);
                    objetoMovible.setLayoutY(suelo);

                    SimulacionData.agregarDatos(
                            tiempo[0],
                            nuevoX,
                            suelo,
                            velocidad.get(),
                            0,
                            0
                    );
                }

                last[0] = now;
            }
        };

        animacion.start();
    }

    private void iniciarMRUV() {

        final double suelo = calcularSuelo();
        objetoMovible.setLayoutY(suelo);

        DoubleProperty vy     = new SimpleDoubleProperty(velocidad.get());
        final double[] tiempo = {0};
        final long[]   last   = {0};

        animacion = new AnimationTimer() {
            @Override
            public void handle(long now) {

                if (last[0] > 0) {

                    double dt = (now - last[0]) / 1e9;
                    tiempo[0] += dt;

                    vy.set(vy.get() + aceleracion.get() * dt);
                    double nuevoY = objetoMovible.getLayoutY() + vy.get() * dt;

                    if (nuevoY >= suelo) {
                        nuevoY = suelo;
                        detenerSimulacion();
                    }

                    objetoMovible.setLayoutY(nuevoY);

                    SimulacionData.agregarDatos(
                            tiempo[0],
                            objetoMovible.getLayoutX(),
                            nuevoY,
                            0,
                            vy.get(),
                            aceleracion.get()
                    );
                }

                last[0] = now;
            }
        };

        animacion.start();
    }

    private void iniciarProyectil() {

        double suelo      = calcularSuelo();
        double limiteIzq  = imgFondoEscena.getLayoutX();
        double limiteDer  = imgFondoEscena.getLayoutX()
                + imgFondoEscena.getFitWidth()
                - objetoMovible.getFitWidth();
        double techo      = imgFondoEscena.getLayoutY();

        GraphicsContext g = canvasTrayectoria.getGraphicsContext2D();
        g.setStroke(Color.BLUE);
        g.setLineWidth(2);

        final double v0  = velocidad.get();
        final double ang = Math.toRadians(angulo.get());

        final double[] x  = {limiteIzq + 30};
        final double[] y  = {suelo};
        final double[] vx = {v0 * Math.cos(ang)};
        final double[] vy = {v0 * Math.sin(ang)};

        objetoMovible.setLayoutX(x[0]);
        objetoMovible.setLayoutY(y[0]);

        final double[] px    = {x[0]};
        final double[] py    = {y[0]};
        final long[]   last  = {0};
        final long     start = System.nanoTime();

        animacion = new AnimationTimer() {
            @Override
            public void handle(long now) {

                if (last[0] > 0) {

                    double dt      = (now - last[0]) / 1e9;
                    double tiempo  = (now - start) / 1e9;
                    boolean parar  = false;

                    x[0] += vx[0] * dt;
                    y[0] -= vy[0] * dt;
                    vy[0] -= gravedad.get() * dt;

                    if (SimulacionData.resistenciaAire) {
                        vx[0] *= 0.99;
                        vy[0] *= 0.99;
                    }

                    if (y[0] >= suelo) {
                        y[0] = suelo;
                        parar = true;
                    }
                    if (y[0] <= techo) {
                        y[0] = techo;
                        vy[0] *= -0.4; 
                    }
                    if (x[0] >= limiteDer) {
                        x[0] = limiteDer;
                        parar = true;
                    }
                    if (x[0] <= limiteIzq) {
                        x[0] = limiteIzq;
                        parar = true;
                    }

                    g.strokeLine(px[0], py[0], x[0], y[0]);
                    px[0] = x[0];
                    py[0] = y[0];

                    double v = Math.sqrt(vx[0] * vx[0] + vy[0] * vy[0]);

                    SimulacionData.agregarDatos(
                            tiempo,
                            x[0],
                            y[0],
                            vx[0],
                            vy[0],
                            -gravedad.get()
                    );

                    SimulacionData.energiaCinetica.add(
                            0.5 * SimulacionData.masa * v * v
                    );
                    SimulacionData.altura.add(y[0]);

                    objetoMovible.setLayoutX(x[0]);
                    objetoMovible.setLayoutY(y[0]);

                    if (parar) detenerSimulacion();
                }

                last[0] = now;
            }
        };

        animacion.start();
    }

    private void detenerSimulacion() {
        if (animacion != null) animacion.stop();
    }

    @FXML
    private void abrirVariables() {
        cambiarPantalla("/fxml/variables.fxml");
    }

    @FXML
    private void abrirGraficas() {
        cambiarPantalla("/fxml/graficos.fxml");
    }

    @FXML
    private void abrirResultados() {
        cambiarPantalla("/fxml/resultados.fxml");
    }

    @FXML
    private void volverMenu() {
        cambiarPantalla("/fxml/menu_principal.fxml");
    }

    private void cambiarPantalla(String ruta) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource(ruta));
            Stage stage = (Stage) panelAnimacion.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
