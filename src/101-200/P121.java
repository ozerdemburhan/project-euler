package com.apkbilisim.pe.p121;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P121 {
    
    public static void main(String[] args) {

        logger.info("started.");

        P121 problem = new P121();
        problem.start();

        logger.info("finished.");
    }

    private void start() {
        
        Map<String, Ratio> map = new HashMap<>();
        for(int red = 1; red <= 15; red++) {
            map = play(map, red);
        }
        
        Ratio result = countMoreBlues(map);
        logger.info("result: " + result.toString());
        
        BigInteger b = result.getB().divide(result.getA());
        logger.info(b.toString());
    }
    
    private Ratio countMoreBlues(Map<String, Ratio> map) {
        
        Ratio r = Ratio.ZERO;
        
        for(String key : map.keySet()) {
            int blues = 0;
            
            for(int i = 0; i < key.length(); i++) {
                if(key.charAt(i) == 'B') {
                    blues++;
                }
            }
            
            if(blues > key.length() - blues) {
                r = r.add(map.get(key));
            }
        }
        
        return r;
    }
    
    private Map<String, Ratio> play(Map<String, Ratio> map, int red) {
        
        Map<String, Ratio> result = new HashMap<>();
        result.put("B", new Ratio(1, red + 1));
        result.put("R", new Ratio(red, red + 1));            
        return cross(result, map);         
    }
    
    private Map<String, Ratio> cross(Map<String, Ratio> m1, Map<String, Ratio> m2) {
        
        Map<String, Ratio> map = new HashMap<>();
        
        if(m2.isEmpty()) {
            map = new HashMap<>(m1);
            return map;
        }
        
        for(String key1 : m1.keySet()) {
            for(String key2: m2.keySet()) {
                Ratio value = m1.get(key1).mul(m2.get(key2));
                map.put(key1 + key2, value);
            }
        }
        
        return map;
    }
}

class Ratio {
    
    private BigInteger        a;

    private BigInteger        b;

    public static final Ratio ZERO = new Ratio(0, 1);

    public static final Ratio ONE  = new Ratio(1, 1);

    public Ratio(long a, long b) {
        this.a = new BigInteger("" + a);
        this.b = new BigInteger("" + b);
        simplify();
    }
    
    public Ratio(BigInteger a, BigInteger b) {
        this.a = a;
        this.b = b;
        simplify();
    }
    
    @Override
    public String toString() {
        return a.toString() + "/" + b.toString();
    }
    
    public Ratio mul(Ratio r) {
        
        BigInteger q = a.multiply(r.a);
        BigInteger d = b.multiply(r.b);
        
        return new Ratio(q, d);
    }
    
    public Ratio add(Ratio r) {
        
        BigInteger q = a.multiply(r.b).add(b.multiply(r.a));
        BigInteger d = b.multiply(r.b);
        
        return new Ratio(q, d);
        
    }
    
    private void simplify() {
        BigInteger gcd = a.gcd(b);
        
        if(gcd.compareTo(BigInteger.ONE) > 0) {
            a = a.divide(gcd);
            b = b.divide(gcd);
        }
    }
    
    public BigInteger getA() {
        return a;
    }
    
    public BigInteger getB() {
        return b;
    }
}
