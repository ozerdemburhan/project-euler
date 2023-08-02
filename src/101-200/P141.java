package com.apkbilisim.pe.p141;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P141 {
    
    private long[]                PRIMES;

    private final DecimalFormat   FMT = new DecimalFormat("", new DecimalFormatSymbols(Locale.GERMANY));

    private Map<Long, List<Long>> map = new LinkedHashMap<>();

    public static void main(String[] args) {

        logger.info("started.");

        P141 problem = new P141();
        problem.start();

        logger.info("finished.");
    }
    
    private void start() {
        
        final long BOUND = (long) Math.pow(10, 12);
//        final long BOUND = (long) Math.pow(10, 5);
        final long UBOUND = Math.round(Math.sqrt(BOUND));
        BigInteger sum = BigInteger.ZERO;
        
        preparePrimes((int) (UBOUND));
        
        for(long m = 1; m < UBOUND; m++) {
            long n = m*m;
            map.put(n, factors(n));
        }
        
        logger.info("factors prepared.");
        
        for (Entry<Long, List<Long>> entry : map.entrySet()) {
            long n = entry.getKey();
            List<Long> factors = entry.getValue();

            long[] abr = abr(n, factors);

            if (abr != null) {
                long a = abr[0], b = abr[1], r = abr[2];
                long a2 = a * a, b2 = b * b;

                long q = (r / b) * a;
                long d = (r / b2) * a2;

                if (n == d * q + r) {
                    sum = sum.add(new BigInteger("" + n));
                    logger.info(FMT.format(n) + ": " + r + "," + q + "," + d + " " + a + "/" + b);
                }
            }
            
        }
        
        logger.info("sum : " + sum);
    }
    
    private long[] abr(long n, List<Long> factors) {
        
        BigInteger bn = new BigInteger("" + n);
        final BigInteger UBOUND = new BigInteger("" + (long) Math.sqrt(n));
        
        for(int i = 0; i < factors.size() - 1; i++) {
            BigInteger f1 = new BigInteger("" + factors.get(i));
            
            if(f1.compareTo(UBOUND) > 0) {
                break;
            }
            
            for(int j = 0; j <= i; j++) {
                BigInteger f2 = new BigInteger("" + factors.get(j));
                
                if(f1.mod(f2).compareTo(BigInteger.ZERO) != 0) {
                    continue;
                }

                BigInteger b = f1.divide(f2);
                BigInteger r = b.multiply(f1);
                BigInteger b3 = b.pow(3);
                BigInteger r2 = r.pow(2);
                
                if(bn.subtract(r).compareTo(BigInteger.ZERO) <= 0) {
                    continue;
                }
                
                if(r2.mod(b3).compareTo(BigInteger.ZERO) != 0) {
                    continue;
                }
                
                BigInteger c = r2.divide(b3);

                if(bn.subtract(r).mod(c).compareTo(BigInteger.ZERO) != 0) {
                    continue;
                }

                BigInteger a3 = bn.subtract(r).divide(c);
                BigInteger a = new BigInteger("" + cbrt(a3.longValue()));
                
                if(a3.compareTo(a.pow(3)) != 0) {
                    continue;
                }
                
                if(a.compareTo(b) < 0) {
                    continue;
                }
                
                return new long[] {a.longValue(), b.longValue(), r.longValue()};
            }
        }
        
        return null;
    }
    
    private long cbrt(long n) {
        return Math.round(Math.cbrt(n));
    }
    
    private List<Long> factors(long n) {
        
        Map<Long, Long> primeFactors = primeFactors(n);

        List<Long> factors = new ArrayList<>();
        factors(primeFactors, factors);
        
        if(factors.isEmpty()) {
            factors.add(1L);
        }
        
        Collections.sort(factors);
        return factors;
    }
    
    private void factors(Map<Long, Long> primeFactors, List<Long> factors) {
        
        if(primeFactors.isEmpty()) {
            return;
        }
        
        Entry<Long, Long> entry = primeFactors.entrySet().iterator().next();
        
        List<Long> currentFactors = new ArrayList<>();
        
        long factor = 1L;
        
        for(long power = 0; power <= entry.getValue(); power++) {
            currentFactors.add(factor);
            factor *= entry.getKey();
        }
        
        
        if(factors.isEmpty()) {
            factors.addAll(currentFactors);
            
        } else {
            int size = factors.size();
            
            for(int i = 1; i < currentFactors.size(); i++) {
                long f1 = currentFactors.get(i);
                
                for(int j = 0; j < size; j++) {
                    long f2 = factors.get(j);
                    factors.add(f1 * f2);
                }
            }
        }
        
        primeFactors.remove(entry.getKey());
        
        factors(primeFactors, factors);
        
    }

    private Map<Long, Long> primeFactors(long n) {
        
        Map<Long, Long> map = new LinkedHashMap<>();
        
        primeFactors(n, map);
        
        return map;
    }
    
    private void primeFactors(long n, Map<Long, Long> map) {
        
        final int UBOUND = (int) Math.sqrt(n);
        
        for(long prime : PRIMES) {
            
            if(n == 1) {
                break;
            }
            
            if(prime > UBOUND) {
                map.put(n, 1L);
                break;
            }
            
            while(n % prime == 0) {
                Long current = map.put(prime, 1L);
                
                if(current != null) {
                    map.put(prime, current + 1);
                }
                
                n /= prime;
            }
        }
    }    
    
    private void preparePrimes(int bound) {

        List<Integer> primes = new ArrayList<>();
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
                primes.add(i);
            }
        }

        PRIMES = new long[primes.size()];

        for (int i = 0; i < primes.size(); i++) {
            PRIMES[i] = primes.get(i);
        }

        logger.info("primes prepared.");
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
