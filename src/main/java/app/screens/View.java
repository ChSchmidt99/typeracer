package app.screens;

import java.io.IOException;
import java.util.Objects;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/** Base class for all views. */
public abstract class View {

  private static final int DEFAULT_WIDTH = 960;
  private static final int DEFAULT_HEIGHT = 540;

  protected final Stage stage;
  private Parent root;
  private static final String ERROR = "Failed to load .fxml file";
  private static final String ALERT_HEADER = "Error";

  /**
   * Loads FXML file.
   *
   * @param stage to host view in
   * @param fxmlPath path to FXML file
   */
  public View(Stage stage, String fxmlPath) {
    this.stage = stage;
    FXMLLoader loader =
        new FXMLLoader(
            (Objects.requireNonNull(
                getClass().getProtectionDomain().getClassLoader().getResource(fxmlPath))));
    loader.setController(this);
    try {
      this.root = loader.load();
      if (stage.getScene() == null) {
        stage.setScene(new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Display message as pop up.
   *
   * @param errorMessage message
   */
  public void displayError(String errorMessage) {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle(ALERT_HEADER);
    alert.setContentText(errorMessage);
    alert.setHeaderText(null);
    alert.showAndWait();
  }

  public Stage getStage() {
    return stage;
  }

  /*
   * Display view.
   */
  public void show() {
    stage.getScene().setRoot(root);
    stage.show();
  }
}
