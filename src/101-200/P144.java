package com.apkbilisim.pe.p144;

import java.math.BigDecimal;
import java.math.MathContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P144 {
    
    private final BigDecimal  _2        = new BigDecimal("2");

    private final BigDecimal  _4        = new BigDecimal("4");

    private final BigDecimal  _100      = new BigDecimal("100");

    private final BigDecimal  TOLERANCE = new BigDecimal("0.0001");

    private final MathContext mc        = new MathContext(20);

    public static void main(String[] args) {

        logger.info("started.");

        P144 problem = new P144();
        problem.start();

        logger.info("finished.");
    }

    private void start() {
        
        BigDecimal[] p0 = {BigDecimal.ZERO, new BigDecimal("10.1")};
        BigDecimal[] p1 = {new BigDecimal("1.4"), new BigDecimal("-9.6")};
        
        long count = 0;
        
        while(true) {
            BigDecimal[] p = p1;
            p1 = reflect(p0, p1);
            p0 = p;
            
            count++;
            
            boolean check = p1[0].abs().compareTo(new BigDecimal("0.01")) <= 0;
            check &= p1[1].compareTo(new BigDecimal("9.9999")) >= 0;
            
            if(check) {
                break;
            }
        }
        
        logger.info("count: " + count);
    }
    
    private BigDecimal[] reflect(BigDecimal[] p1, BigDecimal[] p2) {
        BigDecimal x1 = p1[0], y1 = p1[1], x2 = p2[0], y2 = p2[1];
        
        BigDecimal mv = y2.subtract(y1).divide(x2.subtract(x1), mc);
        BigDecimal mp = y2.divide(_4.multiply(x2), mc);
        BigDecimal tan = mp.subtract(mv).divide(BigDecimal.ONE.add(mp.multiply(mv)), mc);
        BigDecimal m = mp.add(tan).divide(BigDecimal.ONE.subtract(mp.multiply(tan)), mc);
        
        BigDecimal a = m.negate(), b = BigDecimal.ONE;
        BigDecimal c = m.multiply(x2).subtract(y2);
        BigDecimal a2 = a.pow(2), b2 = b.pow(2), c2 = c.pow(2);
        
        BigDecimal d1 = _4.multiply(a2).multiply(c2);
        BigDecimal d2 = _4.multiply(_4.multiply(b2).add(a2)).multiply(_100.multiply(b2).subtract(c2));
        
        BigDecimal d = d1.add(d2).sqrt(mc);
        
        BigDecimal denum = _2.multiply(_4.multiply(b2).add(a2));
        BigDecimal nx1 = _2.negate().multiply(a).multiply(c).add(d).divide(denum, mc);
        BigDecimal nx2 = _2.negate().multiply(a).multiply(c).subtract(d).divide(denum, mc);
        
        BigDecimal x;
        
        if(nx1.subtract(x2).abs().compareTo(TOLERANCE) <= 0) {
            x = nx2;
        } else {
            x = nx1;
        }
        
        BigDecimal y = _100.subtract(_4.multiply(x.pow(2))).sqrt(mc);
        BigDecimal r = a.multiply(x).add(b.multiply(y)).add(c);
        
        if(r.abs().compareTo(TOLERANCE) > 0) {
            y = y.negate();
        }
        
        return new BigDecimal[] {x, y}; 
    }
}
