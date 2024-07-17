package dsaa.lab04;

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
		String line = scan.nextLine().toLowerCase(); //read the next line from the scanner and convert it to lowercase
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

	public static boolean isCorrectId(String id) {
		// TODO
		// checks if the ID matches the specified pattern:
		return id.matches("[a-zA-Z][a-zA-Z0-9_]*");
	}

	// accepted only small letters, capitalic letter, digits nad '_' (but not on the
	// begin)
	// creates a Link object based on the input string:
	// if the string has the format "text(number)", creates a Link with the text in
	// lowercase and the number as weight.
	// otherwise, creates a Link with just the text in lowercase.
	private static Link createLink(String link) {
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

	public String toStringReverse() { // represents the object as a string in reverse order.
		// initializes the return string with the document name
		String retStr = "Document: " + name + "\n";
		// creates a list iterator for the link list
		ListIterator<Link> iter = link.listIterator();
		// put 10 links in one line
		int i = 0;
		// iterates through the link list in reverse
		while (iter.hasPrevious()) {
			i++;
			//checks if 10 links have been added to the line
			if (i == 10) {
				//adds the previous link and starts a new line
				retStr += iter.previous().toString() + "\n";
				//resets the counter
				i = 0;
				//skips to the next iteration
				continue;
			}
			//adds the previous link followed by a space
			retStr += iter.previous().toString() + " ";
		}
		//removes the last space or newline character from the string
		retStr = retStr.substring(0, retStr.length() - 1);
		//returns the constructed string representation
		return retStr;
	}
}
