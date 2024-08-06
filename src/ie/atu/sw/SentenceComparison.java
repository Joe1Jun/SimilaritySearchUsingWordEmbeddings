package ie.atu.sw;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SentenceComparison {

	   private int numTopMatches;
	// stores the parsed words from file
		private String[] words;
		// stores the parsed embeddings from the files
		private double[][] embeddings;
		// stores the top matches after comparison.
		private String[] sentenceWords;
		public SentenceComparison(int numTopMatches, String[] words, double[][] embeddings) {
			
			this.numTopMatches = numTopMatches;
			this.words = words;
			this.embeddings = embeddings;
			
		}
		
		public String[] findSentenceTopMatches(String sentence, int numTopMatches) {
			
			
			String[] sentenceWords = processSentence(sentence);
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

		    return null;  // You might want to change this to return something meaningful
		}
		
		
		private  String []  processSentence( String sentence)  {
			
			 String[] processedWords = sentence.split("\\s+");
			    
			    return processedWords;


		
		}
		
		}
		
		
		
		
		

		
		
		

		



