package dsaa.lab06;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.Scanner;

public class Document {
	public String name;
	public TwoWayCycledOrderedListWithSentinel<Link> link;

	public Document(String name, Scanner scan) {
		this.name = name.toLowerCase();
		link = new TwoWayCycledOrderedListWithSentinel<Link>();
		load(scan);
	}

	public void load(Scanner scan) {
		// TODO
		String line = scan.nextLine().toLowerCase(); // read the next line from the scanner and convert it to lowercase
		while (!line.equals("eod")) {
			String[] words = line.split(" ");
			for (String word : words) {
				if (word.toLowerCase().startsWith("link=")) {
					String[] parts = word.split("=");
					if (parts.length == 2) {
						char[] linkChars = parts[1].toCharArray();
						int weight = 1;
						if (linkChars[linkChars.length - 1] == ')') {
							int i = linkChars.length - 2;
							while (i >= 0 && Character.isDigit(linkChars[i])) {
								i--;
							}
							if (i == 0 || linkChars[i] != '(') {
								continue;
							}
							weight = Integer.parseInt(parts[1].substring(i + 1, parts[1].length() - 1));
							parts[1] = parts[1].substring(0, i);
						}
						if (isCorrectId(parts[1])) {
							link.add(new Link(parts[1], weight));
						}
					}
				}
			}
			line = scan.nextLine().toLowerCase();
		}
	}

	// accepted only small letters, capitalic letter, digits nad '_' (but not on the
	// begin)

	public static boolean isCorrectId(String id) {
		// TODO
		return id.matches("[a-zA-Z][a-zA-Z0-9_]*");
	}

	// accepted only small letters, capitalic letter, digits nad '_' (but not on the
	// begin)
	public static Link createLink(String link) {
		// TODO
		if (link.matches("[a-z0-9]*\\([0-9]*\\)")) {
			int start = link.indexOf("(") + 1;
			String key = link.toLowerCase().substring(0, start - 1);
			int weight = Integer.parseInt(link.substring(start, link.length() - 1));
			return new Link(key, weight);
		} else {
			return new Link(link.toLowerCase());
		}
	}

	@Override
	public String toString() {
		// initializes the return string with the document name
		String retStr = "Document: " + name + "\n";
		// creates an iterator for the link list
		Iterator<Link> iter = link.iterator();
		// put 10 links in one line
		int i = 0;
		// iterates through the link list
		while (iter.hasNext()) {
			i++;
			// checks if 10 links have been added to the line
			if (i == 10) {
				// adds the next link and starts a new line
				retStr += iter.next().toString() + "\n";
				// resets the counter.
				i = 0;
				// skips to the next iteration
				continue;
			}
			// adds the next link followed by a space.
			retStr += iter.next().toString() + " ";
		}
		// remove last space or new line
		retStr = retStr.substring(0, retStr.length() - 1);
		// returns the constructed string representation.
		return retStr;
	}

	public String toStringReverse() {
		// initializes the return string with the document name
		String retStr = "Document: " + name + "\n";
		// creates a list iterator for the link list
		ListIterator<Link> iter = link.listIterator();
		// put 10 links in one line
		int i = 0;
		// iterates through the link list in reverse
		while (iter.hasPrevious()) {
			i++;
			// checks if 10 links have been added to the line
			if (i == 10) {
				// adds the previous link and starts a new line
				retStr += iter.previous().toString() + "\n";
				// resets the counter
				i = 0;
				// skips to the next iteration
				continue;
			}
			// adds the previous link followed by a space
			retStr += iter.previous().toString() + " ";
		}
		// removes the last space or newline character from the string
		retStr = retStr.substring(0, retStr.length() - 1);
		// returns the constructed string representation
		return retStr;
	}

	public int[] getWeights() {
		// TODO
		int[] weights = new int[link.size()];
		int i = 0;
		for (Link l : link) {
			weights[i++] = l.weight;
		}
		return weights;
	}

	public static void showArray(int[] arr) {
		// TODO
		for (int i = 0; i < arr.length - 1; i++) {
			System.out.print(arr[i] + " ");
		}
		if (arr.length > 0)
			System.out.println(arr[arr.length - 1]);
	}

