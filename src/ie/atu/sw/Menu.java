package ie.atu.sw;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Menu {
	// Use Wordcomparison object as instance variable
	private WordComparison compare;
	private SentenceComparison sentcomp;
	FileParser parser = new FileParser();
	// use boolean variable as an instance variable to be used by the class
	private boolean keepRunning = true;
	//store comparison word as an instance variable as it might be used in several methods across the class
	private String comparisonWord = "";
	private String comparisonSentence = "";
	//store outputFilePath as an instance varibale as it may be used across multiple methods.
	private String outputFilePath = "";
	//store the number of top matches that will be stored in the top matches array. 
	// stores number of top sentence matches;
	private int numTopSentenceMatches = 5;
	// Set to 10 but can be changed by the user
	private int numTopMatches = 10;
	//initialise scanner object
    private Scanner input;
	
	//initialize scanner that takes a scanner object as input
	public Menu(Scanner input) {
		
		this.input = input;
	}
	
	
 //method thats called by menu object in Runner class to start the menu
	public void start() throws Exception {
		// loop keeps calling the showOptions method until the user selects the quit option
		while(keepRunning) {
			//call showOptions method to display menu.
			showOptions();
		    // reads the user input parses it to an integer and stores it in the variable choice
			// //Attempt to call process method handling any exceptions that may occur 
			 try {
	                int choice = Integer.parseInt(input.next());
	                input.nextLine(); // Consume the leftover newline
	                processChoice(choice); }
			//call process choice method to deal with the user input.
	                catch (NumberFormatException e) {
		                System.out.println("Invalid input. Please enter a number between 1 and 8.");
			
	                }
			
		}
		
		
	}

	//this method prints the user menu when called
	private void showOptions() {
		
		System.out.println(ConsoleColour.GREEN);
		System.out.println("************************************************************");
		System.out.println("*     ATU - Dept. of Computer Science & Applied Physics    *");
		System.out.println("*                                                          *");
		System.out.println("*          Similarity Search with Word Embeddings          *");
		System.out.println("*                                                          *");
		System.out.println("************************************************************");
		System.out.println("(1) Specify Embedding File");
		System.out.println("(2) Specify an Output File (default: ./out.txt)");
		System.out.println("(3) Enter a Word to compare");
		System.out.println("(4) Configure Options");
		System.out.println("(5) Find Top Matches");
		System.out.println("(6) Output top matches to a file");
		System.out.println("(7) Print Parsed Words");
		System.out.println("(8) Enter a sentence to compare");
		System.out.println("(9) Find top matches for sentence");
		System.out.println("(10) Quit");

		
		//Output a menu of options and solicit text from the user
		System.out.print(ConsoleColour.YELLOW);
		System.out.print("Select Option [1-8]>");
		System.out.println();
		
	}
	
	
	private void processChoice( int choice) {
		
		//user switch statement on the user selection and the call methods based on that selection
		switch(choice) {
		
		case 1 -> parseFile();
		case 2 -> specifyOutputFile();
		case 3 -> enterWord ();
		case 4 -> configureOptions ();
		case 5 -> findTopMatches ();
		case 6 -> outputTopMatchesToFile();
		case 7 -> printParsedWords ();
		case 8 -> enterSentence();
		case 9 -> topSentenceMatches();
		case 10 -> quit();
		default -> System.out.println("Invalid input please select a number from 1 to 10");
	}
	}
	
	

	

	//Most of these methods will take user input and call methods in different classes 
	//using objects of those classes to do so 
	private void parseFile() {
		
		System.out.println("Enter the file path for the embeddings file to be parsed");
		String filePath = input.next();
		//attempt to call parseFile method with FileParser object handling an exceptions that may occur and printing the stack trace.
		try {
			

			
			parser.parseFile(filePath);
			//use object to obtain the number of words stored in the array 
			System.out.println(parser.getWords().length + " words parsed" );
			//catch block if the try block fails will print stack trace and message
		} catch (Exception e) {
			
			e.printStackTrace();
			System.err.println("Error parsing file: " + e.getMessage());
		}
		
	}
	
	//method for user to input outputfile path
	private void specifyOutputFile() {
		
		System.out.print("Enter the output file path: ");
		outputFilePath = input.next();
		System.out.println("Output file set to: " + outputFilePath);
		
		
	}
	
	//method to enter word to compare
	private void enterWord() {
	    System.out.println("Enter a word");
	    comparisonWord = input.next().trim().toLowerCase(); // Normalize input
	    System.out.println("Comparison word set to " + comparisonWord);
	
	}
	
	
	private void enterSentence() {
		
		System.out.println("Enter a sentence");
	    comparisonSentence = input.nextLine().toLowerCase();
	    System.out.println("Comparison sentence set to " + comparisonSentence);
		
	}

	// Configure options
	// Need to limit the number of words the user can select as top matches as to not exceed array length
	
	private void configureOptions () {
		
		// test whether the file has been parsed or not
		if(parser.getWords() == null || parser.getWords().length == 0) {
			System.out.println("Please parse an embedding file first");
		}
		
		int maxTopMatches = parser.getWords().length;
	    
		
	//while loop will run until the user selects  a valid number of top matches
		while(true) {
		
			System.out.println("How many top matches would you like ");
			numTopMatches = input.nextInt();
			input.nextLine();
			
		
			if (numTopMatches > 0 && numTopMatches <= maxTopMatches) {
                break;
            } else {
                System.out.println("Invalid number. Please enter a number between 1 and " + maxTopMatches + ":");
            }
		
		
		
		}//end while
		System.out.println("Number of top matches set to " + numTopMatches);
		
	}
	
	
	private void  findTopMatches () {
		
		//create object of wordcomparison class and pass it the words and embeddings arrays parsed from the file.
		
		 compare = new WordComparison(parser.getWords(), parser.getEmbeddings());
		
		//initialize array to that will be equal to the array returned by the findTopMatches method.
		//because it will return an array i need to initilaize one here
		String [] topMatches = compare.findTopMatches(comparisonWord, numTopMatches);
		 
		
		
		System.out.println("Top " + numTopMatches + " matches for '" + comparisonWord + "':");
		//changed the for loop 
		for (int i = 0; i < numTopMatches ; i++) {
		      
			System.out.println("Number " + (i + 1) + " match is  : " + topMatches[i] + " similarity score : " + compare.getSimilarities()[i] );
			
			
		}

	}
	
	private void topSentenceMatches()  {
		
		compare = new WordComparison(parser.getWords(), parser.getEmbeddings());
		
		sentcomp = new SentenceComparison(compare, parser.getWords(), parser.getEmbeddings(), numTopSentenceMatches, numTopMatches);
		String [] topSentences = sentcomp.findSentenceTopMatches(comparisonSentence, numTopMatches);
		
		
		System.out.println("Top sentences for " + comparisonSentence + " is :");
		for (String sentence : topSentences) {
			
			System.out.println(sentence);
		}
	
	}

	
	
	//Implement after 
	private void outputTopMatchesToFile () {
		
		FileOutput fileOutput = new FileOutput(compare.getTopMatches(), compare.getSimilarities(), comparisonWord, outputFilePath);
		
		fileOutput.outputToFile();
	}
	
	
	private void printParsedWords () {
		//create array to store parsed words that are accessed by parser object that calls getWords method
		String[] words = parser.getWords();
		//if there are no words parsed print message
		if (words == null) {
			System.out.println("No words have been parsed yet.");
			return;
		}

		System.out.println("Parsed words:");
		//use for each loop to print each word in the words array
		//have to initialise variable word to store and print each word during each round of the loop
		for (String word : words) {
			System.out.println(word);
		}
		
	}
	
	//quit menu
	private void quit () {
		
		System.out.println("Exiting program........");
		keepRunning = false;
		
	}
	
	
	
}
	
	
	
	
	
	
	
	

