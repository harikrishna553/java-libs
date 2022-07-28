package com.sample.app;

import com.sample.app.recrods.Add;
import com.sample.app.recrods.Mul;

public class SealedAndRecordDemo {

	public static void main(String[] args) {
		Add add = new Add(new int[] { 2, 3, 5, 7 });
		Mul mul = new Mul(new int[] { 2, 3, 5, 7 });

		System.out.println("Sum of first four primes : %d".formatted(add.operation()));
		System.out.println("Multiplication of first four primes : %d".formatted(mul.operation()));
	}

}
