package ie.atu.sw;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SentenceComparison {
	// Number of top matches to
	private int numTopSentenceMatches;
	// Number of top word matches
	private int numTopWordMatches;
	// stores the parsed embeddings from the files
	private double[][] embeddings;
	// stores the parsed words from file
	private int[] wordIndexes;
	// Stores the top sentences created by the class
	private String[] topSentences;
	// Stores words parsed from embeddings file and passed via Menu class
	private String[] words;
	// stores the top matches after comparison.
	private String[] sentenceWords;
	// stores all top matches
	private String[][] allTopMacthes;

	// Passed
	public SentenceComparison(String[] words, double[][] embeddings, int numTopSentenceMatches, int numTopWordMatches) {

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
	// the results from this method might return unsuitable sentences as the words are compared to all other words
	// not words of their type
	//**COMPLETE BASIC FUNCTIONALITY AND THEN CONSIDER COMPARING WORD SIMILARITY SCORES ONLY AGAINST WORDS OF THEIR TYPE IF POSSIBLE****

	public String[] findSentenceTopMatches(String sentence, int numTopMatches) {

		// Call process
		sentenceWords = processSentence(sentence);

		wordIndexes = findWordIndexes(sentenceWords);

		double[][] wordEmbeddings = findWordEmbeddings();

		//This loop to check if correct words have been added 
		for (int i = 0; i < sentenceWords.length; i++) {
			System.out.print(sentenceWords[i] + ": ");
			for (int j = 0; j < wordEmbeddings[i].length; j++) {
				System.out.print(wordEmbeddings[i][j]);
			}
			System.out.println();

		}
		
		
		

		topSentences = constructSentences(allTopMacthes);

		return topSentences;
	}

	private String[] constructSentences(String[][] allTopWordMatches) {

		String[] constructedSentences = new String[numTopSentenceMatches];

		return constructedSentences;

	}
	
	//Find the embeddings of the words 
	//****MIGHT BE UNNECCESSARY AT ITS DULICATE CODE IE. USED IN WORDCOMPARISON CLASS

	private double[][] findWordEmbeddings() {
		double[][] wordEmbeddings = new double[wordIndexes.length][];
		for (int i = 0; i < wordIndexes.length; i++) {
			int wordIndex = wordIndexes[i];
			wordEmbeddings[i] = embeddings[wordIndex];
		}
        
		int numFeatures = embeddings[0].length;
		System.out.println("Num features is " + numFeatures);

		return wordEmbeddings;
	}
	
	
	// Finds the word indexes of the words in the sentence
	//****MIGHT BE UNNECCESSARY AT ITS DULICATE CODE IE. USED IN WORDCOMPARISON CLASS


	private int[] findWordIndexes(String[] sentenceWords) {

		int[] wordIndexes = new int[sentenceWords.length];

		for (int i = 0; i < sentenceWords.length; i++) {
			String word = sentenceWords[i];
			wordIndexes[i] = -1; // Default value if word is not found

			for (int j = 0; j < words.length; j++) {
				if (words[j].equals(word)) {
					wordIndexes[i] = j;
					break; // Exit the inner loop once the word is found
				}
			}

			
			// Print word to debug
			System.out.println("Index of word '" + word + "': " + wordIndexes[i]);
		}

		return wordIndexes;

	}

	private String[] processSentence(String sentence) {

		String[] processedWords = sentence.split("\\s+");

		return processedWords;

	}

     // Use cosine similarity to compare all the words in the sentence one at a time
     // against the embeddings in the file.
	//****MIGHT BE UNNECCESSARY AT ITS DULICATE CODE IE. USED IN WORDCOMPARISON CLASS


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

}
