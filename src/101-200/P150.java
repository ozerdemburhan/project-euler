package com.apkbilisim.pe.p150;

import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P150 {
    
    public static void main(String[] args) {

        logger.info("started.");

        P150 problem = new P150();
        problem.start();

        logger.info("finished.");
    }
    
    private void start() {
        
        long[][] m = createTriangle();
        
        long minSum = Long.MAX_VALUE;
        
        for(int row = 0; row < m.length; row++) {
            for(int col = 0; col < m[row].length; col++) {
                long sum = 0;
                
                for(int r = row; r < m.length; r++) {
                    for(int c = col; c <= col + r - row; c++) {
                        sum += m[r][c];
                    }
                    
                    if(minSum > sum) {
                        minSum = sum;
                    }
                }
            }
         }
        
        logger.info("min: " + minSum);
    }

    private long[][] createTriangle() {
        
        final int BOUND = 500500;
        final long C = (long) Math.pow(2, 19);
        final long MOD = (long) Math.pow(2, 20);
        
        long t = 0;
        long[] s = new long[(int) BOUND];
        
        for(int k = 1; k <= BOUND; k++) {
            t = (615949*t + 797807) % MOD;
            s[k - 1] = t - C;
        }
        
        long[][] m = new long[1000][];
        
        int from = 0;
        for(int k = 0; k < 1000; k++) {
            m[k] = Arrays.copyOfRange(s, from, from + k + 1);
            from += k + 1;
        }
        
        return m;
    }
}
