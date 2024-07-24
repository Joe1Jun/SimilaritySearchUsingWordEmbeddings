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
    	
    	//make word lower case 
    	 word = word.toLowerCase().trim();
         System.out.println("Searching for word: " + word);
         //initialise wordIndex at -1 so we can check if the word is in the embeddings file.
        int wordIndex = -1;
        //loop through words array to find the word entered by the user in the array and at what index.
        //set wordIndex to the loop number.
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(word)) {
                wordIndex = i;
                break;
            }
            
        }
        

        System.out.println("Index of word '" + word + "': " + wordIndex);
        
        if (wordIndex == -1) {
            System.err.println("Word not found in embeddings.");
            return new String[0];
        }

        //found wordIndex now must find corresponding embeddings
        //variable to store the wordEmbedding at the index of the word entered by the user
        double [] wordEmbedding = embeddings [wordIndex];
        
        
       
       

    	
    	
    	    	
    	return topMatches;   
    	
    
    }



    




}





