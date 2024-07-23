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
	
		try (BufferedReader br = new BufferedReader( new FileReader(filePath))){
			//initialise index to be used during the loop
			int index = 0;
			
			
			while(br.readLine() != null) {
				
				 String [] parsedWords =
						 
						 
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