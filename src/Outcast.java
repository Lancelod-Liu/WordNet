/*
 * Filename : Outcast.java
 * Function : Find the outcast of input words
 * Important Interface : 
*/
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Outcast {
	private WordNet WN;
	private HashMap<Set<String>, Integer> pathCache; //Store the calculated path
	// constructor takes a WordNet object
	public Outcast(WordNet wordnet){
		WN = wordnet;
		pathCache = new HashMap<Set<String>, Integer>(); 
	}

	// given an array of WordNet nouns, return an outcast
	public String outcast(String[] nouns){
		int maxlen = 0;
		String outcast = null;
		Set<String> strPair = new HashSet<String>();
		int countUse = 0, countCal = 0;
		for(int o = 0; o < nouns.length; o++)//calculate the distance of every nouns pair
		{
			String so = nouns[o];
			int pathlen = 0;
			for(int i = 0; i < nouns.length; i++)
			{
				String si = nouns[i];
				//Check for repeat
				strPair = new HashSet<String>();//Initial Set
				strPair.add(so);
				strPair.add(si);
				if(pathCache.containsKey(strPair))//use cache
				{
					pathlen += pathCache.get(strPair);
					countUse++;
				}
				else//calculate and add it to cache
				{
					int dist = WN.distance(so, si);
					pathlen += dist;
					if(i != o) 
					{
						pathCache.put(strPair, dist);
						countCal++;
					}
				}
					
			}
			//check for the maxlen
			if(pathlen > maxlen)
			{
				maxlen = pathlen;
				outcast = so;
			}
		}
		
		StdOut.println("Use: "+ countUse +" Cal: "+ countCal + " Total: "+ nouns.length*nouns.length);
		return outcast;
	}
	
	public static void main(String[] args) {
			
	    WordNet wordnet = new WordNet(args[0], args[1]);
	    Outcast outcast = new Outcast(wordnet);
	    for (int t = 2; t < args.length; t++) {
	        String[] nouns = In.readStrings(args[t]);
	        long start, end;
	        start = System.currentTimeMillis();
	        StdOut.println(args[t] + ": " + outcast.outcast(nouns));
	        end = System.currentTimeMillis();
	        StdOut.println("cost : " + (end - start)/1000.0 + "s");
	        
	        /*long start, end;
			start = System.currentTimeMillis();
			end = System.currentTimeMillis();
			StdOut.println(" time: "+(end-start));*/
	    }
	}

}
