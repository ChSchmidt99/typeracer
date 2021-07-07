package app;

import javafx.scene.image.Image;

/** Representation of a player icon. */
public class Icon {

  private final String id;
  private final String path;

  /**
   * Create Icon.
   *
   * @param id of icon shared across all clients.
   * @param path to icon image file
   */
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
