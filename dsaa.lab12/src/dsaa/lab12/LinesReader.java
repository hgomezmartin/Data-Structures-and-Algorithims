package dsaa.lab12;

import java.util.Scanner;

public class LinesReader {
	String concatLines(int howMany, Scanner scanner) {
		//create a StringBuffer to efficiently concatenate lines
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < howMany; i++) {
			//check if there are more lines to read from the scanner
			if (scanner.hasNextLine()) {
				//read the next line from the input
				String line = scanner.nextLine();
				//append the line to the StringBuffer
				stringBuffer.append(line);
			}
		}
		//convert the StringBuffer to a String and return it
		return stringBuffer.toString();
	}

}
