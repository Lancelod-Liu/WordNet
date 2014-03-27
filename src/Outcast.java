import java.util.ArrayList;


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
		ArrayList<Integer>[] dist = (ArrayList<Integer>[])new ArrayList[nouns.length/2];
		
		for(int o = 0; o < nouns.length / 2; o++)
		{
			String so = nouns[o];
			int temp = 0, disttemp = 0;
			dist[o] = (ArrayList<Integer>)new ArrayList();
			//calculate the distance of every nouns
			//long start = System.currentTimeMillis();
			for(int i = 0; i < nouns.length; i++)
			{
				if(i < o && dist[i].size() > (o - i - 1))//already calculated
				{
					disttemp = dist[i].get(o - i - 1);
					temp += disttemp;
				}
				else if(i == o)//itself
				{
					temp += 0;
				}
				else//need to calculate
				{
					String si = nouns[i];
					disttemp = wn.distance(so, si);
					temp += disttemp;
					dist[o].add(disttemp);
				}
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
