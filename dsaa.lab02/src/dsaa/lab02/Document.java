package dsaa.lab02;

import java.util.Scanner;

public class Document {
    public String name;
    public OneWayLinkedList<Link> links;

    public Document(String name, Scanner scan) {
        this.name = name;
        this.links = new OneWayLinkedList<>();
        load(scan);
    }

    private void load(Scanner scan) {
        while (scan.hasNext()) { //iterat intil theris no input
            String line = scan.nextLine(); //read next line
            if (line.equals("eod")) { //if eod is in the line, stop loading links
                break;
            }

            String[] inline = line.split("\\s+"); //split line into individual words
            for (String word : inline) { //iterate over ach word
                if (word.toLowerCase().startsWith("link=")) { //if the word starts with link=
                    String link = word.substring(5); //extract the link text
                    if (correctLink(link)) { //is valid?
                        Link objectLink = new Link(link); //create new link object
                        links.add(objectLink); //aff the link to the doc
                    }
                }
            }
        }
    }

    private static boolean correctLink(String link) {
        return link.matches("[a-zA-Z][a-zA-Z0-9_]*"); //return true if the link is n correct format
    }

    //c0nverts the doc object to a string representation
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("Document: ").append(name); //start with doc name
        for (Link link : links) { //iterate over each link in the doc
            res.append("\n").append(link.ref); //append each link reference to the string
        }
        return res.toString(); //return the string representation of the document
    }
}
