package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utils.SimulacionData;
import java.io.IOException;

public class GraficosController {

    @FXML private AnchorPane graficoContainer;
    @FXML private Slider sliderTamano;
    @FXML private Button btnBack, btnConfig, btnSound;

    private LineChart<Number, Number> graficaActual;
    private boolean sonidoActivo = true;

    @FXML
    public void initialize() {

        // Evita pantallazo si aún no hay datos
        if (!SimulacionData.tiempo.isEmpty()) {
            generarPosicion();
        }
    }

    @FXML
    private void volverMenu() {
        cambiarEscena("/fxml/menu_principal.fxml");
    }

    @FXML
    private void abrirConfiguracion() {
        cambiarEscena("/fxml/configuracion.fxml");
    }

    private void cambiarEscena(String rutaFXML) {
        try {
            Stage stage = (Stage) btnBack.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource(rutaFXML)));
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("❌ No se pudo abrir: " + rutaFXML);
        }
    }

    @FXML
    private void toggleSonido() {
        sonidoActivo = !sonidoActivo;
        System.out.println("🔊 Sonido " + (sonidoActivo ? "ACTIVADO" : "DESACTIVADO"));
    }

    @FXML
    private void actualizarTamano() {
        if (graficaActual != null) {
            double scale = sliderTamano.getValue() / 100.0;
            graficaActual.setScaleX(scale);
            graficaActual.setScaleY(scale);
        }
    }


    @FXML
private void generarPosicion() {

    LineChart<Number, Number> chart = crearLineChart("Tiempo (s)", "Posición");

    XYChart.Series<Number, Number> serie = new XYChart.Series<>();

    int n = SimulacionData.tiempo.size();

    for (int i = 0; i < n; i++) {

        serie.getData().add(new XYChart.Data<>(
                SimulacionData.tiempo.get(i),
                SimulacionData.posicionY.get(i)
        ));
    }

    chart.getData().add(serie);
    mostrarGrafica(chart);
}


    @FXML
    private void generarVelocidad() {

        LineChart<Number, Number> chart = crearLineChart("Tiempo (s)", "Velocidad");

        XYChart.Series<Number, Number> serie = new XYChart.Series<>();

        int n = Math.min(SimulacionData.tiempo.size(), SimulacionData.velocidadReal.size());

        for (int i = 0; i < n; i++) {
            serie.getData().add(new XYChart.Data<>(
                    SimulacionData.tiempo.get(i),
                    SimulacionData.velocidadReal.get(i)
            ));
        }

        chart.getData().add(serie);
        mostrarGrafica(chart);
    }

    @FXML
    private void generarAceleracion() {

        LineChart<Number, Number> chart = crearLineChart("Tiempo (s)", "Aceleración");

        XYChart.Series<Number, Number> serie = new XYChart.Series<>();

        int n = Math.min(SimulacionData.tiempo.size(), SimulacionData.aceleracionReal.size());

        for (int i = 0; i < n; i++) {
            serie.getData().add(new XYChart.Data<>(
                    SimulacionData.tiempo.get(i),
                    SimulacionData.aceleracionReal.get(i)
            ));
        }

        chart.getData().add(serie);
        mostrarGrafica(chart);
    }


    private LineChart<Number, Number> crearLineChart(String ejeX, String ejeY) {

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel(ejeX);
        yAxis.setLabel(ejeY);

        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setLegendVisible(false);
        chart.setAnimated(false);

        // Fondo transparente
        chart.setStyle("-fx-background-color: transparent;");

        return chart;
    }

    private void mostrarGrafica(LineChart<Number, Number> chart) {

        graficoContainer.getChildren().clear();
        graficoContainer.getChildren().add(chart);

        chart.prefWidthProperty().bind(graficoContainer.widthProperty());
        chart.prefHeightProperty().bind(graficoContainer.heightProperty());

        graficaActual = chart;
        actualizarTamano();
    }
}

