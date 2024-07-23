package ie.atu.sw;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileParser {
    
	
	private static final int NUM_FEATURES = 50;
	private String[] words;
	private double[][] embeddings;

	public void parseFile(String filePath) throws Exception {
        //When parsing a file containing words and embeddings, each line typically corresponds to one word and its associated embedding vector.
		//To store each word and its embedding, you need to know how many words (lines) are in the file. This count gives you the number of entries you will need to store in your arrays.
		//For example, if the file has 1,000 lines, you need arrays that can hold 1,000 words and 1,000 embedding vectors.
		// count the number of lines and store it in the numLines variable
		int numLines = countLines(filePath);
		
		//intialize arrays based on number of lines
		words = new String [numLines];
		embeddings = new double [numLines][NUM_FEATURES];
	
		//read the file again to populate arrays.
		try (BufferedReader br = new BufferedReader( new FileReader(filePath))){
			//initialise index to be used during the loop
			int index = 0;
			//variable to hold each line read from the array
			String line ;
			
			//the line variable is equal to each line as its read.
			while((line = br.readLine())!= null) {
				
				//The read line including the words and the embeddings values
				// are all stored as string values in the parsed words array.
				//the split method splits the line at each space and stores this value in the array
				//This means the first index , index 0 of the array will be the word as the word is at the start of each line
				//This also means that the start index for the embedding on each line  is 1 which should be increased by 1 in a for loop to store the values in the embeddings array
				String [] parsedWords = line.split(" ");
				
				//store the word in the words array
				// the word at index [0] from each line is store in the words array
				//at the current index which increases by one each round of the loop
				
				words[index] = parsedWords[0];
				
				//for loop to populate the embeddings array
				//there are 50 features to loop through
				for (int i = 0; i < NUM_FEATURES; i++) {
					//loop iterates through the features and populates the the array 
					//at the current index with the values that have been stored in the String array above
					//this value at the index is stored along with the current feature value to populate the 2D array
					//have to parse to double from the stored String values
					
					embeddings[index][i] = Double.parseDouble(parsedWords[i + 1].replace(",", ""));
					
				}
						 
			  index++;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private int countLines(String filePath) throws Exception {
		// Initialise array to store the number of lines in the file
		int lineCount = 0;

		// method will try to create buffered reader object to read the file by line.
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			// the object uses the readLine method to read each line until there is no lines
			// left
			while (br.readLine() != null) {
				// the variable will increase by one after each round of the loop.
				lineCount++;
			}
			// stack trace will be printed if there try block fails.
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        //the final count for the lines in the file is returned to the parse file method.
		return lineCount;
	}

	public String[] getWords() {
		return words;
	}

	public double[][] getEmbeddings() {
		return embeddings;
	}

}