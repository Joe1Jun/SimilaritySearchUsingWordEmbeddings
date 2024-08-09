package ie.atu.sw;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SentenceComparison {
    
	
    
	private WordComparison wordComparison;
	// Number of top matches to
	private int numTopSentenceMatches;
	// Number of top word matches
	private int numTopWordMatches;
	// Stores the top sentences created by the class
	private String [] words;
	private double [ ] [] embeddings;
	private String[] topSentences;
	// stores the top matches after comparison.
	private String[] sentenceWords;
	// stores all top matches . 
	private String[][] allTopMacthes;

	// Passed from the menu class . However might not need to pass the words and embeddings if passing the instace of the Wordcomparison object
	public SentenceComparison(WordComparison wordComparison, String [] words, double [] [] embeddings, int numTopSentenceMatches, int numTopWordMatches) {

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

		// Call processSentence method to seperate the sentence into words and populate array;
		sentenceWords = processSentence(sentence);

		
		for(int i =0; i < sentenceWords.length; i++) {
			
			
			double wordEmbeddings [] = getWordEmbeddings(sentenceWords[i]);
			
		}
		
		
		
		
		
		
		// Manually copy the words and similarities arrays before sorting
				// This preserves the original words array as the instance variable shouldnt change
				// Without this modification the words array would be altered and not correspond to its embeddings .
				// All subsequent word searches would be incorrect.
		        String[] wordsCopy = copyArray(words);
		
		     // Process and print top matches for each word in the sentence
		        
	   

		topSentences = constructSentences(allTopMacthes);

		return topSentences;
	}
	
	private void printTopMatches(String originalWord, double[][] similarities, String[] words, int topN) {
	    System.out.println("Top " + topN + " matches for '" + originalWord + "':");
	    for (int i = 0; i < Math.min(topN, similarities.length); i++) {
	        int originalIndex = (int) similarities[i][1];
	        System.out.printf("%d. %s (similarity: %.4f)%n", i + 1, words[originalIndex], similarities[i][0]);
	    }
	    System.out.println();
	}

	private String[] constructSentences(String[][] allTopWordMatches) {

		String[] constructedSentences = new String[numTopSentenceMatches];

		return constructedSentences;

	}

	private String[] processSentence(String sentence) {

		String[] processedWords = sentence.split("\\s+");

		return processedWords;

	}
	
	
	private double[][] similarityScores(double[][] wordEmbeddings) {
	    // Ensure words array is initialized
	    if (words == null || words.length == 0) {
	        throw new IllegalStateException("Words array is not initialized.");
	    }

	    // Initialize the similaritiesOfWords array
	    double[][] similaritiesOfWords = new double[wordEmbeddings.length][words.length];

	    // Iterate over each word embedding from the sentence
	    for (int i = 0; i < wordEmbeddings.length; i++) {
	        // Retrieve the embedding of the current sentence word
	        double[] sentenceWordEmbedding = wordEmbeddings[i];

	        // Iterate over each word in the corpus
	        for (int j = 0; j < words.length; j++) {
	            // Retrieve the embedding for the current word in the file
	            double[] fileWordEmbedding = wordComparison.getEmbedding(words[j]);

	            // Compute the similarity score and store it in the variable similarity
	            double similarity = cosineSimilarity(sentenceWordEmbedding, fileWordEmbedding);

	            // Store the similarity score in the array
	            similaritiesOfWords[i][j] = similarity;
	        }
	    }

	    return similaritiesOfWords;
	}

	
	
	private void quickSort(double[]  similarities, String[] words, int low, int high) {

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

	private int partition(double [] similarities, String[] words, int low, int high) {

		// Initialise the pivot at the last index of the similarities array
		double pivot = similarities[high];

		// Initialise the i variable at one value less than the first index

		int i = low - 1;

		// Use a for loop that starts at the low value and ends at the high value.
		for (int j = low; j < high; j++) {

			// Check if the value of similarities at j is greater than the pivot value.
			// Because the end array must be in descending order.
			// If the value is less than our pivot its ignored.
			if (similarities[j], pivot) > 0) {

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
				double  temp = similarities[i];
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

		double  tempSim = similarities[i + 1];
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
