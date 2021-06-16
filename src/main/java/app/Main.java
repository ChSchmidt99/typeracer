package app;

import java.util.Objects;

import app.controller.StartscreenController;
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
  public void start(Stage stage) throws Exception {
    StartscreenController startscreenController = new StartscreenController(stage);
    startscreenController.show();
    stage.setTitle("TypeRacer");
  }

  public static void main(String[] args) {
    launch(args);
  }
}
