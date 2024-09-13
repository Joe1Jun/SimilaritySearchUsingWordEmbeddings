package ie.atu.sw;



public class Runner {
	
public static void main(String[] args) throws Exception {
		
	    //Create objectManager object.
	    ObjectManager objectManager = new ObjectManager();
		
		//Create menu object passing scanner object to it 
        Menu menu = new Menu(objectManager);
        
        //Attempt to call start method from menu class handling any exceptions that may occur
        try {
        	 menu.start();
        } catch (Exception e) {
        	 // Print a user-friendly message instead of the stack trace
             e.printStackTrace();
        }
		
		
		

		

}

	

}
