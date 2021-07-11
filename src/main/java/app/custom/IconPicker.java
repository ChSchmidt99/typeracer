package app.custom;

import app.Icon;
import app.IconManager;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/** Custom Picker for Icons. */
public class IconPicker extends GridPane {

  private final ToggleGroup group;

  /**
   * Create new IconPicker including all Icons specified in {@link IconManager}.
   *
   * @param iconsPerRow after how many icons a new line is started
   */
  public IconPicker(int iconsPerRow) {
    this.group = new ToggleGroup();
    addIcons(IconManager.getAllIcons(), iconsPerRow);
    group
        .selectedToggleProperty()
        .addListener(
            new ChangeListener<Toggle>() {
              @Override
              public void changed(
                  ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (newValue != null) {
                  IconManager.setSelectedIcon((Icon) newValue.getUserData());
                }
              }
            });
  }

  private void addIcons(List<Icon> icons, int iconsPerRow) {
    int index = 0;
    for (int i = 0; i < icons.size(); i++) {
      VBox entry = makePickerNode(icons.get(i), i == 0);
      this.add(entry, index % iconsPerRow, i / iconsPerRow);
      index++;
    }
  }

  private VBox makePickerNode(Icon icon, boolean isSelected) {
    RadioButton radioButton = new RadioButton();
    radioButton.setUserData(icon);
    radioButton.setFocusTraversable(false);
    radioButton.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #000000");
    radioButton.setToggleGroup(group);
    if (isSelected) {
      radioButton.setSelected(isSelected);
      IconManager.setSelectedIcon(icon);
    }
    ImageView view = new ImageView(icon.getImage());
    VBox box = new VBox();
    box.setSpacing(15);
    box.setAlignment(Pos.CENTER);
    box.getChildren().add(view);
    box.getChildren().add(radioButton);
    return box;
  }
}
