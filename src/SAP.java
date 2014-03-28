/*
 * Filename : SAP.java
 * Function : Represent a shortest ancestor path
 * Important Interface : Find shortest path between input point(or set) v and w
*/

import java.util.HashMap;

public class SAP {
  private BreadthFirstDirectedPaths pv, pw;
  private Digraph G;
  private int pathlen, acstr;
  private HashMap<int[], int[]> sapCache; //first array for v-w, second for pathlen & acstr
  private HashMap<String[], int[]> isapCache;
  // constructor takes a digraph (not necessarily a DAG)
  public SAP(Digraph G) {
    pv = null;
    pw = null;
    pathlen = -1;
    acstr = -1;
    sapCache = new HashMap<int[], int[]>();
    isapCache = new HashMap<String[], int[]>();
    this.G = new Digraph(G);
  }

  // length of shortest ancestral path between v and w; -1 if  no such path
  public int length(int v, int w) {
    //check input
    if (v < 0 || v >= G.V() || w < 0 || w >= G.V()) 
      throw new IndexOutOfBoundsException();
    //build key
    int[] vw = new int[] {v, w};
    int[] wv = new int[] {w, v};
    //check key
    if(sapCache.containsKey(vw) || sapCache.containsKey(wv)) 
    {
        return sapCache.get(vw)[0];
    }
    //need to calculate
    pathlen = -1; //initialize pathlen
    acstr = -1;
    pv = new BreadthFirstDirectedPaths(G, v);
    pw = new BreadthFirstDirectedPaths(G, w);
    for (int i = 0; i < G.V(); i++)
    {
      if (pv.hasPathTo(i) && pw.hasPathTo(i))
      {
        if (pathlen == -1) //first loop
          pathlen = pv.distTo(i) + pw.distTo(i);
        else if (pv.distTo(i) + pw.distTo(i) < pathlen)
        {
          pathlen = pv.distTo(i) + pw.distTo(i);
          acstr = i;
        }          
        else continue;
        //StdOut.println("G = "+i+" vlen = "+pv.distTo(i)+" wlen = "+pw.distTo(i));
      }
    }
    //build the cache
    sapCache.put(vw, new int[] {pathlen, acstr});
    return pathlen;
  }

  // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if  no such path
  public int length(Iterable<Integer> v, Iterable<Integer> w) {
    //Check for  input    
    for (int vv : v)
    {
      if (vv < 0 || vv >= G.V()) 
        throw new IndexOutOfBoundsException();
    }
    for (int ww : w)
    {
      if (ww < 0 || ww >= G.V()) 
        throw new IndexOutOfBoundsException();
    }
  //build key
    String[] vw = new String[] {v.toString(), w.toString()};
    String[] wv = new String[] {w.toString(), v.toString()};
  //check key
    if(sapCache.containsKey(vw) || sapCache.containsKey(wv)) //cache is available
    {
        return sapCache.get(vw)[0];
    }
    //initialize pathlen
    pathlen = -1;
    acstr = -1;
    pv = new BreadthFirstDirectedPaths(G, v);
    pw = new BreadthFirstDirectedPaths(G, w);
    
    for (int i = 0; i < G.V(); i++)
    {
      if (pv.hasPathTo(i) && pw.hasPathTo(i))
      {
        if (pathlen == -1)//first loop
        {
          pathlen = pv.distTo(i) + pw.distTo(i);
        }
        else if (pv.distTo(i) + pw.distTo(i) < pathlen)
        {
          pathlen = pv.distTo(i) + pw.distTo(i);
          acstr = i;
        }
        else continue;
      }
    }
  //build the cache
    isapCache.put(vw, new int[] {pathlen, acstr});
    return pathlen;
  }

  // a common ancestor of v and w that participates in a shortest ancestral path; -1 if  no such path
  public int ancestor(int v, int w) {
    //check input
    if (v < 0 || v >= G.V() || w < 0 || w >= G.V()) 
      throw new IndexOutOfBoundsException();
    int[] vw = new int[] {v, w};
    int[] wv = new int[] {w, v};
    if(sapCache.containsKey(vw) || sapCache.containsKey(wv)) //cache is available
    {
        return sapCache.get(vw)[1];
    }
    //need to calculate
    pathlen = -1; //initialize pathlen acstor
    acstr = -1;    
    pv = new BreadthFirstDirectedPaths(G, v);
    pw = new BreadthFirstDirectedPaths(G, w);
    for (int i = 0; i < G.V(); i++)
    {
      if (pv.hasPathTo(i) && pw.hasPathTo(i))
      {
        if (pathlen == -1)//first loop
        {
          pathlen = pv.distTo(i) + pw.distTo(i);
          acstr = i;
        }
        else if (pv.distTo(i) + pw.distTo(i) < pathlen)
        {
          pathlen = pv.distTo(i) + pw.distTo(i);
          acstr = i;
        }
        else continue;
      }
    }
    //build the cache
    sapCache.put(vw, new int[] {pathlen, acstr});
    return acstr;
  }
  
  // a common ancestor that participates in shortest ancestral path; -1 if  no such path
  public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {  
    //Check for  input
    for (int vv : v)
    {
      if (vv < 0 || vv >= G.V()) 
        throw new IndexOutOfBoundsException();
    }
    for (int ww : w)
    {
      if (ww < 0 || ww >= G.V()) 
        throw new IndexOutOfBoundsException();
    }
    //build key
    String[] vw = new String[] {v.toString(), w.toString()};
    String[] wv = new String[] {w.toString(), v.toString()};
    //check key
    if(sapCache.containsKey(vw) || sapCache.containsKey(wv)) //cache is available
    {
        return sapCache.get(vw)[1];
    }
    pathlen = -1; //initialize pathlen acstor
    acstr = -1;
    pv = new BreadthFirstDirectedPaths(G, v);
    pw = new BreadthFirstDirectedPaths(G, w);
    for (int i = 0; i < G.V(); i++)
    {
      if (pv.hasPathTo(i) && pw.hasPathTo(i))
      {
        if (pathlen == -1)//first loop
        {
          pathlen = pv.distTo(i) + pw.distTo(i);
          acstr = i;
        }
        else if (pv.distTo(i) + pw.distTo(i) < pathlen)
        {
          pathlen = pv.distTo(i) + pw.distTo(i);
          acstr = i;
        }
        else continue;
      }
    }
    //build the cache
    isapCache.put(vw, new int[] {pathlen, acstr});
    
    return acstr;
  }
  
  public static void main(String[] args) {
    In in = new In("digraph1.txt");
    Digraph G = new Digraph(in);
    SAP sap = new SAP(G);
    while (!StdIn.isEmpty()) {
      int v = StdIn.readInt();
      int w = StdIn.readInt();
      int length   = sap.length(v, w);
      int ancestor = sap.ancestor(v, w);
      StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    }
  }

}
