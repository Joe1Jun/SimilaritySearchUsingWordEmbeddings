package ie.atu.sw;

import java.io.File;
import java.io.IOException;

import java.util.Scanner;

public class FileManager {

	private Scanner input;
	private FileParser parser;
	private WordComparison compare;
	private WordManager wordManager;
	private String outputFilePath = "";
	private String filePath = "";
	private String method = "";
	private String topOrLowest = "";
	private boolean isTopMatches;

	public FileManager(Scanner input, FileParser parser, WordComparison compare, WordManager wordManager) {

		this.input = input;
		this.parser = parser;
		this.wordManager = wordManager;
		this.compare = compare;

	}

	// Parse file method will take the user specified file and pass it to the
	// FileParser class to be parsed.
	public void parseFile() throws Exception {
		// Calls the getFilePathFromUser method and sets the filePath to the return
		// value;
		// Variable filePath is initialised to the value of the file path specified by
		// the user.
		filePath = getFilePathFromUser();
		// Calls the isValidFile method to check if the file is valid.
		if (isValidFile(filePath)) {
			try {
				// Passes the file path to the File parser class.
				parser.parseFile(filePath);
				// calls the method below to handle the parsed file.
				handleParsedFile();
			} catch (IOException e) {
				ConsoleUtils.printError("An error occurred while reading the file. Please try again.");
			} catch (Exception e) {
				ConsoleUtils.printError("File not parsed: " + e.getMessage());
			}
		}
	}

	private String getFilePathFromUser() {

		ConsoleUtils.printPrompt("Enter the file path for the embeddings file to be parsed");

		// Returns the input of the user from where it was called.
		// Sets the value to lowercase and trims.
		return input.next().trim().toLowerCase();

	}

	private boolean isValidFile(String filePath) {

		// attempt to call parseFile method with FileParser object handling an
		// exceptions that may occur and printing the stack trace.
		// Create a File object using the filePath string
		File file = new File(filePath);

		// Validate if the file exists
		if (!file.exists()) {
			ConsoleUtils.printError("File not found. Please check the file path and ensure the file exists.");
			return false;
		}

		// Validate if the file is readable by the current user
		// If the user does not have permissions to read the file they will be shown a
		// message to change the permissions.
		if (!file.canRead()) {
			ConsoleUtils.printError("File exists but cannot be read. Please check the file permissions.");
			return false;
		}
		if (!parser.isEmbeddingsFile(filePath)) {

			ConsoleUtils.printError("The selected file is not a valid embeddings file. Please select a correct file.");
			return false;
		}
		if (!parser.doFeatureCountsMatch(filePath)) {
			return false;
		}

		return true;

	}

	private void handleParsedFile() {

		// Checks if the file has been parsed but doesn't contain the relevant data.
		// This could happen if the file is technically valid but contains no words or
		// relevant data after parsing.
		// It acts as a safety net to ensure that the file parsing was successful and
		// that the parsed data is usable.
		if (parser.getWords() == null || parser.getWords().length == 0) {
			ConsoleUtils.printPrompt("File parsed but no words were found. Please check the file content.");
		} else {
			ConsoleUtils.printSuccess(parser.getWords().length + " words parsed from current file " + filePath);
		}
	}
	
	

      
	

	// This method will be used by the ObjectManager class to set the wordComparator
	// object for the File Manager class
	// after the file has been parsed.
	public void setWordManager(WordManager wordManager) {

		this.wordManager = wordManager;
	}

