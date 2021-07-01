package app;

import app.controller.StartscreenController;
import javafx.application.Application;
import javafx.stage.Stage;

/** Main Class and method. */
public class Main extends Application {

  @Override
  public void start(Stage stage) {
    StartscreenController startscreenController = new StartscreenController(stage);
    startscreenController.show();
    stage.setTitle("TypeRacer");
  }

  public static void main(String[] args) {
    launch(args);
  }
}
