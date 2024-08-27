package ie.atu.sw;

import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class FileOutput {

	private String[] topMatches;
	private double[] similarities;
	private String word;
	private String outputFilePath;
	
	

	public FileOutput(String[] topMatches, double[] similarities, String word, String outputFilePath) {

		this.topMatches = topMatches;
		this.similarities = similarities;
		this.word = word;
		this.outputFilePath = outputFilePath;
		
	}

	public void outputToFile() throws Exception {

		
		
		
		try {
			// Create printwriter object to write to the output file.
			PrintWriter pw = new PrintWriter(outputFilePath);
			
			
			

			// Will print this line at the top of the output file.
			pw.println("Top matches for word : " + word);
			pw.println("-----------------------------------------");
			// Add headings
			pw.printf("    %-15s : %-20s%n", "Word", "Similarity Score");
			// Underline for headings
			pw.println("-----------------------------------------");
			// Prints each top match in the topMatches array to the file

			for (int i = 0; i < topMatches.length; i++) {

				pw.printf("%2d. %-15s : %-20s%n", i + 1, topMatches[i], similarities[i]);

			}
			
			
			
					

			pw.close();
			// Prints that the outpu has been written to the specified file if successful.
			ConsoleUtils.printSuccess("Output written to " + outputFilePath);
			// If there is an error in the operation the user will get a message.
		} catch (FileNotFoundException e) {
			System.err.println("Could not write to file: " + e.getMessage());
		}
		
	}

}
