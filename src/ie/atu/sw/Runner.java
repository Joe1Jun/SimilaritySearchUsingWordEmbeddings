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

  //Print progress method will be implemented at the end******
public static void printProgress(int index, int total) {
	if (index > total) return;	//Out of range
    int size = 50; 				//Must be less than console width
    char done = '█';			//Change to whatever you like.
    char todo = '░';			//Change to whatever you like.
    
    //Compute basic metrics for the meter
    int complete = (100 * index) / total;
    int completeLen = size * complete / 100;
    
    /*
     * A StringBuilder should be used for string concatenation inside a 
     * loop. However, as the number of loop iterations is small, using
     * the "+" operator may be more efficient as the instructions can
     * be optimized by the compiler. Either way, the performance overhead
     * will be marginal.  
     */
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    for (int i = 0; i < size; i++) {
    	sb.append((i < completeLen) ? done : todo);
    }
    
    /*
     * The line feed escape character "\r" returns the cursor to the 
     * start of the current line. Calling print(...) overwrites the
     * existing line and creates the illusion of an animation.
     */
    System.out.print("\r" + sb + "] " + complete + "%");
    
    //Once the meter reaches its max, move to a new line.
    if (done == total) System.out.println("\n");
}

	

}
