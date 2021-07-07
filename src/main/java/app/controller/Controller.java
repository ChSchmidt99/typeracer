package app.controller;

import java.util.Objects;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

abstract class Controller {

  protected Stage stage;
  private final Parent root;
  private static final String ERROR = "Failed to load .fxml file";
  private static final String ALERT_HEADER = "Error";

  Controller(Stage stage, String fxmlpath) throws IOException {
    this.stage = stage;
    FXMLLoader loader =
        new FXMLLoader(
            (Objects.requireNonNull(
                getClass().getProtectionDomain().getClassLoader().getResource(fxmlpath))));
    loader.setController(this);
    this.root = loader.load();
    if (stage.getScene() == null) {
      stage.setScene(new Scene(root, 960, 540));
    }
  }

  void displayError(String errormessage) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle(ALERT_HEADER);
    alert.setContentText(errormessage);
    alert.setHeaderText(null);
    alert.showAndWait();
  }

  /*
   * Displays current stage.
   */
  public void show() {
    stage.getScene().setRoot(root);
    stage.show();
  }
}
