package com.apkbilisim.pe.p129;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P129 {

    private long[] PRIMES;

    public static void main(String[] args) {

        logger.info("started.");

        P129 problem = new P129();
        problem.start();

        logger.info("finished.");
    }
    
    private void start() {
        
        preparePrimes(10_000_000);
        
        final long LBOUND = 1_000_000L;
        final long UBOUND = 1_000_171L;
        
        for(long n = LBOUND; n < UBOUND; n++) {
            if((n % 2 == 0) || (n % 5 == 0)) {
                continue;
            }
            
            BigInteger bn = new BigInteger("" + n);
            
            List<Long> factors = factors(n);
            
            logger.info("n: " + n);
            
            out:
            while(true) {
                
                for(long k : factors) {
                    logger.info("factor: " + k);
                    BigInteger r = new BigInteger("1".repeat((int) k));
                    
                    if(r.mod(bn).compareTo(BigInteger.ZERO) == 0) {
                        logger.info("A(" + n + "): " + k);
                        break out;
                    }
                }
                
                iterateFactors(factors, n);
            }
        }
    }
    
    private void iterateFactors(List<Long> factors, long n) {
        
        Set<Long> set = new HashSet<>();
        
        for(long factor : factors) {
            
            if(factor < 2) {
                continue;
            }
            
            List<Long> temp = factors(factor - 1);
            
            for(int i = 0; i < temp.size(); i++) {
                long f1 = temp.get(i);
                
                for(int j = 0; j < factors.size(); j++) {
                    long f2 = factors.get(j);
                    
                    if(n > f1 * f2) {
                        set.add(f1 * f2);
                    }
                }
            }
        }
        
        set.removeAll(factors);
        
        factors.clear();
        factors.addAll(set);

        Collections.sort(factors);
    }
    
    private List<Long> factors(long n) {
        
        Map<Long, Long> primeFactors = primeFactors(n);
        
        List<Long> factors = new ArrayList<>();
        
        factors(primeFactors, factors);
        
        return factors;
        
    }
    
    private void factors(Map<Long, Long> primeFactors, List<Long> factors) {
        
        if(primeFactors.isEmpty()) {
            return;
        }
        
        Entry<Long, Long> entry = primeFactors.entrySet().iterator().next();
        
        List<Long> currentFactors = new ArrayList<>();
        
        long factor = 1L;
        
        for(long power = 0; power <= entry.getValue(); power++) {
            currentFactors.add(factor);
            factor *= entry.getKey();
        }
        
        if(factors.isEmpty()) {
            factors.addAll(currentFactors);
            
        } else {
            int size = factors.size();
            
            for(int i = 1; i < currentFactors.size(); i++) {
                long f1 = currentFactors.get(i);
                
                for(int j = 0; j < size; j++) {
                    long f2 = factors.get(j);
                    factors.add(f1 * f2);
                }
            }
        }
        
        primeFactors.remove(entry.getKey());
        
        factors(primeFactors, factors);
        
    }
    
    private Map<Long, Long> primeFactors(long n) {
        
        Map<Long, Long> map = new LinkedHashMap<>();
        
        primeFactors(n, map);
        
        return map;
    }
    
    private void primeFactors(long n, Map<Long, Long> map) {
        
        final int UBOUND = (int) Math.sqrt(n);
        
        for(long prime : PRIMES) {
            
            if(n == 1) {
                break;
            }
            
            if(prime > UBOUND) {
                map.put(n, 1L);
                break;
            }
            
            while(n % prime == 0) {
                Long current = map.put(prime, 1L);
                
                if(current != null) {
                    map.put(prime, current + 1);
                }
                
                n /= prime;
            }
        }
    }
    
    private void preparePrimes(int bound) {

        List<Integer> primes = new ArrayList<>();
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
                primes.add(i);
            }
        }

        PRIMES = new long[primes.size()];

        for (int i = 0; i < primes.size(); i++) {
            PRIMES[i] = primes.get(i);
        }

        logger.info("primes prepared.");
    }
}
