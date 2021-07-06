package app;

import javafx.scene.image.Image;

public class Icon {

  private final String id;
  private final String path;

  public Icon(String id, String path) {
    this.id = id;
    this.path = path;
  }

  public Image getImage() {
    return new Image(this.path);
  }

  public String getId() {
    return id;
  }

  public String getPath() {
    return path;
  }
}
