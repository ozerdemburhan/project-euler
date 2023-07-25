package com.apkbilisim.pe.p130;

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
public class P130 {
    
    private List<Long> PRIMES = new ArrayList<>();

    public static void main(String[] args) {

        logger.info("started.");

        P130 problem = new P130();
        problem.start();

        logger.info("finished.");
    }
    
    private void start() {
        
        preparePrimes(100_000);
        
        long n = 1, sum = 0;
        int count = 0;
        
        while(true) {
            n++;
            
            if(PRIMES.contains(n)) {
                continue;
            }
            
            if((n % 2 == 0) || (n % 5 == 0)) {
                continue;
            }
            
            long k = A(n);
            
            if((n - 1) % k == 0) {
                sum += n;
                
                if(++count == 25) {
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
