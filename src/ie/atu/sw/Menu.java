
package ie.atu.sw;

public class Menu {
    private ObjectManager objectManager;
    private boolean keepRunning = true;

    // The menu only takes the object manager class in its constructor passed from the Runner class.
    public Menu(ObjectManager objectManager) {
        this.objectManager = objectManager;
    }

    // This method is called from runner and start the menu.
    public void start() throws Exception {
        while (keepRunning) {
        	// The while loop will continue to run until the user selects the quit option
        	// which sets the keepRunning boolean value to false.
            showOptions();
            try {
            	// Read the user's choice from the console input.
                // Integer.parseInt converts the string input to an integer.
                // This integer determines the action to be taken by the processChoice method.
            	// Using the same Scanner instance ensures consistency across an application.
                int choice = Integer.parseInt(objectManager.getInput().next());
                
                // Consume the leftover newline
                objectManager.getInput().nextLine();
                // processChoice method is called to process the user input.
                processChoice(choice);
                // Catch block will print a error message to the user if they don't enter a valid input.
            } catch (NumberFormatException e) {
                ConsoleUtils.printError("Invalid input. Please enter a number between 1 and 8.");
            }
        }
    }
    
    // This is the main menu that will be shown to the user throughout the program 

    private void showOptions() {
    	System.out.println(ConsoleColour.GREEN_BOLD_BRIGHT);
    	System.out.println("*************************************************************");
    	System.out.println("*     ATU - Dept. of Computer Science & Applied Physics     *");
    	System.out.println("*                                                           *");
    	System.out.println("*          Similarity Search Using Word Embeddings          *");
    	System.out.println("*                                                           *");
    	System.out.println("*************************************************************");
    	System.out.println("(1) Specify Embedding File");
    	System.out.println("(2) Specify an Output File ");
    	System.out.println("(3) Specify a word to compare");
    	System.out.println("(4) Specify number of word matches");
    	System.out.println("(5) Find Highest Similarity Matches");
    	System.out.println("(6) Find Lowest Similarity Matches");
    	System.out.println("(7) Output matches to a file");
    	System.out.println("(8) Print Parsed Words");
    	System.out.println("(9) Quit");
    	ConsoleUtils.printPrompt("Select Option [1-8]>");

    }
    
    
    // The choice entered by the user accesses the methods to carry out the request
    // The calling of these methods are controlled by the ObjectManager class.
    private void processChoice(int choice) throws Exception {
        switch (choice) {
            case 1 -> objectManager.parseFile();
            case 2 -> objectManager.specifyOutputFile();
            case 3 -> objectManager.specifyWord();
            case 4 -> objectManager.specifyTopMatches();
            case 5 -> objectManager.findTopMatches();
            case 6 -> objectManager.findLowMatches();
            case 7 -> objectManager.outputMatchesToFile();
            case 8 -> objectManager.printParsedWords();
            case 9 -> quit();
            default -> ConsoleUtils.printError("Invalid input. Please select a number from 1 to 8.");
        }
    }

    // This method quite the program.
    private void quit() {
        System.out.println("Exiting program........");
        keepRunning = false;
    }
}

