package com.apkbilisim.pe.p113;

import java.math.BigInteger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P113 {

    public static void main(String[] args) {

        logger.info("started.");

        P113 problem = new P113();
        problem.start();

        logger.info("finished.");
    }

    private void start() {
        
        final BigInteger NINE = new BigInteger("9");
        
        BigInteger count = BigInteger.ZERO;
        
        for(int n = 3; n <= 100; n++) {
            BigInteger inc = countIncreasing(n);
            BigInteger dec = countDecreasing(n);
            
            BigInteger dcount = inc.add(dec).subtract(NINE); 
            count = count.add(dcount);
        }
        
        count = count.add(new BigInteger("99"));
        
        logger.info("count: " + count);
    }
    
    private BigInteger countDecreasing(int n) {
        
        BigInteger sum = BigInteger.ZERO;        
        BigInteger f = BigInteger.ONE;
        
        for(int i = 1; i <= n - 1; i++) {
            f = f.multiply(new BigInteger("" + i));
        }
        
        for(int d = 1; d <= 9; d++) {
            
            BigInteger mul = BigInteger.ONE;
            
            for(int k = 1; k < n; k++) {
                mul = mul.multiply(new BigInteger("" + (k + d)));
            }
            
            sum = sum.add(mul);
        }
        
        BigInteger inc = sum.divide(f);
        
        return inc;
        
    }
    
    private BigInteger countIncreasing(int n) {

        BigInteger sum = BigInteger.ZERO;        
        BigInteger f = BigInteger.ONE;
        
        for(int i = 1; i <= n - 1; i++) {
            f = f.multiply(new BigInteger("" + i));
        }
        
        
        for(int d = 1; d <= 9; d++) {
            
            BigInteger mul = BigInteger.ONE;
            
            for(int k = 10; k < n + 9; k++) {
                mul = mul.multiply(new BigInteger("" + (k - d)));
            }
            
            sum = sum.add(mul);
        }
        
        BigInteger inc = sum.divide(f);
        
        return inc;
    }
    
}
