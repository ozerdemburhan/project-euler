package com.apkbilisim.pe.p131;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P131 {
    
    private List<Long> PRIMES = new ArrayList<>();

    public static void main(String[] args) {

        logger.info("started.");

        P131 problem = new P131();
        problem.start();

        logger.info("finished.");
    }
    
    private void start() {
        
        preparePrimes(1_000_000);
        
        int count = 0;
        
        for(long p : PRIMES) {
            if(p >= 1_000_000) {
                break;
            }
            
            for(long t = 1; t < 1000; t++) {
                long n = t * t * t;
                double l = Math.pow(n + p, 1.0 / 3);
                
                long ceil = (long) Math.ceil(l);
                long c3 = (long) Math.pow(ceil, 3);
                
                long floor = (long) Math.floor(l);
                long f3 = (long) Math.pow(floor, 3);
                
                if(c3 == n + p) {
                    count++;
                    logger.info("(" + n + "," + p + "," + c3 + ")");
                    break;
                }
                
                if(f3 == n + p) {
                    count++;
                    logger.info("(" + n + "," + p + "," + f3 + ")");
                    break;
                }
            }
        }
        
        logger.info("count: " + count);
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
