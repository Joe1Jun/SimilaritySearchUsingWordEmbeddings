package ie.atu.sw;

public class WordComparison {

	private WordIndexFinder findWordIndex;
	// Stores the parsed words from file
	private String[] words;
	// Stores the parsed embeddings from the files
	private double[][] embeddings;
	// Stores the top matches after comparison.
	private String[] topMatches;
	// Stores the similarity scores
	double[] similarities;
	
	
	
	

	// Generate constructor that takes the words and embeddings arrays as fields.
	public WordComparison(String[] words, double[][] embeddings) {

		this.words = words;
		this.embeddings = embeddings;
		this.similarities = new double[words.length];
	}

	
	

	



	public String[] findTopMatches(String word, int numTopMatches) {

		// Make word lower case to avoid
		word = word.toLowerCase().trim();
		System.out.println("Searching for word: " + word);

		// Pass the words array to the class to find the word index.
		findWordIndex = new WordIndexFinder(words);
        
		// Set the wordIndex variable to the value returned from the WordIndexFinder
		// class.
		int wordIndex = findWordIndex.findWordIndex(word);
		
		System.out.println("Index of word '" + word + "': " + wordIndex);

		// -1 is returned from the findWordIndex method then the word is not in the
		// embeddings file.
		if (wordIndex == -1) {
			ConsoleUtils.printError("Wordnot found in embeddings");
			return new String[0];
		}
		// Found wordIndex now must find corresponding embeddings
		// Variable to store the wordEmbedding is equal to value returned from the
		// getEmbedding method
		double[] wordEmbedding = getEmbedding(word);
		// Array to store similarities of the size of the words length as every
		// Similarity score will be returned during the for loop;
		SimilarityCalculator similarityCalc = new SimilarityCalculator();
		similarities = similarityCalc.computeSimilarities(wordEmbedding, embeddings);

		// Manually copy the words array before sorting
		// This preserves the original words array as the instance variable shouldn't
		// change
		// Without this modification the words array would be altered and not correspond
		// to its embeddings .
		// All subsequent word searches would be incorrect.
		String[] wordsCopy = copyArray(words);

		// Pass the quick sort method in the sort class the similarities and wordsCopy array and also the
		// length that will be used by the algorithm
		// In this case 0 representing the first index in the arrays and the last index
		// in the array.
        
		Sort sorter = new Sort();
        sorter.quickSort(similarities, wordsCopy, 0, similarities.length - 1);

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

	// Method to find the embeddings of the word
	public double[] getEmbedding(String word) {
		// This may be duplicate code but this method may be used across multiple
		// classes.
		int wordIndex = findWordIndex.findWordIndex(word);
		if (wordIndex == -1) {
			System.err.println("Word not found in embeddings.");
			return new double[0];
		}
		// we return the embeddings that are found at the index of the word.
		return embeddings[wordIndex];
	}

	

	
	public String[] getTopMatches() {

		return topMatches;
	}

	// Method to manually copy a String array
	private String[] copyArray(String[] array) {
		String[] copy = new String[array.length];
		for (int i = 0; i < array.length; i++) {
			copy[i] = array[i];
		}
		return copy;
	}

	public double[] getSimilarities() {
		return similarities;
	}

}
