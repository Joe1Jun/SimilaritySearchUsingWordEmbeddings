package ie.atu.sw;

import java.util.Scanner;

public class WordManager {

	private Scanner input;
	private FileParser parser;

	private WordComparison compare;
	private ConfigurationManager configManager;
	private WordIndexFinder wordIndexFinder;
	// This array stores the similarity scores in either from lowest to highest or
	// highest to lowest depending on which method was selected by the user
	private String[] topMatches;
	// This stores the word specified by the user for comparison.
	private String comparisonWord = "";
	// This variable holds the value of "Top" or "Lowest" depending on user method
	// selection.
	// This is only used for user output so users can see exactly what they are
	// writing to the output file.
	private String topOrLowest = "";
	// Holds the number of top matches to output specified by the user.
	private int numTopMatches;
	// This boolean is used to determine which method the user selected. Lowest
	// Similarity Scores or Highest
	private boolean isTopMatches;

	// All these instances of objects are initialised and passed to the constructor
	// via the objectManager class
	public WordManager(Scanner input, FileParser parser, WordComparison compare, ConfigurationManager configManager,
			WordIndexFinder wordIndexFinder) {
		this.input = input;
		this.parser = parser;
		this.compare = compare;
		this.configManager = configManager;
		this.wordIndexFinder = wordIndexFinder;

	}

	public void specifyWord() {
		// Asks the user to enter a word
		ConsoleUtils.printPrompt("Enter a word");
		// The newComparisonWord variable is set to the user input.
		// The word is also normalised. All white space is trimmed and it is reduced to
		// lower case to match the words in the array
		String newComparisonWord = input.next().trim().toLowerCase();
		// checks if word is valid
		if (!isWordInEmbeddingFile(newComparisonWord)) {

			return;

		}
		// sets the newComparisonWord to the instance variable comparison word.
		setNewComparisonWord(newComparisonWord);

	}

	// Checks if word is in the words array.
	private boolean isWordInEmbeddingFile(String newComparisonWord) {
		// Creates new WordIndexFinder object and pass the words array from this
		// instance of the parser object.
		wordIndexFinder = new WordIndexFinder(parser.getWords());
		// Access the findWordInex method to determine which index the word is at in the
		// words array.
		int wordIndex = wordIndexFinder.findWordIndex(newComparisonWord);
		// -1 is returned from the method if the word doesn't equal any words in the
		// words array and therefore has no index.
		if (wordIndex == -1) {
			// returns false if an index is not found and prints an error to the user.
			ConsoleUtils.printError("Word not found in embeddings. PLease input another word");
			return false;
		}
		// Returns true if an index is found
		return true;

	}

	private void setNewComparisonWord(String newComparisonWord) {

		// Clear previous topMatches and similarities if the word has changed
		// This is to make sure that the top matches from the previous comparison word
		// are not output to the file
		// This could occur if the user sets a new comparison word but doesn't find the
		// top matches of the new word.
		if (!newComparisonWord.equals(comparisonWord)) {
			comparisonWord = newComparisonWord;
			// Clear the topMatches to reset the state .
			topMatches = null;
			ConsoleUtils.printSuccess("Comparison word set to " + comparisonWord);
			ConsoleUtils
					.printPrompt("Previous matches cleared. Please find matches again by selecting the menu option.");
		} else {
			ConsoleUtils.printSuccess("Comparison word remains " + comparisonWord);
		}
	}

	// This method finds the top highest or lowest matches of the word.
	// If the user selects lowest matches from the main menu the boolean value here
	// passed from the objectManager class is set to false.
	// Set to true if the user selects highest matches.
	// This way the logic can be contained within one method as all the validations
	// and methods that are accessed are the same.
	public void findMatches(boolean isTopMatches) {
		// This sets the instance variable topMatches to the boolean value passed from
		// the objectManager class.
		// This value was not passed to the constructor as the method of highest or
		// lowest matches can be chosen any time after the file is parsed.
		this.isTopMatches = isTopMatches;
		// The program will try to execute the code inside the try block.
		try {

			// Gets the  number of top matches from the ConfigurationManager class
			numTopMatches = configManager.getNumTopMatches();

			//These validations check if the comparison word is specified or if the number of top matches is set.
			// If not it will return the print error statements to the user
			if (!isComaparisonWord() || !isNumTopMatches()) {
				return;
			}

			// Initialise array to that will be equal to the array returned by the
			// findTopMatches method.
			// findTopMatches method is accessed by the WordComparison object compare.
			// The parameters for this method are the instance variables that have been
			// initialised by other methods in the Menu class.

			topMatches = compare.findTopMatches(comparisonWord, numTopMatches, isTopMatches);

			// Call method to print top word matched to the console and pass it the
			// topMatches array as a parameter
			printWordMatches(topMatches);
			// The catch block will catch any exceptions print the stack trace and an error
			// message.

		} catch (Exception e) {

			e.printStackTrace();
			ConsoleUtils.printError("An error occured " + e.getMessage());

		}

	}

	// Prints the top word matches to the console.
	private void printWordMatches(String[] topMatches) {
		// Checks if the user selected Highest or Lowest similarities via the boolean value.
		if (isTopMatches) {
			topOrLowest = "Top ";
		} else {
			topOrLowest = "Lowest";
		}
		// These lines add headings to  the matches that will be displayed to the
		// user
		// Including formatting to make sure that all the scores and words align neatly
		System.out.println(ConsoleColour.CYAN);
		System.out.println(topOrLowest + " " + numTopMatches + " similarity matches for '" + comparisonWord + "':");
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

	


	// Checks if the top or lowest matches of the words have been computed.
	public boolean haveWordsBeenCompared() {
		// If the topMatches array is null is has not been populated and
		// therefore there are no topMatches which means the user has not selected to
		// find top matches from the menu.
		if (topMatches == null) {

			ConsoleUtils
					.printError("Number of matches for word:" + " ' " + comparisonWord + " ' " + "have not been computed");
			ConsoleUtils.printPrompt("Please select  option to find higest or lowest matches from main menu.");

			return false;
		}

		return true;
	}

	// Method to check is there is a comparison word stored in the instance
	// variable.
	public boolean isComaparisonWord() {
		// Checks if the comparison word is empty.
		if (comparisonWord.isEmpty()) {

			ConsoleUtils.printError("No comparison word has been specified.");
			ConsoleUtils.printPrompt("Please select the option to specify a comparison word from the main menu.");

			return false;
		}

		return true;
	}

	// Check if the number of top matches has been set
	private boolean isNumTopMatches() {

		if (numTopMatches == 0) {
			ConsoleUtils.printError("Number of matches has not been specified.");
			ConsoleUtils.printPrompt("Please select option to specify matches from main menu.");
			return false;

		}

		return true;
	}

	public void printParsedWords() throws Exception {
		// Create array to store parsed words.
		// Calls getWords method using the FileParser object.

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

	// Get methods for instance variables to be accessed by other classes.
	public boolean isTopMatches() {
		return isTopMatches;
	}

	public String getComparisonWord() {
		return comparisonWord;
	}

	public WordComparison getComparisonObject() {

		return compare;

	}
	
	// Getter to get top matches
		public String[] getTopMatches() {
			return topMatches;
		}

}
