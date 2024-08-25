package ie.atu.sw;



public class SentenceComparison {

	private WordComparison wordComparison;
	// Number of top matches to
	private int numTopSentenceMatches;
	// Number of top word matches
	private int numTopWordMatches;
	// Stores the top sentences created by the class
	private String[] words;
	private double[][] embeddings;
	private String[] topSentences;
	// stores the top matches after comparison.
	private String[] sentenceWords;
	// stores all top matches .
	private String[][] allTopMacthes;

	// Passed from the menu class . However might not need to pass the words and
	// embeddings if passing the instace of the Wordcomparison object
	public SentenceComparison(WordComparison wordComparison, String[] words, double[][] embeddings,
			int numTopSentenceMatches, int numTopWordMatches) {

		this.wordComparison = wordComparison;
		this.words = words;
		this.embeddings = embeddings;
		this.numTopSentenceMatches = numTopSentenceMatches;
		this.numTopWordMatches = numTopWordMatches;

	}

	// Method to find top sentence matches
	// Will try first to get a sentence constructed using the same logic as for word
	// comparison.
	// Will seperate the words out of the user sentence sore the words in and array
	// Find their index in the words array , find associated embeddings.
	// Comapare words one at a time to other words in the file and return the
	// similarity score
	// these scores will be sorted using quicksort to sort a top matches array in
	// descending order with top matches at the beginning of the array.
	// Will have to store each word and its top 5 matches possibly a 2D array with
	// an index for the position of the word that contains the 5 top words.
	// When all words from the sentence have been returned and stored in the top
	// matches array will have to loop through the array using a nested for loop
	// to create sentence
	// These sentences will have to be stored in an array that will be either passed
	// back to the menu class to be printed or printed in from this class directly
	// the results from this method might return unsuitable sentences as the words
	// are compared to all other words
	// not words of their type
	// **COMPLETE BASIC FUNCTIONALITY AND THEN CONSIDER COMPARING WORD SIMILARITY
	// SCORES ONLY AGAINST WORDS OF THEIR TYPE IF POSSIBLE****

	public String[] findSentenceTopMatches(String sentence, int numTopMatches) {

		// Call processSentence method to seperate the sentence into words and populate
		// array;
		sentenceWords = processSentence(sentence);
		
		// Initialize the allTopMacthes array
	    allTopMacthes = new String[sentenceWords.length][numTopWordMatches];

		for (int i = 0; i < sentenceWords.length; i++) {
			System.out.println("Processing word: " + sentenceWords[i]); // Print before progress bar
			 Runner.printProgress(i + 1, sentenceWords.length);  // Update progress
			double[] wordEmbedding = getWordEmbeddings(sentenceWords[i]);
			double[] similarities = new double[words.length];
			
			// Manually copy the words  array before sorting
			// This preserves the original words array as the instance variable shouldnt change
			// Without this modification the words array would be altered and not correspond to its embeddings .
			// All subsequent word searches would be incorrect.
			String[] wordsCopy = copyArray(words);

			for (int j = 0; j < words.length; j++) {
				
			   
				similarities[j] = cosineSimilarity(wordEmbedding, wordComparison.getEmbedding(words[j]));
			}

			// Pass method to quick sort
			quickSort(similarities, wordsCopy, 0, similarities.length -1);

			// Store top matches for this word in an 2D array 
			for (int k = 0; k < numTopWordMatches; k++) {
				
				// Populate array with the sorted words lists 
				// This list has the words sorted in descending order according to similarity score
				// Will populate the array up to k at numTopWordMatches
				// i here will hold the current words in the sentence . k holds the top words associated with that word
				allTopMacthes[i][k] = wordsCopy[k];
			}
			
			
			
			// Print top matches for this word
			System.out.println("Top " + numTopWordMatches + " matches for the word: '" + sentenceWords[i] + "'");

			for (int k = 0; k < numTopWordMatches; k++) {
			    System.out.println((k + 1) + ". " + wordsCopy[k + 1] + " (similarity: " + similarities[k] + ")");
			}

			System.out.println();
		}
		
		

		topSentences = constructSentences(allTopMacthes);

		return topSentences;

	}

	

	private String[] constructSentences(String[][] allTopWordMatches) {

	    String[] constructedSentences = new String[numTopSentenceMatches];
	    
	    // Iterate over each sentence to be constructed
	    for (int i = 0; i < numTopSentenceMatches; i++) {
	        StringBuilder sentenceBuilder = new StringBuilder();
	        
	     // Construct each sentence by picking different matches for each word
	        for (int j = 0; j < allTopWordMatches.length; j++) {
	            // Rotate through the top matches 
	        	// Increase i by one to make sure that the actual word isnt selected as the top word.
	            String word = allTopWordMatches[j][i + 1];
	            sentenceBuilder.append(word).append(" ");
	        }
	        
	        // Trim the trailing space and store the sentence
	        constructedSentences[i] = sentenceBuilder.toString().trim();
	    }

	    return constructedSentences;
	}

