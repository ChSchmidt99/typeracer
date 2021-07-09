package app.screens.start;

/** Observer for StartScreenModel. */
interface StartScreenModelObserver {

  void registered();

  void receivedError(String message);
}
