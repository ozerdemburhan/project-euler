package com.apkbilisim.pe.p128;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P128 {

    private int[]                PRIMES;

    public static void main(String[] args) {

        logger.info("started.");

        P128 problem = new P128();
        problem.start();

        logger.info("finished.");
    }
    
    private void start() {
        preparePrimes(1_000_000);
        
        final int UBOUND = 2000;
        
        int count = 0;
        long n = 8, layer = 2;
        long prevLayerStart = 2, prevLayerEnd = f(layer - 1);
        long start = n, end = f(layer);
        long nextLayerStart = end + 1, nextLayerEnd = f(layer + 1);
        
        List<Long> pds = new ArrayList<>();
        pds.addAll(Set.of(1L, 2L));
        
        while (count < UBOUND) {

            long length = end - start + 1;
            long edge = length / 6;

            Set<Long> set = Set.of(start, end);

            for (long k : set) {
                Set<Long> neighbors = getNeighbors(k, layer, start, end, edge, prevLayerStart, prevLayerEnd, nextLayerStart, nextLayerEnd);
                
                if (pd(k, neighbors) == 3) {
                    pds.add(k);
                    count++;
                }

                if (count >= UBOUND) {
                    break;
                }
            }            
            
            prevLayerStart = start;
            prevLayerEnd = end;
            
            start = end + 1;
            end = f(++layer);
            
            nextLayerStart = end + 1;
            nextLayerEnd = f(layer + 1);
        }
        
        Collections.sort(pds);
        logger.info("result: " + pds.get(UBOUND - 1));
    }
    
    
    private Set<Long> getNeighbors(long n, long layer, long start, long end, long edge, long prevLayerStart, long prevLayerEnd, long nextLayerStart, long nextLayerEnd) {
        
        Set<Long> neighbors  = new HashSet<>();
        
        if(n == start) {
            neighbors.add(end);
            neighbors.add(n + 1);
            neighbors.add(nextLayerEnd);
            neighbors.add(nextLayerStart);
            neighbors.add(nextLayerStart + 1);
            
            
        } else if(n == end) {
            neighbors.add(start);
            neighbors.add(n - 1);
            neighbors.add(prevLayerStart);
            neighbors.add(prevLayerEnd);
            neighbors.add(nextLayerEnd);
            neighbors.add(nextLayerEnd - 1);
            
        } else {
            neighbors.add(n - 1);
            neighbors.add(n + 1);
        }
        
        long cp = (n - start) / edge;
        long ep = (n - start) - (cp * edge);
        long prevValue = prevLayerStart + (edge - 1) * cp;
        
        if(ep > 0) {
            prevValue += ep - 1;
            neighbors.add(prevValue + 1);
        }
        
        neighbors.add(prevValue);
        
        long nextValue = nextLayerStart + (edge + 1) * cp;
        
        if(ep > 0) {
            nextValue += ep;
            neighbors.add(nextValue + 1);
            
        } else {
            neighbors.add(nextValue - 1);
            neighbors.add(nextValue + 1);
        }
        
        neighbors.add(nextValue);
        return neighbors;
    }
    
    
    private long f(long n) {
        return 3 * n * n + 3 * n + 1;
    }
    
    private int pd(long n, Set<Long> neighbors) {
        
        int pd = 0;
        
        for(long value : neighbors) {
            long diff = (long) Math.abs(n - value);
            
            if(isPrime(diff)) {
                pd++;
            }
        }
        
        return pd;
    }    

    private boolean isPrime(long n) {
        
        if(n == 1) {
            return false;
        }
        
        int bound = (int) Math.sqrt(n);
        
        for(int p : PRIMES) {
            if(p > bound) {
                break;
            }
            
            if(n % p == 0) {
                return false;
            }
        }
        
        return true;
    }
    
    private void preparePrimes(int bound) {
        
        List<Integer> primes = new ArrayList<>();
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
        
        PRIMES = new int[primes.size()];
        
        for(int i = 0; i < primes.size(); i++) {
            PRIMES[i] = primes.get(i);
        }
    }    
}
