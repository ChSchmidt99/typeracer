package app.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import model.TextToType;
import model.Typeracer;


class MultiplayerController extends Controller {

  private static final String FXMLPATH = "view/multiplayer.fxml";
  private String enteredText = "";
  Typeracer game;
  TextToType textToType2;

  @FXML
  TextFlow textToType;

  @FXML
  TextField entertext;

  public MultiplayerController(Stage stage, Typeracer game) {
    super(stage, FXMLPATH);
    this.game = game;
    this.textToType2 = game.getState().getTypeChar();
    Text text = new Text(game.getState().getTypeChar().getCompleteText());
    textToType.getChildren().addAll(text);
  }

  public void enteredText() {
    entertext.addEventHandler(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
      public void handle(KeyEvent event) {
        game.check(event.getCharacter().charAt(0));
      }
    });
  }
}