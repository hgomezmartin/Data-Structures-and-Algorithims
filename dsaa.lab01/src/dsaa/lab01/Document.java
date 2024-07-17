package dsaa.lab01;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Document {

	//mathod that analyze a doc line by line
	public static void loadDocument(String name, Scanner scan) {
		int stringCounter = 1; //counter to keep track of number of strings read
		
		while(scan.hasNextLine()) //loop to read all the lines(until there are lines to read)
		{
			String line = scan.nextLine(); //read the next line from the scanner
			String[] arrString = new String[stringCounter]; //create an array to hold strings
			
			if(line.equals("eod")) { //end of doc found, exit the method
				return;
			}
				//split the line into an array Strings and asign it to arrString
				for(int i = 0; i < arrString.length; i++)
				{
					arrString = line.split("\\s+"); //split the line by whitespace
					stringCounter++;//incr the counter of strings read
				}
				
				
				for(String auxiliar : arrString) //iterate over the arrray of strings
				{
						String identifier = auxiliar.toLowerCase(); //convert string (id) to lowercse
						
						if(correctLink(identifier)) //if is a correct link (method bellow)
						{
								//if is a correct link, split it and print the secnd part
								String[] identifier2 = identifier.split("link=");
								System.out.println(identifier2[1]);
							
						}
					
				}
		}
		
	}
	
	// accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
	public static boolean correctLink(String link) { //mthod to check if a given link string follows the correct format
		String pattern = "link=[a-zA-Z][a-zA-Z0-9_]*"; //regular expression patter for the correct link format
        return link.matches(pattern); //chck if link mathces that pattern
	}

}