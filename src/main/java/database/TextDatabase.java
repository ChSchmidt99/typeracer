package database;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class TextDatabase {

  private ArrayList<String> dictionary;

  public TextDatabase() throws FileNotFoundException {
    dictionary = new ArrayList<String>();
    String path = TextDatabase.class.getResource("dictionary.txt").getPath();

    //        Scanner s = new Scanner(new File(path), "UTF-8");
    //        while (s.hasNextLine()) {
    //            dictionary.add(s.next());
    //        }
    //        s.close();
    System.out.println(path);
  }

  public ArrayList<String> getDictionary() {
    return dictionary;
  }

  public static void main(String[] args) throws FileNotFoundException {
    TextDatabase t = new TextDatabase();
    System.out.println(t.getDictionary());
  }
}
