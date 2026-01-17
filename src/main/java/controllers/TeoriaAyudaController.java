package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class TeoriaAyudaController {

    @FXML private Button btnConfiguracion;
    @FXML private Button btnVolver;
    @FXML private Button btnSonido;

    private Scene parentScene;

    @FXML
    public void initialize() {
       
    }

    public void setParentScene(Scene scene) {
        this.parentScene = scene;
    }

    @FXML
    private void onVolverClick() {

        if (parentScene != null) {

            Stage stage = (Stage) btnVolver.getScene().getWindow();

            stage.setScene(parentScene);
            stage.sizeToScene();
            stage.centerOnScreen();
            stage.show();

        } else {
            System.out.println("⚠ No se asignó la escena padre (parentScene)");
        }
    }

    @FXML
    private void onConfigClick() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/configuracion.fxml"));
            Stage stage = (Stage) btnConfiguracion.getScene().getWindow();

            Scene currentScene = stage.getScene();

            Scene configScene = new Scene(loader.load());
            stage.setScene(configScene);
            stage.sizeToScene();
            stage.centerOnScreen();

            ConfiguracionController controller = loader.getController();
            controller.setParentScene(currentScene);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("❌ Error cargando configuracion.fxml");
        }
    }

    @FXML
    private void onSonidoClick() {
        System.out.println("🔊 Opciones de sonido (pendiente)");
    }
}
    