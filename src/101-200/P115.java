package com.apkbilisim.pe.p115;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P115 {
    
    public static void main(String[] args) {

        logger.info("started.");

        P115 problem = new P115();
        problem.start();

        logger.info("finished.");
    }

    private void start() {

        int n = 50;
        
        while(true) {
            long count = fillCount(50, n);
            
            if(count > 1_000_000) {
                logger.info(n + ": " + count);
                break;
            }
            
            n++;
        }
    }
    
    private long fillCount(int m, int n) {
        return 1 + fillCount(m, n, 0);
    }
    
    private long fillCount(int m, int n, int startPos) {
        
        long count = 0;

        for (int block = m; block <= n; block++) {
            if (startPos + block > n) {
                break;
            }

            for (int pos = startPos; pos <= n - block; pos++) {
                count += 1 + fillCount(m, n, pos + block + 1);
            }
        }
        
        return count;
    }    
}
