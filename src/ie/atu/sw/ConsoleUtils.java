package ie.atu.sw;

public class ConsoleUtils {
	
	
    // Method to print messages with a newline in a specific color
    public static void printlnColored(String message, ConsoleColour color) {
        System.out.print(color);
        System.out.println(message);
        System.out.print(ConsoleColour.RESET);
        
    }

    // Method to print error messages (Red color)
    public static void printError(String message) {
        printlnColored(message, ConsoleColour.RED_BRIGHT);
    }

    // Method to print success messages (Green color)
    public static void printSuccess(String message) {
        printlnColored(message, ConsoleColour.GREEN);
    }

    // Method to print prompts (Yellow color)
    public static void printPrompt(String message) {
        printlnColored(message, ConsoleColour.YELLOW_BRIGHT);
    }

    

    // Method to print headers (Bold Bright Green color)
    public static void printHeader(String message) {
        printlnColored(message, ConsoleColour.CYAN);
    }


}