	void bubbleSort(int[] arr) {
		showArray(arr); // print initial array
		// TODO

		for (int i = 0; i < arr.length - 1; i++) {// outerloop to traverse the array
			for (int j = arr.length - 1; j > i; j--) { // innerloop to compare and swap the elements
				if (arr[j] < arr[j - 1]) { // compare adjacent elements and swap if necesart
					int temp = arr[j]; // swap elements
					arr[j] = arr[j - 1];
					arr[j - 1] = temp;
				}
			}
			showArray(arr); // print array after each pass
		}
	}

	public void insertSort(int[] arr) {
		showArray(arr); // print initial arry
		// TODO

		for (int i = arr.length - 2; i >= 0; i--) { // koop through the array starting from the second element to fiirst
			int j = i + 1; // initialize a variable to track the position for comparison
			while (j < arr.length && arr[j] < arr[j - 1]) { // find the correct position for the current rlrmrnt in the
															// sorted subarray
				int temp = arr[j]; // swap elements
				arr[j] = arr[j - 1];
				arr[j - 1] = temp;
				j++; // move to the next position
			}
			showArray(arr); // print array after each pass
		}
	}

	public void selectSort(int[] arr) {
		showArray(arr); // print the initial array
		// TODO

		for (int i = 0; i < arr.length - 1; i++) { // outerloop to traverse the array
			int maxIndex = 0; // initialize the index of the maximum element
			for (int j = 0; j < arr.length - i; j++) { // inner loop to find the maximum element in the insorted
														// subarray
				if (arr[j] > arr[maxIndex]) { // update the index of the maximum element if a larger element is found
					maxIndex = j;
				}
			}
			int temp = arr[arr.length - i - 1]; // swap the maximum element with the last element of the unsorted
												// subarray
			arr[arr.length - i - 1] = arr[maxIndex];
			arr[maxIndex] = temp;
			showArray(arr); // print the array after each pass
		}
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////7

	public void iterativeMergeSort(int[] arr) {
		showArray(arr); // show initial array

		// start merging arrays of size 1, 2, 4, 8, 16...
		for (int size = 1; size < arr.length; size *= 2) { // *2 for 2, 4, 8...
			//uterate over the array fusionating the subarrays wuth the lenght 'size'
			for (int left = 0; left < arr.length - size; left += 2 * size) {
				int mid = left + size - 1; // middle index
				int right = Math.min(left + 2 * size - 1, arr.length - 1); // right index
				merge(arr, left, mid, right); // merge/fusionate the arrays
			}
			showArray(arr); // show the array after each iteration of outr loop
		}
	}

	private void merge(int[] arr, int left, int mid, int right) {
		// merge/fusionate two shorted subarrays into one
		int[] temp = new int[right - left + 1]; // temporary array to store the merged elemnts
		int i = left, j = mid + 1, k = 0;

		//fusionate all the ordered subarrays in the temorary array
		while (i <= mid && j <= right) {
			if (arr[i] <= arr[j]) {
				temp[k++] = arr[i++];
			} else {
				temp[k++] = arr[j++];
			}
		}

		while (i <= mid) {
			temp[k++] = arr[i++];
		}

		while (j <= right) {
			temp[k++] = arr[j++];
		}

		// cop the merged elemnts back to the original array
		for (i = left; i <= right; i++) {
			arr[i] = temp[i - left];
		}
	}

	public void radixSort(int[] arr) {
		showArray(arr);
		//iterate 3 times to order units, tens and hundreds
        for (int i = 0; i < 3; i++) {
            countingSort(arr, (int)Math.pow(10, i)); //pass the appropriate exponent value for the digit position
            showArray(arr);
        }
	}

	private void countingSort(int[] arr, int exp) {
		int n = arr.length;
		int[] output = new int[n]; // array to store the shorted elements
		int[] count = new int[10]; // count array to store the frequency of each digit

		// calculate the frequency of each digit in the array
		for (int i = 0; i < n; i++) {
			count[(arr[i] / exp) % 10]++;
		}

		// update the count array to contain the position of each digit in the output
		// array
		for (int i = 1; i < 10; i++) {
			count[i] += count[i - 1];
		}

		// build the output array
		for (int i = n - 1; i >= 0; i--) {
			output[count[(arr[i] / exp) % 10] - 1] = arr[i];
			count[(arr[i] / exp) % 10]--;
		}

		// copy shorted elemnts bacj to the original array
		for (int i = 0; i < n; i++) {
			arr[i] = output[i];
		}
	}

}
