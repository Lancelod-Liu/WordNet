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
    private HashMap<String, SET<Integer>> idset;
    
  public WordNet(String synsets, String hypernyms) {
    // First pass builds the index by reading strings to associate
    // distinct strings with an index
    st = new ArrayList<String>();
    In in = new In(synsets);
    while (in.hasNextLine()) {
      String[] a = in.readLine().split(","); 
      st.add(a[1]);
    }
    // create a cache set to link one word to a set of ids
    idset = new HashMap<String, SET<Integer>>();
    for (int id = 0; id < st.size(); id++)
    {
      String[] a = st.get(id).split(" ");
      for (String ss : a)
      {
        SET<Integer> idtemp = new SET<Integer>();
        if (idset.containsKey(ss))
        {
          idtemp = idset.get(ss); //read the old idset
          idtemp.add(id); //refresh the idset
          idset.put(ss, idtemp);
        }
        else
        {
          idtemp.add(id);
          idset.put(ss, idtemp);
        }
      }
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
          //Check for cycle
          for (int k : G.adj(w)) 
          {
            if (k == v) 
              throw new IllegalArgumentException();
          }
          
          G.addEdge(v, w);
      }
    }
    //Check for rooted DAG
    int cnt = 0;
    for (int i = 0; i < G.V(); i++) 
    {
      if (((Bag<Integer>) G.adj(i)).size() == 0) //count the root
      {
        cnt++;
        if (cnt > 1)
          throw new IllegalArgumentException();
      }
    }
    sap = new SAP(G);
    
  }
  

  // the set of nouns (no duplicates), returned as an Iterable
  public Iterable<String> nouns()
  {
    return idset.keySet();
  }
  
    // is the word a WordNet noun?
  public boolean isNoun(String word) {

    if (idset.containsKey(word)) 
      return true;
    return false;
  }
    
    // distance between nounA and nounB (defined below)
  public int distance(String nounA, String nounB) {
    if (isNoun(nounA) && isNoun(nounB))
    {
      if (nounA.equals(nounB)) 
        return 0;
      int length = sap.length(idset.get(nounA), idset.get(nounB));
      return length;
     }
    else 
      throw new IllegalArgumentException();
  }
  
  // in a shortest ancestral path (defined below)
  public String sap(String nounA, String nounB) {
    if (isNoun(nounA) && isNoun(nounB))
    {
      int acstr = -1;  
      acstr = sap.ancestor(idset.get(nounA), idset.get(nounB));
      return st.get(acstr);
    }
    else 
      throw new IllegalArgumentException();
  }
    
  public static void main(String[] args) {
    //synsets.txt hypernyms.txt       
       WordNet wn = new WordNet(args[0], args[1]);
       /*       String[] nouns = {"Lepidocybium", "discontentment"};
       long start, cnt = 0;
       start = System.currentTimeMillis();
       while (System.currentTimeMillis() - start < 3000)
       {
         wn.distance(nouns[0], nouns[1]);
       }
       start = System.currentTimeMillis();
       while (System.currentTimeMillis() - start < 3000)
       {
         wn.distance(nouns[0], nouns[1]);
         cnt++;
       }
       StdOut.println(" speed: "+ cnt/3);
       StdOut.println(wn.sap(nouns[0], nouns[1]) + " " + wn.distance(nouns[0], nouns[1]));*/
      

  }

}
