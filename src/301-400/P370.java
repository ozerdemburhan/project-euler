package com.apkbilisim.pe.p370;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P370 {
	
	private final long MAX_PERIMETER = 25000000000000L;
//	private final long MAX_PERIMETER = 10000000000L;
	
	private final long BMAX = (long) Math.ceil(MAX_PERIMETER / 3);
	
	private final int BMAX_SQRT = (int) Math.ceil(Math.sqrt(BMAX));
	
	private final double RATIO = (Math.sqrt(5) + 1) / 2;
	
	private int[] primes = null;
	
	private int[][] pfs = null;
	
	private final ObjectMapper mapper = new ObjectMapper();
	
	
	public static void main(String[] args) {
		
		long now = System.currentTimeMillis();
		logger.info("started.");
		
		P370 problem = new P370();
		problem.start();
		
		long elapsed = System.currentTimeMillis() - now;
		logger.info("elapsed time: " + elapsed);
		
	}
	
	private void start1() {
		
		preparePrimes();
		preparePrimeFactors();
		logger.info("initialized.");
		
		long count = startInternal(1, BMAX_SQRT);
		count += BMAX;
		
		logger.info("total count: " + count);
	}
	
	
	private void start() {
		
		preparePrimes();
		preparePrimeFactors();
		logger.info("initialized.");
		
		long count = 0L;
		final long STEP = (BMAX_SQRT / 100);
		int[] percentRanges = new int[]{0, 51, 71, 86, 100};

		List<Callable<Long>> tasks = new ArrayList<>();
		ExecutorService es = Executors.newFixedThreadPool(4);
		
		for(int i = 0; i < percentRanges.length - 1; i++) {
			
			long mstart = percentRanges[i] * STEP + 1;
			long mend = (i < 3) ? percentRanges[i + 1] * STEP : BMAX_SQRT;
			
			Callable<Long> task = () -> {
				return startInternal(mstart, mend);
			};
			
			tasks.add(task);
		}
		
		
		try {
			List<Future<Long>> futures = es.invokeAll(tasks);
			
			for(Future<Long> future : futures) {
				long partialCount = future.get();
				count += partialCount;
			}
		} catch (Exception e) {
			logger.error("", e);
			
		} finally {
			es.shutdown();
		}
		
		count += BMAX;
		logger.info("total count: " + count);
		
	}
	
	private long startInternal(long mstart, long mend) {

		final long now = System.currentTimeMillis();
		long count = 0L;

		for(long m = mstart; m <= mend; m++) {
			
			int ubound = (int) (m * RATIO);
			
			List<Integer> coprimes = findCoprimes((int) m, (int) ubound);
			
			for(int n : coprimes) {
				long p = m*m + m*n + n*n;
				
				if(p > MAX_PERIMETER) {
					continue;
				}
				
				count += (long) (MAX_PERIMETER / p);
			}
		}
		
		return count;
	}
	
	private List<Integer> findCoprimes(int m, int ubound) {
		
		List<Integer> coprimes = new ArrayList<>();
		
		int[] primeFactors = pfs[m];
		boolean[] arr = new boolean[ubound - m + 1];
		Arrays.fill(arr, true);
		
		for(int p : primeFactors) {
			for(int n = m; n <= ubound; n += p) {
				arr[n - m] = false;
			}
		}
		
		for(int i = 1; i < arr.length; i++) {
			if(arr[i]) {
				coprimes.add(m + i);
			}
		}
		
		return coprimes;
	}
	
	private void preparePrimeFactors() {
		
		final int UBOUND = (int) (BMAX_SQRT * RATIO);
		pfs = new int[UBOUND + 1][];
		
		for(int n = 1; n <= UBOUND; n++) {
			Set<Integer> temp = getPrimeFactors(n);
			pfs[n] = new int[temp.size()];
			int i = 0;
			
			for(int p : temp) {
				pfs[n][i++] = p;
			}
		}
	}
	
	private boolean hasCommon(int[] f1, int[] f2) {
		
		for(int a : f2) {
			for(int b : f1) {
				
				if(a == b) {
					return true;
				}				
				
				if(b > a) {
					break;
				}
			}
		}
		
		return false;		
	}
	
	private Set<Integer> getPrimeFactors(int n) {
		
		List<Integer> primeFactors = new ArrayList<>();
		factorizeInternal(n, 0, primeFactors);
		
		LinkedHashSet<Integer> pfs = new LinkedHashSet<>();
		for(int pf : primeFactors) {
			pfs.add(pf);
		}
		
		return pfs;
		
	}
	
	private void factorizeInternal(int n, int lastIndex, List<Integer> primeFactors) {
		
		if(n == 1) {
			return;
		}
		
		int size = primeFactors.size();		
		
		int index;
		
		for(index = lastIndex; index < primes.length; index++) {
			
			int prime = primes[index];
			
			if(prime > Math.sqrt(n)) {
				primeFactors.add(n);
				n = 1;
				break;
			}
			
			if(n % prime == 0) {
				primeFactors.add(prime);
				n /= prime;
				break;
			}
		}
		
		if(primeFactors.size() == size) {
			primeFactors.add(n);
			n = 1;
		}
			
		factorizeInternal(n, index, primeFactors);
	}
	
	
	
	private void preparePrimes() {
		
		List<Integer> primeList = new ArrayList<>(100);
		
		int psqrt = 1;
		
		for(int n = 2; n <= BMAX_SQRT; n++) {
			
			int upper = psqrt;
			if((psqrt + 1) * (psqrt + 1) == n) {
				upper = ++psqrt;
			}
			
			boolean isPrime = true;
			
			for(int p : primeList) {
				if(p > upper) break;
				
				if(n % p == 0) {
					isPrime = false;
					break;
				}
			}
			
			if(isPrime) {
				primeList.add(n);
			}
		}
		
		primes = new int[primeList.size()];
		
		for(int i = 0; i < primeList.size(); i++) {
			int prime = primeList.get(i);
			primes[i] = prime;
		}
		
		logger.info("prime count: " + primes.length);		
	}
	
	private void printJson(Object object) {
		try {
			logger.info(mapper.writeValueAsString(object));
		} catch (Exception e) {
			logger.error("", e);
		}
	}
}
