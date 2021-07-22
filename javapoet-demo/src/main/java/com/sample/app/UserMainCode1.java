package com.sample.app;

public class UserMainCode1 {

	public static int smallestDigitOfANumber(int number) {
		int smallestNumber = 9;

		while (number != 0) {
			int remainder = number % 10;

			smallestNumber = Math.min(remainder, smallestNumber);
			number = number / 10;

		}

		return smallestNumber;
	}

	public static int getNthDight(int number, int nthDigit) {
		String s = "" + number;
		int position = s.length() - nthDigit - 1;

		int val = Integer.valueOf("" + s.charAt(position));

		return val;
	}

	public int findKey(int input1, int input2, int input3) {
		getNthDight(input1, 3);
		return getNthDight(input1, 3) * getNthDight(input2, 2) + smallestDigitOfANumber(input3);
	}

}
