package dsaa.lab10;

import java.util.Scanner;

import java.util.*;

public class Document implements IWithName {
	public String name;
	private static final int MODVALUE = 100000000;
	private final char[] nameChars;
	private static final int[] HASH_SEQUENCE = { 7, 11, 13, 17, 19 };
	// TODO? You can change implementation of Link collection
	public SortedMap<String, Link> link;

	public Document(String name) {
		this.name = name.toLowerCase();
		link = new TreeMap<String, Link>();
		nameChars = this.name.toCharArray();
	}

	public Document(String name, Scanner scan) {
		this.name = name.toLowerCase();
		link = new TreeMap<String, Link>();
		nameChars = this.name.toCharArray();
		load(scan);
	}

	public void load(Scanner scan) {
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
							link.put(parts[1], new Link(parts[1], weight));
							
						}
					}
				}
			}
			line = scan.nextLine().toLowerCase();
		}
	}

	public static boolean isCorrectId(String id) {
		// TODO
		return id.matches("[a-zA-Z][a-zA-Z0-9_]*");
	}

	// accepted only small letters, capitalic letter, digits nad '_' (but not on the
	// begin)
	static Link createLink(String link) {
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
		String retStr = "Document: " + name + "\n";
		// TODO?
		retStr += link.toString();
		return retStr;
	}

	@Override
	public int hashCode() {
		int hash = nameChars[0];
		int i = 0;
		for (int index = 1; index < nameChars.length; index++) {
			hash = (HASH_SEQUENCE[i] * hash + nameChars[index]) % MODVALUE;
			i = (i + 1) % HASH_SEQUENCE.length;
		}
		return hash;
	}

	@Override
	public String getName() {
		return name;
	}
}
