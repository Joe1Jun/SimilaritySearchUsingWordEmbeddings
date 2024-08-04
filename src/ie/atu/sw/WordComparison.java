package ie.atu.sw;

public class WordComparison {

	int configure;
	// stores the parsed words from file
	private String[] words;
	// stores the parsed embeddings from the files
	private double[][] embeddings;
	// stores the top matches after comparison.
	private String[] topMatches = new String[configure];

	// generate constructor that takes the words and embeddings arrays as fields.
	public WordComparison(String[] words, double[][] embeddings) {

		this.words = words;
		this.embeddings = embeddings;
	}

	// NEED A METHOD THAT WILL FIND TOP MATCHES
	// This method needs to take the comparison word that is passed to it.
	// Then run a loop to find if that exists in the words array
	// then need to find the index of this word in the array and find the associated
	// embeddings in order to compare them

	// this method will store the topmatches in the instance variable array
	// topMatches

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
			similarities[i] = cosineSimiliarity(wordEmbedding, embeddings[i]);

		}

		// as similiarities have been returned must now calculate the highest 10 scores
			

				/*
				 * 
				 * outer Loop (i): Bubble algorithm
				 * 
				 * the outer loop runs from 0 to similarities.length - 1. This loop determines
				 * the number of passes required to sort the array. After each pass, the largest
				 * unsorted element moves to its correct position at the end of the array.
				 * Therefore, we need one fewer pass than the number of elements because the
				 * last element will automatically be in place after the previous passes. Inner
				 * Loop j
				 * 
				 * the inner loop runs from 0 to similarities.length - 1 - i. This loop performs
				 * the actual comparisons and swaps. The -1 adjustment is necessary because we
				 * access similarities[j + 1] inside the loop. Without the -1, j + 1 would go
				 * out of bounds on the last iteration. The - i part ensures that we don't
				 * compare already sorted elements. With each pass, the largest unsorted element
				 * is placed at the end, reducing the number of comparisons needed for
				 * subsequent passes.
				 * 
				 */
		
		
		 
		
		
		
//				for (int i = 0; i < similarities.length - 1; i++) {
//				    for (int j = 0; j < similarities.length - 1 - i; j++) {
//				    	//if similarities at j is less than at j + 1 they are swapped 
//				        if (similarities[j] < similarities[j + 1]) {
//				            // Swap similarities
//				            double tempSim = similarities[j];
//				            similarities[j] = similarities[j + 1];
//				            similarities[j + 1] = tempSim;
//
//				            // Swap corresponding words
//				            String tempWord = words[j];
//				            words[j] = words[j + 1];
//				            words[j + 1] = tempWord;
//				        }
//				    }
//				}
				
	// Pass the quick sort method the similarities and words array and also the length that will be used by the algorithm
	// In this case 0 representing the first index in the arrays and the last index in the array.	
		quickSort(similarities, words, 0 , similarities.length -1);				

		
	           
				
				topMatches = new String[numTopMatches];
				// Directly populate topMatches, skipping the first index
				// as the user word will always be the at index 0 because it has the highest similarity score the prevents
				// the user word being printed as a top match
				// numTopMatches needs to be plus one so that the printed words is the correct number entered by the user
				for (int i = 1; i < numTopMatches + 1; i++) {
				    topMatches[i - 1] = words[i];
				   
				}

			    return topMatches;
			}
	
	// Will use quicksort instead of bubblesort as the sorting algorithm as its time complexity is better especially for large data sets.

	private void quickSort(double[] similarities, String[] words, int low, int high) {
		
		// Choose the last element as the pivot
		// The pivot is the double around which the values in the array are sorted
		// In this case what is needed is to have the values sorted in descending order in the array
		double pivot = similarities[high]; 
		
		// Checks if the first index is less than the pivot 
		if (low < high) { 
			// Partition the array and get the pivot index
			int pi = partition(similarities, words, low, high); 
			
		}
	        
		
		
		
		
	}
	
		
		private int partition(double [] similarities, String [] words, int low, int high ) {
			
			// Initialise the pivot at the last index of the similarities array
			double pivot = similarities[high];
			
			// Initialise the i variable at one value less than the first index
			
			int i = low -1;
			
			// Use a for loop that starts at the low value and ends at the high value.
			for (int j = low; j < high ; j++) {
				
				// Check if the value of similarities at j is greater than the pivot value.
				// Because the end array must be in descending order.
				//  If the value is less than our pivot its ignored.
				if(similarities[j] > pivot) 
					// i is incremented first as it is required to be at an index in the array. 
					//If the swap occurs before this it would trigger an out of bounds exception
					// If the first element is greater than the pivot it will be essentially swapped with itself as i and j will be at the same index
					i++;
				
				     // Initialise a double to temporarily store values to be swapped
				    // The double takes the value of similarities at the index of i 
				    // Example array on second pass of loop  [2.3][7.1]                              high [ 5.2]
				    // i is incremented on second pass of loop as first pass is ignored.
				    
				    // temp is equal to 2.3
				   double temp = similarities[i];
				    // Make value of index 0 7.1.
				    similarities[i] = similarities[j];
				    // Swap the value back in for j so index1 now has a value of 2.3
				    // Array at the end [7.1][2.3]                   high[5.2]
				    similarities[j] = temp;
				    
				    // If the first double is greater than the pivot it will essentially be swapped with itself as both i and j will be at the same index
					
					
				    //Do the same for the words at the corresponding indexes
				    String wordTemp = words[i];
				    words[i] = words[j];
				    words[j] = wordTemp;
					
				}
				
				
				
			
			
			
			return 0;
			
			
		}

		

	public String[] getTopMatches() {

		return topMatches;
	}

	/**
	 * Calculates the cosine similarity between two vectors. Cosine similarity is a
	 * measure of similarity between two non-zero vectors of an inner product space
	 * that measures the cosine of the angle between them.
	 *
	 *  vecA The first vector.
	 *  vecB The second vector.
	 * The cosine similarity between vecA and vecB.
	 */

	private double cosineSimiliarity(double[] vecA, double[] vecB) {

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

}
