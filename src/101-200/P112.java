package com.apkbilisim.pe.p112;

import com.apkbilisim.pe.p111.P111;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P112 {
    

    public static void main(String[] args) {

        logger.info("started.");

        P112 problem = new P112();
        problem.start();

        logger.info("finished.");
    }
    
    private void start() {
        
        int bouncy = 0;
        int n = 1;
        
        while(true) {
            if(isBouncy(n)) {
                bouncy++;
            }
            
            if((n % 100 == 0) && (bouncy == n * 99 / 100)) {
                logger.info("%99: " + n);
                break;
            }
            
            n++;
        }
    }
    
    private boolean isBouncy(int n) {
        
        if(n < 100) {
            return false;
        }
        
        String s = String.valueOf(n);
        boolean inc = false, dec = false;
        
        for(int i = 1; i < s.length(); i++) {
            int prev = Integer.parseInt(s.substring(i - 1,  i));            
            int digit = Integer.parseInt(s.substring(i, i + 1));
            
            if(digit > prev) {
                inc = true;
            } else if(digit < prev) {
                dec = true;
            }
            
            if(inc && dec) {
                return true;
            }
        }
        
        return false;
    }

}
