package com.apkbilisim.pe.p123;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P123 {
    
    private int[] PRIMES;
    
    public static void main(String[] args) {

        logger.info("started.");

        P123 problem = new P123();
        problem.start();

        logger.info("finished.");
    }
    
    private void start() {
        
        final int UBOUND = 1_000_000;
        
        final BigInteger LIMIT = BigInteger.TEN.pow(10); 
        
        preparePrimes(UBOUND);
        
        for(int n = 1; n < PRIMES.length; n += 2) {
            BigInteger p = new BigInteger("" + PRIMES[n]);
            BigInteger p2 = p.multiply(p);
            BigInteger r = new BigInteger("" + n).multiply(p).multiply(BigInteger.TWO);
            r = r.mod(p2);
            
            if(r.compareTo(LIMIT) > 0) {
                logger.info(n + " " + p.toString() + ", r: " + r.toString());
                break;
            }
        }
    }
    
    private void preparePrimes(int bound) {
        
        List<Integer> primes = new ArrayList<>(1000);
        
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
        
        PRIMES = new int[primes.size() + 1];
        
        for(int i = 0; i < primes.size(); i++) {
            PRIMES[i + 1] = primes.get(i); 
        }
    }       
}
