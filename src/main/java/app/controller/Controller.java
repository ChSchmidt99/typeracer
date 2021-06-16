package app.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

abstract class Controller {

  protected Stage stage;
  private final static String ERROR = "Failed to load .fxml file";


  /**
   * Constructor for Controllers.
   */
  public Controller(Stage stage, String fxmlpath) {
    try {
      this.stage = stage;
      FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlpath));
      Parent root = loader.load();
      loader.setController(this);
      stage.setScene(new Scene(root, 1280, 720));
    } catch (Exception e) {
      System.out.println(ERROR);
      e.printStackTrace();
    }
  }

  public void show() {
    stage.show();
  }

}
