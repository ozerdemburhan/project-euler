package com.apkbilisim.pe.p140;

import java.math.BigDecimal;
import java.math.BigInteger;

public class P140 {
    
    private final BigInteger _5 = new BigInteger("5");
    
    private final BigInteger _14 = new BigInteger("14");
    
    private final BigDecimal[] LBOUNDS = {
        new BigDecimal("3.535322"),
        new BigDecimal("1.938748")
    };
    
    public static void main(String[] args) {

        long now = System.currentTimeMillis();
        System.out.println("started: " + now);

        P140 problem = new P140();
        problem.start();

        System.out.println("finished: " + (System.currentTimeMillis() - now));
    }
    
    private void start() {
        
        int count = 1;
        
        BigInteger sum = BigInteger.ZERO;
        BigInteger n = BigInteger.ZERO;
        
        while(true) {
            
            n = n.add(BigInteger.ONE);
            
            BigInteger d = _5.multiply(n.pow(2)).add(_14.multiply(n)).add(BigInteger.ONE);
            BigInteger sqrt = d.sqrt();
            
            if(d.compareTo(sqrt.pow(2)) == 0) {
                
                sum = sum.add(n);
                
                System.out.println(count + ": " + n);
                
                if(count == 30) {
                    break;
                }
                
                BigDecimal b = new BigDecimal(n);
                b = b.multiply(LBOUNDS[count % 2]);
                n = b.toBigInteger();
                
                count++;
            }
        }
        
        System.out.println("sum: " + sum);
    }
}
