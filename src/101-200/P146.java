package com.apkbilisim.pe.p146;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P146 {

    private List<Long>        PRIMES = new ArrayList<>();

    private final Set<String> POST   = Set.of("10", "70", "00");
    
    private volatile long sum = 0;

    public static void main(String[] args) {

        logger.info("started.");

        P146 problem = new P146();
        problem.start();

        logger.info("finished.");
    }
    
    private class Processor implements Runnable {
        
        private final List<Integer> seq = Arrays.asList(1, 3, 7, 9, 13, 27);
        
        private final List<Integer> exclude = Arrays.asList(2, 4, 5, 6, 8, 10, 11, 12, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26);
        
        private List<Long> list;
        
        
        public Processor(List<Long> list) {
            this.list = list;
        }
        
        @Override
        public void run() {
            List<Long> numbers = new ArrayList<>();
            
            for(long n : list) {
                long n2 = n * n;
                
                boolean ok = true;
                for (int diff : seq) {
                    if (!isPrime(n2 + diff, n)) {
                        ok = false;
                        break;
                    }
                }

                if (ok) {
                    numbers.add(n);
                    logger.info(numbers.toString());
                }            
            }
            
            logger.info(numbers.toString());
            
            int size = numbers.size();

            for (int i = size - 1; i >= 0; i--) {
                long n = numbers.get(i), n2 = n * n;

                for (int diff : exclude) {
                    if (isPrime(n2 + diff, n)) {
                        numbers.remove(i);
                        break;
                    }
                }
            }
            
            for(long n : numbers) {
                sum += n;
            }
            
            logger.info("sum: " + sum);
        }
        
    }
    
    private void start() {
        
        final long BOUND = 150_000_000;
        
        preparePrimes((int) BOUND);
        
        List<Long> list = new ArrayList<>();
        
        for(long n = 10; n < BOUND; n += 10) {
            if(!check(n)) {
                continue;
            }
            
            list.add(n);
        }
        
        logger.info("size: " + list.size());
        
        final int[] ratios = {70, 35, 25, 20};
        
        int step = list.size() / 150;
        
        int start = 0;
        
        for(int i = 0; i < ratios.length; i++) {
            
            int end = start + step * ratios[i];
            if(i == ratios.length - 1) {
                end = list.size();
            }
            
            List<Long> numbers = list.subList(start, end);
            
            Processor p = new Processor(numbers);
            Thread t = new Thread(p);
            t.start();
            start = end;
        }
        
    }

    private boolean check(long n) {

        if(n % 3 == 0) {
            return false;
        }
        
        if(n % 7 == 0) {
            return false;
        }

        if(n % 13 == 0) {
            return false;
        }
        
        return true;
    }

    private boolean isPrime(long n2, long n) {

        if (n2 == 1) {
            return false;
        }

        for (long p : PRIMES) {

            if (p > n + 1) {
                break;
            }

            if (n2 % p == 0) {
                return false;
            }
        }

        return true;
    }

    private void preparePrimes(int bound) {

        boolean[] field = new boolean[bound];

        for (int n = 2; n < bound; n++) {
            if (field[n]) {
                continue;
            }

            for (int i = 2 * n; i < bound; i += n) {
                field[i] = true;
            }
        }

        for (int i = 2; i < bound; i++) {
            if (!field[i]) {
                PRIMES.add((long) i);
            }
        }

        logger.info("primes prepared: " + PRIMES.size());
    }

}
