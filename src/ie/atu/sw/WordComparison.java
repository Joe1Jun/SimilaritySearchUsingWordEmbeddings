package ie.atu.sw;



public class WordComparison {
    //sets the length of the topMatches array. Can be changed by the user
	private int configure;
	//stores the parsed words from file
	private String[] words;
	//stores the parsed embeddings from the files
    private double[][] embeddings;
    //stores the top matches after comparison.
    private String [] topMatches = new String [configure];
}
