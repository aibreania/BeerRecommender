import java.util.*;
import java.io.*;

public class cleaning2 {
    public static void main (String[] args) throws FileNotFoundException {
        Map<String, String> map = new HashMap<>();
        Scanner scanner = new Scanner(new File("reviews3.csv"));
        scanner.useDelimiter("\n");

        while(scanner.hasNext()){
            String[] strs = scanner.next().split(",");
            //for(String str: strs) System.out.println(str);

            if(strs.length != 2) continue;
            String beer = strs[0];
            String review = strs[1];

            if(map.containsKey(beer)) {
              String tmp = map.get(beer);
              tmp = tmp + " " + review;
              map.put(beer, tmp);
            } else {
              map.put(beer, review);
            }
        }
        scanner.close();


        PrintWriter pw = new PrintWriter(new File("cleanReviews.csv"));
        PrintWriter pw2 = new PrintWriter(new File("cleanReviews2.csv"));

        StringBuilder sb = new StringBuilder();
        sb.append("beer");
        sb.append(",");
        sb.append("review");
        sb.append("\n");
        int i = 0;

        for(String key : map.keySet()) {
          sb.append(key);
          sb.append(",");
          sb.append(map.get(key));
          sb.append("\n");
          i++;
          System.out.println(i);
          if(i == 1000) {
            pw.write(sb.toString());
            pw.close();
            sb = new StringBuilder();
            sb.append("beer");
            sb.append(",");
            sb.append("review");
            sb.append("\n");
          }
        }
        pw2.write(sb.toString());
        pw2.close();
        System.out.println("done!");
    }
}
