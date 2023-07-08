package com.apkbilisim.pe.p117;

import java.math.BigInteger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P117 {
    
    public static void main(String[] args) {

        logger.info("started.");

        P117 problem = new P117();
        problem.start();

        logger.info("finished.");
    }
    
    private void start() {
        
        final int L = 50;
        int amax = L;
        int bmax = (int) (L / 2);
        int cmax = (int) (L / 3);
        int dmax = (int) (L / 4);
        
        BigInteger count = BigInteger.ZERO;
        
        for(int a = 0; a <= amax; a++) {
            for(int b = 0; b <= bmax; b++) {
                for(int c = 0; c <= cmax; c++) {
                    for(int d = 0; d <= dmax; d++) {
                        int l = a + 2 * b + 3 * c + 4 * d;
                        
                        if(L == l) {
                            count = count.add(calculate(a, b, c, d));
                        }
                    }
                }
            }
        }
        
        logger.info("count: " + count.toString());
    }
    
    private BigInteger calculate(int a, int b, int c, int d) {
        BigInteger x = BigInteger.ONE;
        
        BigInteger q = f(a + b + c + d);
        BigInteger de = f(a);
        de = de.multiply(f(b));
        de = de.multiply(f(c));
        de = de.multiply(f(d));
        
        return q.divide(de);
    }
    
    private BigInteger f(int n) {
        
        if(n == 0) {
            return BigInteger.ONE;
        }
        
        BigInteger f = new BigInteger("" + n);
        return f.multiply(f(n - 1));
    }
    
}
