
import java.lang.*;

public class WordNet {
    private ST<Integer, String> st;  // index  -> string
    private Digraph G;
    private SAP sap;
    
	public WordNet(String synsets, String hypernyms) {
        st = new ST<Integer, String>();

        // First pass builds the index by reading strings to associate
        // distinct strings with an index
        In in = new In(synsets);
        while (in.hasNextLine()) {
            String[] a = in.readLine().split(","); 
            st.put(Integer.parseInt(a[0]), a[1]);
        }

        StdOut.println("Dict build ok.");
        
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
        StdOut.println("Graph build ok.");
        
        sap = new SAP(G);
    }
	
	// find the first id related to the input word
	// return -1 if fail
	public Queue<Integer> id(String word) {
		Queue<Integer> ids = (Queue<Integer>)new Queue();
		for(int id : st.keys())
    	{
			//get every single word in a compact
    		String[] a = st.get(id).split(" ");
    		for(String s : a)
			{
    			if(s.equals(word)) 
    				ids.enqueue(id);
			}    			    		
    	}
        return ids;
    }

    public Digraph G() {
        return G;
    }
	
    // is the word a WordNet noun?
    public boolean isNoun(String word){
    	for(int id : st.keys())
    	{
    		String[] a = st.get(id).split(" ");
    		for(String s : a)
			{
    			if(s.equals(word)) 
    				return true;
			}
    		
    	}
    	return false;
    }
    
    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB){
    	if(isNoun(nounA) && isNoun(nounB))
    	{
    		return sap.length(id(nounA), id(nounB));
    	}
    	else throw new IllegalArgumentException();
    }
    
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB){
    	if(!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
    	
    	Stack<Integer> stA = (Stack<Integer>) sap.path(id(nounA), id(nounB))[0];
    	Stack<Integer> stB = (Stack<Integer>) sap.path(id(nounA), id(nounB))[1];
    	Queue<String> pA =new Queue<String>();
    	Queue<String> pB =new Queue<String>();
    	pA.enqueue("["+nounA+"]");
    	pB.enqueue("["+nounB+"]");
    	while(!stA.isEmpty()) 
    		pA.enqueue("["+st.get(stA.pop())+"]");
    	while(!stB.isEmpty()) 
    		pB.enqueue("["+st.get(stB.pop())+"]");
    	return nounA + ":" + pA + "\n"+nounB + ":" + pB;
    }
    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//synsets.txt hypernyms.txt	     
	     WordNet wn = new WordNet(args[0], args[1]);
	     String[] nouns = {"dog","horse"};
	     
	     StdOut.println("dist of "+nouns[0]+" & "+nouns[1]+": "+wn.distance(nouns[0],nouns[1]));
	     StdOut.println("SAP "+"\n"+wn.sap(nouns[0],nouns[1]));
	}

}
