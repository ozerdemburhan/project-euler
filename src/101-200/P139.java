package com.apkbilisim.pe.p139;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P139 {
    
    public static void main(String[] args) {

        logger.info("started.");

        P139 problem = new P139();
        problem.start();

        logger.info("finished.");
    }
    
    private void start() {
        
        final long UBOUND = 100_000_000;
        final long BOUND = (long) Math.sqrt(UBOUND);
        
        int count = 0;
        
        for(long n = 1; n < BOUND; n++) {
            for(long m = n + 1; m <= BOUND; m++) {
                long a = 2*m*n;
                long b = m*m - n*n;
                long c = m*m + n*n;
                
                long P = a + b + c;
                
                if(P >= UBOUND) {
                    break;
                }
                
                if(gcd(a, b) != 1) {
                    continue;
                }
                
                long edge = (long) Math.abs(a - b);
                
                if(c % edge == 0) {
                    logger.info(b + "," + a + "," + c + ", P: " + P + ", edge: " + edge + ", (" + m + "," + n + ")");
                    count += (long) UBOUND / P;
                }
            }
        }
        
        logger.info("count: " + count);
        
    }
    
    private long gcd(long a, long b) {
        while (b != 0) {
            long t = b;
            b = a % b;
            a = t;
        }
        
        return a;
    }
}

