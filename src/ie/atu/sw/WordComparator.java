package ie.atu.sw;

import java.util.Scanner;

public class WordComparator {

	private Scanner input;
	private FileParser parser;
	private FileManager fileManager;

	private WordComparison compare;
	private ConfigurationManager configManager;
	private String[] topMatches;
	private String comparisonWord = "";
	private int numTopMatches;

	public WordComparator(Scanner input) {

		this.input = input;

	}

	public WordComparator(Scanner input, FileParser parser, WordComparison compare, FileManager fileManager,
			ConfigurationManager configManager) {
		this.input = input;
		this.parser = parser;
		this.fileManager = fileManager;
		//this.compare = fileManager.getCompare();
		this.compare = compare;
		this.configManager = configManager;

	}

	public void specifyWord() {
		// Asks the user to enter a word
		ConsoleUtils.printPrompt("Enter a word");

		// The newComparisonWord variable is set to the user input.
		// The word is also normalised. All white space is trimmed and it is reduced to
		// lower case to match the words in the array
		String newComparisonWord = input.next().trim().toLowerCase();

		if (!isWordInEmbeddings(newComparisonWord)) {
			return;
		}

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
			// Clear the topMatches  to reset the state .
			 topMatches = null;
			ConsoleUtils.printSuccess("Comparison word set to " + comparisonWord);
			ConsoleUtils.printPrompt(
					"Previous top matches cleared. Please find top matches again by selecting the menu option.");
		} else {
			ConsoleUtils.printSuccess("Comparison word remains " + comparisonWord);
		}
		// If validation passes the word is printed to the console.

		ConsoleUtils.printSuccess("Comparison word set to " + comparisonWord);

	}

	public void findTopMatches() {

		// The program will try to execute the code inside the try block.
		try {
			
			 numTopMatches = configManager.getNumTopMatches();

			if (!fileManager.isFileParsed() || !isComaparisonWord() ||!isNumTopMatches()) {
				return;
			}
			System.out.println("Compare object initialized: " + (compare != null));

			// Create object of WordComparison class and pass the the words and embeddings
			// arrays parsed from the file to the contructor.

			// compare = new WordComparison(parser.getWords(), parser.getEmbeddings());

			// Initialise array to that will be equal to the array returned by the
			// findTopMatches method.
			// findTopMatches method is accessed by the WordComparison object compare.
			// The parameters for this method are the instance variables that have been
			// initialised by other methods in the Menu class.
			

			topMatches = compare.findTopMatches(comparisonWord, numTopMatches);

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

	public String[] getTopMatches() {
		return topMatches;
	}

	public void setTopMatches(String[] topMatches) {
		this.topMatches = topMatches;
	}

	public boolean haveWordsBeenCompared() {
		// If the wordComparison object is empty is has not been initialised and
		// therefore there are no topMatches.
		if (compare.getTopMatches() == null) {

			ConsoleUtils
					.printError("Top matches for word:" + " ' " + comparisonWord + " ' " + "have not been computed");
			ConsoleUtils.printPrompt("Please select  option to find top matches from main menu.");

			return false;
		}

		return true;
	}

	// Method to check is there is a comparison word stored in the instance
	// variable.
	public boolean isComaparisonWord() {

		if (comparisonWord.isEmpty()) {

			ConsoleUtils.printError("No comparison word has been specified.");
			ConsoleUtils.printPrompt("Please select the option to specify a comparison word from the main menu.");

			return false;
		}

		return true;
	}

	private boolean isWordInEmbeddings(String newComparisonWord) {

		WordIndexFinder findWordIndex = new WordIndexFinder(parser.getWords());
		int wordIndex = findWordIndex.findWordIndex(newComparisonWord);
		if (wordIndex == -1) {
			ConsoleUtils.printError("Word not found in embeddings. PLease input another word");
			return false;
		}
		return true;

	}
	
	private boolean isNumTopMatches() {
		
		if (numTopMatches == 0) {
			ConsoleUtils.printError("Number of top matches has not been selected. Please select configure option from main menu.");
			return false;
			
		}
		
		return true;
	}

	public void printParsedWords() throws Exception {
		// Create array to store parsed words.
		// Calls getWords method using the FileParser object.
		if (!fileManager.isFileParsed()) {
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

	public String getComparisonWord() {
		return comparisonWord;
	}

	public WordComparison getComparisonObject() {

		return compare;

	}

}
