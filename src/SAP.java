
public class SAP {
	private DeluxeBFS pv, pw;
	private Digraph G;
	private Stack<Integer>[] path;
	private Queue<Integer> v,w;//cache for length & ancestor
	private int iv,iw;
	private int pathlen, acstr;
	// constructor takes a digraph (not necessarily a DAG)
	public SAP(Digraph G){
		pv = null;
		pw = null;
		v = null;
		w = null;
		iv = -1;
		iw = -1;
		pathlen = -1;
		acstr = -1;
		this.G = new Digraph(G);
		path =(Stack<Integer>[]) new Stack[2];
	}

	// length of shortest ancestral path between v and w; -1 if no such path
	public int length(int v, int w){
		if(v < 0 || v >= G.V() || w < 0 || w >= G.V()) throw new IndexOutOfBoundsException();
		if(iv == v && iv == w)
		{
			return pathlen;
		}
		pathlen = -1;//initialize pathlen
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
		if(this.v != null && this.w != null && this.v.equals(v) && this.w.equals(w))
		{
			return pathlen;
		}
		//Check for input		
		for(int vv : v)
		{
			if(vv < 0 || vv >= G.V()) throw new IndexOutOfBoundsException();
		}
		for(int ww : w)
		{
			if(ww < 0 || ww >= G.V()) throw new IndexOutOfBoundsException();
		}
		pathlen = -1;//initialize pathlen
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
		if(v < 0 || v >= G.V() || w < 0 || w >= G.V()) throw new IndexOutOfBoundsException();
		if(iv == v && iv == w)
		{
			return acstr;
		}
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
		if(acstr != -1)
		{
			path[0] = (Stack<Integer>) pv.pathTo(acstr);
			path[1] = (Stack<Integer>) pw.pathTo(acstr);
		}
		return acstr;
	}
	
	// a common ancestor that participates in shortest ancestral path; -1 if no such path
	public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
		if(this.v != null && this.w != null && this.v.equals(v) && this.w.equals(w))
		{
			return acstr;
		}
		//Check for input
		for(int vv : v)
		{
			if(vv < 0 || vv >= G.V()) throw new IndexOutOfBoundsException();
		}
		for(int ww : w)
		{
			if(ww < 0 || ww >= G.V()) throw new IndexOutOfBoundsException();
		}
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
		if(acstr != -1)
		{
			path[0] = (Stack<Integer>) pv.pathTo(acstr);
			path[1] = (Stack<Integer>) pw.pathTo(acstr);
		}
		
		return acstr;
	}
	
	public Iterable<Integer>[] path(int v, int w){	
		ancestor(v,w);
		return this.path;
	}
	
	public Iterable<Integer>[] path(Iterable<Integer> v, Iterable<Integer> w){	
		ancestor(v,w);
		return this.path;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
