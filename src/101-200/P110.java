package com.apkbilisim.pe.p110;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class P110 {

	private final int	LBOUND	= 8_000_000 - 1;

	private int[]		PRIMES;

	public static void main(String[] args) {

		long now = System.currentTimeMillis();
		System.out.println("started: " + now);

		P110 problem = new P110();
		problem.start();

		System.out.println("elapsed time: " + (System.currentTimeMillis() - now));
	}

	private void start() {
		
		preparePrimes(1000000);

		BigInteger min = null;
		List<Integer> minPrimeFactors = null;

		for (int count = 2; count <= 15; count++) {
			for (int n = LBOUND; n <= 2 * LBOUND; n += 2) {

				List<Integer> primeFactors = primeFactors(n);

				if (primeFactors.size() == count) {
					Collections.sort(primeFactors, Collections.reverseOrder());
					BigInteger value = evaluate(primeFactors);

					if ((min == null) || min.compareTo(value) > 0) {
						min = value;
						minPrimeFactors = new ArrayList<>(primeFactors);
					}

					break;
				}
			}
		}

		for (int i = 0; i < minPrimeFactors.size(); i++) {
			int power = minPrimeFactors.get(i);
			minPrimeFactors.set(i, (power - 1) / 2);
		}

		System.out.println("n: " + min.toString());

		System.out.println(minPrimeFactors);
	}

	private BigInteger evaluate(List<Integer> primeFactors) {

		BigInteger n = BigInteger.ONE;

		for (int i = 0; i < primeFactors.size(); i++) {
			int power = (primeFactors.get(i) - 1) / 2;
			n = n.multiply(new BigInteger("" + PRIMES[i]).pow(power));
		}

		return n;
	}

	private List<Integer> primeFactors(int n) {

		List<Integer> primeFactors = new ArrayList<>();
		int ubound = (int) Math.sqrt(n);
		
		for (int p : PRIMES) {
			while (n % p == 0) {
				primeFactors.add(p);
				n /= p;
			}

			if (p > ubound) {
				primeFactors.add(n);
				return primeFactors;
			}

			if (n == 1) {
				return primeFactors;
			}
		}

		return primeFactors;
	}
	
	private void preparePrimes(int bound) {
		
		List<Integer> primes = new ArrayList<>();
		boolean[] field = new boolean[bound];
		
		for(int n = 2; n < bound; n++) {
			if(field[n]) {
				continue;
			}
			
			for(int i = 2*n; i < bound; i += n) {
				field[i] = true;
			}
		}
		
		for(int i = 2; i < bound; i++) {
			if(!field[i]) {
				primes.add(i);
			}
		}
		
		PRIMES = new int[primes.size()];
		
		for(int i = 0; i < primes.size(); i++) {
			PRIMES[i] = primes.get(i);
		}
	}
	
}
