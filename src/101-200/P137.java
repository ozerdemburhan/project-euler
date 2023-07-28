package com.apkbilisim.pe.p137;

import java.math.BigInteger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P137 {
    
    private final BigInteger _4  = new BigInteger("4");

    private final BigInteger _20 = new BigInteger("20");

    private final BigInteger _16 = new BigInteger("16");

    public static void main(String[] args) {

        logger.info("started.");

        P137 problem = new P137();
        problem.start();

        logger.info("finished.");
    }
    
    private void start() {
        
        long f1 = 1;
        long f2 = 1;
        
        int count = 0;
        
        while(true) {
            
            Long m = check(f2);
            
            if(m != null) {
                logger.info(++count + ": " + m);
                
                if(count == 15) {
                    break;
                }
            }
            
            long current = f2;
            f2 = f2 + f1;
            f1 = current;
        }
    }
    
    private Long check(long f) {

        BigInteger m = _20.multiply(new BigInteger("" + f).pow(2)).subtract(_16);
        
        if(m.compareTo(_4) <= 0) {
            return null;
        }

        BigInteger sqrt = m.sqrt();

        if (sqrt.pow(2).compareTo(m) != 0) {
            return null;
        }

        m = sqrt.subtract(BigInteger.TWO);

        if (m.mod(BigInteger.TEN).compareTo(BigInteger.ZERO) != 0) {
            return null;
        }
        
        m = m.divide(BigInteger.TEN);
        
        return m.longValue();
    }
    
}

