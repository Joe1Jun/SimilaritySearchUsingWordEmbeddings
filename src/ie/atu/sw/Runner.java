package ie.atu.sw;

import java.util.Scanner;

public class Runner {
	
public static void main(String[] args) throws Exception {
		
		Scanner scanner = new Scanner(System.in);
        
		
		
		

		

		// You may want to include a progress meter in you assignment!
		System.out.print(ConsoleColour.YELLOW); // Change the colour of the console text
		int size = 100; // The size of the meter. 100 equates to 100%
		for (int i = 0; i < size; i++) { // The loop equates to a sequence of processing steps
			printProgress(i + 1, size); // After each (some) steps, update the progress meter
			Thread.sleep(10); // Slows things down so the animation is visible
		}
	}

	public static int printProgress(int a, int b) {

		return 0;

	}

	

}
