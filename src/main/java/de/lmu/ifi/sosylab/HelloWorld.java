package de.lmu.ifi.sosylab;

/**
 * Application that prints a nice and warm hello on invocation. This class ignores any command-line
 * arguments.
 */
public class HelloWorld {

  /**
   * Runs the application.
   *
   * @param args ignored
   */
  public static void main(String[] args) {
    if (args[1] == "Hello") {
      System.out.println("Hello, World");
    } else {
      // do nothing
    }
  }
}
