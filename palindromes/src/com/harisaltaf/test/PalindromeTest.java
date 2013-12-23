package com.harisaltaf.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.harisaltaf.Palindrome;

/**
 * Conatins Unit Tests for the Class Palindrome
 * 
 * @author Muhammad Haris Altaf
 * 
 */
public class PalindromeTest {
	private char[] orderdCharArray = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	private String orderdCharStr = "0123456789";

	@Test
	public void testPalindrome1() {
		executeTest(53099035l, 53100135l);
	}

	@Test
	public void testPalindrome2() {
		executeTest(969656969l, 969666969l);
	}

	@Test
	public void testPalindrome3() {
		executeTest(2988338892l, 2988448892l);
	}

	@Test
	public void testPalindrome4() {
		executeTest(45717371754l, 45717471754l);
	}

	@Test
	public void testPalindrome5() {
		executeTest(9999999999l, 10000000001l);
	}

	@Test
	public void testPalindrome6() {
		executeTest(99999999999l, 100000000001l);
	}

	private void executeTest(long startNumber, long endNumber) {
		String endNumStr = String.valueOf(endNumber);

		// start
		{
			String startNumStr = String.valueOf(startNumber);
			char[] startingValue = startNumStr.toCharArray();
			Palindrome p = new Palindrome(orderdCharArray, startingValue);
			String nextPalindrome = p.nextPalindrome();
			assertEquals("Start", startNumStr, nextPalindrome);
			startNumber++;
		}

		// inbetween
		{
			for (; startNumber < endNumber; startNumber++) {
				String startNumStr = String.valueOf(startNumber);
				Palindrome p = new Palindrome(orderdCharStr, startNumStr);
				String nextPalindrome = p.nextPalindrome();
				assertEquals("In-b/w", endNumStr, nextPalindrome);
			}
		}

		// end
		{
			String startNumStr = String.valueOf(startNumber);
			char[] startingValue = startNumStr.toCharArray();
			Palindrome p = new Palindrome(orderdCharArray, startingValue);
			String nextPalindrome = p.nextPalindrome();
			assertEquals("End", endNumStr, nextPalindrome);
		}
	}

}
