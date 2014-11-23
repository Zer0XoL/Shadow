package Shadow.System.Script;

import java.util.ArrayList;
import java.util.List;

import Shadow.System.ShadowEngine;

public class ShadowParser {

	/*
	 * To be implemented
	 */
	public static void parseFile(ShadowFile file) {

	}

	/*
	 * Returns the token of the string for the given index (tokens are separated by spaces, so each index points to each token)
	 * 
	 * Returns empty string if index out of bounds relative to string
	 */
	public static String getToken(String src, int index) {
		char prevChar = ' ';
		int token = 0;
		int start = -1;
		for (int i = 0; i < src.length(); ++i) {                                  
			if (index > 0) {
				if (src.charAt(i) != ' ' && prevChar == ' ') { //if there is a space and the token is equal to the index, find&return substring from then till next space/EoL
					if (token == index) {
						if (start == -1)
							start = i;
						for (int j = start; j < src.length(); ++j) {
							if (src.charAt(j) == ' ' || j + 1 == src.length()) { //accounts for the edge case that the token index is the last and therefore has no space
								return src.substring(start, j + 1).trim(); //trim cuz paranoid
							}
						}
					}
					token++;
				}
				prevChar = src.charAt(i);
			} else { //if the token index is the first token, make sure first characters aren't whitespaces (could've solved by trimming)
				if (start == -1) {
					if (src.charAt(i) != ' ') {
						start = i;
					}
				} else {
					if (src.charAt(i) == ' ') {
						return src.substring(start, i).trim();
					}
				}
			}
		}
		return "";
	}
	
	/*
	 * Counts and returns the amount of arguments in the command string
	 */
	public static int args() {
		return 0;
	}
	
	/*
	 * Returns the command string sans the command key
	 * Better name might be argumentList, argsString or something like that
	 */
	public static String removeKey(String cmdString) {
		String key = getToken(cmdString, 0).trim();
		String tmp = cmdString;
		tmp = tmp.replace(key, "").trim();
		return tmp;
	}
}
