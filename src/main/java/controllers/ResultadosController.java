package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import utils.SimulacionData;

import java.io.IOException;

public class ResultadosController {

    @FXML private TableView<ResultadoData> tablaDatos;
    @FXML private TableColumn<ResultadoData, Double> colTiempo;
    @FXML private TableColumn<ResultadoData, Double> colPosicion;
    @FXML private TableColumn<ResultadoData, Double> colVelocidad;
    @FXML private TableColumn<ResultadoData, Double> colAceleracion;

    @FXML private Label distanciaLbl, tiempoLbl, velocidadLbl, movimientoLbl;

    @FXML private Button btnBack, btnConfig, btnSound;

    private boolean sonidoActivo = true;

    @FXML
    public void initialize() {

        colTiempo.setCellValueFactory(new PropertyValueFactory<>("tiempo"));
        colPosicion.setCellValueFactory(new PropertyValueFactory<>("posicion"));
        colVelocidad.setCellValueFactory(new PropertyValueFactory<>("velocidad"));
        colAceleracion.setCellValueFactory(new PropertyValueFactory<>("aceleracion"));

        cargarTabla();
        calcularResumen();
    }

    private void cargarTabla() {

        tablaDatos.getItems().clear();

        int n = SimulacionData.tiempo.size();

        for (int i = 0; i < n; i++) {

            double tiempo = SimulacionData.tiempo.get(i);

            double pos = !SimulacionData.posicionX.isEmpty()
                    ? SimulacionData.posicionX.get(i)
                    : !SimulacionData.posicionY.isEmpty()
                        ? SimulacionData.posicionY.get(i)
                        : !SimulacionData.altura.isEmpty()
                            ? SimulacionData.altura.get(i)
                            : 0;

            double vel = !SimulacionData.velocidadReal.isEmpty()
                    ? SimulacionData.velocidadReal.get(i)
                    : SimulacionData.velocidad;

            double acel = !SimulacionData.aceleracionReal.isEmpty()
                    ? SimulacionData.aceleracionReal.get(i)
                    : 0;

            tablaDatos.getItems().add(
                    new ResultadoData(tiempo, pos, vel, acel)
            );
        }
    }

    private void calcularResumen() {

        if (SimulacionData.tiempo.isEmpty()) {
            movimientoLbl.setText("⚠ Sin datos");
            distanciaLbl.setText("-");
            tiempoLbl.setText("-");
            velocidadLbl.setText("-");
            return;
        }

        int n = SimulacionData.tiempo.size() - 1;

        double tiempoFinal = SimulacionData.tiempo.get(n);

        double distancia = !SimulacionData.posicionX.isEmpty()
                ? SimulacionData.posicionX.get(n)
                : !SimulacionData.posicionY.isEmpty()
                    ? SimulacionData.posicionY.get(n)
                    : !SimulacionData.altura.isEmpty()
                        ? SimulacionData.altura.get(0) - SimulacionData.altura.get(n)
                        : 0;

        double vel = !SimulacionData.velocidadReal.isEmpty()
                ? SimulacionData.velocidadReal.get(n)
                : SimulacionData.velocidad;

        tiempoLbl.setText(String.format("Tiempo: %.2f s", tiempoFinal));
        distanciaLbl.setText(String.format("Distancia: %.2f m", distancia));
        velocidadLbl.setText(String.format("Velocidad final: %.2f m/s", vel));
        movimientoLbl.setText("Movimiento: " + detectarMovimiento());
    }

    private String detectarMovimiento() {

    switch (SimulacionData.tipoMovimiento) {

        case "MRU":
            return "MRU";

        case "MRUV":
            return "MRUV";

        case "PROYECTIL":
            return "Proyectil";

        default:
            return "No definido";
    }
}


    @FXML
    private void volverMenu(){
        cambiarVentana("/fxml/menu_principal.fxml");
    }

    @FXML
    private void abrirConfiguracion(){
        cambiarVentana("/fxml/configuracion.fxml");
    }

    @FXML
    private void abrirSimulacion(){
        cambiarVentana("/fxml/menu_principal.fxml");
    }

    @FXML
    private void toggleSonido(){
        sonidoActivo = !sonidoActivo;
        System.out.println("🔊 Sonido: " + (sonidoActivo ? "ON" : "OFF"));
    }
    private void cambiarVentana(String ruta){
        try{
            Stage stage = (Stage) btnBack.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource(ruta)));
            stage.setScene(scene);
            stage.centerOnScreen();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static class ResultadoData{

        private final Double tiempo;
        private final Double posicion;
        private final Double velocidad;
        private final Double aceleracion;

        public ResultadoData(Double t, Double p, Double v, Double a){
            this.tiempo = t;
            this.posicion = p;
            this.velocidad = v;
            this.aceleracion = a;
        }

        public Double getTiempo(){ return tiempo; }
        public Double getPosicion(){ return posicion; }
        public Double getVelocidad(){ return velocidad; }
        public Double getAceleracion(){ return aceleracion; }
    }
}
