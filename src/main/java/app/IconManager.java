package app;

public class IconManager {

  private static final Icon[] icons = {
          new Icon("1","icons/floppy.PNG"),
          new Icon("2","icons/minesweep.PNG"),
          new Icon("3","icons/msdos.PNG"),
          new Icon("4","icons/paint.PNG"),
          new Icon("5","icons/print.PNG"),
          new Icon("6","icons/regedit.PNG"),
          new Icon("7","icons/solitaire.PNG"),
          new Icon("8","icons/win.PNG")
  };

  public static Icon[] getAllIcons() {
    return icons;
  }

  public static Icon getSelectedIcon() {
    return selected;
  }

  public static void setSelectedIcon(Icon icon) {
    IconManager.selected = icon;
  }

  public static Icon iconForId(String iconId) throws Exception {
    for (Icon icon: icons) {
      if (icon.getId().equals(iconId)) {
        return icon;
      }
    }
    throw new Exception("Icon not found!");
  }

  private static Icon selected;

}
