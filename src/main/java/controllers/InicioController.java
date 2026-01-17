package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class InicioController {

    @FXML
    private Button btnIniciar;

    @FXML
    private void abrirMenuPrincipal(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu_principal.fxml"));
Stage stage = (Stage) btnIniciar.getScene().getWindow(); 
Scene scene = new Scene(loader.load()); 
stage.setScene(scene);
stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error cargando menu_principal.fxml");
        }
    }
}
