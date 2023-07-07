package com.apkbilisim.pe.p119;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P119 {
    
    public static void main(String[] args) {
        
        logger.info("started.");

        P119 problem = new P119();
        problem.start();

        logger.info("finished.");
    }

    private void start() {
        
        List<BigInteger> list = new ArrayList<>();
        
        for(BigInteger base = new BigInteger("2"); base.compareTo(new BigInteger("100")) < 0; base = base.add(BigInteger.ONE)) {
            for(int power = 2; power < 100; power++) {
                
                BigInteger n = base.pow(power);
                BigInteger digitSum = new BigInteger("" + digitSum(n));
                if(base.compareTo(digitSum) == 0) {
                    list.add(n);
                }
            }
        }
        
        Collections.sort(list);
        
        logger.info(list.get(29).toString());
        
    }
    
    private int digitSum(BigInteger n) {
        int sum = 0;
        String s = n.toString();
        
        for(int i = 0; i < s.length(); i++) {
            sum += Integer.parseInt(s.substring(i, i + 1));
        }
        
        return sum;
    }
    
    

}
