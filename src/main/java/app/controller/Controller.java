package app.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;

abstract class Controller {

  protected Stage stage;
  private final static String ERROR = "Failed to load .fxml file";

  /**
   * Constructor for controllers, takes current @param stage and @param fxmlpath and switches to next view upon creation.
   */
  Controller(Stage stage, String fxmlpath) {
    try {
      this.stage = stage;
      FXMLLoader loader = new FXMLLoader((Objects.requireNonNull(getClass().getProtectionDomain()
              .getClassLoader().getResource(fxmlpath))));
      loader.setController(this);
      Parent root = loader.load();
      stage.setScene(new Scene(root, 1280, 720));
      stage.setMaxHeight(1080);
      stage.setMaxWidth(1920);
      stage.setMinHeight(480);
      stage.setMinWidth(640);
    } catch (Exception e) {
      System.out.println(ERROR);
      e.printStackTrace();
    }
  }

  public void show() {
    stage.show();
  }
}
