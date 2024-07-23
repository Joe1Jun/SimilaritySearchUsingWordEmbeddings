package ie.atu.sw;



public class WordComparison {
    
	int configure;
	//stores the parsed words from file
	private String[] words;
	//stores the parsed embeddings from the files
    private double[][] embeddings;
    //stores the top matches after comparison.
    private String [] topMatches = new String [configure];
    
    //generate constructor that takes the words and embeddings arrays as fields.
    public WordComparison(String[] words, double[][] embeddings) {
		
		this.words = words;
		this.embeddings = embeddings;
	}
    
    //NEED A METHOD THAT WILL FIND TOP MATCHES
    //This method needs to take the comparison word that is passed to it.
    //Then run a loop to find if that exists in the words array
    //then need to find the index of this word in the array and find the associated embeddings in order to compare them
    
    //this method will store the topmatches in the instance variable array topMatches
    
    public String [] findTopMatches(String word , int numTopMatches ) {
    	
    	int index = 0;
    	for (int i = 0 ; i < words.length ; i++) {
    		
    		if(words[i].equalsIgnoreCase(word)) {
    			
    			index = i;
    			break;
    		}
    		
    	}//end for
    	
    	System.out.println("Index of word '" + word + "': " + index);
    	
    	
    	
    	
    	
    	return topMatches;
    }



	
    
    
    




}





