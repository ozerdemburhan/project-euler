package com.apkbilisim.pe.p124;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P124 {
    
    private final List<Integer> PRIMES = new ArrayList<>();
    
    public static void main(String[] args) {
        
        logger.info("started.");

        P124 problem = new P124();
        problem.start();

        logger.info("finished.");
    }
    
    private void start() {
        
        final int UBOUND = 100_000;
        
        preparePrimes(UBOUND);
        
        List<Value> E = new ArrayList<>();
        
        for(int n = 1; n <= UBOUND; n++) {
            Value value = new Value(n, rad(n));
            E.add(value);
        }
        
        Collections.sort(E);
        logger.info(E.get(10000 - 1).toString());
    }
    
    private int rad(int n) {
        
        Set<Integer> dpf = new HashSet<>();
        
        dpf.addAll(primeFactors(n));
        
        int rad = 1;
        
        for(int prime : dpf) {
            rad *= prime;
        }
        
        return rad;
    }
    
    private List<Integer> primeFactors(int n) {

        List<Integer> primeFactors = new ArrayList<>();
        int ubound = (int) Math.sqrt(n);
        
        for (int p : PRIMES) {
            while (n % p == 0) {
                primeFactors.add(p);
                n /= p;
            }

            if (p > ubound) {
                primeFactors.add(n);
                return primeFactors;
            }

            if (n == 1) {
                return primeFactors;
            }
        }

        return primeFactors;
    }    
    
    private void preparePrimes(int bound) {
        
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
                PRIMES.add(i);
            }
        }
    }        
}

class Value implements Comparable<Value> {
    
    private int n;
    
    private int rad;
    
    public Value(int n, int rad) {
        this.n = n;
        this.rad = rad;
    }
    
    @Override
    public String toString() {
        return n + " " + rad;
    }
    
    @Override
    public int hashCode() {
        return ("" + n + "-" + rad).hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        if((obj == null) || !(obj instanceof Value)) {
            return false;
        }
        
        Value value = (Value) obj;
        return compareTo(value) == 0;
    }
    
    @Override
    public int compareTo(Value o) {
        
        int c = Integer.compare(rad, o.rad);
        
        if(c != 0) {
            return c;
        }
        
        return Integer.compare(n, o.n);
    }
    
    public int getN() {
        return n;
    }
    
    public int getRad() {
        return rad;
    }    
}
