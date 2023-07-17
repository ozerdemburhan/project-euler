package com.apkbilisim.pe.p126;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P126 {

    public static void main(String[] args) {

        logger.info("started.");

        P126 problem = new P126();
        problem.start();

        logger.info("finished.");
    }

    private void start() {
        
        for(int ubound = 3; ubound <= 10000; ubound++) {
            final int lbound = ubound * 2;
            int count = 0;
            
            for(int a = 1; a <= ubound; a++) {
                
                int amax = ubound / a;
                
                for(int b = a; b <= amax; b++) {
                    
                    int cmax = (lbound - a * b) / (a + b);
                    
                    for(int c = b; c <= cmax; c++) {
                        long l = a * b + b * c + a * c;
                        long t = a + b + c;
                        
                        if(2 * l <= lbound) {
                            if(f(l, t, lbound)) {
                                count++;
                            }
                        }
                    }
                }
            }
            
            if(count == 1000) {
                logger.info("l: " + lbound);
                break;
            }
        }
    }
    
    
    private boolean f(long l, long t, long search) {
        
        int n = 0;
        long f = 0;
        
        while(true) {
            if(f >= search) {
                break;
            }
            
            if(++n == 1) {
                f = 2 * l;
            } else {
                f += 4 * (t + 2 * n - 4);
            }
        }
        
        return f == search;
    }
}
