package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class TextDatabase {

  private ArrayList<String> dictionary;

  public TextDatabase() throws IOException {
    dictionary = new ArrayList<String>();
    URL path = this.getClass().getClassLoader().getResource("dictionary.txt");
    if (path == null) {
      throw new FileNotFoundException();
    }

    System.out.println(path.getPath());

    File f = new File(path.getPath());
    Scanner s = new Scanner(f, StandardCharsets.UTF_8);

    while (s.hasNextLine()) {
      dictionary.add(s.nextLine());
    }

    s.close();

  }

  public ArrayList<String> getDictionary() {
    return dictionary;
  }

  public static void main(String[] args) {
    try {
      TextDatabase t = new TextDatabase();
      System.out.println(t.getDictionary());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
