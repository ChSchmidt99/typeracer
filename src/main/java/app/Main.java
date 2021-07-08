package app;

import app.controller.StartscreenController;
import app.model.StartScreenModel;
import client.Client;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/** Main Class and method. */
public class Main extends Application {

  @Override
  public void start(Stage stage) {
    try {
      stage.setOnCloseRequest(
          new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
              Platform.exit();
              ApplicationState.getInstance().close();
            }
          });
      stage.setTitle("TypeRacer");
      stage.setMaxHeight(1080);
      stage.setMaxWidth(1920);
      stage.setMinHeight(540);
      stage.setMinWidth(960);
      StartscreenController startscreenController =
          new StartscreenController(stage, new StartScreenModel());
      startscreenController.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    launch(args);
  }
}
