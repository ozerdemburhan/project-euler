package com.apkbilisim.pe.p134;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P134 {

    private List<Long> PRIMES = new ArrayList<>();

    public static void main(String[] args) {

        logger.info("started.");

        P134 problem = new P134();
        problem.start();

        logger.info("finished.");
    }
    
    private void start() {
        
        final int BOUND = 1_000_004; 
        preparePrimes(BOUND);
        long sum = 0L;
        
        for(int i = 2; i < PRIMES.size() - 1; i++) {
            long p1 = PRIMES.get(i);
            long p2 = PRIMES.get(i + 1);
            long k = ((long) Math.log10(p1)) + 1;
            k = (long) Math.pow(10, k);
            long l = 1;
            
            while(true) {
                long n = l * k + p1;
                
                if(n % p2 == 0) {
                    sum += n;
                    break;
                }
                
                l++;
            }
        }
        
        logger.info("sum: " + sum);
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
