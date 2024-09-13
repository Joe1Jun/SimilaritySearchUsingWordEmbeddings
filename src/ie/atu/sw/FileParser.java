package ie.atu.sw;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class FileParser {
	private Scanner input;

	private static int numfeatures = 50;
	private String[] words;
	private double[][] embeddings;
	private int featureCount;

	public FileParser(Scanner input) {

		this.input = input;
	}

	// This is a rudimentary check to see if the file that the user is attempting to
	// parse is an embeddings file.
	// Later the feature count is checked.
	// This way the user will know if the file is an embeddings file but has a
	// different number of features then expected.
	// This is to protect against the user accidently parsing a file that is not an
	// embeddings file
	// which could lead to incorrect results or a program crash.
	public boolean isEmbeddingsFile(String filePath) {
		// Attempt to read the file.
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
			int linesChecked = 0;

			// Check the first few lines for the correct format
			while ((line = br.readLine()) != null && linesChecked < 5) {
				// Split on one or more whitespace characters
				String[] parsedWords = line.split("\\s+");

				// Check if the first index of the parsedWords array is a number.
				// Check is all the other indexes of parsedWords are numbers.
				// If both these conditions are not true this method will return false meaning
				// the file is not an embeddings file.

				if (!isFirstIndexNumeric(parsedWords[0]) && !areAllIndexValuesAfterFirstIndexNumeric(parsedWords)) {
					return false;
				}

				linesChecked++;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	// This method checks if the given string can be parsed to a double.
	// If parsing is successful, the string is numeric; otherwise, it is not.
	private boolean isFirstIndexNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private boolean areAllIndexValuesAfterFirstIndexNumeric(String[] parsedWords) {

		// Check if all remaining indexes in the array are numerical values again by
		// attempting to parse the values to double values.
		for (int i = 1; i < parsedWords.length; i++) {
			try {
				Double.parseDouble(parsedWords[i].replace(",", ""));
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return true;
	}

	// Checks if the features of the file theat the user is trying to parse match
	// the features set as a constant in this class
	public boolean doFeatureCountsMatch(String filePath) {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			// Read the first line
			String line;
			// Variable initilaised to be used as a counter in the loop
			int linesChecked = 0;
			while ((line = br.readLine()) != null && linesChecked < 3) {
				// Split on one or more whitespace characters
				String[] parsedWords = line.split("\\s+");
				// Subtract 1 to exclude the word at the first index of each line
				featureCount = parsedWords.length - 1;
				// Increase the counter by one after each loop.
				linesChecked++;
			}
			// If the feature count of the file doesn't match that of the NUM_FEAUTURES
			// constant notify the user.
			if (featureCount != numfeatures) {
				ConsoleUtils.printError("This is an embeddings file.");
				ConsoleUtils.printError("However the number of features in this file is " + featureCount
						+ " which does not match the expected number of features of " + numfeatures + ".");

				changeFeaturesNum();
				return false;
			}

		} catch (IOException e) {

			e.getMessage();
		}
		return true;

	}

	// This method asks the user if they would like to change the number of
	// features in the embeddings file to match the file they just tried to parse
	// unsuccessfully.
	private void changeFeaturesNum() {
		// Consume any leftover newline character
		input.nextLine();
		while (true) {

			ConsoleUtils.printPrompt("Would you like to change the features to " + featureCount + " y/n ?");

			String choice = input.nextLine();

			if (choice.equalsIgnoreCase("y")) {
				// if y changes the feautures to match the file that the user tried to parse.
				numfeatures = featureCount;
				ConsoleUtils.printSuccess("Features set to " + numfeatures + ".");
				break;
			} else if (choice.equalsIgnoreCase("n")) {
				// User can decide to return to main menu.
				ConsoleUtils.printPrompt("Returning to main menu.");
				break;
			} else {

				ConsoleUtils.printError("Invalid input. Please enter 'y' or 'n'.");

			}

		}

	}

	public void parseFile(String filePath) throws Exception {
		// When parsing a file containing words and embeddings, each line typically
		// corresponds to one word and its associated embedding vector.
		// To store each word and its embedding, you need to know how many words (lines)
		// are in the file. This count gives you the number of entries you will need to
		// store in your arrays.
		// For example, if the file has 1,000 lines, you need arrays that can hold 1,000
		// words and 1,000 embedding vectors.
		// count the number of lines and store it in the numLines variable

		int numLines = countLines(filePath);

		// Initialise arrays based on number of lines
		words = new String[numLines];
		embeddings = new double[numLines][numfeatures];

		// read the file again to populate arrays.
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			// initialise index to be used during the loop
			int index = 0;
			// variable to hold each line read from the array
			String line;

			// the line variable is equal to each line as its read.
			while ((line = br.readLine()) != null) {

				// The read line including the words and the embeddings values
				// are all stored as string values in the parsed words array.
				// the split method splits the line one or more whitespace characters and stores
				// this value in the
				// array
				// This means the first index , index 0 of the array will be the word as the
				// word is at the start of each line
				// This also means that the start index for the embedding on each line is 1
				// which should be increased by 1 in a for loop to store the values in the
				// embeddings array
				String[] parsedWords = line.split("\\s+");

				// store the word in the words array
				// the word at index [0] from each line is store in the words array
				// at the current index which increases by one each round of the loop
				// **had to remove commas so word could be compare properly and move to
				// lowercase
				words[index] = parsedWords[0].replace(",", "").toLowerCase();

				// for loop to populate the embeddings array
				// there are 50 features to loop through
				for (int i = 0; i < numfeatures; i++) {
					// loop iterates through the features and populates the the array
					// at the current index with the values that have been stored in the String
					// array above
					// this value at the index is stored along with the current feature value to
					// populate the 2D array
					// have to parse to double from the stored String values

					embeddings[index][i] = Double.parseDouble(parsedWords[i + 1].replace(",", ""));

				}

				// index is increased after each loop to match the current line and also store
				// the word at the
				// index in the words array
				index++;
			}

		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	// private because its only used inside the class
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

		// the final count for the lines in the file is returned to the parse file
		// method.
		return lineCount;
	}

	// Get methods for the populated arrays.
	// These can be accessed by other classes using the parser object.
	public String[] getWords() {
		return words;
	}

	public double[][] getEmbeddings() {
		return embeddings;
	}

}