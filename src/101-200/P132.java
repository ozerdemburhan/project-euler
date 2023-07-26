package com.apkbilisim.pe.p132;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P132 {
    
    private List<Long> PRIMES = new ArrayList<>();

    public static void main(String[] args) {

        logger.info("started.");

        P132 problem = new P132();
        problem.start();

        logger.info("finished.");
    }

    private void start() {
        
        final long BOUND = 1_000_000_000; 

        preparePrimes((int) BOUND);
        
        logger.info("primes: " + PRIMES.size());
        
        int count = 0;
        long sum = 0;
        
        for(long p : PRIMES) {
            if((p % 2 == 0) || (p % 5 == 0)) {
                continue;
            }
            
            long a = A(p);
            
            if(BOUND % a == 0) {
                logger.info("" + p);
                sum += p;
                if(++count == 40) {
                    break;
                }
            }
        }
        
        logger.info("sum: " + sum);
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
