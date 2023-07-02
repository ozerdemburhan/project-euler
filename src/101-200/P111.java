package com.apkbilisim.pe.p111;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P111 {

    private final long    START  = 1_000_000_000;
    private final long    END    = START * 10;
    private final long    STEP   = 100_000_000;

//    private final long                 START      = 1_000;
//    private final long                 END        = START * 10;
//    private final long                 STEP       = 1_000;

    private List<Long>                 PRIMES     = new ArrayList<>(100);

    private Map<Character, List<Long>> maxLists   = new HashMap<>();
    private Map<Character, Long>       maxRepeats = new HashMap<>();

    public static void main(String[] args) {

        logger.info("started.");

        P111 problem = new P111();
        problem.start();

        logger.info("finished.");
    }

    private void start() {

        preparePrimes(100_000);

        logger.info("" + PRIMES.size());

        for (long start = START; start < END; start += STEP) {
            logger.info(start + "-" + (start + STEP));
            
            long[] primes = scanPrimes(start, start + STEP);
            process(primes);
        }
        
        BigInteger total = BigInteger.ZERO;
        
        for(List<Long> primes : maxLists.values()) {
            for(long prime : primes) {
                total = total.add(new BigInteger("" + prime));
            }
        }
        
        logger.info("total: " + total);

    }

    private void process(long[] primes) {

        for (char d = '0'; d <= '9'; d++) {
            final char digit = d;

            if (!maxLists.containsKey(d)) {
                maxLists.put(digit, new ArrayList<>());
                maxRepeats.put(digit, 0L);
            }

            List<Long> maxList = maxLists.get(digit);
            long maxRepeat = maxRepeats.get(digit);

            for (long prime : primes) {
                long repeat = String.valueOf(prime).chars().filter(ch -> ch == digit).count();

                if (maxRepeat < repeat) {
                    maxRepeat = repeat;
                    maxList.clear();
                    maxList.add(prime);

                } else if (repeat == maxRepeat) {
                    maxList.add(prime);
                }
            }

            maxRepeats.put(digit, maxRepeat);
        }
    }

    private long[] scanPrimes(long start, long end) {
        
        final int UBOUND = (int) Math.sqrt(end);

        final int length = (int) (end - start);
        final boolean[] field = new boolean[length];
        
        for(int i = 0; i < PRIMES.size(); i++) {
            if(i > UBOUND) {
                break;
            }
            
            long p = PRIMES.get(i);
            long mod = start % p;
            long s = start + ((mod != 0) ? (p - mod) : 0);

            for (long k = s; k < end; k += p) {
                int index = (int) (k - start);
                field[index] = true;
            }
            
        }

        List<Long> list = new ArrayList<>();

        for (int index = 0; index < field.length; index++) {
            if (!field[index]) {
                long p = start + index;
                list.add(p);
            }
        }

        long[] primes = new long[list.size()];

        for (int index = 0; index < list.size(); index++) {
            primes[index] = list.get(index);
        }

        return primes;
    }

    private void preparePrimes(long bound) {

        boolean[] field = new boolean[(int) bound];

        for (int i = 2; i < bound; i++) {
            for (int n = 2 * i; n < bound; n += i) {
                field[n] = true;
            }
        }

        for (long i = 2; i < field.length; i++) {
            if (!field[(int) i]) {
                PRIMES.add(i);
            }
        }
    }

}
