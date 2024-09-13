package ie.atu.sw;

public class WordComparison {

	// This was object instance was initialised in the object Manager class
	private WordIndexFinder wordIndexFinder;
	// Stores the parsed words from file
	private String[] words;
	// Stores the parsed embeddings from the files
	private double[][] embeddings;
	// Stores the top matches after comparison.
     private String[] topMatches;
	// Stores the similarity scores
	double[] similarities;
	// The choice of method for similarity calculation made by the user
	private int similarityChoice;

	// All these values and objects are passed to the constructor via the objectManager class.
	
	public WordComparison(String[] words, double[][] embeddings, WordIndexFinder wordIndexFinder) {

		this.words = words;
		this.embeddings = embeddings;
		this.wordIndexFinder = wordIndexFinder;
		this.similarities = new double[words.length];

	}
    // This method will return the topMatches of the similarity search to the WordManager class.
	public String[] findTopMatches(String word, int numTopMatches, boolean isTopMatches) {

		
		// Prints what the word is.
		ConsoleUtils.printHeader("Word set to: " + word);

		
		// Set the wordIndex variable to the value returned from the WordIndexFinder
		// class.
		int wordIndex = wordIndexFinder.findWordIndex(word);

		ConsoleUtils.printHeader("Index of word '" + word + "': " + wordIndex);

		// Once wordIndex is found  must find corresponding embeddings
		// Variable to store the wordEmbedding is equal to parsed embeddings array at
		// the wordIndex
		double[] wordEmbedding = embeddings[wordIndex];

		// Compute similarity scores for all words in the embeddings array
		// against the word provided by the user.
		// The resulting similarities array will be sorted by the quickSort method
		// below.
		similarities = calculateSimilarities(wordEmbedding);

		// Manually copy the words array before sorting
		// This preserves the original words array as the instance variable shouldn't
		// change
		// Without this modification the words array would be altered and not correspond
		// to its embeddings.
		// This is because the sort method sorts the similarities and wordsCopy arrays in place, directly modifying
		// their order.
		// All subsequent word searches would be incorrect.
		String[] wordsCopy = copyArray(words);
		// Create a boolean to send to the sort similarities method based on what type of matches the user has selected.
		boolean sortAscending;
	    if (isTopMatches) {
	    	// This is the order for the scores sorted for Cosine and dot product
	        sortAscending = false;  
	    } else {
	    	// This is the order for the score using Euclidean method.
	        sortAscending = true;  
	    }
		
	    sortSimilarities(similarityChoice, similarities, wordsCopy, sortAscending);

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

	private double[] calculateSimilarities(double[] wordEmbedding) {
		// Create a new SimilarityCalculator instance for each word comparison.
		// This is done because SimilarityCalculator requires the wordEmbedding
		// to be passed to its constructor. Since a new wordEmbedding is specified 
		// for each comparison, it makes sense to create a fresh instance of 
		// SimilarityCalculator each time.
		SimilarityCalculator similarityCalc = new SimilarityCalculator(embeddings, wordEmbedding);
		// Access the method of similarity calculation made by the user
		double[] calculatedSimilarities = similarityCalc.computeSimilarities();
		// The choice of method for similarity calculation made by the user
		similarityChoice = similarityCalc.getChoice();
        // Returns calculated similarities to be stored in the similarities array.
		return calculatedSimilarities;

	}

	private void sortSimilarities(int choice, double[] similarities, String[] wordsCopy, boolean descending ) {
		Sort sorter = new Sort();

		// Sorts the similarities and wordsCopy arrays in place, directly modifying
		// their order without needing to return them.
		// This is possible because arrays are passed by reference in Java, allowing the
		// sort class to directly alter the original arrays.
		// The similarities array will be sorted in descending order for
		// Cosine/DotProduct
		// and in ascending order for Euclidean Distance depending on user choice.
		// The sorted similarities array and the corresponding wordsCopy array
		// are directly used to determine the top matches.
		// The sorter object passes the values needed to sort the similarity scores.
		// The boolean value here is altered depending on whether the user selected lowest or top matches.
		switch (choice) {
        case 1, 2 -> sorter.quickSort(similarities, wordsCopy, 0, similarities.length - 1, descending);
        case 3 -> sorter.quickSort(similarities, wordsCopy, 0, similarities.length - 1, !descending);
    }

	}
	
	

	// Create get methods so other classes can access the top matches and
	// similarities and also access the similarity method chosen by the user.

	public String[] getTopMatches() {

		return topMatches;
	}

	public double[] getSimilarities() {
		return similarities;
	}

	public int getSimilarityChoice() {
		return similarityChoice;
	}

	// Method to manually copy a String array
	// This is used to copy the words array.
	
	private String[] copyArray(String[] array) {
		String[] copy = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			copy[i] = array[i];
		}
		return copy;
	}

}
