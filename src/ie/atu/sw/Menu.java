package ie.atu.sw;

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
	// Use WordComparison object as instance variable
	private WordComparison compare;
	private SentenceComparison sentcomp;
	private FileParser parser = new FileParser();
	// Use boolean variable as an instance variable to be used by the start method
	// to keep printing the menu options.
	// until the user decides to quit the program.
	private boolean keepRunning = true;
	// store comparison word as an instance variable as it might be used in several
	// methods across the class
	private String comparisonWord = "";
	private String comparisonSentence = "";
	// store outputFilePath as an instance varibale as it may be used across
	// multiple methods.
	private String outputFilePath = "";
	// store the number of top matches that will be stored in the top matches array.
	// stores number of top sentence matches;
	private int numTopSentenceMatches = 5;
	// Set to 10 but can be changed by the user
	private int numTopMatches = 10;
	// initialise scanner object
	private Scanner input;

	// InitialiSe scanner that takes a scanner object as input
	public Menu(Scanner input) {

		this.input = input;
	}

	public Menu() {

	}

	// method thats called by menu object in Runner class to start the menu
	public void start() throws Exception {
		// Loop keeps calling the showOptions method until the user selects the quit.
		// option
		while (keepRunning) {
			// call showOptions method to display menu.
			showOptions();
			// Reads the user input parses it to an integer and stores it in the variable
			// choice.
			// Attempt to call process method handling any exceptions that may occur.
			try {
				int choice = Integer.parseInt(input.next());
				input.nextLine(); // Consume the leftover newline
				// Call process choice method to deal with the user input.
				processChoice(choice);
			}
			// This catch block throws an exception if anything other than a number is input
			// by the user.
			catch (NumberFormatException e) {

				ConsoleUtils.printError("Invalid input. Please enter a number between 1 and 8.");

			}

		}

	}

	// This method prints the user menu when called from the start method.
	private void showOptions() {

		System.out.println(ConsoleColour.GREEN_BOLD_BRIGHT);
		System.out.println("************************************************************");
		System.out.println("*     ATU - Dept. of Computer Science & Applied Physics    *");
		System.out.println("*                                                          *");
		System.out.println("*          Similarity Search Using Word Embeddings          *");
		System.out.println("*                                                          *");
		System.out.println("************************************************************");
		System.out.println("(1) Specify Embedding File");
		System.out.println("(2) Specify an Output File (default: ./out.txt)");
		System.out.println("(3) Specify a word to compare");
		System.out.println("(4) Configure Options");
		System.out.println("(5) Find Top Matches");
		System.out.println("(6) Output top matches to a file");
		System.out.println("(7) Print Parsed Words");
		System.out.println("(8) Enter a sentence to compare");
		System.out.println("(9) Find top matches for sentence");
		System.out.println("(10) Quit");

		// Output a menu of options and solicit text from the user

		ConsoleUtils.printPrompt("Select Option [1-8]>");
		System.out.println();

	}

	private void processChoice(int choice) throws Exception {

		// User switch statement on the user selection and the call methods based on
		// that selection
		switch (choice) {

		case 1 -> parseFile();
		case 2 -> specifyOutputFile();
		case 3 -> specifyWord();
		case 4 -> configureOptions();
		case 5 -> findTopMatches();
		case 6 -> outputTopMatchesToFile();
		case 7 -> printParsedWords();
		case 8 -> enterSentence();
		case 9 -> topSentenceMatches();
		case 10 -> quit();

		default -> {

			ConsoleUtils.printError("Invalid input. Please select a number from 1 to 10.");
		}
		}
	}

	// Parse file method will take the user specified file and pass it to the
	// FileParser class to be parsed.
	private void parseFile() throws Exception {

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
	private void specifyOutputFile() {

		ConsoleUtils.printPrompt("Enter the output file path: ");
		outputFilePath = input.next();

		ConsoleUtils.printSuccess("Output file set to: " + outputFilePath);

	}

	// Method to enter word to compare
	private void specifyWord() {
		// Asks the user to enter a word
		ConsoleUtils.printPrompt("Enter a word");
		// The newComparisonWord variable is set to the user input.
		// The word is also normalised. All white space is trimmed and it is reduced to
		// lower case to match the words in the array
		String newComparisonWord = input.next().trim().toLowerCase();

		// This checks if the newComparisonWord entered by the user is valid.
		// If the word is empty or consists of non word charachters the user is asked to
		// enter another word.
		if (newComparisonWord.isEmpty() || !newComparisonWord.matches("[a-zA-Z]+")) {
			ConsoleUtils.printError("Invalid input. Please enter a valid word consisting of letters only.");
			// Calls the function again if the word is not valid.
			specifyWord();
			return;
		}

		// Clear previous topMatches and similarities if the word has changed
		// This is to make sure that the top matches from the previous comparison word
		// are not output to the file
		// This could occur if the user sets a new comparison word but doesn't find the
		// top matches of the new word.
		if (!newComparisonWord.equals(comparisonWord)) {
			comparisonWord = newComparisonWord;
			// Clear the comparison object to reset the state.
			compare = null;
			ConsoleUtils.printSuccess("Comparison word set to " + comparisonWord);
			ConsoleUtils.printPrompt(
					"Previous top matches cleared. Please find top matches again by seclecting the menu option.");
		} else {
			ConsoleUtils.printSuccess("Comparison word remains " + comparisonWord);
		}
		// If validation passes the word is printed to the console.

		ConsoleUtils.printSuccess("Comparison word set to " + comparisonWord);

	}

	private void enterSentence() {

		ConsoleUtils.printPrompt("Enter a sentence");
		comparisonSentence = input.nextLine().toLowerCase();
		ConsoleUtils.printSuccess("Comparison sentence set to " + comparisonSentence);

	}

	// Configure options
	// Need to limit the number of words the user can select as top matches as to
	// not exceed array length
	private void configureOptions() throws Exception {

		if (!isFileParsed()) {

			return;
		}

		try {

			// Initialise a maximum number of top matches and make it equal to the length of
			// the array

			int maxTopMatches = parser.getWords().length;
			// Use a while loop so that the option is repeated to the user if the number the
			// select is larger than maxTopMatches.
			while (true) {

				ConsoleUtils.printPrompt("How many top word matches would you like?");
				// Make the instance variable numTopMatches equal to the user input.
				numTopMatches = input.nextInt();
				// Consumes next line charachter.
				input.nextLine();
				// If the number entered by the user is greater than 0 and less than or equal to
				// the variable maxTopMatches the program breaks out of the loop.
				if (numTopMatches > 0 && numTopMatches <= maxTopMatches) {
					break;
					// Otherwise the user is presented with the statement below and the loop is
					// repeated.
				} else {
					ConsoleUtils
							.printError("Invalid number. Please enter a number between 1 and " + maxTopMatches + ":");
				}

			} // end while

			ConsoleUtils.printSuccess("Number of top matches set to " + numTopMatches);

			// Catch block will catch any errors
			// The first catch handles the case where input is not an integer
		} catch (InputMismatchException e) {

			ConsoleUtils.printError("Invalid input. Please enter a valid number.");

			input.nextLine();
			configureOptions(); // Retry configuration
		}

		catch (Exception e) {
			// Catch any other exceptions and inform the user
			ConsoleUtils.printError("An error occurred: " + e.getMessage());
			// Restart or handle the error appropriately
			start();
		}

	}

	private void findTopMatches() {

		// The program will try to execute the code inside the try block.
		try {

			if ( !isComaparisonWord() || !isFileParsed() )  {
				return;
			}

			

			// Create object of WordComparison class and pass the the words and embeddings
			// arrays parsed from the file to the contructor.

			compare = new WordComparison(parser.getWords(), parser.getEmbeddings());

			// Initialise array to that will be equal to the array returned by the
			// findTopMatches method.
			// findTopMatches method is accessed by the WordComparison object compare.
			// The parameters for this method are the instance variables that have been
			// initialised by other methods in the Menu class.

			String[] topMatches = compare.findTopMatches(comparisonWord, numTopMatches);

			// Call method to print top word matched to the console and pass it the
			// topMatches array as a parameter
			printTopWordMatches(topMatches);
			// The catch block will catch any exceptions print the stack trace and an error
			// message.

		} catch (Exception e) {

			e.printStackTrace();
			ConsoleUtils.printError("An error occured " + e.getMessage());

		}

	}

	private void printTopWordMatches(String[] topMatches) {

		// These lines add headings to the top matches that will be displayed to the
		// user
		// Including formatting to make sure that all the scores and words align neatly
		System.out.println(ConsoleColour.CYAN);
		System.out.println("Top " + numTopMatches + " matches for '" + comparisonWord + "':");
		System.out.println("------------------------------------------");
		System.out.printf("    %-15s | %-20s%n", "Word", "Similarity Score   |");
		System.out.println("------------------------------------------");
		// Use a for loop to loop thorough the topMatches and print them to the console.
		// Use the WordComparison object here to retrieve the similarities from the
		// getSimilarities method in the WordComparison class.
		// This is and array of the matched similarity scores so can loop through each
		// value at [i]
		// which will print the associated similarity score of each top word match.
		for (int i = 0; i < numTopMatches; i++) {

			System.out.printf("%2d. %-15s : %-20s%n", i + 1, topMatches[i], compare.getSimilarities()[i]);

		}

	}

	private void topSentenceMatches() throws Exception {

		if (!isFileParsed()) {

			return;
		}

		compare = new WordComparison(parser.getWords(), parser.getEmbeddings());

		sentcomp = new SentenceComparison(compare, parser.getWords(), parser.getEmbeddings(), numTopSentenceMatches,
				numTopMatches);
		String[] topSentences = sentcomp.findSentenceTopMatches(comparisonSentence, numTopMatches);

		System.out.println("Top sentences for " + comparisonSentence + " is :");
		for (String sentence : topSentences) {

			System.out.println(sentence);
		}

	}

	// Method when selected passed the topMatches and similarities to the FileOutput
	// class to be printed to
	// a user specified output file.
	private void outputTopMatchesToFile() throws Exception {

		// Attempt to send data to be printed to the FileOutput class
		try {

			// Validations access the boolean methods to check if the relevant instance
			// variables hold valid values

			if (!isFileParsed() || !isComaparisonWord() || !isOutputFileSpecified() || !haveWordsBeenCompared()) {

				return;

			}

			ConsoleUtils.printPrompt("Output top word matches and similarity scores of word " + " ' " + comparisonWord
					+ " ' " + " to " + outputFilePath + " y/n  ?");
			// Use a while loop to continue while the user input is not a ' y ' or a ' n'
			// This validation is important so that the user is given one last option to
			// decide whether to output the data to a file.
			// It also gives them a chance to see that the outputFile and comparison word
			// are correct.
			while (true) {
				String choice = input.nextLine();

				if (choice.equalsIgnoreCase("y")) {
					FileOutput fileOutput = new FileOutput(compare.getTopMatches(), compare.getSimilarities(),
							comparisonWord, outputFilePath);
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

	private void printParsedWords() throws Exception {
		// Create array to store parsed words.
		// Calls getWords method using the FileParser object.
		if (!isFileParsed()) {
			return;
		}
		String[] words = parser.getWords();
		// if there are no words parsed print message

		System.out.println("Parsed words:");
		// Use for each loop to print each word in the words array
		// Have to initialise variable word to store and print each word during each
		// round of the loop.

		for (String word : words) {
			System.out.println(word);
		}

	}

	// Validation methods to be used by other methods

	private boolean isFileParsed() throws Exception {

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

	// This method checks if the top matches for the comparison word have been
	// computed.
	private boolean haveWordsBeenCompared() {
		// If the wordComparison object is empty is has not been initialised and
		// therefore there are no topMatches.
		if (compare == null) {

			ConsoleUtils
					.printError("Top matches for word:" + " ' " + comparisonWord + " ' " + "have not been computed");
			ConsoleUtils.printPrompt("Please select  option to find top matches from main menu.");

			return false;
		}

		return true;
	}

	// Method to check is there is a comparison word stored in the instance
	// variable.
	private boolean isComaparisonWord() {

		if (comparisonWord.isEmpty()) {

			ConsoleUtils.printError("No comparison word has been specified.");
			ConsoleUtils.printPrompt("Please select the option to specify a comparison word from the main menu.");

			return false;
		}

		return true;
	}

	// quit menu
	private void quit() {

		System.out.println("Exiting program........");
		keepRunning = false;

	}

}
