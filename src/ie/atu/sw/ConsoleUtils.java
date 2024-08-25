package ie.atu.sw;

public class ConsoleUtils {
	
	// Method to print messages in a specific color
    public static void printColored(String message, ConsoleColour color) {
        System.out.print(color);
        System.out.println(message);
        System.out.print(ConsoleColour.RESET);
    }

    // Method to print messages with a newline in a specific color
    public static void printlnColored(String message, ConsoleColour color) {
        System.out.print(color);
        System.out.println(message);
        System.out.print(ConsoleColour.RESET);
        
    }

    // Method to print error messages (Red color)
    public static void printError(String message) {
        printlnColored(message, ConsoleColour.RED);
    }

    // Method to print success messages (Green color)
    public static void printSuccess(String message) {
        printlnColored(message, ConsoleColour.GREEN);
    }

    // Method to print prompts (Yellow color)
    public static void printPrompt(String message) {
        printlnColored(message, ConsoleColour.YELLOW_BRIGHT);
    }

    // Method to print normal output (Bold Bright Black color)
    public static void printOutput(String message) {
        printlnColored(message, ConsoleColour.BLACK_BOLD_BRIGHT);
    }

    // Method to print headers (Bold Bright Green color)
    public static void printHeader(String message) {
        printlnColored(message, ConsoleColour.GREEN_BOLD_BRIGHT);
    }


}
