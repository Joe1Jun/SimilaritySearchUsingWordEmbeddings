package ie.atu.sw;

import java.util.Scanner;

public class Menu {
	
	FileParser parser = new FileParser();
	// use boolean variable as an instance variable to be used by the class
	private boolean keepRunning = true;
	//store comparison word as an instance variable as it might be used in several methods across the class
	private String comparisonWord = "";
	//store outputFilePath as an instance varibale as it may be used across multiple methods.
	private String outputFilePath = "";
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
		System.out.println("(3) Enter a Word or Text");
		System.out.println("(4) Configure Options");
		System.out.println("(5) Find Top Matches");
		System.out.println("(6) Output top matches to a file");
		System.out.println("(7) Print Parsed Words");
		System.out.println("(8) Quit");

		
		//Output a menu of options and solicit text from the user
		System.out.print(ConsoleColour.BLACK_BOLD_BRIGHT);
		System.out.print("Select Option [1-?]>");
		System.out.println();
		
	}
	
	
	private void processChoice( int choice) {
		
		//user switch statement on the user selection and the call methods based on that selection
		switch(choice) {
		
		case 1 -> parseFile();
		case 2 -> specifyOutputFile();
		case 3 -> enterWordOrText ();
		case 4 -> configureOptions ();
		case 5 -> findTopMatches ();
		case 6 -> outputTopMatchesToFile();
		case 7 -> printParsedWords ();
		case 8 -> quit();
		default -> System.out.println("Invalid input please select a number from 1 to 8");
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
		
		System.out.println("Enter the output file path");
		outputFilePath = input.next();
		
	}
	
	//method to enter word to compare
	private void enterWordOrText() {
		
		System.out.println("Enter a word");
		comparisonWord = input.next();
		
		
		
	}
	
	
	//configure options
	//*** Add more option *******
	private void configureOptions () {
		
		System.out.println("How many top matches would you like ");
		int topMatches = input.nextInt();
		
	}
	
	// Implement logic after Fileparser class is complete and 
	// Will probably need another class to compare the words and output top matches
	private void  findTopMatches () {
		
		
		
	}
	
	//Implement after 
	private void outputTopMatchesToFile () {
		
		
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
