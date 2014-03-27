import java.util.ArrayList;


public class Outcast {
	private WordNet wn;
	// constructor takes a WordNet object
	public Outcast(WordNet wordnet){
		wn = wordnet;
	}

	// given an array of WordNet nouns, return an outcast
	public String outcast(String[] nouns){
		int sum = 0;
		String outcast = null;
		
		for(int o = 0; o < nouns.length / 2; o++)
		{
			String so = nouns[o];
			int temp = 0, disttemp = 0;
			//calculate the distance of every nouns
			for(int i = 0; i < nouns.length; i++)
			{
				if(i == o)//itself
				{
					temp += 0;
				}
				else//need to calculate
				{
					String si = nouns[i];
					disttemp = wn.distance(so, si);
					temp += disttemp;
					//StdOut.println("calculate: "+so+" "+si);
				}
				if(o == 0)//first word must be caculated
				{
					String si = nouns[i];
					disttemp = wn.distance(so, si);
					sum += disttemp;
					outcast = so;
				}				
			}
			if(temp > sum) 
			{
				sum = temp;
				outcast = so;
				break;
			}			
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
	        StdOut.println("cost : " + (end - start)/1000.0 + "s");
	    }
	}

}
