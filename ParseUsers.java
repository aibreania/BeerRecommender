import java.util.*;
import java.io.*;

public class ParseUsers {
  public static void main(String[] args) throws FileNotFoundException {
      Scanner scanner = new Scanner(new File("rawUserRatings.csv"));
      scanner.useDelimiter("\n");
      PrintWriter pw = new PrintWriter(new File("userRatings.csv"));
      StringBuilder sb = new StringBuilder();

      sb.append("UserID");
      sb.append(",");
      sb.append("Rating");
      sb.append(",");
      sb.append("BeerName");
      sb.append("\n");

      int j = 0;
      while(scanner.hasNext()){
          String[] strs = scanner.next().split(",");
          if(strs.length != 3) continue;
          String[] users = strs[0].split(" ");
          String[] ratings = strs[1].split(" ");
          int len = Math.min(users.length, ratings.length);
          String beer = strs[2];
          for(int i = 0; i < len; i++) {
            sb.append(users[i]);
            sb.append(",");
            sb.append(ratings[i]);
            sb.append(",");
            sb.append(beer);
            sb.append("\n");
            System.out.println(j++);
          }
      }

      scanner.close();
      pw.write(sb.toString());
      pw.close();
      System.out.println("done!");

  }
}
