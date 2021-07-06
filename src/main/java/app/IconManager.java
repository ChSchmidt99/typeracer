package app;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Stores the currently selected Icon and all paths to all possible icons. */
public class IconManager {

  private static final Icon[] icons = {
    new Icon("1", "icons/floppy.PNG"),
    new Icon("2", "icons/minesweep.PNG"),
    new Icon("3", "icons/msdos.PNG"),
    new Icon("4", "icons/paint.PNG"),
    new Icon("5", "icons/print.PNG"),
    new Icon("6", "icons/regedit.PNG"),
    new Icon("7", "icons/solitaire.PNG"),
    new Icon("8", "icons/win.PNG")
  };

  /**
   * Get an array of all possible Icons.
   *
   * @return array of {@link Icon}
   */
  public static List<Icon> getAllIcons() {
    List<Icon> out = new ArrayList<>();
    Collections.addAll(out, icons);
    return out;
  }

  /**
   * Set currently selected Icon.
   *
   * @return icon {@link Icon}
   */
  public static Icon getSelectedIcon() {
    return selected;
  }

  /**
   * Set currently selected Icon.
   *
   * @param icon {@link Icon}
   */
  public static void setSelectedIcon(Icon icon) {
    IconManager.selected = icon;
  }

  /**
   * Create new Icon.
   *
   * @param iconId id of icon consistent across all clients
   * @return {@link Icon}
   * @throws FileNotFoundException If icon is not found
   */
  public static Icon iconForId(String iconId) throws FileNotFoundException {
    for (Icon icon : icons) {
      if (icon.getId().equals(iconId)) {
        return icon;
      }
    }
    throw new FileNotFoundException("Icon not found");
  }

  private static Icon selected;
}
