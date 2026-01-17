package controllers;

import utils.PopupManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.io.IOException;
import utils.SimulacionData;
import javafx.scene.Parent;

public class VariablesController {

    @FXML private TextField txtVelocidad;
    @FXML private TextField txtAngulo;
    @FXML private TextField txtGravedad;
    @FXML private TextField txtMasa;
    @FXML private ToggleButton toggleAire;

    @FXML private Button btnAplicar;
    @FXML private Button btnReset;
    @FXML private Button btnBack;
    @FXML private Button btnConfig;
    @FXML private Button btnSound;

    private boolean sonidoActivo = true;

    @FXML
    private void initialize() {
        txtVelocidad.setText(String.valueOf(SimulacionData.velocidad));
        txtAngulo.setText(String.valueOf(SimulacionData.angulo));
        txtGravedad.setText(String.valueOf(SimulacionData.gravedad));
        txtMasa.setText(String.valueOf(SimulacionData.masa));

        toggleAire.setSelected(SimulacionData.resistenciaAire);
        actualizarToggle();
    }

    // ================= APLICAR =================
    @FXML
    private void aplicarValores() {

        if (txtVelocidad.getText().isEmpty() || txtAngulo.getText().isEmpty()) {
            PopupManager.mostrarError("Campos incompletos", "Debes ingresar velocidad y ángulo.");
            return;
        }

        try {

            double v = limpiar(txtVelocidad.getText());
            double a = limpiar(txtAngulo.getText());
            double g = limpiar(txtGravedad.getText());
            double m = limpiar(txtMasa.getText());

            if (v < 0) throw new Exception("La velocidad no puede ser negativa");
            if (a < 0 || a > 90) throw new Exception("El ángulo debe estar entre 0° y 90°");
            if (g <= 0) throw new Exception("La gravedad debe ser mayor que 0");
            if (m <= 0) throw new Exception("La masa debe ser mayor que 0");

            SimulacionData.velocidad = v;
            SimulacionData.angulo = a;
            SimulacionData.gravedad = g;
            SimulacionData.masa = m;
            SimulacionData.resistenciaAire = toggleAire.isSelected();

            // Actualizar simulación
            if (SimulacionesController.instancia != null) {
                SimulacionesController.instancia.actualizarDesdeVariables();
            }

            PopupManager.mostrarFelicitacion(
                    "¡Listo!",
                    "Parámetros guardados correctamente."
            );

        } catch (NumberFormatException e) {

            marcarError(txtVelocidad);
            marcarError(txtAngulo);
            marcarError(txtGravedad);
            marcarError(txtMasa);

            PopupManager.mostrarError(
                    "Entrada inválida",
                    "Solo se permiten números."
            );

        } catch (Exception e) {

            PopupManager.mostrarError(
                    "Error en datos",
                    e.getMessage()
            );
        }
    }

    private double limpiar(String texto) {
        return Double.parseDouble(texto.replace("kg", "").replace("°", "").trim());
    }

    @FXML
    private void restablecerValores() {

        txtVelocidad.setText("20");
        txtAngulo.setText("45");
        txtGravedad.setText("9.8");
        txtMasa.setText("1");
        toggleAire.setSelected(false);
        actualizarToggle();

        SimulacionData.velocidad = 20;
        SimulacionData.angulo = 45;
        SimulacionData.gravedad = 9.8;
        SimulacionData.masa = 1;
        SimulacionData.resistenciaAire = false;

        if (SimulacionesController.instancia != null) {
            SimulacionesController.instancia.actualizarDesdeVariables();
        }

        PopupManager.mostrarFelicitacion(
                "Restablecido",
                "Valores reiniciados correctamente."
        );
    }

    @FXML
    private void toggleResistencia() {
        actualizarToggle();
    }

    private void actualizarToggle() {
        if (toggleAire.isSelected()) {
            toggleAire.setText("ON");
            toggleAire.getStyleClass().remove("toggle-off");
            toggleAire.getStyleClass().add("toggle-on");
        } else {
            toggleAire.setText("OFF");
            toggleAire.getStyleClass().remove("toggle-on");
            toggleAire.getStyleClass().add("toggle-off");
        }
    }
    @FXML
    private void toggleSonido() {
        sonidoActivo = !sonidoActivo;
        System.out.println("🔊 Sonido: " + (sonidoActivo ? "ON" : "OFF"));
    }

    @FXML
    private void abrirConfiguracion(javafx.event.ActionEvent event) {
        cambiarEscena("/fxml/configuracion.fxml", event);
    }

    @FXML
    private void volverMenu(javafx.event.ActionEvent event) {
        cambiarEscena("/fxml/menu_principal.fxml", event);
    }

    private void cambiarEscena(String ruta, javafx.event.ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(ruta));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
        } catch (IOException e) {
            PopupManager.mostrarError("Error", "No se pudo abrir la ventana.");
        }
    }

    private void marcarError(TextField campo) {
        campo.setStyle("-fx-border-color:red;");
    }
}
