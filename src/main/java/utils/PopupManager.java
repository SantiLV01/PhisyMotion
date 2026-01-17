package utils;

import controllers.PopupMensajeController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopupManager {

    public static void mostrarFelicitacion(String titulo, String mensaje) {
        abrirPopup("/fxml/popup_felicidades.fxml", titulo, mensaje);
    }

    public static void mostrarError(String titulo, String mensaje) {
        abrirPopup("/fxml/popup_error.fxml", titulo, mensaje);
    }

    private static void abrirPopup(String ruta, String titulo, String mensaje) {

        try {
            FXMLLoader loader = new FXMLLoader(PopupManager.class.getResource(ruta));
            Parent root = loader.load();

            PopupMensajeController controller = loader.getController();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.setResizable(false);

            controller.setData(titulo, mensaje, stage);

            stage.showAndWait();

        } catch (Exception e) {
            System.out.println("❌ ERROR ABIERTENDO POPUP: " + ruta);
            e.printStackTrace();
        }
    }
}
