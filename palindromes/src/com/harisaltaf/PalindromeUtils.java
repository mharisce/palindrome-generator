package com.harisaltaf;

/**
 * Contains some useful utility functions for Palindromes.
 * 
 * @author Muhammad Haris Altaf
 * 
 */
public class PalindromeUtils {

	/**
	 * Tells whether the given input is a palindrome or not.
	 * 
	 * @param input
	 *            Input String to be checked
	 * @return true in case given input string is a palindorme, false otherwise.
	 */
	public static boolean isPalindrome(String input) {
		if (input != null && input.length() > 0) {
			int sI = 0;
			int lI = input.length() - 1;
			for (; sI < lI; sI++, lI--) {
				if (input.charAt(sI) != input.charAt(lI)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Tells whether the given input is a palindrome or not.
	 * 
	 * @param input
	 *            Input character array to be checked
	 * @return true in case given input string is a palindorme, false otherwise.
	 */
	public static boolean isPalindrome(char[] input) {
		if (input != null && input.length > 0) {
			int sI = 0;
			int lI = input.length - 1;
			for (; sI < lI; sI++, lI--) {
				if (input[sI] != input[lI]) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	// Internal to Package
	static int indexOf(char[] array, char toSearch) {
		for (int i = 0; i < array.length; i++)
			if (array[i] == toSearch)
				return i;

		return -1;
	}

	static char[] add(char[] myArray, int pos, char ch) {

		char[] newArray = new char[myArray.length + 1];

		for (int i = 0; i < pos; i++) {
			newArray[i] = myArray[i];
		}

		newArray[pos] = ch;

		for (int i = pos + 1; i < myArray.length + 1; i++) {
			newArray[i] = myArray[i - 1];
		}

		return newArray;
	}
}
