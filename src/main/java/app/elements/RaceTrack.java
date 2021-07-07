package app.elements;

import app.Icon;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/** Custom Race Track Element used to show progress of all Players. */
public class RaceTrack extends Pane {

  private double progress;
  private final double trackWidth;
  private final double imageWidth;
  private final ImageView imageView;

  /**
   * Create new Race track with given dimensions. Size of Icons is adjusted automatically.
   *
   * @param icon of Player on track
   * @param trackWidth width of track in pixels
   * @param trackHeight height of track in pixels
   * @param trackColor background color of track
   */
  public RaceTrack(Icon icon, double trackWidth, double trackHeight, Paint trackColor) {
    Rectangle track = new Rectangle(trackWidth, trackHeight, trackColor);
    getChildren().add(track);
    Image image = icon.getImage();
    this.imageView = new ImageView(image);
    imageView.setPreserveRatio(true);
    imageView.setFitHeight(trackHeight);
    imageView.setX(0);
    double aspectRatio = image.getWidth() / image.getHeight();
    this.imageWidth = imageView.getFitHeight() * aspectRatio;
    getChildren().add(imageView);
    this.trackWidth = trackWidth;
    this.progress = 0;
  }

  /**
   * Update progress of player on track.
   *
   * @param progress of player in range [0,1]
   */
  public void updateProgress(double progress) {
    this.progress = progress;
    this.imageView.setX(currentXpos());
  }

  private double currentXpos() {
    double effectiveWidth = this.trackWidth - imageWidth;
    return effectiveWidth * progress;
  }
}
