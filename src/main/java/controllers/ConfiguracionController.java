package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.util.prefs.Preferences;

public class ConfiguracionController {

    @FXML private Slider sliderVolumen;
    @FXML private ComboBox<String> comboTema;
    @FXML private ComboBox<String> comboIdioma;
    @FXML private Button btnGuardar;
    @FXML private Button btnVolver;

    private final Preferences prefs = Preferences.userNodeForPackage(ConfiguracionController.class);

    private Scene parentScene; 
    public void setParentScene(Scene scene) {
        this.parentScene = scene;
    }

    @FXML
    public void initialize() {
        comboTema.getItems().addAll("Claro", "Oscuro", "Azul");
        comboIdioma.getItems().addAll("Español", "Inglés", "Portugués");

        sliderVolumen.setValue(prefs.getDouble("volumen", 80.0));
        comboTema.setValue(prefs.get("tema", "Claro"));
        comboIdioma.setValue(prefs.get("idioma", "Español"));

        btnGuardar.setOnAction(e -> guardarConfiguracion());
    }

    private void guardarConfiguracion() {
        prefs.putDouble("volumen", sliderVolumen.getValue());
        if (comboTema.getValue() != null) prefs.put("tema", comboTema.getValue());
        if (comboIdioma.getValue() != null) prefs.put("idioma", comboIdioma.getValue());
        System.out.println("⚙ Preferencias guardadas correctamente");
    }

    @FXML
    private void onVolverClick(MouseEvent event) {
        try {
            Stage stage = (Stage) btnVolver.getScene().getWindow();
            if (parentScene != null) {
              
                stage.setScene(parentScene);
            } else {
       
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/inicio.fxml"));
                AnchorPane root = loader.load();
                stage.setScene(new Scene(root));
            }
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
