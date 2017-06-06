import java.util.*;
import java.io.*;

public class LagerStyle {
  public static void main(String[] args) throws FileNotFoundException {
      Map<String, String> map = new HashMap<>();
      List<Beer> list = new ArrayList<>();

      Scanner scanner = new Scanner(new File("task3.csv"));
      Scanner scanner2 = new Scanner(new File("beerStyles.csv"));
      scanner.useDelimiter("\n");
      scanner2.useDelimiter("\n");

      while(scanner2.hasNext()){
          String[] strs = scanner2.next().split(",");
          if(strs.length != 2) continue;
          map.put(strs[0], strs[1]);
      }
      scanner2.close();

      while(scanner.hasNext()){
          String[] strs = scanner.next().split(",");

          if(strs.length != 6) continue;
          list.add(new Beer(strs[0], strs[2], strs[4], strs[5]));
      }
      scanner.close();

      List<Beer> newList = matchStyle(list, map);

      PrintWriter pw = new PrintWriter(new File("newBeerStyle.csv"));
      StringBuilder sb = new StringBuilder();
      sb.append("Name");
      sb.append(",");
      sb.append("State");
      sb.append(",");
      sb.append("Style");
      sb.append(",");
      sb.append("Abv");
      sb.append("\n");

      int i = 0;

      for(Beer B : newList) {
        //i++;
        //System.out.println(i);
        sb.append(B.name);
        sb.append(",");
        sb.append(B.state);
        sb.append(",");
        sb.append(B.style);
        sb.append(",");
        sb.append(B.abv);
        sb.append("\n");
      }
      pw.write(sb.toString());
      pw.close();
      System.out.println("done!");

  }

  public boolean equals(String a, String b) {
    if(a == null || b == null || a.length() != b.length()) return false;
    for(int i = 0; i < a.length(); i++) {
      if(a.charAt(i) != b.charAt(i)) return false;
    }
      return true;
  }

  public static List<Beer> matchStyle(List<Beer> list, Map<String, String> map) {
    for(Beer B: list) {
      for(String s:map.keySet()) {
        if(s.equals(B.style))  B.style = map.get(B.style);
      }
    }
    return list;
  }


  static class Beer {
    String name;
    String state;
    String style;
    String abv;

    public Beer(String name, String state, String style, String abv) {
      this.name = name;
      this.state = state;
      this.style = style;
      this.abv = abv;
    }
  }
}
