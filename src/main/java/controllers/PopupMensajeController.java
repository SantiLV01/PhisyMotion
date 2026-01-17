package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PopupMensajeController {

    @FXML private Label lblTitulo;

    @FXML private Button btnContinuar;
    @FXML private Button btnReintentar;

    private Stage popupStage;

    public void setData(String titulo, String mensaje, Stage stage) {
        lblTitulo.setText(mensaje);
        this.popupStage = stage;
    }

    @FXML
    private void initialize() {

        if (btnContinuar != null) {
            btnContinuar.setOnAction(e -> cerrar());
        }

        if (btnReintentar != null) {
            btnReintentar.setOnAction(e -> cerrar());
        }
    }

    private void cerrar() {
        if (popupStage != null)
            popupStage.close();
    }
}
