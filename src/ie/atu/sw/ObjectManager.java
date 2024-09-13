
package ie.atu.sw;

import java.util.Scanner;

// This class purpose is to manage all the object instances that are dependent on each other and their initialisations.
// Will include all necessary objects as instance variables.
public class ObjectManager {

	private Scanner input;
	private FileParser parser;
	private FileManager fileManager;
	private WordComparison compare;
	private WordManager wordManager;
	private ConfigurationManager configurationManager;
	private WordIndexFinder wordIndexFinder;

// The ObjectManager constructor will initialise the instances of the objects.
	public ObjectManager() {

		this.input = new Scanner(System.in);
		// Initialise the parser object
		this.parser = new FileParser(input);
		// The last two values being passed to the FileManager constructor are initially
		// set to null.
		// These are the instances of the compare and wordManager object.
		// These objects can only be initialised after the file is parsed.
		// The compare object needs to access methods from a fully initialised parser
		// object in order to pass values to its constructor.
		// The wordManager object needs to pass fully initialised compare object, parser
		// object to its constructor.
		// For this reason the compare and wordManger object are only initialised after
		// the fileManger calls the parseFile method.
		// Their valued can then be set in thefileManager class.

		this.fileManager = new FileManager(input, parser, null, null);
		this.configurationManager = new ConfigurationManager(input, parser);
	}

	public void parseFile() throws Exception {
		// fileManger object calls the parse file method
		fileManager.parseFile();

		if (fileManager.isFileParsed()) {
			// The wordIndexFinder object is initialised first via this method.
			initialiseWordIndexFinder();
			// The compareobject is initialised second via this method.
			initialiseCompare();
			// Otherwise the wordManager object would pass a null compare object to its
			// constructor.
			initialiseWordManager();

		}

	}

	// The rational initialising the three objects after a file parsing is
	// successful is that the program can use the same instance of the
	// object during the duration of the program until a different file is parsed.
	// This ensures consistency throughout the application.

	private void initialiseWordIndexFinder() {

		this.wordIndexFinder = new WordIndexFinder(parser.getWords());

	}

	private void initialiseCompare() {

		// This will reinitialise the compare object each time an embeddings file is
		// successfully parsed.

		this.compare = new WordComparison(parser.getWords(), parser.getEmbeddings(), wordIndexFinder);
		// This initialises this instance of the compare object in the fileManger class.
		this.fileManager.setCompare(compare);

	}

	private void initialiseWordManager() {
		// This will initialise the wordManager object each time an embeddings file is
		// successfully parsed
		this.wordManager = new WordManager(input, parser, compare, configurationManager, wordIndexFinder);
		this.fileManager.setWordManager(wordManager);

	}

	// Each method checks first if the file has been parsed before the method can be
	// successfully called.
	// This validation is called via the fileManager object.
	public void specifyOutputFile() throws Exception {
		if (fileManager.isFileParsed()) {
			fileManager.specifyOutputFile();
		}

	}

	public void specifyWord() throws Exception {

		if (fileManager.isFileParsed()) {
			wordManager.specifyWord();
		}
	}

	public void findTopMatches() throws Exception {
		// Pass a boolean true value to the wordManager method to find Top matches.
		// This will eventually lead the sort Class to sort the similarity scores array
		// from highest match to lowest for all
		// similarity methods.
		if (fileManager.isFileParsed()) {
			wordManager.findMatches(true);
		}
	}

	public void findLowMatches() throws Exception {
		// Pass a boolean true value to the wordManager method to find Top matches.
		// This will eventually lead the sort Class to sort the similarity scores from
		// lowest to
		// highest for all
		// similarity methods.
		if (fileManager.isFileParsed()) {
			wordManager.findMatches(false);
		}
	}

	public void outputMatchesToFile() throws Exception {
		if (fileManager.isFileParsed()) {
			fileManager.outputTopMatchesToFile();

		}

	}

	public void printParsedWords() throws Exception {
		if (fileManager.isFileParsed()) {
			wordManager.printParsedWords();
		}
	}

	public void specifyTopMatches() throws Exception {
		if (fileManager.isFileParsed()) {
			configurationManager.specifyTopMatches();

		}

	}

	// Getters
	public Scanner getInput() {
		return input;
	}

}