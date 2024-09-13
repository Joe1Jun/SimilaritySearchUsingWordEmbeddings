package ie.atu.sw;

import java.util.Scanner;

public class SimilarityCalculator {
	private Scanner input = new Scanner(System.in);
	private double[] similarities;
	private double[][] embeddings;
	private double[] wordEmbedding;
	private int choice;

	// Constructor takes the parsed embedings and the embeddings of the comparison
	// word
	public SimilarityCalculator(double[][] embeddings, double[] wordEmbedding) {

		this.embeddings = embeddings;
		this.wordEmbedding = wordEmbedding;
	}

	// This starts the process to compute the similarities.
	public double[] computeSimilarities() {
		// initialise the instance variable similarities to the length of the the parsed
		// embeddings.

		similarities = new double[embeddings.length];

		// While loop will run until a valid choice is made.
		boolean validChoice = false;
		while (!validChoice) {
			try {
				// Initialises the instance variable choice to the value returned from the
				// showOptions method.
				// This value will be the user choice.
				// If this choice its passed to the method to process this choice if not the
				// loop rund again.
				choice = Integer.parseInt(showOptions());
				// Only break loop if choice is valid
				// processChoice will call the methods to compute the similarities.
				validChoice = processChoice(choice);
				// Catch block catches if the user input is not a number.
			} catch (NumberFormatException e) {
				ConsoleUtils.printError("Invalid input. Please enter a number between 1 and 3.");
			}
		}
		// Returns the similarities that have been stored in the instance variable array
		// after the similarity methods have executed.
		return similarities;
	}

	// This is the menu shown to the user to choose between the 3 similarity
	// methods.
	private String showOptions() {

		ConsoleUtils.printPrompt("Which method would you like to use to calculate the similarities?");
		ConsoleUtils.printPrompt(
				"1. Cosine Distance: Measures the angle between word vectors; effective for comparing the direction, useful for finding words with similar contexts.");
		ConsoleUtils.printPrompt(
				"2. Dot Product: Measures the magnitude of overlap between word vectors; good for identifying commonly associated words.");
		ConsoleUtils.printPrompt(
				"3. Euclidean Distance: Measures the straight-line distance between word vectors; useful for finding words that are not closely related.");

		// The value chosen by the user is returned to the method computeSimilarities.
		return input.nextLine();
	}

	// This method uses a switch block to call the methods that match the user
	// choice.
	// This was made a boolean method so that the boolean value can be returned to
	// the while loop in computeSimilarities method.
	// This allows the menu to be shown to the user with an error message if the
	// input is incorrect.
	private boolean processChoice(int choice) {

		switch (choice) {
		case 1 -> {
			cosineChoice();
			return true;
		}
		case 2 -> {
			dotProductChoice();
			return true;
		}
		case 3 -> {
			euclideanDistanceChoice();
			return true;
		}
		default -> {
			// This will print if the user input is a number but not a number contained
			// within the switch block.

			ConsoleUtils.printError("Invalid input. Please select a number from 1 to 3.");
			return false;
		}
		}
	}

	// Compare each embedding in the array with the wordEmbedding using cosine
	// similarity.
	// The return values from the cosine similarity, dot Product and euclidean
	// distance methods are the similarity scores
	// and are stored in the similarities array depending on which method was chosen
	// by the user.
	// The loop runs for the length of the embeddings array.

	private void cosineChoice() {
		for (int i = 0; i < embeddings.length; i++) {
			// Stored each returned similarity score from the cosineSimilarity method in the
			// instance variable similarities array.
			similarities[i] = cosineSimilarity(wordEmbedding, embeddings[i]);
		}

	}

	private void dotProductChoice() {
		for (int i = 0; i < embeddings.length; i++) {
			// Stored each returned similarity score from the cosineSimilarity method in the
			// instance variable similarities array.
			similarities[i] = dotProduct(wordEmbedding, embeddings[i]);
		}
	}

	private void euclideanDistanceChoice() {
		for (int i = 0; i < embeddings.length; i++) {
			// Stored each returned similarity score from the cosineSimilarity method in the
			// instance variable similarities array.
			similarities[i] = euclideanDistance(wordEmbedding, embeddings[i]);
		}

	}

