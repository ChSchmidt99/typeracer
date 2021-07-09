package app;

import app.screens.start.StartScreenController;
import app.screens.start.StartScreenModel;
import app.screens.start.StartScreenView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/** Main Class and method. */
public class Main extends Application {

  @Override
  public void start(Stage stage) {
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

    StartScreenView view = new StartScreenView(stage);
    StartScreenModel model = new StartScreenModel();
    new StartScreenController(model, view);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
