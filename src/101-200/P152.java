package com.apkbilisim.pe.p152;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P152 {
    
    private final R      R2     = new R(1, 2);

    private final int    BOUND  = 80;

    private final double D      = Math.log(BOUND);

    private int[]        PRIMES = { 2, 3, 5, 7, 13 };

    int                  count  = 0;

    public static void main(String[] args) {

        logger.info("started.");

        P152 problem = new P152();
        try {
            problem.start();
        } catch (Exception e) {
            logger.error("", e);
        }


        logger.info("finished.");
    }
    
    private void start() throws Exception {

        int[] powers = new int[PRIMES.length];
        int lcm = 1;
        
        Set<Integer> factors = factors(powers, lcm, 0);
        List<Integer> list = new ArrayList<>(factors);
        Collections.sort(list);
        list.remove(0);

        logger.info(list.toString());
        logger.info("size: " + list.size());
        
        search(list);
        
        logger.info("count: " + count);
        
    }
    
    private void search(List<Integer> numbers) throws Exception {
        
        PrintWriter pw = new PrintWriter("P152.txt");
        
        long[] arr = new long[numbers.size()];
        for(int i = 0; i < arr.length; i++) {
            arr[i] = (long) numbers.get(i);
        }
        
        List<Long> list = new ArrayList<>();
        R r = R2;
        int index = 0;

        out:
        while (true) {
            
//            logger.info(list.toString());

            while (r.compareTo(R.ZERO) >= 0 && index < arr.length) {
                r = r.subtract(new R(1, arr[index]*arr[index]));
                list.add(arr[index]);
                index++;
                
                if (r.compareTo(R.ZERO) == 0) {
                    count++;
                    logger.info(list.toString());
                    pw.println(list.toString());
                    pw.flush();
                }
            }

            int size = list.size();
            int lastIndex = numbers.size() - 1;
            for (int i = size - 1; i >= 0; i--) {
                long value = list.get(i);
                
                if(value == numbers.get(lastIndex)) {
                    list.remove(i);
                    lastIndex--;
                    r = r.add(new R(1, value*value));
                    
                } else {
                    break;
                }
            }
            
            if(list.isEmpty()) {
                break out;
            }
            
            lastIndex = list.size() - 1;
            long value = list.get(lastIndex);
            list.remove(lastIndex);
            r = r.add(new R(1, value*value));
            index = numbers.indexOf((int) value) + 1;
        }
        
        pw.close();
    }
    
    private Set<Integer> factors(int[] powers, int lcm, int index) {
        
        Set<Integer> set = new HashSet<>();
        
        if(index == powers.length) {
            return set;
        }
        
        final int max = (int) (D/Math.log(PRIMES[index]));
        
        for(int power = 0; power <= max; power++) {
            int n = (int) Math.pow(PRIMES[index], power);
            int factor = lcm*n;
            
            if(factor > BOUND) {
                return set;
            }
            
            set.add(factor);
            set.addAll(factors(powers, factor, index + 1));
        }
        
        return set;
    }
}

class R implements Comparable<R> {

    public static final R ZERO = new R(BigInteger.ZERO, BigInteger.ONE);

    public BigInteger     a;

    public BigInteger     b;

    public R(BigInteger a, BigInteger b) {
        this.a = a;
        this.b = b;
        simplify();
    }

    public R(long a, long b) {
        this.a = new BigInteger("" + a);
        this.b = new BigInteger("" + b);
        simplify();
    }

    private void simplify() {

        if (a.compareTo(BigInteger.ZERO) == 0 || b.compareTo(BigInteger.ZERO) == 0) {
            return;
        }

        BigInteger gcd = a.gcd(b);
        a = a.divide(gcd);
        b = b.divide(gcd);
    }

    public R negate() {
        return new R(a.negate(), b);
    }

    public R add(R r) {
        r.simplify();

        BigInteger num = a.multiply(r.b).add(r.a.multiply(b));
        BigInteger denum = b.multiply(r.b);
        return new R(num, denum);
    }

    public R subtract(R r) {
        return add(r.negate());
    }

    @Override
    public String toString() {
        return a + "/" + b;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof R)) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        R r = (R) obj;
        return compareTo(r) == 0;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    public int compareTo(R o) {
        simplify();
        o.simplify();

        BigInteger gcd = b.gcd(o.b);
        BigInteger lcm = b.multiply(o.b).divide(gcd);

        BigInteger num = a.multiply(lcm).divide(b);
        BigInteger onum = o.a.multiply(lcm).divide(o.b);

        return num.compareTo(onum);
    }
}







