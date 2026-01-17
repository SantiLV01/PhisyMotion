package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {

    private static Stage stage;

    public static void setStage(Stage s) {
        stage = s;
    }

    public static void changeScene(String fxml) {
        try {
            Parent root = FXMLLoader.load(SceneManager.class.getResource(
                "/com/phisymotion/views/" + fxml
            ));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
    