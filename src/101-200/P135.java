package com.apkbilisim.pe.p135;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P135 {
    
    public static void main(String[] args) {

        logger.info("started.");

        P135 problem = new P135();
        problem.start();

        logger.info("finished.");
    }
    
    private void start() {
        
        long totalCount = 0;
        
        for(long n = 1155; n < 1_000_000; n++) {
            
            long mmax = (3 * n - 1) / 4;
            int count = 0;
            
            for(long m = 1; m <= mmax; m++) {
                
                long value = 4 * m * m + 3 * n;
                long sqrt = (long) Math.sqrt(value);
                
                if(value == sqrt * sqrt) {
                    long k = sqrt - m;
                    
                    if((k > 0) && (k % 3 == 0)) {
                        count++;
                    }
                }
            }
            
            if(count == 10) {
                totalCount++;
            }
        }
        
        logger.info("count: " + totalCount);
    }

}
