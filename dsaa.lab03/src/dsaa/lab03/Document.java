package dsaa.lab03;

import java.util.Scanner;

public class Document {
    public String name;
    public TwoWayUnorderedListWithHeadAndTail<Link> link;

    public Document(String name, Scanner scan) {
        this.name = name;
        this.link = new TwoWayUnorderedListWithHeadAndTail<>();
        load(scan);
    }

    public void load(Scanner scan) {
        while (scan.hasNext()) { // iterate until there's no input
            String line = scan.nextLine(); // read next line
            if (line.equals("eod")) { // if eod is in the line, stop loading links
                break;
            }
            

            String[] inline = line.split("\\s+"); // split line into individual words
            for (String word : inline) { // iterate over each word
                if (word.toLowerCase().startsWith("link=")) { // if the word starts with link=
                    String linkText = word.substring(5); // extract the link text
                    if (correctLink(linkText)) { // is valid?
                        Link objectLink = new Link(linkText); // create new link object
                        link.add(objectLink); // add the link to the doc
                    }
                }
            }
        }
    }

    public static boolean correctLink(String link) {
        return link.matches("[a-zA-Z][a-zA-Z0-9_]*"); // return true if the link is in correct format
    }

    // converts the doc object to a string representation
    @Override
    public String toString() {
    	if (link == null) {
            return "Document not loaded";
        }
        StringBuilder res = new StringBuilder("Document: ").append(name); // start with doc name
        for (Link link : link) { // iterate over each link in the doc
            res.append("\n").append(link.ref); // append each link reference to the string
        }
        return res.toString(); // return the string representation of the document
    }

    // converts the doc object to a string representation in reverse order
    public String toStringReverse() {
    	if (link == null) {
            return "Document not loaded";
        }
    	StringBuilder result = new StringBuilder("Document: " + name);
    	result.append("\n").append(link.toStringReverse());
        return result.toString();
    }
}
