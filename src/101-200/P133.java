package com.apkbilisim.pe.p133;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P133 {
    
    private List<Long> PRIMES = new ArrayList<>();

    public static void main(String[] args) {

        logger.info("started.");

        P133 problem = new P133();
        problem.start();

        logger.info("finished.");
    }
    
    private void start() {
        
        final long BOUND = 100_000; 

        preparePrimes((int) BOUND);
        
        long sum = 0;
        
        List<Long> factors = new ArrayList<>();
        
        for(long p : PRIMES) {
            if((p % 2 == 0) || (p % 5 == 0)) {
                continue;
            }
            
            long a = A(p);
            
            if(isFactor(a)) {
                factors.add(p);
            }
        }
        
        PRIMES.removeAll(factors);
        
        for(long prime : PRIMES) {
            sum += prime;
        }
        
        logger.info("sum: " + sum);
    }
    
    private boolean isFactor(long a) {
        
        long mod = 1;
        Set<Long> set = new HashSet<>();
        
        while((mod = mod*10 % a) != 0) {
            if(!set.add(mod)) {
                return false;
            }
        }
        
        return true;
    }
    
    private long A(long n) {
        
        long a = 1;
        long mod = 1;
        
        while((mod = mod % n) != 0) {
            mod = mod * 10 + 1;
            a++;
        }
        
        return a;
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

        logger.info("primes prepared.");
    }
}
