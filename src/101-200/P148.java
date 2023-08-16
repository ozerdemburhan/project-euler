package com.apkbilisim.pe.p148;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P148 {

    public static void main(String[] args) {

        logger.info("started.");

        P148 problem = new P148();
        problem.start();

        logger.info("finished.");
    }
    
    private void start() {
        
        List<Long> list = new ArrayList<>();
        long exp = 1;
        for(long k = 0; k <= 11; k++) {
            list.add(exp);
            exp *= 7;
        }
        
        long[] powers = new long[list.size()];
        for(int i = 0; i < list.size(); i++) {
            powers[i] = list.get(i);
        }
        
        long count = 0;
        
        for(long n = 0; n < 1_000_000_000; n++) {
            int power = 0;
            while(n >= powers[++power]);
            power--;
            
            long mul = 1;
            long number = n;
            
            for(int k = power; k >= 0; k--) {
                mul *= number/powers[k] + 1;
                number %= powers[k];
            }
            
            count+= mul;
        }
        
        logger.info("count: " + count);
        
    }
}
