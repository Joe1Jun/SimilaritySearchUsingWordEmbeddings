package ie.atu.sw;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class FileOutput {

	private String [] topMatches;
	private String word;
	private String outputFilePath;
	
	
	public FileOutput(String[] topMatches, String word, String outputFilePath) {
		
		this.topMatches = topMatches;
		this.word = word;
		this.outputFilePath = outputFilePath;
	}
	
	
	
	public void outputToFile() {
		
		// Will try to 
		 try {
			 // Create printwriter object to write to the output file.
	            PrintWriter pw = new PrintWriter(outputFilePath);
	            // Will print this line at the top of the output file.
	            pw.println("Top matches for word: " + word);
	            // Prints each top match in the topMatches array to the file
	            for (String match : topMatches) {
	                pw.println(match);
	            }
	            
	            pw.close();
	            // Prints that the outpu has been written to the specified file if successful.
	            System.out.println("Output written to " + outputFilePath);
	            // If there is an error in the operation the user will get a message.
	        } catch (FileNotFoundException e) {
	            System.err.println("Could not write to file: " + e.getMessage());
	        }
	    }
		
		
	}

