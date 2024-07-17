package dsaa.lab12;

import java.util.LinkedList;

public class Automaton implements IStringMatcher {

	@Override
	public LinkedList<Integer> validShifts(String pattern, String text) {
		//get the length of the pattern and the text
		int m = pattern.length();
		int n = text.length();
		//create a list to store the positions where the pattern matches the text
		LinkedList<Integer> result = new LinkedList<>();

		//build the automaton transition table
		int[][] delta = buildTransitionTable(pattern);

		int state = 0; //initial state of the automaton
		for (int i = 0; i < n; i++) {
			//transition to the next state based on the current character in the text
			state = delta[state][text.charAt(i)];
			//if we have reached the final state, a match is found
			if (state == m) {
				//add the starting index of the match to the result list
				result.add(i - m + 1);
			}
		}
		return result;
	}

	//builds the transition table (delta) for the automaton based on the pattern
	private int[][] buildTransitionTable(String pattern) {
        int m = pattern.length();
        //initialize the transition table for all possible ASCII characters (256)
        int[][] delta = new int[m + 1][256]; //considering the ASCII range

        for (int q = 0; q <= m; q++) {
            for (int a = 0; a < 256; a++) {
            	//if the curent state is non-zero and either we are at the end of the pattern 
                // or the current character doesn't match the pattern character at position q
                if (q > 0 && (q == m || a != pattern.charAt(q))) {
                	//transition to the state defined by the suffix function
                    delta[q][a] = delta[getSuffixState(q, pattern)][a];
                } else if (q < m && a == pattern.charAt(q)) {
                	//if the character matches, move to the next state
                    delta[q][a] = q + 1;
                } else {
                	//Otherwise, transition to the initial state 0
                    delta[q][a] = 0;
                }
            }
        }
        return delta; //return the trasnsition table
    }

	// Determines the state to transition to based on the longest suffix of the pattern that is also a prefix
	private int getSuffixState(int q, String pattern) {
		if (q == 0) {
			return 0;// If at the initial state, remain in the initial state
		}
		
		//iterate from the current state back to find the largest suffix
		for (int k = q - 1; k > 0; k--) {
			//check if the substring from the start matches the suffix ending at the current state
			if (pattern.substring(0, k).equals(pattern.substring(q - k, q))) {
				return k;
			}
		}
		//if not match found, return to initial state
		return 0;
	}

}
