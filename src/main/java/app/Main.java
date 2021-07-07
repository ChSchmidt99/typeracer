package app;

import app.controller.StartscreenController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/** Main Class and method. */
public class Main extends Application {

  @Override
  public void start(Stage stage) {
    try {
      stage.setTitle("TypeRacer");
      stage.setMaxHeight(1080);
      stage.setMaxWidth(1920);
      stage.setMinHeight(540);
      stage.setMinWidth(960);
      StartscreenController startscreenController = new StartscreenController(stage);
      startscreenController.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
