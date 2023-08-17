package com.apkbilisim.pe.p149;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P149 {
    
    private long max = Long.MIN_VALUE;

    public static void main(String[] args) {

        logger.info("started.");

        P149 problem = new P149();
        problem.start();

        logger.info("finished.");
    }
    
    private void start() {
        long[][] m = createTable();

        final int L = m.length;
        
        List<Long> list = new ArrayList<>();
        
        for(int r = 0; r < L; r++) {
            
            for(int col = 0; col < L; col++) {
                list.add(m[r][col]);
            }
            maxSumSubsequence(list);
            
            for(int row = 0; row < L; row++) {
                list.add(m[row][r]);
            }
            maxSumSubsequence(list);
        }
        
        for (int r = 0; r < L; r++) {
            
            int row = 0;
            for (int col = r; col >= 0; col--) {
                list.add(m[row++][col]);
            }
            maxSumSubsequence(list);

            int col = L - 1;
            for (row = r; row < L; row++) {
                list.add(m[row][col--]);
            }
            maxSumSubsequence(list);
        }
        
        for(int r = 0; r < L; r++) {
            
            int col = 0;
            for(int row = r; row < L; row++) {
                list.add(m[row][col++]);
            }
            maxSumSubsequence(list);

            int row = 0;
            for(col = r; col < L; col++) {
                list.add(m[row++][col]);
            }
            maxSumSubsequence(list);
        }

        logger.info("max: " + max);
    }
    
    private void maxSumSubsequence(List<Long> list) {
        
        long maxSoFar = Long.MIN_VALUE;
        long maxEndingHere = 0;
        
        for(int i = 0; i < list.size(); i++) {
            maxEndingHere += list.get(i);
            
            if(maxSoFar < maxEndingHere) {
                maxSoFar = maxEndingHere;
            }
            
            if(maxEndingHere < 0) {
                maxEndingHere = 0;
            }
        }
        
        if(max < maxSoFar) {
            max = maxSoFar;
        }
        
        list.clear();
    }

    private long[][] createTable() {

        final int L = 2000;
        final BigInteger _1M = new BigInteger("" + 1000000);
        final BigInteger _4M = new BigInteger("" + 4000000);
        final BigInteger _1HT3 = new BigInteger("" + 100003);
        final BigInteger _2HT3 = new BigInteger("" + 200003);
        final BigInteger _3HT7 = new BigInteger("" + 300007);

        final BigInteger _55 = new BigInteger("55");
        final BigInteger _5HT = new BigInteger("" + 500000);

        BigInteger[] s = new BigInteger[4 * _1M.intValue()];

        for (BigInteger k = BigInteger.ONE; k.compareTo(_4M) <= 0; k = k.add(BigInteger.ONE)) {

            int ki = k.intValue() - 1;
            BigInteger sk = null;

            if (k.compareTo(_55) <= 0) {
                sk = _1HT3.subtract(_2HT3.multiply(k)).add(_3HT7.multiply(k.pow(3)));
                sk = sk.mod(_1M).subtract(_5HT);
                
            } else {
                sk = s[ki - 24].add(s[ki - 55]).add(_1M);
                sk = sk.mod(_1M).subtract(_5HT);
            }

            s[ki] = sk;
        }

        logger.info("" + s[10 - 1]);
        logger.info("" + s[100 - 1]);

        long[][] m = new long[L][L];
        for(int row = 0; row < L; row++) {
            for(int col = 0; col < L; col++) {
                int i = row*L + col;
                m[row][col] = s[i].longValue();
            }
        }

        return m;
    }

}
