package com.apkbilisim.pe.p116;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P116 {
    
    public static void main(String[] args) {

        logger.info("started.");

        P116 problem = new P116();
        problem.start();

        logger.info("finished.");
    }

    private void start() {
        
        final int ROW_SIZE = 50;
        
        long red = fillCount(2, ROW_SIZE, 0);
        long green = fillCount(3, ROW_SIZE, 0);
        long blue = fillCount(4, ROW_SIZE, 0);
        
        long count = red + green + blue;
        
        logger.info("count: " + count);
    }
    
    private long fillCount(int m, int n, int startPos) {
        
        long count = 0;

        if (startPos + m > n) {
            return count;
        }

        for (int pos = startPos; pos <= n - m; pos++) {
            count += 1 + fillCount(m, n, pos + m);
        }
        
        return count;
    }    
}
