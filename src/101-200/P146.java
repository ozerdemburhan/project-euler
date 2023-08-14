package com.apkbilisim.pe.p146;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P146 {

    private List<Long>        PRIMES = new ArrayList<>();

    private final Set<String> POST   = Set.of("10", "70", "00");
    
    private volatile long sum = 0;

    public static void main(String[] args) {

        logger.info("started.");

        P146 problem = new P146();
        problem.start();

        logger.info("finished.");
    }
    
    private void start() {
        
        final long BOUND = 150_000_000;
        
        preparePrimes((int) BOUND);
        
        List<Long> numbers = new ArrayList<>();
        
        final int[] diffs = {1,3,7,9,13,27};
        
        for(long n = 10; n < BOUND; n += 10) {
            
            if(n % 1_000_000 == 0) {
                logger.info("" + n);
            }
            
            if(!check(n)) {
                continue;
            }
            
            long n2 = n*n;
            boolean prime = true;
            
            for(int diff : diffs) {
                if(!isPrimeEx(n2 + diff, n)) {
                    prime = false;
                    break;
                }
            }
            
            if(prime) {
                numbers.add(n); 
            }
        }
        
        logger.info("size: " + numbers.size());
        int size = numbers.size();
        
        int count = 0;
        
        for(int i = size - 1; i >= 0; i--) {
            
            if(++count % 100 == 0) {
                logger.info("" + count);
            }
            
            long n = numbers.get(i);
            long n2 = n*n;
            
            for(int diff : diffs) {
                if(!isPrime(n2 + diff, n)) {
                    numbers.remove(i);
                    break;
                }
            }
        }
        
        size = numbers.size();
        List<Integer> seq = Arrays.asList(1,3,7,9,13,27);
        
        for(int i = size - 1; i >= 0; i--) {
            long n = numbers.get(i);
            long n2 = n*n;
            
            for(int diff = 1; diff <= 27; diff++) {
                if(seq.indexOf(diff) != -1) {
                    continue;
                }
                
                if(isPrime(n2 + diff, n)) {
                    numbers.remove(i);
                }
            }
        }
        
        long sum = 0;
        for(long n : numbers) {
            sum += n;
        }
        
        logger.info(numbers.toString());
        logger.info("sum: " + sum);
    }

    private boolean check(long n) {

        if(n % 3 == 0) {
            return false;
        }
        
        if(n % 7 == 0) {
            return false;
        }

        if(n % 13 == 0) {
            return false;
        }
        
        return true;
    }
    
    private boolean isPrimeEx(long n2, long n) {

        if (n2 == 1) {
            return false;
        }
        
        long bound = n + 1;
        bound = bound < 10_000 ? bound : 10_000;
        
        for (long p : PRIMES) {
            
            if(p > bound) {
                break;
            }

            if (n2 % p == 0) {
                return false;
            }
        }

        return true;
    }
    

    private boolean isPrime(long n2, long n) {

        if (n2 == 1) {
            return false;
        }

        for (long p : PRIMES) {

            if (p > n + 1) {
                break;
            }

            if (n2 % p == 0) {
                return false;
            }
        }

        return true;
    }

    private void preparePrimes(int bound) {

        boolean[] field = new boolean[bound];

        for (int n = 2; n < bound; n++) {
            if (field[n]) {
                continue;
            }

            for (int i = 2 * n; i < bound; i += n) {
                field[i] = true;
            }
        }

        for (int i = 2; i < bound; i++) {
            if (!field[i]) {
                PRIMES.add((long) i);
            }
        }

        logger.info("primes prepared: " + PRIMES.size());
    }

}
