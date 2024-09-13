package ie.atu.sw;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class FileOutput {

	private String[] topMatches;
	private double[] similarities;
	private String word;
	private String outputFilePath;
	private String method = "";
	private String topOrLowest = "";
	

	public FileOutput(String[] topMatches, double[] similarities, String word, String outputFilePath, String method) {

		this.topMatches = topMatches;
		this.similarities = similarities;
		this.word = word;
		this.outputFilePath = outputFilePath;
		this.method = method;
		
	}
	
	
 // This method writes the top matches to an output file.
	public void outputToFile(boolean isTopMatches) throws Exception {
        
		
		if(isTopMatches) {
			topOrLowest = "Top";
			
		}else {
			topOrLowest = "Lowest";
		}
		
		
		
		
		try {
			// Create printwriter object to write to the output file.
			PrintWriter pw = new PrintWriter(outputFilePath);
			
			
			

			// Will print this line at the top of the output file.
			pw.println(topOrLowest + " matches for word  " + " ' " + word + " ' " + " using " + method + ".");
			pw.println("-------------------------------------------------------");
			// Add headings
			pw.printf("    %-15s : %-20s%n", "Word", "Similarity Score");
			// Underline for headings
			pw.println("-----------------------------------------");
			// Prints each top match in the topMatches array to the file

			for (int i = 0; i < topMatches.length; i++) {

				pw.printf("%2d. %-15s : %-20s%n", i + 1, topMatches[i], similarities[i]);

			}
			
			pw.println();
			pw.println();
			pw.println();
			
			
					

			pw.close();
			// Prints that the outpu has been written to the specified file if successful.
			ConsoleUtils.printSuccess("Output written to " + outputFilePath);
			// If there is an error in the operation the user will get a message.
		} catch (FileNotFoundException e) {
			System.err.println("Could not write to file: " + e.getMessage());
		}
		
	}
	
	// This method appends top matches to an output file.
	// This is useful if users want top matches of multiple words in the output file.
	// Also can be used to show the top matches from the three different similarity search methods on the same word.
	public void appendResultsToFile(boolean isTopMatches) {
		
		if(isTopMatches) {
			topOrLowest = "Top";
			
		}else {
			topOrLowest = "Lowest";
		}
		
		
		
		try (PrintWriter pw = new PrintWriter(new FileWriter(outputFilePath, true))) {
	        // Append the new results to the file.
			pw.println(topOrLowest + " matches for word + " + " ' " + word + " ' " + " using " + method + " .");
	        pw.println("------------------------------------------------------");
	        // Add headings and format.
	        
	        pw.printf("    %-15s : %-20s%n", "Word", "Similarity Score");
	        // Underline for headings
	        pw.println("-----------------------------------------");

	        // Prints each top match in the topMatches array to the file
	        for (int i = 0; i < topMatches.length; i++) {
	            pw.printf("%2d. %-15s : %-20s%n", i + 1, topMatches[i], similarities[i]);
	        }
	        
	        pw.println();
	        pw.println();
	        pw.println();

	        // Inform the user that the output has been successfully appended.
	        ConsoleUtils.printSuccess("Output appended to " + outputFilePath);

	    } catch (IOException e) {
	        // Handle any I/O errors.
	        ConsoleUtils.printError("Could not write to file: " + e.getMessage());
	    }
    }

}
