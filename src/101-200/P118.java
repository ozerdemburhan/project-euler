package com.apkbilisim.pe.p118;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P118 {
    
    private final int                        UBOUND   = 1_000_000_000;

    private final Map<Integer, List<String>> PMAP     = new HashMap<>();

    private final List<List<Integer>>        PATTERNS = new ArrayList<>();

    public static void main(String[] args) {
        
        logger.info("started.");

        P118 problem = new P118();
        problem.start();

        logger.info("finished.");
    }
    
    private void start() {
        
        preparePrimes(UBOUND);
        filterPrimes();
        
        preparePatterns();
        
        for(int i = 0; i < PATTERNS.size(); i++) {
            List<Integer> pattern = PATTERNS.get(i);
            searchPrimes(pattern, new ArrayList<String>(), 0, 0);
        }
        
        logger.info("count: " + count);
    }
    
    private long count = 0;
    
    private void searchPrimes(List<Integer> pattern, List<String> primes, int patternIndex, int primeIndex) {
        
        if(pattern.size() == patternIndex) {
            count++;
            primes.clear();
            return;
        }
        
        int length = pattern.get(patternIndex);
        List<String> plist = PMAP.get(length);
        
        if(patternIndex > 0) {
            if(length != pattern.get(patternIndex - 1)) {
                primeIndex = 0;
            }
        }
        
        for(int i = primeIndex; i < plist.size(); i++) {
            String prime = plist.get(i);
            
            if(canAdd(primes, prime)) {
                List<String> cprimes = new ArrayList<>(primes);
                cprimes.add(prime);                
                searchPrimes(pattern, cprimes, patternIndex + 1, i + 1);
            }
        }
    }
    
    private boolean canAdd(List<String> primes, String prime) {
        
        StringBuilder sb = new StringBuilder(prime);
        
        for(String p : primes) {
            sb.append(p);
        }
        
        for(int i = 0; i < sb.length(); i++) {
            for(int j = i + 1; j < sb.length(); j++) {
                if(sb.charAt(i) == sb.charAt(j)) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    private void preparePatterns() {
        
        List<Integer> list = new ArrayList<>();
        resolve(list, 1);
        
        for(int i = PATTERNS.size() - 1; i >= 0; i--) {
            List<Integer> pattern = PATTERNS.get(i);
            
            StringBuilder sb = new StringBuilder();
            
            for(Integer n : pattern) {
                sb.append(n);
            }
            
            if(sb.toString().lastIndexOf('1') > 3) {
                PATTERNS.remove(i);
            }
            
            if(sb.toString().equals("9")) {
                PATTERNS.remove(i);
            }
        }
    }
    
    
    private void resolve(List<Integer> list, int last) {
        
        int sum = 0;
        for(int n : list) {
            sum += n;
        }
        
        if(sum == 9) {
            PATTERNS.add(new ArrayList<>(list));
            list.clear();
            return;
        }
        
        for(int n = last; n <= 9 - sum; n++) {
            List<Integer> temp = new ArrayList<>(list);
            temp.add(n);
            resolve(temp, n);
        }
    }
    
    private void preparePrimes(int bound) {
        
        for(int length = 1; length < 10; length++) {
            PMAP.put(length, new ArrayList<>());
        }
        
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
                String s = String.valueOf(i);
                PMAP.get(s.length()).add(s);
            }
        }
    }
    
    private void filterPrimes() {
        
        for(List<String> primes : PMAP.values()) {
            
            for(int i = primes.size() - 1; i >= 0; i--) {
                String prime = primes.get(i);
                
                if(prime.contains("0")) {
                    primes.remove(i);
                    continue;
                }
                
                out:
                for(int j = 0; j < prime.length(); j++) {
                    for(int k = j + 1; k < prime.length(); k++) {
                        if(prime.charAt(j) == prime.charAt(k)) {
                            primes.remove(i);
                            break out;
                        }
                    }
                }
            }
        }
        
        PMAP.remove(9);
    }

}
