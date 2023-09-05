import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Carregar o arquivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainApp.fxml"));
            Parent root = loader.load();

            // Criar uma cena com o layout carregado do FXML
            Scene scene = new Scene(root);

            // Definir a cena principal e exibir a janela
            primaryStage.setScene(scene);
            primaryStage.setTitle("Sua Aplicação JavaFX");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}