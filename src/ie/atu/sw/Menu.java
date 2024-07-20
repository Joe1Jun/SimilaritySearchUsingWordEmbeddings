package ie.atu.sw;

import java.util.Scanner;

public class Menu {
	
	FileParser parser = new FileParser();
	// use boolean variable as an instance variable to be used by the class
	private boolean keepRunning = true;
	
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
		try {
			parser.parseFile(filePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void specifyOutputFile() {
		
		System.out.println("Enter the output file path");
		String outputFilePath = input.next();
		
	}
	
	private void enterWordOrText() {
		
		System.out.println("Enter a word");
		String comparisonWord = input.next();
		
	}
	
	private void configureOptions () {
		
		System.out.println("How many top matches would you like ");
		int topMatches = input.nextInt();
		
	}
	
	private void  findTopMatches () {
		
		
		
	}
	
	private void outputTopMatchesToFile () {
		
		
	}
	
	private void printParsedWords () {
		
		
	}
	
	private void quit () {
		
		System.out.println("Exiting program........");
		keepRunning = false;
		
	}
	
	
	
	
	
	
	
	
}
