package database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

/** class to save texts from txt file into a array list. */
public class TextDatabase {

  private final ArrayList<String> dictionary;

  /**
   * constructor to initialize dictionary data structure.
   *
   * @throws IOException if database file cannot be loaded
   */
  public TextDatabase() throws IOException {
    dictionary = new ArrayList<>();
    InputStream in = this.getClass().getResourceAsStream("/dictionary.txt");
    if (in == null) {
      throw new FileNotFoundException();
    }
    BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
    while (reader.ready()) {
      dictionary.add(reader.readLine());
    }
    reader.close();
  }

  /**
   * getter to retrieve a dictionary.
   *
   * @return dictionary as list
   */
  public ArrayList<String> getDictionary() {
    return dictionary;
  }

  /**
   * getter to retrieve a random text from the dictionary.
   *
   * @return random phrase to type
   */
  public String getPhrase() {
    int guessedIndex = new Random().nextInt(dictionary.size());
    return dictionary.get(guessedIndex);
  }
}
