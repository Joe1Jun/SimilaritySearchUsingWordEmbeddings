package ie.atu.sw;

//import java scanner
import java.util.Scanner;

public class Runner {
	
public static void main(String[] args) throws Exception {
		
	    // Create scanner object
		Scanner scanner = new Scanner(System.in);
		
		//Create menu object passing scanner object to it 
        Menu menu = new Menu(scanner);
        
        //Attempt to call start method from menu class handling any exceptions that may occur
        try {
        	 menu.start();
        } catch (Exception e) {
        	//Print the stack trace if an exception occurs
            e.printStackTrace();
        }
		
		
		

		

        // Change the colour of the console text
		System.out.print(ConsoleColour.YELLOW);
		 // The size of the meter. 100 equates to 100%
		int size = 100;
		// The loop equates to a sequence of processing steps
		for (int i = 0; i < size; i++) { 
			// After each (some) steps, update the progress meter
			printProgress(i + 1, size); 
			 // Slows things down so the animation is visible
			Thread.sleep(10);
		}
	}

public static void printProgress(int index, int total) {
    if (index > total) return;    // Out of range
    int size = 50;                // Must be less than console width
    char done = '█';              // Change to whatever you like.
    char todo = '░';              // Change to whatever you like.
    
    // Compute basic metrics for the meter
    int complete = (100 * index) / total;
    int completeLen = size * complete / 100;
    
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    for (int i = 0; i < size; i++) {
        sb.append((i < completeLen) ? done : todo);
    }
    
    System.out.print("\r" + sb + "] " + complete + "%");
    System.out.flush();  // Ensure progress bar is updated immediately
    
    // Once the meter reaches its max, move to a new line.
    if (index == total) System.out.println();  // Move to a new line after completion
}

	

}
