package app.controller;

import java.util.Objects;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

abstract class Controller {

  protected Stage stage;
  private static final String ERROR = "Failed to load .fxml file";
  private static final String ALERT_HEADER = "Error";

  /**
   * Constructor for controllers,
   * takes current
   * @param stage and
   * @param fxmlpath and switches to next view upon creation.
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

  void displayError(String errormessage) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle(ALERT_HEADER);
    alert.setContentText(errormessage);
    alert.setHeaderText(null);
    alert.showAndWait();
  }

  public void show() {
    stage.show();
  }
}
