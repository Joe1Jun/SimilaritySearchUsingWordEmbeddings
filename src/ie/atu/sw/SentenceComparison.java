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
			
			
			 processSentence(sentence);
			
			
			
			
			
			
			
			return null;
		}
		
		
		private  void  processSentence( String sentence)  {
			
			 String[] processedWords = sentence.split("\\s+");
			    
			    for (String word : processedWords) {
			        System.out.println(word);
			    }
			}


		
		
		
		}
		
		
		
		
		

		
		
		

		



