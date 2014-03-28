/*
 * Filename : SAP.java
 * Function : Represent a shortest ancestor path
 * Important Interface : Find shortest path between input point(or set) v and w
*/
public class SAP {
	private DeluxeBFS pv, pw;
	private Digraph G;
	private Queue<Integer> qv,qw;
	private int pathlen, acstr;
	private int iv,iw;
	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph G){
		pv = null;
		pw = null;
		pathlen = -1;
		acstr = -1;
		qv = new Queue<Integer>();
		qw = new Queue<Integer>();
		iv = 0;
		iw = 0;
		this.G = new Digraph(G);
	}

	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w){
		if(iv == v && iw == w || iv == w && iw ==v)
		{
			return pathlen;
		}
		if(v < 0 || v >= G.V() || w < 0 || w >= G.V()) 
			throw new IndexOutOfBoundsException();
		pathlen = -1;//initialize pathlen
		iv = v;
		iw = w;
		pv = new DeluxeBFS(G, v);
		pw = new DeluxeBFS(G, w);
		for(int i = 0; i < G.V(); i++)
		{
			if(pv.hasPathTo(i) && pw.hasPathTo(i))
			{
				if(pathlen == -1) //first loop
					pathlen = pv.distTo(i) + pw.distTo(i);
				else if(pv.distTo(i) + pw.distTo(i) < pathlen)
					pathlen = pv.distTo(i) + pw.distTo(i);
				else continue;
				//StdOut.println("G = "+i+" vlen = "+pv.distTo(i)+" wlen = "+pw.distTo(i));
			}
		}
		return pathlen;
	}

	// length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
	public int length(Iterable<Integer> v, Iterable<Integer> w){
		if(qv.toString().equals(v.toString()) && qw.toString().equals(w.toString()) 
		|| qv.toString().equals(w.toString()) && qw.toString().equals(v.toString()))
		{
			//StdOut.println("same!");
			return pathlen;
		}
		//Check for input		
		for(int vv : v)
		{
			if(vv < 0 || vv >= G.V()) 
				throw new IndexOutOfBoundsException();
		}
		for(int ww : w)
		{
			if(ww < 0 || ww >= G.V()) 
				throw new IndexOutOfBoundsException();
		}
		//initialize pathlen
		pathlen = -1;
		qv = (Queue<Integer>) v;
		qw = (Queue<Integer>) w;
		
		pv = new DeluxeBFS(G, v);
		pw = new DeluxeBFS(G, w);
		
		for(int i = 0; i < G.V(); i++)
		{
			if(pv.hasPathTo(i) && pw.hasPathTo(i))
			{
				if(pathlen == -1)//first loop
				{
					pathlen = pv.distTo(i) + pw.distTo(i);
				}
				else if(pv.distTo(i) + pw.distTo(i) < pathlen)
				{
					pathlen = pv.distTo(i) + pw.distTo(i);
				}
				else continue;
			}
			
		}
		
		return pathlen;
	}

	// a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
	public int ancestor(int v, int w){
		if(iv == v && iw == w || iv == w && iw ==v)
		{
			return pathlen;
		}
		if(v < 0 || v >= G.V() || w < 0 || w >= G.V()) 
			throw new IndexOutOfBoundsException();

		pathlen = -1;//initialize pathlen acstor
		acstr = -1;
		
		pv = new DeluxeBFS(G, v);
		pw = new DeluxeBFS(G, w);
		for(int i = 0; i < G.V(); i++)
		{
			if(pv.hasPathTo(i) && pw.hasPathTo(i))
			{
				if(pathlen == -1)//first loop
				{
					pathlen = pv.distTo(i) + pw.distTo(i);
					acstr = i;
				}
				else if(pv.distTo(i) + pw.distTo(i) < pathlen)
				{
					pathlen = pv.distTo(i) + pw.distTo(i);
					acstr = i;
				}
				else continue;
			}
		}

		return acstr;
	}
	
	// a common ancestor that participates in shortest ancestral path; -1 if no such path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w){	
		if(this.qv == v && this.qw == w || this.qv == w && this.qw ==v)
		{
			return acstr;
		}
		//Check for input
		for(int vv : v)
		{
			if(vv < 0 || vv >= G.V()) 
				throw new IndexOutOfBoundsException();
		}
		for(int ww : w)
		{
			if(ww < 0 || ww >= G.V()) 
				throw new IndexOutOfBoundsException();
		}
		pathlen = -1;//initialize pathlen acstor
		acstr = -1;
		qv = (Queue<Integer>) v;
		qw = (Queue<Integer>) w;
		pv = new DeluxeBFS(G, v);
		pw = new DeluxeBFS(G, w);
		for(int i = 0; i < G.V(); i++)
		{
			if(pv.hasPathTo(i) && pw.hasPathTo(i))
			{
				if(pathlen == -1)//first loop
				{
					pathlen = pv.distTo(i) + pw.distTo(i);
					acstr = i;
				}
				else if(pv.distTo(i) + pw.distTo(i) < pathlen)
				{
					pathlen = pv.distTo(i) + pw.distTo(i);
					acstr = i;
				}
				else continue;
			}
		}

		
		return acstr;
	}
	
/*	public Iterable<Integer>[] path(int v, int w){	
		ancestor(v,w);
		return this.path;
	}
	
	public Iterable<Integer>[] path(Iterable<Integer> v, Iterable<Integer> w){	
		ancestor(v,w);
		return this.path;
	}*/
	
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
