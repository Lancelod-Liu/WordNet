
public class Outcast {
	private WordNet wn;
	// constructor takes a WordNet object
	public Outcast(WordNet wordnet){
		wn = wordnet;
	}

	// given an array of WordNet nouns, return an outcast
	public String outcast(String[] nouns){
		int sum = -1;
		String outcast = null;
		
		for(int o = 0; o < nouns.length / 2; o++)
		{
			String so = nouns[o];
			int temp = 0;
			//calculate the distance of every nouns
			//long start = System.currentTimeMillis();
			for(String si : nouns)
			{
				temp += wn.distance(so, si);
				if(temp > sum) 
				{
					sum = temp;
					outcast = so;
					break;
				}
			}
			//long end = System.currentTimeMillis();
		    //StdOut.println("word "+so+" cost: "+ (end-start)+" msec");
			//StdOut.println(so + " : "+temp);
			
		}
		return outcast;
	}
	
	public static void main(String[] args) {
	    WordNet wordnet = new WordNet(args[0], args[1]);
	    Outcast outcast = new Outcast(wordnet);
	    for (int t = 2; t < args.length; t++) {
	        String[] nouns = In.readStrings(args[t]);
	        long start = System.currentTimeMillis();
	        StdOut.println(args[t] + ": " + outcast.outcast(nouns));
	        long end = System.currentTimeMillis();
	        StdOut.println(args[t] + ": " + (end - start)/1000.0 + "s");
	    }
	}

}
