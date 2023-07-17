package com.apkbilisim.pe.p127;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class P127 {
    
    private final int                        UBOUND        = 120_000;
    private final Map<Integer, Set<Integer>> PRIME_FACTORS = new HashMap<>();
    private final long[]                     RADICALS      = new long[UBOUND];
    private boolean[][]                      rps           = new boolean[UBOUND / 2][UBOUND];

    public static void main(String[] args) {

        long now = System.currentTimeMillis();
        System.out.println("started: " + now);

        P127 problem = new P127();
        problem.start();

        System.out.println("finished: " + (System.currentTimeMillis() - now));
    }
    
    private void start() {
        mapPrimeFactors(UBOUND);
        prepareRadicals();
        for(int a = 2; a < UBOUND / 2; a++) {
            Set<Integer> pfs = PRIME_FACTORS.get(a);
            
            for(int p : pfs) {
                for(int b = 2 * p; b < UBOUND - 1; b += p) {
                    rps[a][b] = true;
                }
            }
        }
        
        System.out.println("map prepared.");
        long sum = 0;
        for(int a = 1; a < UBOUND / 2; a++) {
            for(int b = a + 1; b < UBOUND - a; b++) {
                if(!rps[a][b]) {
                    int c = a + b;
                    if(c < UBOUND && rad(a, b, c) < c) {
                        sum += c;
                    }
                }
            }
        }
        System.out.println("sum: " + sum);
    }
    
    private long rad(int... arr) {
        long rad = 1;
        for(int n : arr) {
            rad *= RADICALS[n];
        }
        return rad;
    }
    
    private void prepareRadicals() {
        for(Entry<Integer, Set<Integer>> entry : PRIME_FACTORS.entrySet()) {
            long rad = 1;
            for(int p : entry.getValue()) {
                rad *= p;
            }
            RADICALS[entry.getKey()] = rad;
        }
        
        for(int i = 0; i < RADICALS.length; i++) {
            if(RADICALS[i] == 0) {
                RADICALS[i] = i;
            }
        }
    }
    
    private void mapPrimeFactors(int bound) {
        boolean[] field = new boolean[bound];
        
        for(int n = 2; n < bound; n++) {
            if(field[n]) {
                continue;
            }
            
            if(!PRIME_FACTORS.containsKey(n)) {
                Set<Integer> set = new HashSet<>();
                set.add(n);
                PRIME_FACTORS.put(n, set);
            }
            
            for(int i = 2*n; i < bound; i += n) {
                if(!PRIME_FACTORS.containsKey(i)) {
                    PRIME_FACTORS.put(i, new HashSet<>());
                }
                PRIME_FACTORS.get(i).add(n);
                field[i] = true;
            }
        }
    }    
}
