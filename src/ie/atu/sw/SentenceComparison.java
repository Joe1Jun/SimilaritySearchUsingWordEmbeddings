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
	   // stores the parsed words from file
	   private int [] wordIndexes;
		private String[] words;
		// stores the parsed embeddings from the files
		private double[][] embeddings;
		// stores the top matches after comparison.
		private String[] sentenceWords;
		
		// Passed 
		public SentenceComparison( String[] words, double[][] embeddings, int numTopSentenceMatches, int numTopWordMatches) {
			
			
			this.words = words;
			this.embeddings = embeddings;
			this.numTopSentenceMatches = numTopSentenceMatches;
			this.numTopWordMatches = numTopWordMatches;
			
		}
		
		// Method to find top sentence matches 
		// Will try first to get a sentence constructed using the same logic as for word comparison.
		// Will seperate the words out of the user sentence sore the words in and array
		// Find their index in the words array , find associated embeddings.
		// Comapare words one at a time to other words in the file and return the similarity score
		// these scores will be sorted using quicksort to sort a top matches array in descending order with top matches at the beginning of the array.
		// Will have to store each word and its top 5 matches possibly a 2D array with an index for the position of the word that contains the 5 top words.
		// When all words from the sentence have been returned and stored in the top matches array will have to loop through the array using a nested for loop
		// to create sentence 
		// These sentences will have to be stored in an array that will be either passed back to the menu class to be printed or printed in from this class directly
		
		public String[] findSentenceTopMatches(String sentence, int numTopMatches) {
			
			
			sentenceWords = processSentence(sentence);
			
			wordIndexes = findWordIndexes(sentenceWords);
		    
			double [][] wordEmbeddings = findWordEmbeddings(); 
			
			for (int i = 0; i < sentenceWords.length; i++) {
	            System.out.print(sentenceWords[i] + ": ");
	            for (int j = 0; j < wordEmbeddings[i].length; j++) {
	                System.out.print(wordEmbeddings[i][j]);
	            }
	            System.out.println();
	        }
		    
		    

		    return null;  
		}
		
		
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
			
			
			
			
		



		private int [] findWordIndexes(String[] sentenceWords) {
			
            int[] wordIndexes = new int[sentenceWords.length];
		    
		    for (int i = 0; i < sentenceWords.length; i++) {
		        String word = sentenceWords[i];
		        wordIndexes[i] = -1;  // Default value if word is not found
		        
		        for (int j = 0; j < words.length; j++) {
		            if (words[j].equals(word)) {
		                wordIndexes[i] = j;
		                break;  // Exit the inner loop once the word is found
		            }
		        }
		        
		        System.out.println("Index of word '" + word + "': " + wordIndexes[i]);
		    }
			
		    return wordIndexes;
			
		}
		
		

		private  String []  processSentence( String sentence)  {
			
			 String[] processedWords = sentence.split("\\s+");
			    
			    return processedWords;


		
		}
		
		}
		

       // Use cosine similarity to compare all the words in the sentence one at a time against the embeddings in the file.



		
		
		
		

		
		
		

		