	// Calculates the cosine similarity between two vectors.
	// Cosine similarity is a measure that calculates the cosine of the angle
	// between two non-zero vectors in an inner product space. It is used to
	// determine how similar the vectors are, regardless of their magnitude.
	// A value of 1 indicates that the vectors are identical in direction,
	// 0 means they are orthogonal (no similarity), and -1 means they are
	// diametrically opposed. This method is particularly useful in
	// applications such as document similarity in text analysis.
	//
	// Parameters:
	// - vecA: The first vector.
	// - vecB: The second vector.
	//
	// Returns:
	// - The cosine similarity between vecA and vecB, a value between -1 and 1.
	private double cosineSimilarity(double[] vecA, double[] vecB) {

		// Ensure that both vectors have the same length.
		if (vecA.length != vecB.length) {
			throw new IllegalArgumentException("Vectors must be of the same length.");
		}

		// Variables to store the dot product and the norms of the vectors.
		double dotProduct = 0.0;
		double normA = 0.0;
		double normB = 0.0;

		// Calculate the dot product and the norms for vecA and vecB.
		for (int i = 0; i < vecA.length; i++) {
			// Sum of products for dot product.
			dotProduct += vecA[i] * vecB[i]; 
			// Sum of squares for vecA.
			normA += Math.pow(vecA[i], 2); 
			// Sum of squares for vecB.
			normB += Math.pow(vecB[i], 2); 
		}

		// Compute the norms by taking the square root of the summed squares.
		normA = Math.sqrt(normA);
		normB = Math.sqrt(normB);

		// Return the cosine similarity, which is the dot product divided by the
		// product of the magnitudes (norms) of the two vectors.
		return dotProduct / (normA * normB);
	}

	// Computes the dot product of two vectors.
	// The dot product is a measure of the alignment between two vectors and is
	// calculated
	// as the sum of the products of their corresponding elements. It provides a
	// single
	// scalar value that represents the degree of similarity between the vectors in
	// terms
	// of their direction and magnitude. A higher value indicates a greater degree
	// of similarity,
	// while a lower value indicates less similarity or orthogonality.
	// This method is useful in various applications, including machine learning and
	// vector space models, to determine how much two vectors align.
	//
	// Parameters:
	// - vecA: The first vector.
	// - vecB: The second vector.
	//
	// Returns:
	// - The dot product of vecA and vecB, a scalar value that indicates the
	// alignment
	// between the two vectors. This value can range from negative to positive,
	// depending
	// on the direction and magnitude of the vectors.
	public double dotProduct(double[] vecA, double[] vecB) {
		// Validate that the vectors have the same length.
		if (vecA.length != vecB.length) {
			throw new IllegalArgumentException("Vectors must be of the same length.");
		}

		double dotProduct = 0.0;

		// Calculate the dot product by summing the products of corresponding elements.
		for (int i = 0; i < vecA.length; i++) {
			dotProduct += vecA[i] * vecB[i];
		}

		return dotProduct;
	}

	// Computes the Euclidean distance between two vectors.
	// The Euclidean distance is a measure of the straight-line distance between two
	// points
	// in Euclidean space, represented by the vectors. It is calculated as the
	// square root
	// of the sum of the squared differences between corresponding elements of the
	// vectors.
	// A distance of 0 indicates that the vectors are identical, while a larger
	// value indicates
	// greater dissimilarity. This method is commonly used in clustering,
	// classification, and
	// other applications that involve measuring the difference between vectors.
	//
	// Parameters:
	// - vecA: The first vector.
	// - vecB: The second vector.
	//
	// Returns:
	// - The Euclidean distance between vecA and vecB, a non-negative scalar value
	// representing
	// the straight-line distance between the two vectors. This value is always zero
	// or positive,
	// with zero indicating that the vectors are identical and larger values
	// indicating greater distance.
	public double euclideanDistance(double[] vecA, double[] vecB) {
		// Validate that the vectors have the same length.
		if (vecA.length != vecB.length) {
			throw new IllegalArgumentException("Vectors must be of the same length.");
		}

		double sumSquaredDifferences = 0.0;

		// Calculate the sum of the squared differences between corresponding elements.
		for (int i = 0; i < vecA.length; i++) {
			double difference = vecA[i] - vecB[i];
			sumSquaredDifferences += Math.pow(difference, 2);
		}

		// Compute and return the square root of the sum of squared differences,
		// which is the Euclidean distance.
		return Math.sqrt(sumSquaredDifferences);
	}

	// Get method to access the simlarities computed.
	public double[] getSimilarities() {

		return similarities;
	}

	// Get method to get the choice of similarity comparison method chosen by the
	// user.

	public int getChoice() {

		return choice;

	}

}
