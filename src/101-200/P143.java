package com.apkbilisimpe.p143;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P143 {
    
    private final BigInteger _2 = BigInteger.TWO;

    private final BigInteger _3 = new BigInteger("3");
    
    public static void main(String[] args) {

        logger.info("started.");

        P143 problem = new P143();
        problem.start();

        logger.info("finished.");
    }
    
    private void start() {
        
        final long BOUND = 120_000;
        
        Set<BigInteger> set = new HashSet<>();
        
        for(long p = 1; p <= BOUND; p++) {
            if(p % 1000 == 0) {
                logger.info("" + p);
            }
            
            long p2 = p*p;
            
            for(long q = p; q <= BOUND - p; q++) {
                long q2 = q*q;
                
                long b2 = q2 + q*p + p2;                
                if(!isps(b2)) {
                    continue;
                }
                
                for(long r = q; r <= BOUND - p - q; r++) {
                    long l = p + q + r;
                    
                    if(l > BOUND) {
                        continue;
                    }
                    
                    long r2 = r*r;
                    long a2 = r2 + q*r + q2;
                    long c2 = p2 + p*r + r2;
                    
                    boolean check = isps(a2) && isps(c2);
                    if(!check) {
                        continue;
                    }
                    
                    BigInteger a = new BigInteger("" + (long) Math.sqrt(a2));
                    BigInteger b = new BigInteger("" + (long) Math.sqrt(b2));
                    BigInteger c = new BigInteger("" + (long) Math.sqrt(c2));
                    
                    BigInteger u = a.add(b).add(c);
                    BigInteger t2 = _3.multiply(u);
                    t2 = t2.multiply(u.subtract(_2.multiply(a)));
                    t2 = t2.multiply(u.subtract(_2.multiply(b)));
                    t2 = t2.multiply(u.subtract(_2.multiply(c)));
                    
                    if(!isps(t2)) {
                        continue;
                    }
                    
                    BigInteger t = t2.sqrt();
                    BigInteger l1 = t.add(new BigInteger("" + (a2 + b2 + c2)));
                    
                    if(l1.mod(_2).compareTo(BigInteger.ZERO) != 0) {
                        continue;
                    }
                    
                    l1 = l1.divide(_2);
                    
                    if(!isps(l1)) {
                        continue;
                    }
                    
                    l1 = l1.sqrt();
                    
                    if(l != l1.longValue()) {
                        continue;
                    }
                    
                    logger.info("l: " + l1);
                    set.add(l1);
                }
            }
        }
        
        BigInteger sum = BigInteger.ZERO;
        
        for(BigInteger l : set) {
            sum = sum.add(l);
        }
        
        logger.info("sum: " + sum);
        
    }
    
    private boolean isps(long n) {
        long sqrt = (long) Math.sqrt(n);
        return n == sqrt*sqrt;
    }
    
    private boolean isps(BigInteger n) {
        BigInteger sqrt = n.sqrt();
        return n.compareTo(sqrt.pow(2)) == 0;
    }
}
