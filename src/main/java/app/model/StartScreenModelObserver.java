package app.model;

/** Observer for StartScreenModel. */
public interface StartScreenModelObserver {

  void registered();

  void receivedError(String message);
}