	// This method will be used by the ObjectManager class to set the compare object
	// for the File Manager class
	// after the file has been parsed.
	// This is because compare can only be initialised after the file is parsed and
	// wordManager needs the compare object to
	// be initialise in order to initialise itself.
	public void setCompare(WordComparison compare) {

		this.compare = compare;

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

			// Calls the method to that checks all validations relating to the output file.
			// Makes sure that the file is ready to be written to.
			if (outputFileValidations()) {
				// This accessed the method used to compute the similarities and converts it to
				// a String
				// to be used when printing information either to the user or to the output
				// file.
				method = getSimilarityMethod();
				handleOutputFileChoices();

			}

		} catch (Exception e) {
			ConsoleUtils.printError("An error occurred " + e.getMessage());
		}

	}

	// Checks output file validations.
	private boolean outputFileValidations() throws Exception {
		// Checks if there is a comarison word or if words have been comapred or if the
		// output file is specified.
		if (!wordManager.isComaparisonWord() || !wordManager.haveWordsBeenCompared() || !isOutputFileSpecified()) {

			return false;

		}
		// Checks that all required objects are initialised
		if (parser == null || compare == null || wordManager == null) {
			ConsoleUtils.printError("Required components not initialized.");
			return false;
		}

		return true;
	}

	private String showOptions() {
		// This checks which method topMatches or LowMatches was used and updates the
		// message to the user.
		// It does this by changing the String variable.
		// Value for boolean is accessed by the wordManager object
		isTopMatches = wordManager.isTopMatches();
		if (isTopMatches) {
			topOrLowest = "Top";

		} else {
			topOrLowest = "Lowest";
		}
		// Options displayed to the user before output.

		ConsoleUtils.printPrompt("Select an option");
		ConsoleUtils.printPrompt("1. Append " + topOrLowest + " similarity matches using the " + method
				+ " to current output file " + outputFilePath + ".");
		ConsoleUtils.printPrompt("2. Write to the current output file " + outputFilePath + " with " + topOrLowest
				+ " simlarity matches using the " + method + ". All previous outputs will be overwritten.");
		ConsoleUtils.printPrompt("3. Specify a new output file to write " + topOrLowest + " matches using the " + method + ".");
		


		return input.nextLine();

	}

	private String getSimilarityMethod() {
		// Access the similarity method chosen by the user in the compare class.
		int similarityMethod = compare.getSimilarityChoice();
		// This will return the string value of the similarity method to be used when
		// printing information to the user.
		return switch (similarityMethod) {
		case 1 -> "Cosine Distance method";
		case 2 -> "Dot Product method";
		case 3 -> "Euclidean Distance method";
		default -> "Unknown method";
		};
	}

	// This handles the choices made by the user for writing to the output file.
	private void handleOutputFileChoices() throws Exception {
		// Use a while loop that will continue to show the options until a validChoice
		// is made.
		boolean validChoice = false;
		while (!validChoice) {
			try {
				int choice = Integer.parseInt(showOptions());
				// If process choice returns true the choice is valid and the while loop will
				// stop and the program can continue.
				validChoice = processChoice(choice);
				// If there is anything but a number inputted it is caught by the catch block.
			} catch (NumberFormatException e) {
				ConsoleUtils.printError("Invalid input. Please enter a number between 1 and 3.");
			}
		}
	}

	// This method calls the necessary functions depending on the user choice .
	// Also returns a true or false value back to handleOutputFileChoices method. 
	private boolean processChoice(int choice) throws Exception {

		switch (choice) {
		case 1 -> {
			appendTopMatches();
			return true;
		}
		case 2 -> {
			outputToFile();
			return true;
		}
		case 3 -> {
			specifyOutputFile();
			return true;
		}
		
		default -> {
			// If the number value is not 1 to 3 this message is printed.
			ConsoleUtils.printError("Invalid input. Please select a number from 1 to 3.");
			return false;
		}
		}
	}

	// This method passes all the relevant information to the appendResultsToFile method in the FileOutput class.
	private void appendTopMatches() {
		// User is presented with a prompt to append the file or not
		ConsoleUtils.printPrompt("Append " + topOrLowest + " word matches and similarity scores of word " + " ' "
				+ wordManager.getComparisonWord() + " '  using " + method + " for similarity search to "
				+ outputFilePath + " y/n  ?");

		while (true) {
			// Use of while loop again to loop the question until the user enter "y" or "n".
			String choice = input.nextLine();

			// If the user enters y the Fileoutput object is initialised with relevant values.
			if (choice.equalsIgnoreCase("y")) {
				FileOutput fileOutput = new FileOutput(compare.getTopMatches(), compare.getSimilarities(),
						wordManager.getComparisonWord(), outputFilePath, method);
				// The fileOutPut method is called using the object and is passed the boolean isTopMatche in order
				// to print the correct information about what type of search it was to the user. "Lowest" match or "highest".
				fileOutput.appendResultsToFile(isTopMatches);
				break;
			} else if (choice.equalsIgnoreCase("n")) {
				ConsoleUtils.printPrompt("Returning to main menu.");
				break;
			} else {

				ConsoleUtils.printError("Invalid input. Please enter 'y' or 'n'.");

			}
		}

	}

	private void outputToFile() throws Exception {

		ConsoleUtils.printPrompt("Output " + topOrLowest + " word matches and similarity scores of word " + " ' "
				+ wordManager.getComparisonWord() + " '  using " + method + " for similarity search  to "
				+ outputFilePath + " y/n  ?");

		// Use a while loop to continue while the user input is not a ' y ' or a ' n'
		// This validation is important so that the user is given one last option to
		// decide whether to output the data to a file.
		// It also gives them a chance to see that the outputFile and comparison word
		// are correct.
		while (true) {
			String choice = input.nextLine();

			if (choice.equalsIgnoreCase("y")) {
				FileOutput fileOutput = new FileOutput(compare.getTopMatches(), compare.getSimilarities(),
						wordManager.getComparisonWord(), outputFilePath, method);
				fileOutput.outputToFile(isTopMatches);
				break;
			} else if (choice.equalsIgnoreCase("n")) {
				ConsoleUtils.printPrompt("Returning to main menu.");
				break;
			} else {

				ConsoleUtils.printError("Invalid input. Please enter 'y' or 'n'.");

			}
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
			ConsoleUtils.printError("Embeddings file has not been parsed yet.");
			ConsoleUtils.printPrompt("Please select option 1 to specify and parse the embeddings file first.");
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

}
