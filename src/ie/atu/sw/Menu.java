
package ie.atu.sw;

public class Menu {
    private ObjectManager objectManager;
    private boolean keepRunning = true;

    public Menu(ObjectManager objectManager) {
        this.objectManager = objectManager;
    }

    public void start() throws Exception {
        while (keepRunning) {
            showOptions();
            try {
                int choice = Integer.parseInt(objectManager.getInput().next());
                objectManager.getInput().nextLine(); // Consume the leftover newline
                processChoice(choice);
            } catch (NumberFormatException e) {
                ConsoleUtils.printError("Invalid input. Please enter a number between 1 and 8.");
            }
        }
    }

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
    	System.out.println("(8) Quit");
    	ConsoleUtils.printPrompt("Select Option [1-8]>");

    }

    private void processChoice(int choice) throws Exception {
        switch (choice) {
            case 1 -> objectManager.parseFile();
            case 2 -> objectManager.specifyOutputFile();
            case 3 -> objectManager.specifyWord();
            case 4 -> objectManager.configureOptions();
            case 5 -> objectManager.findTopMatches();
            case 6 -> objectManager.outputTopMatchesToFile();
            case 7 -> objectManager.printParsedWords();
            case 8 -> quit();
            default -> ConsoleUtils.printError("Invalid input. Please select a number from 1 to 8.");
        }
    }

    private void quit() {
        System.out.println("Exiting program........");
        keepRunning = false;
    }
}

