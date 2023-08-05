package com.apkbilisim.pe.p142;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P142 {
    
    public static void main(String[] args) {

        logger.info("started.");

        P142 problem = new P142();
        problem.start();

        logger.info("finished.");
    }
    
    private void start() {
        
        final long BOUND = 3000;
        
        out:
        for(long a = 1; a < BOUND; a++) {
            long a2 = a*a;
            
            for(long b = 1; b < BOUND; b++) {
                long b2 = b*b;
                
                for(long c = 1; c < BOUND; c++) {
                    long c2 = c*c;
                    
                    if((b2 + c2) % 2 != 0) {
                        continue;
                    }
                                        
                    long x = (2*a2 + b2 + c2)/2;
                    long y = (c2 + b2)/2;
                    long z = (c2 - b2)/2;
                    
                    if(!isps(x + y) || !isps(x - y) || !isps(x + z) || !isps(x - z) || !isps(y + z) || !isps(y - z)) {
                        continue;
                    }
                    
                    if((x > y) && (y > z) && (z > 0)) {
                        logger.info(x + "," + y + "," + z + ", x+y+z=" + (x+y+z));
                        break out;
                    }
                }
            }
        }
        
    }
    
    private boolean isps(long n) {
        long sqrt = (long) Math.sqrt(n);
        return n == sqrt*sqrt;
    }
}

