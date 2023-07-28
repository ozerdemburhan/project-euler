package com.apkbilisim.pe.p137;

import java.math.BigInteger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P137 {
    
    private final BigInteger _20 = new BigInteger("20");
    
    private final BigInteger _16 = new BigInteger("16");
    
    public static void main(String[] args) {

        logger.info("started.");

        P137 problem = new P137();
        problem.start();

        logger.info("finished.");
    }
    
    private void start() {
        
        BigInteger fp = BigInteger.ONE;
        BigInteger f = BigInteger.TWO;
        
        int count = 0;
        
        while(true) {
            
            BigInteger m = check(f);
            
            if(m != null) {
                logger.info(++count + ": " + m.toString());
                
                if(count == 15) {
                    break;
                }
            }
            
            BigInteger current = f;
            f = f.add(fp);
            fp = current;
        }
    }
    
    private BigInteger check(BigInteger f) {

        BigInteger m = _20.multiply(f.pow(2)).subtract(_16);

        BigInteger sqrt = m.sqrt();

        if (sqrt.pow(2).compareTo(m) != 0) {
            return null;
        }

        m = sqrt.subtract(BigInteger.TWO);

        if (m.mod(BigInteger.TEN).compareTo(BigInteger.ZERO) != 0) {
            return null;
        }
        
        m = m.divide(BigInteger.TEN);
        
        return m;
    }
    
}

