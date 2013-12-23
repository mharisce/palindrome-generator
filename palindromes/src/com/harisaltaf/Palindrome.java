package com.harisaltaf;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Palindrome class. It is Thread-Safe way of generating palindromes from a
 * given set of alphabet and an optional start value.
 * 
 * @author Muhammad Haris Altaf
 * 
 */
public class Palindrome {

	// TODO orderdCharList should be unmodifiable.

	private char[] orderdCharList = null;
	private char[] currentPalindrome = null;
	private char[] startingValue = null;

	private Palindrome() {
	}

	/**
	 * Constructor for class Palindrome.
	 * 
	 * @param orderdCharList
	 *            Provide an array of characters (char[]). The
	 *            alphabet/characters should be in the defined chronological
	 *            order. e.g. for arithmetic palindromes pass this (new char[] {
	 *            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' })
	 * @param startingValue
	 *            Optional value. If you don't want to provide any starting
	 *            value pass null. Provide the value from which the palindrome
	 *            calculation must be started. e.g. for passing 100 in
	 *            arithmetic pass this parameter (new char[] { '1', '0', '0' })
	 */
	public Palindrome(char[] orderdCharList, char[] startingValue) {
		this();

		synchronized (this) {

			this.orderdCharList = Arrays.copyOf(orderdCharList, orderdCharList.length);
			this.startingValue = Arrays.copyOf(startingValue, startingValue.length);

			if (this.orderdCharList == null || this.orderdCharList.length == 0) {
				throw new InvalidParameterException("orderdCharList is Invalid.");
			}

			if (this.startingValue != null && this.startingValue.length > 0) {
				if (!validateInput(this.startingValue)) {
					throw new InvalidParameterException("startingValue is Invalid.");
				}
			}
		}
	}

	/**
	 * Constructor for class Palindrome.
	 * 
	 * @param orderdCharList
	 *            Provide an string of characters. The alphabet/characters
	 *            should be in the defined chronological order. e.g. for
	 *            arithmetic palindromes pass this ("0123456789")
	 * @param startingValue
	 *            Optional value. If you don't want to provide any starting
	 *            value pass null. Provide the value from which the palindrome
	 *            calculation must be started. e.g. for passing 100 in
	 *            arithmetic pass this parameter ("100")
	 */
	public Palindrome(String orderdCharList, String startingValue) {
		this(orderdCharList.toCharArray(), (startingValue == null ? null : startingValue.toCharArray()));
	}

	/**
	 * Provides the next palindrome. Assume you are working with arithmetic
	 * palindromes. EXAMPLE 1: Say you have provided "100" as starting value.
	 * Calling this function for the 1st time would return "101". Calling this
	 * method again and again would now return "111", "121" (so on) which are
	 * the next palindormes. EXAMPLE 2: Say you provided "80008" as starting
	 * value. Calling this function for the 1st time would return "80008" as
	 * starting value is ALREADY a palindrome. Calling this method again and
	 * again would return "80108", "80208" (so on).
	 * 
	 * 
	 * @return String value of Next Palindrome.
	 */
	public synchronized String nextPalindrome() {
		if (currentPalindrome == null) {// The first time
			generatePalindromeFirstTime();
		} else {
			calculateNext();
		}

		return new String(currentPalindrome);
	}

	/**
	 * Provides the String value of Current Palindrome. For details read
	 * description of nextPalindrome().
	 * 
	 * @return String value of current Palindrome.
	 */
	public synchronized String currentPalindrome() {
		if (currentPalindrome == null) {// The first time
			generatePalindromeFirstTime();
		}

		return new String(currentPalindrome);
	}

	/**
	 * Returns the ordered character list String which you provided in the
	 * constructor argument. You can't change this.
	 * 
	 * @return
	 */
	public String getOrderdCharList() {
		return new String(orderdCharList);
	}

	// Private Methods
	// TODO use this method and change your code.
	private ArrayList<Character> charArrToList(char[] charArray) {
		ArrayList<Character> list = new ArrayList<Character>(charArray.length);
		for (int i = 0; i < charArray.length; i++) {
			list.add(charArray[i]);
		}
		return list;
	}

