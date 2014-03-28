/*
 * Filename : Outcast.java
 * Function : Find the outcast of input words
 * Important Interface : 
*/
import java.util.HashMap;

public class Outcast {
  private WordNet WN;
  private HashMap<String, Integer> pathCache; //Store the calculated path
  // constructor takes a WordNet object
  public Outcast(WordNet wordnet) {
    WN = wordnet;
    pathCache = new HashMap<String, Integer>(); 
  }

  // given an array of WordNet nouns, return an outcast
  public String outcast(String[] nouns) {
    int maxlen = 0;
    String outcast = null;
    String[] strPair = new String[2];
    strPair[0] = new String();
    strPair[1] = new String();
    for (int o = 0; o < nouns.length; o++) //calculate the distance of every nouns pair
    {
      String so = nouns[o];
      int pathlen = 0;
      for (int i = 0; i < nouns.length; i++)
      {
        String si = nouns[i];
        //Check for  repeat
        strPair[0] = so;
        strPair[1] = si;
        if (pathCache.containsKey(strPair[0]+strPair[1])) //use cache
        {
          pathlen += pathCache.get(strPair[0]+strPair[1]);
          //StdOut.println("!");
        }
        else //calculate and add it to cache
        {
          int dist = WN.distance(so, si);
          pathlen += dist;
          if (i != o) 
          {
            pathCache.put(strPair[0]+strPair[1], dist);
            //swap the two nouns
            pathCache.put(strPair[1]+strPair[0], dist);
          }
        }
          
      }
      //check for  the maxlen
      if (pathlen > maxlen)
      {
        maxlen = pathlen;
        outcast = so;
      }
    }
    
    //StdOut.println("Use: "+ countUse +" Cal: "+ countCal + " Total: "+ nouns.length*nouns.length);
    return outcast;
  }
  
  public static void main(String[] args) {
      
      WordNet wordnet = new WordNet(args[0], args[1]);
      Outcast outcast = new Outcast(wordnet);
      for (int t = 2; t < args.length; t++) {
          String[] nouns = In.readStrings(args[t]);
          StdOut.println(args[t] + ": " + outcast.outcast(nouns));
      }
          
/*      HashMap<String, Integer> tt = new HashMap<String, Integer>(); //Store the calculated path    
      String s = new String();
      s = "1";tt.put(s,1);
      s = new String();
      s = "1";
      StdOut.println(s.toString());*/
     
      
          /*long start, end;
      start = System.currentTimeMillis();
      end = System.currentTimeMillis();
      StdOut.println(" time: "+(end-start));*/
  }

}
