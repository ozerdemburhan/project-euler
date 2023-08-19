package com.apkbilisim.pe.p151;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P151 {
    
    private final MathContext MC      = new MathContext(10);
    private BigDecimal        totalPr = BigDecimal.ZERO;

    public static void main(String[] args) {

        logger.info("started.");

        P151 problem = new P151();
        problem.start();

        logger.info("finished.");
    }

    private void start() {

        int[] env = { 1, 1, 1, 1 };
        
        batch(env, BigDecimal.ONE);
        
        logger.info("totalPr: " + totalPr.round(new MathContext(6)));

    }
    
    private void batch(int[] env, BigDecimal p) {
        
        int sheetCount = 0;
        for(int i = 0; i < env.length; i++) {
            sheetCount += env[i];
        }
        
        if(sheetCount == 1 && env[3] == 0) {
            totalPr = totalPr.add(p);
        }
        
        BigDecimal sheets = new BigDecimal(sheetCount);
        
        for(int i = 0; i < env.length; i++) {
            if(env[i] == 0) {
                continue;
            }

            BigDecimal pr = p.multiply(new BigDecimal(env[i])).divide(sheets, MC);
            int[] copy = Arrays.copyOf(env, env.length);
            
            copy[i]--;
            for(int j = i + 1; j < copy.length; j++) {
                copy[j]++;
            }
            
            batch(copy, pr);
        }
    }

}
