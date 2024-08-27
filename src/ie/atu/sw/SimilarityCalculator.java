package ie.atu.sw;



public class SimilarityCalculator {
	private double [] similarities;

	//added in compare passed from menu
	
   
	// This method takes is passed two parameters from the WordComparison class
	// The wordEmbeddings of the word at a particular 

	public double[] computeSimilarities(double[] wordEmbedding, double[][] embeddings) {
		similarities = new double[embeddings.length];
		for (int i = 0; i < embeddings.length; i++) {
			similarities[i] = cosineSimilarity(wordEmbedding, embeddings[i]);
		}
		return similarities;
	}

	// Calculates the cosine similarity between two vectors. Cosine similarity is a
	// measure of similarity between two non-zero vectors of an inner product space
	// that measures the cosine of the angle between them.
	// vecA The first vector. vecB The second vector. The cosine similarity between
	// vecA and vecB.

	public void setSimilarities(double[] similarities) {
		this.similarities = similarities;
	}

	private double cosineSimilarity(double[] vecA, double[] vecB) {

		// Validate that the vectors have the same length
		if (vecA.length != vecB.length) {
			throw new IllegalArgumentException("Vectors must be of the same length.");
		}

		// Initialize variables to store the dot product and the norms of the vectors
		// Accumulates the sum of products of corresponding components
		double dotProduct = 0.0;
		// Accumulates the sum of squares of vecA components
		double normA = 0.0;
		// Accumulates the sum of squares of vecB components
		double normB = 0.0;
		// Loop over each component of the vectors
		for (int i = 0; i < vecA.length; i++) {
			// Calculate the dot product by summing the products of corresponding components
			dotProduct += vecA[i] * vecB[i];
			// Calculate the sum of squares for vecA
			normA += Math.pow(vecA[i], 2);
			// Calculate the sum of squares for vecB
			normB += Math.pow(vecB[i], 2);
		}

		// Compute norms
		normA = Math.sqrt(normA);
		normB = Math.sqrt(normB);
		// Calculate and return the cosine similarity
		// Cosine similarity is the dot product divided by the product of the magnitudes
		// (norms) of the vectors
		return dotProduct / (normA * normB);

	}

	public double[] getSimilarities() {
		// TODO Auto-generated method stub
		return similarities;
	}

}
