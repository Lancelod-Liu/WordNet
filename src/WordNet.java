/*
 * Filename : WordNet.java
 * Function : Build a digraph describing a wordnet
 * Important Interface : Calculate distance between two input words
*/
import java.util.ArrayList;
import java.util.HashMap;

public class WordNet {
    private ArrayList<String> st;  // index  -> string
    private Digraph G;
    private SAP sap;
    private HashMap<String[], Integer> distCache, acstrCache;
    
  public WordNet(String synsets, String hypernyms) {
    // First pass builds the index by reading strings to associate
    // distinct strings with an index
    st = new ArrayList<String>();
    In in = new In(synsets);
    while (in.hasNextLine()) {
      String[] a = in.readLine().split(","); 
      st.add(a[1]);
    }
    
    // second pass builds the digraph by connecting first vertex on each
    // line to all others
    G = new Digraph(st.size());
    in = new In(hypernyms);
    while (in.hasNextLine()) {
      String[] a = in.readLine().split(",");
      int v = Integer.parseInt(a[0]);
      for (int i = 1; i < a.length; i++) {
          int w = Integer.parseInt(a[i]);
          G.addEdge(v, w);
      }
    }
    sap = new SAP(G);
    distCache = new HashMap<String[], Integer>();
    acstrCache = new HashMap<String[], Integer>();
  }
  
  // find the first id related to the input word
  // return -1 if  fail
  private ArrayList<Integer> id(String word) {
    ArrayList<Integer> ids = new ArrayList<Integer>();
    for (int id = 0; id < st.size(); id++)
      {
      //get every single word in a compact
      String[] a = st.get(id).split(" ");
      for (String s : a)
      {
        if (s.equals(word)) 
          ids.add(id);
      }                  
    }
      return ids;
  }

  // the set of nouns (no duplicates), returned as an Iterable
  public Iterable<String> nouns()
  {
    return st;
  }
  
    // is the word a WordNet noun?
  public boolean isNoun(String word) {
    for (String id : st)
    {
      String[] a = id.split(" ");
      for (String s : a)
      {
          if (s.equals(word)) 
            return true;
      }      
    }
    return false;
  }
    
    // distance between nounA and nounB (defined below)
  public int distance(String nounA, String nounB) {
    if (isNoun(nounA) && isNoun(nounB))
    {
      if (nounA.equals(nounB)) 
        return 0;
      else if (distCache.containsKey(new String[]{nounA, nounB}) 
             || distCache.containsKey(new String[]{nounB, nounA}))
              return distCache.get(new String[]{nounB, nounA});
      else
      {
        int length = sap.length(id(nounA), id(nounB));
        distCache.put(new String[]{nounB, nounA}, length);
        return length;
      }
        
    }
    else 
      throw new IllegalArgumentException();
  }
  
  // in a shortest ancestral path (defined below)
  public String sap(String nounA, String nounB) {
   /* if (!isNoun(nounA) || !isNoun(nounB)) 
      throw new IllegalArgumentException();  
    
    int acstr = sap.ancestor(id(nounA), id(nounB));
    return st.get(acstr);*/
    if (isNoun(nounA) && isNoun(nounB))
    {
      int acstr = -1;
      if (acstrCache.containsKey(new String[]{nounA, nounB}) 
             || acstrCache.containsKey(new String[]{nounB, nounA}))
        acstr = acstrCache.get(new String[]{nounB, nounA});
      else
      {
        acstr = sap.ancestor(id(nounA), id(nounB));
        acstrCache.put(new String[]{nounB, nounA}, acstr);
      }
      return st.get(acstr);
    }
    else 
      throw new IllegalArgumentException();
  }
    
  public static void main(String[] args) {
    //synsets.txt hypernyms.txt       
       WordNet wn = new WordNet(args[0], args[1]);
       String[] nouns = {"Lepidocybium", "discontentment"};
       long start, cnt = 0;
       start = System.currentTimeMillis();
       while(System.currentTimeMillis() - start < 5000)
       {
         wn.distance(nouns[0], nouns[1]);
         cnt++;
       }
       StdOut.println(" cnt: "+ cnt);
       //StdOut.println(wn.sap(nouns[0], nouns[1]) + " " + wn.distance(nouns[0], nouns[1]));
      

  }

}
