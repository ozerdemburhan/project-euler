package com.apkbilisim.pe.p125;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P125 {

    public static void main(String[] args) {

        logger.info("started.");

        P125 problem = new P125();
        problem.start();

        logger.info("finished.");
    }

    private void start() {
        
        final long UBOUND = 100_000_000;
        final int SIZE = (int) Math.ceil(Math.sqrt((double) UBOUND / 2)) + 1;
        long[] squares = new long[SIZE];
        
        for(int i = 1; i <= SIZE; i++) {
            squares[i - 1] = i * i;
        }
        
        Set<Long> set = new HashSet<>();
        
        for(int pos = 0; pos < squares.length; pos++) {
            
            for(int r = 2; r < squares.length; r++) {
                
                if(pos + r > squares.length) {
                    break;
                }
                
                long sum = 0;
                
                for(int n = pos; n < pos + r; n++) {
                    sum += squares[n];
                }
                
                if(sum < UBOUND) {
                    if(isPalindromic(sum) ) {
                        set.add(sum);
                    }
                }
            }
        }
        
        long total = 0;
        for(long sq : set) {
            total += sq;
        }
        
        logger.info("total: " + total);
    }

    private boolean isPalindromic(long n) {

        String s = String.valueOf(n);
        StringBuilder b = new StringBuilder(s);
        String r = b.reverse().toString();

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != r.charAt(i)) {
                return false;
            }
        }

        return true;
    }

}
