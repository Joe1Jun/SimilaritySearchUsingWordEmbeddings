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
    
//    public String [] findTopMatches(String word , int numTopMatches ) {
//    	
//    	
//        System.out.println("Searching for word: " + word);
//
//       
//       //set word index at -1 .If the index at i doesn't match the word the index stays at -1 which means the word is not in the list.
//        int wordIndex= -1 ;
//        for (int i = 0; i < words.length; i++) {
//            if (words[i].equalsIgnoreCase(word)) {
//                wordIndex = i;
//                break;
//            }
//        }
//
//        System.out.println("Index of word '" + word + "': " + wordIndex);
//
//    	
//    	
//    	
//    	
//    	return topMatches;
//    }
//
//

    public void printIndex(String word) {
    	
    	 word = word.toLowerCase().trim();
         System.out.println("Searching for word: " + word);
        int wordIndex = -1;
        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(word)) {
                wordIndex = i;
                break;
            }
            // Debug print for comparison
//            System.out.println("Comparing '" + words[i] + "' with '" + word + "'");
        }

        System.out.println("Index of word '" + word + "': " + wordIndex);
	
    
    
    }




}





