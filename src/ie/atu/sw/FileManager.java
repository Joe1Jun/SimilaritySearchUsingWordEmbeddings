package ie.atu.sw;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FileManager {

	private Scanner input;
	private FileParser parser;
	// private FileManager fileManager;
	private WordComparison compare;
	private WordComparator comparator;
	private String outputFilePath = "";

	public FileManager(Scanner input, FileParser parser, WordComparison compare, WordComparator comparator) {

		this.input = input;
		this.parser = parser;
		this.comparator = comparator;
		this.compare = compare;

	}

	// Parse file method will take the user specified file and pass it to the
	// FileParser class to be parsed.
	public void parseFile() throws Exception {

		ConsoleUtils.printPrompt("Enter the file path for the embeddings file to be parsed");
		// Variable filePath is initialised to the value of the file path specified by
		// the user.
		String filePath = input.next();

		// attempt to call parseFile method with FileParser object handling an
		// exceptions that may occur and printing the stack trace.
		// Create a File object using the filePath string
		File file = new File(filePath);

		// Validate if the file exists
		if (!file.exists()) {
			ConsoleUtils.printError("File not found. Please check the file path and ensure the file exists.");
			return;
		}

		// Validate if the file is readable by the current user
		// If the user does not have permissions to read the file they will be shown a
		// message to change the permissions.
		if (!file.canRead()) {
			ConsoleUtils.printError("File exists but cannot be read. Please check the file permissions.");
			return;
		}
		try {

			parser.parseFile(filePath);
			// Use object to obtain the number of words stored in the array.
			if (parser.getWords().length == 0) {
				ConsoleUtils.printPrompt("File parsed but no words were found. Please check the file content.");
			} else {

				ConsoleUtils.printSuccess(parser.getWords().length + " words parsed from file " + filePath);
			}
			// compare = new WordComparison(parser.getWords(), parser.getEmbeddings());
			System.out.println("Compare object initialized: " + (compare != null));
			// compare = new WordComparison(parser.getWords(), parser.getEmbeddings());

			// Catches general I/O errors related to file operations that might occur during
			// file reading.
		} catch (IOException e) {

			e.printStackTrace();
			ConsoleUtils.printError("An error occurred while reading the file. Please try again.");

		} catch (Exception e) {
			// Catches any other unexpected errors that don’t fall under IOException or any
			// specific exceptions.
			e.printStackTrace();
			ConsoleUtils.printError("An unexpected error occurred: " + e.getMessage());
		}

	}

	// method for user to input outputfile path
	public void specifyOutputFile() {

		ConsoleUtils.printPrompt("Enter the output file path: ");
		outputFilePath = input.next();

		ConsoleUtils.printSuccess("Output file set to: " + outputFilePath);

	}

	// Method when selected passed the topMatches and similarities to the FileOutput
	// class to be printed to
	// a user specified output file.
	public void outputTopMatchesToFile() throws Exception {

		// Attempt to send data to be printed to the FileOutput class
		try {

			// Validations access the boolean methods to check if the relevant instance
			// variables hold valid values

			if (!isFileParsed() || !comparator.isComaparisonWord() || !isOutputFileSpecified()
					|| !comparator.haveWordsBeenCompared()) {

				return;

			}
			if (parser == null || compare == null || comparator == null) {
				ConsoleUtils.printError("Required components not initialized.");
				return;
			}

			System.out.println(outputFilePath);

			ConsoleUtils.printPrompt("Output top word matches and similarity scores of word " + " ' "
					+ comparator.getComparisonWord() + " ' " + " to " + outputFilePath + " y/n  ?");
			// Use a while loop to continue while the user input is not a ' y ' or a ' n'
			// This validation is important so that the user is given one last option to
			// decide whether to output the data to a file.
			// It also gives them a chance to see that the outputFile and comparison word
			// are correct.
			while (true) {
				String choice = input.nextLine();

				if (choice.equalsIgnoreCase("y")) {
					FileOutput fileOutput = new FileOutput(compare.getTopMatches(), compare.getSimilarities(),
							comparator.getComparisonWord(), outputFilePath);
					fileOutput.outputToFile();
					break;
				} else if (choice.equalsIgnoreCase("n")) {
					ConsoleUtils.printPrompt("Returning to main menu.");
					break;
				} else {

					ConsoleUtils.printError("Invalid input. Please enter 'y' or 'n'.");

				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	// Validation methods to be used by other methods

	public boolean isFileParsed() throws Exception {

		// Test first to see if the words array is empty or null.
		// If the FileParser class has not been accessed via the option above then the
		// words array is null as it has not been initialised.
		// If the FileParser class has not been used to parse a file, the words array
		// will be null, as it has not been initialised.

		if (parser.getWords() == null || parser.getWords().length == 0) {

			ConsoleUtils.printError("No words have been parsed yet.");
			ConsoleUtils.printPrompt("Please select the option to specify an embeddings file from the main menu.");
			return false;
		}

		return true; // Return true if file has been parsed or user wants to continue
	}

	// Method to check if the output file has been specified by the user.
	private boolean isOutputFileSpecified() {

		if (outputFilePath.isEmpty()) {

			ConsoleUtils.printError("Output file has not been specified.");
			ConsoleUtils.printPrompt("Please select the option to specify an output file from the main menu");
			return false;

		}

		return true;
	}

	public void setComparator(WordComparator comparator) {
		
		this.comparator = comparator;
	}

	public void setCompare(WordComparison compare) {

		this.compare = compare;

	}

}