	private String[] processSentence(String sentence) {

		String[] processedWords = sentence.split("\\s+");

		return processedWords;

	}

	private void quickSort(double[] similarities, String[] words, int low, int high) {

		// Checks if the first index is less than the pivot
		if (low < high) {

			// The index of the pivot is returned from the partition function
			// This value is used to in order to pass the values of the low and high back
			// into the quick sort function
			// This allows the function to sort the sub array to the left of the pivot and
			// the sub array to the right.
			int pi = partition(similarities, words, low, high);

			// Recursively sort the left subarray
			// The high value of the left subarray is set to the index before the pivot.
			quickSort(similarities, words, low, pi - 1);
			// Recursively sort the right subarray
			// The low value is set to the index after the pivot.
			quickSort(similarities, words, pi + 1, high);

		}

	}

	private int partition(double[] similarities, String[] words, int low, int high) {

		// Initialise the pivot at the last index of the similarities array
		double pivot = similarities[high];

		// Initialise the i variable at one value less than the first index

		int i = low - 1;

		// Use a for loop that starts at the low value and ends at the high value.
		for (int j = low; j < high; j++) {

			// Check if the value of similarities at j is greater than the pivot value.
			// Because the end array must be in descending order.
			// If the value is less than our pivot its ignored.
			if (similarities[j] > pivot) {

				// i is incremented first as it is required to be at an index in the array.
				// If the swap occurs before this it would trigger an out of bounds exception
				// If the first element is greater than the pivot it will be essentially swapped
				// with itself as i and j will be at the same index
				i++;

				// Initialise a double to temporarily store values to be swapped
				// The double takes the value of similarities at the index of i
				// Example array on second pass of loop [2.3][7.1] high [ 5.2]
				// i is incremented on second pass of loop as first pass is ignored.

				// temp is equal to 2.3
				double temp = similarities[i];
				// Make value of index 0 7.1.
				similarities[i] = similarities[j];
				// Swap the value back in for j so index1 now has a value of 2.3
				// Array at the end [7.1][2.3] high[5.2]
				similarities[j] = temp;

				// If the first double is greater than the pivot it will essentially be swapped
				// with itself as both i and j will be at the same index

				// Do the same for the words at the corresponding indexes
				String wordTemp = words[i];
				words[i] = words[j];
				words[j] = wordTemp;

			}

		}

		// Must now swap the pivot at high to its correct index.
		// Create a temporary variable to hold the value of the index one up from the
		// index of i. As the index of i after the loop will
		// be the index of the last value lower than the pivot so need to increase the
		// the index of i by one so the index is now on a value higher than the pivot.

		double tempSim = similarities[i + 1];
		// Make the value of index i + 1 the same as the value of the pivot
		similarities[i + 1] = similarities[high];
		// Make the last index of the array the same value as the one stored in the
		// temporary variable.
		// This is the same value as the original i + 1 index before the swap.
		// Now that the pivot is in the correct position with all values to the left
		// being higher and to the right being lower.

		similarities[high] = tempSim;

		// Swap words[i+1] and words[high] (or pivot)
		String tempWord = words[i + 1];
		words[i + 1] = words[high];
		words[high] = tempWord;

		// The index of the pivot will be passed back to the quickSort function so the
		// sub arrays to the left and right of the pivot
		// can be passed back into the quicksort function.
		return i + 1;

	}

	private double[] getWordEmbeddings(String word) {

		double[] wordEmbedding = wordComparison.getEmbedding(word);

		return wordEmbedding;
	}

	// Use cosine similarity to compare all the words in the sentence one at a time
	// against the embeddings in the file.
	// ****MIGHT BE UNNECCESSARY AT ITS DULICATE CODE IE. USED IN WORDCOMPARISON
	// CLASS

	private double cosineSimilarity(double[] vecA, double[] vecB) {
		if (vecA.length != vecB.length) {
			throw new IllegalArgumentException("Vectors must be of the same length.");
		}

		double dotProduct = 0.0;
		double normA = 0.0;
		double normB = 0.0;

		for (int i = 0; i < vecA.length; i++) {
			dotProduct += vecA[i] * vecB[i];
			normA += vecA[i] * vecA[i];
			normB += vecB[i] * vecB[i];
		}

		return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
	}

	// Method to manually copy a String array
	private String[] copyArray(String[] array) {
		String[] copy = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			copy[i] = array[i];
		}
		return copy;
	}

}
