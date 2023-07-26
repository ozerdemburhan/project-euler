package com.apkbilisim.pe.p136;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P136 {
    
    private long[] PRIMES;

    public static void main(String[] args) {

        logger.info("started.");

        P136 problem = new P136();
        problem.start();

        logger.info("finished.");
    }
    
    private void start() {
        
        final long BOUND = 50_000_000;
        
        preparePrimes((int) BOUND);
        
        long count = 0;
        
        for(long n = 1; n < BOUND; n++) {
            
            boolean check = (n % 4 == 0) || (n % 4 == 3);
            if(!check) {
                continue;
            }
            
            if(checkFactors(n)) {
                count++;
            }
        }
        
        logger.info("count: " + count);
         
    }
    
    private boolean checkFactors(long n) {
        
        List<Long> factors = factors(n);
        int size = factors.size();
        
        int count = 0;
        
        for(int i = 0; i < size; i++) {
            int j = size - 1 - i;
            
            if(i > j) {
                break;
            }
            
            long u = factors.get(i);
            long v = factors.get(j);
            long k = u + v;
            
            if(k % 4 == 0) {
                
                long m1 = 3 * u - v;
                long m2 = 3 * v - u;
                
                if(m1 > 4 && m1 % 4 == 0) {
//                    logger.info(n + "," + k + "," + (m1 / 4));
                    count++;
                }
                
                if(m1 != m2 && m2 > 4 && m2 % 4 == 0) {
//                    logger.info(n + "," + k + "," + (m2 / 4));
                    count++;
                }
            }
        }
        
        return count == 1;
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


