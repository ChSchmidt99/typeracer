package app;

import java.util.Objects;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main Class and method, starts Stage for Startscreen.
 */
public class Main extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getProtectionDomain()
            .getClassLoader().getResource("gui/startscreen.fxml"))));
    primaryStage.setTitle("TypeRacer");
    primaryStage.setScene(new Scene(root, 1280, 720));
    primaryStage.show();
  }


  public static void main(String[] args) {
    launch(args);
  }
}
