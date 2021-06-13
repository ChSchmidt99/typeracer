package database;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;

public class TextDatabase {

  private ArrayList<String> dictionary;

  public TextDatabase() throws FileNotFoundException {
    dictionary = new ArrayList<String>();
    URL path = this.getClass().getClassLoader().getResource("dictionary.txt");
    if (path == null) {
      throw new FileNotFoundException();
    }

    System.out.println(path.getPath());

           Scanner s = new Scanner(new File(path), "UTF-8");
           while (s.hasNextLine()) {
               dictionary.add(s.next());
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
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}
