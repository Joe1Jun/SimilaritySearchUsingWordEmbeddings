package ie.atu.sw;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConfigurationManager {
    private final Scanner input;
   
    private FileParser parser; 
    
    private int  numTopMatches ;

    public ConfigurationManager(Scanner input, FileParser parser) {
        this.input = input;
        
        this.parser = parser;
    }

    
 // Configure options
 	// Need to limit the number of words the user can select as top matches as to
 	// not exceed array length
 	public void specifyTopMatches() throws Exception {

 		

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
 			specifyTopMatches(); // Retry configuration
 		}

 		catch (Exception e) {
 			// Catch any other exceptions and inform the user
 			ConsoleUtils.printError("An error occurred: " + e.getMessage());
 			// Restart or handle the error appropriately
 			
 		}
 		
 	}

 	// Get method so numTopMatches can be accessed by other classes.

	public int getNumTopMatches() {
		return numTopMatches;
	}
  
    
    
}

