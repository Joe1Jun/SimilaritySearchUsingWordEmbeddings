package ie.atu.sw;

// This class could store multiple sorting algorithms depending on the size of the data set.

public class Sort {
	
	
	
	public void quickSort(double[] similarities, String[] words, int low, int high) {

		// Checks if the first index is less than the pivot
		if (low < high) {

			// The index of the pivot is returned from the partition function
			// This value is used to in order to pass the values of the low and high back
			// into the quick sort function
			// This allows the function to sort the sub array to the left of the pivot and
			// the sub array to the right.
			int pi = partition(similarities, words, low, high);

			// Recursively sort the left subarray
			// The high value of the left subarray is set to the index before the pivot.
			quickSort(similarities, words, low, pi - 1);
			// Recursively sort the right subarray
			// The low value is set to the index after the pivot.
			quickSort(similarities, words, pi + 1, high);

		}

	}

	private int partition(double[] similarities, String[] words, int low, int high) {

		// Initialise the pivot at the last index of the similarities array
		double pivot = similarities[high];

		// Initialise the i variable at one value less than the first index

		int i = low - 1;

		// Use a for loop that starts at the low value and ends at the high value.
		for (int j = low; j < high; j++) {

			// Check if the value of similarities at j is greater than the pivot value.
			// Because the end array must be in descending order.
			// If the value is less than our pivot its ignored.
			if (similarities[j] > pivot) {

				// i is incremented first as it is required to be at an index in the array.
				// If the swap occurs before this it would trigger an out of bounds exception
				// If the first element is greater than the pivot it will be essentially swapped
				// with itself as i and j will be at the same index
				i++;

				// Initialise a double to temporarily store values to be swapped
				// The double takes the value of similarities at the index of i
				// Example array on second pass of loop [2.3][7.1] high [ 5.2]
				// i is incremented on second pass of loop as first pass is ignored.

				// temp is equal to 2.3
				double temp = similarities[i];
				// Make value of index 0 7.1.
				similarities[i] = similarities[j];
				// Swap the value back in for j so index1 now has a value of 2.3
				// Array at the end [7.1][2.3] high[5.2]
				similarities[j] = temp;

				// If the first double is greater than the pivot it will essentially be swapped
				// with itself as both i and j will be at the same index

				// Do the same for the words at the corresponding indexes
				String wordTemp = words[i];
				words[i] = words[j];
				words[j] = wordTemp;

			}

		}

		// Must now swap the pivot at high to its correct index.
		// Create a temporary variable to hold the value of the index one up from the
		// index of i. As the index of i after the loop will
		// be the index of the last value lower than the pivot so need to increase the
		// the index of i by one so the index is now on a value higher than the pivot.

		double tempSim = similarities[i + 1];
		// Make the value of index i + 1 the same as the value of the pivot
		similarities[i + 1] = similarities[high];
		// Make the last index of the array the same value as the one stored in the
		// temporary variable.
		// This is the same value as the original i + 1 index before the swap.
		// Now that the pivot is in the correct position with all values to the left
		// being higher and to the right being lower.

		similarities[high] = tempSim;

		// Swap words[i+1] and words[high] (or pivot)
		String tempWord = words[i + 1];
		words[i + 1] = words[high];
		words[high] = tempWord;

		// The index of the pivot will be passed back to the quickSort function so the
		// sub arrays to the left and right of the pivot
		// can be passed back into the quicksort function.
		return i + 1;

	}


}
