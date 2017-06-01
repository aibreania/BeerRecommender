import java.util.*;
import java.io.*;

public class cleaning {
    public static void main (String[] args) throws FileNotFoundException {
        Map<String, String> map = new HashMap<>();
        Scanner scanner = new Scanner(new File("myDT.csv"));
        scanner.useDelimiter("\n");
        int i = 0;

        while(scanner.hasNext()){
            String[] strs = scanner.next().split(",");
            if(strs.length != 2) continue;
            String beer = strs[0];
            String grade = strs[1];

            if(map.containsKey(beer)) {
              String tmp = map.get(beer);
              tmp = tmp + grade;
              map.put(beer, tmp);
            } else {
              map.put(beer, grade);
            }
            i++;
        }
        scanner.close();

        PrintWriter pw = new PrintWriter(new File("cleanGrades.csv"));
        StringBuilder sb = new StringBuilder();
        sb.append("beer");
        sb.append(",");
        sb.append("look");
        sb.append(",");
        sb.append("smell");
        sb.append(",");
        sb.append("taste");
        sb.append(",");
        sb.append("feel");
        sb.append(",");
        sb.append("overall");
        sb.append("\n");

        for(String key : map.keySet()) {
          Beer B = avg(map, key);
          sb.append(B.beer);
          sb.append(",");
          Map m = B.map;
          sb.append(m.get("look"));
          sb.append(",");
          sb.append(m.get("smell"));
          sb.append(",");
          sb.append(m.get("taste"));
          sb.append(",");
          sb.append(m.get("feel"));
          sb.append(",");
          sb.append(m.get("overall"));
          sb.append("\n");
        }
        pw.write(sb.toString());
        pw.close();
        System.out.println("done!");
    }

    public static Beer avg(Map<String, String> map, String beer){
      String grade = map.get(beer).replace("\"", "");
      grade = grade.replaceAll("\\s","");
      String[] grades = grade.split("\\|");

      Map<String, Double> beerMap = new HashMap<>();
      beerMap.put("look", 0.0);
      beerMap.put("smell", 0.0);
      beerMap.put("taste", 0.0);
      beerMap.put("feel", 0.0);
      beerMap.put("overall", 0.0);
      int i = 0;

      for(String g : grades) {
        String[] gs = g.split(":");
        if(gs.length != 2 || !beerMap.containsKey(gs[0])) continue;
        double gs1 = Double.parseDouble(gs[1]);
        double value = beerMap.get(gs[0]) + gs1;
        beerMap.put(gs[0], value);
        i++;
      }

      i = i / 5;

      for(String key : beerMap.keySet()) {
        double value = Math.round(beerMap.get(key) / i * 100);
        value = value / 100;
        beerMap.put(key, value);
      }

      return new Beer(beer, beerMap);
    }


    static class Beer {
      String beer;
      Map<String, Double> map;

      public Beer(String beer, Map<String, Double> map) {
        this.beer = beer;
        this.map = map;
      }
    }

}
