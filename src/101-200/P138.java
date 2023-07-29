package com.apkbilisim.pe.p138;

import java.math.BigInteger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P138 {
    
    private final BigInteger _4 = new BigInteger("4");
    
    private final BigInteger _5 = new BigInteger("5");
    
    private long sum = 0;
    
    public static void main(String[] args) {

        logger.info("started.");

        P138 problem = new P138();
        problem.start();

        logger.info("finished.");
    }
    
    private void start() {
        
        long lp = 1, l = 17;
        long sum = l;
        
        int count = 1;
        
        while(++count <= 12) {
            
            long temp = l;
            l = 18*l - lp;
            lp = temp;
            
            sum += l;
            logger.info(count + ": " + l);
        }
        
        logger.info("sum: " + sum);
        
    }
}

