package ie.atu.sw;

public class WordComparison {

	// stores the parsed words from file
	private String[] words;
	// stores the parsed embeddings from the files
	private double[][] embeddings;
	// stores the top matches after comparison.
	private String[] topMatches;

	// generate constructor that takes the words and embeddings arrays as fields.
	public WordComparison(String[] words, double[][] embeddings) {

		this.words = words;
		this.embeddings = embeddings;
	}

	// This method needs to take the comparison word that is passed to it.
	// Then run a loop to find if that exists in the words array
	// then need to find the index of this word in the array and find the associated
	// Embeddings in order to compare them

	// this method will store the topmatches in the instance variable array topMatches
	

	public String[] findTopMatches(String word, int numTopMatches) {

		// make word lower case
		word = word.toLowerCase().trim();
		System.out.println("Searching for word: " + word);
		// initialise wordIndex at -1 so we can check if the word is in the embeddings
		// file.
		int wordIndex = -1;
		// loop through words array to find the word entered by the user in the array
		// and at what index.
		// set wordIndex to the loop number.
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

		// found wordIndex now must find corresponding embeddings
		// variable to store the wordEmbedding at the index of the word entered by the
		// user
		double[] wordEmbedding = embeddings[wordIndex];
		// array to store similarities of the size of the words length as every
		// similarity score will be returned during the for loop;
		double[] similarities = new double[words.length];

		// need to loop through the embeddings array and compare each embedding to the
		// one corresponding to the user one.
		// the values will be passed each time to the method embeddingsSimiliarity
		for (int i = 0; i < words.length; i++) {

			// need to store the similairity scores in the similarities array at each index
			similarities[i] = cosineSimilarity(wordEmbedding, embeddings[i]);

		}
		
		
		// Manually copy the words and similarities arrays before sorting
		// This preserves the original words array as the instance variable that doesnt change
		// Without this modification the words array would be altered and not correspond to its embeddings .
		// All subsequent word searches would be incorrect.
        String[] wordsCopy = copyArray(words);
        
		// Pass the quick sort method the similarities and wordsCopy array and also the
		// length that will be used by the algorithm
		// In this case 0 representing the first index in the arrays and the last index
		// in the array.
        
        quickSort(similarities, wordsCopy, 0, similarities.length - 1);
		
		topMatches = new String[numTopMatches];
		// Directly populate topMatches, skipping the first index
		// as the user word will always be the at index 0 because it has the highest
		// similarity score the prevents
		// the user word being printed as a top match
		// numTopMatches needs to be plus one so that the printed words is the correct
		// number entered by the user
		for (int i = 0; i < numTopMatches; i++) {
	        topMatches[i] = wordsCopy[i + 1];
	    }

		return topMatches;
	}

	// Will use quicksort instead of bubblesort as the sorting algorithm as its time
	// complexity is better especially for large data sets.

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

	public String[] getTopMatches() {

		return topMatches;
	}

	/**
	 * Calculates the cosine similarity between two vectors. Cosine similarity is a
	 * measure of similarity between two non-zero vectors of an inner product space
	 * that measures the cosine of the angle between them.
	 *
	 * vecA The first vector. vecB The second vector. The cosine similarity between
	 * vecA and vecB.
	 */

	private double cosineSimilarity(double[] vecA, double[] vecB) {

		// Validate that the vectors have the same length
		if (vecA.length != vecB.length) {
			throw new IllegalArgumentException("Vectors must be of the same length.");
		}

		// Initialize variables to store the dot product and the norms of the vectors
		// Accumulates the sum of products of corresponding components
		double dotProduct = 0.0;
		// Accumulates the sum of squares of vecA components
		double normA = 0.0;
		// Accumulates the sum of squares of vecB components
		double normB = 0.0;
		// Loop over each component of the vectors
		for (int i = 0; i < vecA.length; i++) {
			// Calculate the dot product by summing the products of corresponding components
			dotProduct += vecA[i] * vecB[i];
			// Calculate the sum of squares for vecA
			normA += Math.pow(vecA[i], 2);
			// Calculate the sum of squares for vecB
			normB += Math.pow(vecB[i], 2);
		}

		// Compute norms
		normA = Math.sqrt(normA);
		normB = Math.sqrt(normB);
		// Calculate and return the cosine similarity
		// Cosine similarity is the dot product divided by the product of the magnitudes
		// (norms) of the vectors
		return dotProduct / (normA * normB);

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