	private void calculateNext() {

		int firstMiddleIndex = getFirstMiddleIndex();
		int secondMiddleIndex = getSecondMiddleIndex();
		int fmi = firstMiddleIndex;
		int smi = secondMiddleIndex;
		int len = getCurrentLength();

		int lPreviousCarry = 0;
		int rPreviousCarry = 0;

		for (; fmi >= 0 && smi < len; fmi--, smi++) {

			char clch = currentPalindrome[fmi];
			char crch = currentPalindrome[smi];

			char nlch = orderdCharList[getNextCharIndex(clch, 0)];
			char nrch = orderdCharList[getNextCharIndex(crch, 0)];

			currentPalindrome[fmi] = nlch;
			currentPalindrome[smi] = nrch;

			lPreviousCarry = getCarry(clch, 0);
			rPreviousCarry = getCarry(crch, 0);

			if (lPreviousCarry == 0 && rPreviousCarry == 0) {
				return;
			}

			if (fmi == 0 && smi == len - 1 && lPreviousCarry > 0 && rPreviousCarry > 0) {
				// last index int this case ad new character at pos
				// (firstMiddleIndex + 1)
				currentPalindrome = PalindromeUtils.add(currentPalindrome, firstMiddleIndex + 1, orderdCharList[0]);
			}

		}
	}

	private void generatePalindromeFirstTime() {
		if (startingValue != null && startingValue.length > 0) {
			// Some initial value is given

			// In following section we would be changing this currentPalindrome
			// object
			currentPalindrome = Arrays.copyOf(startingValue, startingValue.length);

			if (!PalindromeUtils.isPalindrome(startingValue)) {
				// Given initial value is not a palindrome so make a palindrome
				// out of it
				flipLHSOverRHS();

				boolean RHSgtLHS = RHSgtLHS(startingValue);
				if (RHSgtLHS) {
					calculateNext();
				}
			}

		} else {
			// Initial value is not given
			currentPalindrome = Arrays.copyOfRange(orderdCharList, 0, 1);
		}
	}

	private void flipLHSOverRHS() {

		int fiLHS = getFirstMiddleIndex();
		int siRHS = getSecondMiddleIndex();
		int len = getCurrentLength();

		for (; fiLHS >= 0 && siRHS < len; fiLHS--, siRHS++) {
			char charAtFiLHS = currentPalindrome[fiLHS];
			currentPalindrome[siRHS] = charAtFiLHS;
		}
	}

	private boolean RHSgtLHS(char[] initialValue) {
		int fiLHS = getFirstMiddleIndex(initialValue);
		int siRHS = getSecondMiddleIndex(initialValue);
		int len = initialValue.length;

		for (; fiLHS >= 0 && siRHS < len; fiLHS--, siRHS++) {
			char charAtFiLHS = initialValue[fiLHS];
			char charAtSiRHS = initialValue[siRHS];

			int indexOfCharAtFiLHS = PalindromeUtils.indexOf(orderdCharList, charAtFiLHS);
			int indexOfCharAtSiRHS = PalindromeUtils.indexOf(orderdCharList, charAtSiRHS);

			if (indexOfCharAtSiRHS > indexOfCharAtFiLHS) {
				return true;
			} else if (indexOfCharAtSiRHS < indexOfCharAtFiLHS) {
				return false;
			} else {
				// In case of equal continue until last index
			}

		}
		return false;
	}

	private int getCarry(char ch, int previousCarry) {
		return (PalindromeUtils.indexOf(orderdCharList, ch) + 1 + previousCarry) / orderdCharList.length;
	}

	private int getNextCharIndex(char ch, int previousCarry) {
		return (PalindromeUtils.indexOf(orderdCharList, ch) + 1 + previousCarry) % orderdCharList.length;
	}

	private int getCurrentLength() {
		return currentPalindrome.length;
	}

	private int getFirstMiddleIndex() {
		return getFirstMiddleIndex(currentPalindrome);
	}

	private static int getFirstMiddleIndex(char[] input) {
		if (input.length == 0) {
			return -1;
		}
		if (input.length == 1) {
			return 0;
		}

		if (input.length % 2 == 0) {
			// even
			return (input.length / 2) - 1;
		} else {
			// odd
			return (input.length / 2);
		}
	}

	private int getSecondMiddleIndex() {
		return getSecondMiddleIndex(currentPalindrome);
	}

	private static int getSecondMiddleIndex(char[] input) {
		if (input.length == 0) {
			return -1;
		}
		if (input.length == 1) {
			return 0;
		}

		if (input.length % 2 == 0) {
			// even
			return (input.length / 2);
		} else {
			// odd
			return (input.length / 2);
		}
	}

	private boolean validateInput(char[] input) {
		for (char c : input) {
			if (!validChar(c)) {
				return false;
			}
		}
		return true;
	}

	private boolean validChar(char c) {
		for (char co : this.orderdCharList) {
			if (co == c) {
				return true;
			}
		}
		return false;
	}
}
