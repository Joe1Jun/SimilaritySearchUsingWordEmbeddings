package ie.atu.sw;

public class WordIndexFinder {
	
	
	private String [] words;
	
	
	
	
	
	public WordIndexFinder( String[] words) {
		
		
		this.words = words;
	}





	// Method to find the index of the word
		 public int findWordIndex(String word) {
			 
			    // loop through words array to find the word entered by the user in the array
				// and at what index.
				// set wordIndex to the loop number.
		        word = word.toLowerCase().trim();
		        for (int i = 0; i < words.length; i++) {
		            if (words[i].equalsIgnoreCase(word)) {
		                return i;
		            }
		        }
		        // -1 will be returned if the word is not in the words array.
		        return -1;
		        
		        
		    }

}
