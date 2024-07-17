package dsaa.lab12;

import java.util.LinkedList;

public class KMP implements IStringMatcher {

	@Override
    public LinkedList<Integer> validShifts(String pattern, String text) {
		//get the length of the pattern and the text
        int m = pattern.length();
        int n = text.length();
        // create a list to store the positions where the pattern matches the text
        LinkedList<Integer> result = new LinkedList<>();

        //compute the longest prefix suffix lps array
        int[] lps = computeLPSArray(pattern);

        int i = 0; // index for text
        int j = 0; // index for pattern
        while (i < n) {
        	//if the current character matches the pattern, move to the next characters
            if (pattern.charAt(j) == text.charAt(i)) {
                j++; 
                i++;
            }
            //if we have matched the entire pattern, add the match position to the result
            if (j == m) {
                result.add(i - j);
                //move to the next potential match using the lps array
                j = lps[j - 1];
             //if there is a mismatch after matching some characters
            } else if (i < n && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0) {
                	//use the lps array to skip characters in the pattern
                    j = lps[j - 1];
                } else {
                	//move to the next character in the text
                    i++;
                }
            }
        }
        //return the list of matching positions
        return result;
    }
	
	//computes the longest prefix suffix (lps) array for the pattern
	private int[] computeLPSArray(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];
        int len = 0; //length of the previous longest prefix suffix
        int i = 1;
        lps[0] = 0; // lps[0] is always 0

        while (i < m) {
        	//if the current character matches the prefix character, extend the prefix
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
            	//if there is a mismatch
                if (len != 0) {
                	//use the lps array to skip characters in the pattern
                    len = lps[len - 1];
                } else {
                	//move to the next character in the pattern
                    lps[i] = 0;
                    i++;
                }
            }
        }
        //return the computed lps array
        return lps;
    }
	
	

}
