package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.Node;
import javafx.scene.Parent;

public class MenuPrincipalController {

    @FXML private Button btnConfig;
    @FXML private Button btnSonido;
    @FXML private Button btnVolver;
    @FXML private Button btnMRU;
    @FXML private Button btnMRUV;
    @FXML private Button btnProyectiles;
    @FXML private Button btnAyuda;

    @FXML
    private void abrirConfiguracion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/configuracion.fxml"));
            Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();

            Scene parentScene = stage.getScene(); 
            Scene configScene = new Scene(loader.load());
            stage.setScene(configScene);
            stage.show();

            ConfiguracionController controller = loader.getController();
            controller.setParentScene(parentScene);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("❌ Error cargando configuracion.fxml");
        }
    }

    @FXML
    private void volverAlInicio(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/inicio.fxml"));
            Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 900, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("❌ Error cargando inicio.fxml");
        }
    }

  @FXML 
private void abrirAyuda(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/teoria_ayuda.fxml"));
        Parent root = loader.load();

        TeoriaAyudaController controller = loader.getController();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene escenaActual = stage.getScene();

        controller.setParentScene(escenaActual);

        stage.setScene(new Scene(root));
        stage.centerOnScreen();
        stage.show();

    } catch (IOException e) {
        e.printStackTrace();
        System.out.println("❌ Error abriendo Teoría/Ayuda");
    }
}


    @FXML private void abrirSimulacionMRU(ActionEvent event) { abrirSimulacion("MRU"); }
    @FXML private void abrirSimulacionMRUV(ActionEvent event) { abrirSimulacion("MRUV"); }
    @FXML private void abrirSimulacionProyectiles(ActionEvent event) { abrirSimulacion("PROYECTIL"); }

    private void abrirSimulacion(String tipo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/simulaciones.fxml"));
            Scene scene = new Scene(loader.load());

            SimulacionesController controller = loader.getController();
            controller.setTipoSimulacion(tipo);

            Stage stage = (Stage) btnMRU.getScene().getWindow();
            stage.setScene(scene);
            stage.show(); 

            System.out.println("🟢 Simulación cargada: " + tipo);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Error cargando simulaciones.fxml");
        }
    }

    private void abrirPantalla(String ruta) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
            Stage stage = (Stage) btnMRU.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 900, 600));
            stage.show();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void abrirSonido() {
        System.out.println("🔊 Opciones de sonido próximamente...");
    }
}
