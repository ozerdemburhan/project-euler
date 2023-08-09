package com.apkbilisim.pe.p145;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P145 {
    
    public static void main(String[] args) {

        logger.info("started.");

        P145 problem = new P145();
        problem.start();

        logger.info("finished.");
    }
    
    private void start() {
        
        final long BOUND = 1_000_000_000L;
        
        long count = 0;
        
        for(long n = 1; n < BOUND; n++) {            
            StringBuilder sn = new StringBuilder("" + n);
            String sr = sn.reverse().toString();
            
            if(sr.startsWith("0")) {
                continue;
            }
            
            long r = Long.parseLong(sr);
            
            long sum = n + r;
            
            String s = new String("" + sum);
            
            boolean ok = true;
            
            for(int i = 0; i < s.length(); i++) {
                int digit = Integer.parseInt(s.substring(i, i + 1));
                
                if(digit % 2 == 0) {
                    ok = false;
                    break;
                }
            }
            
            if(ok) {
                count++;
            }
        }
        
        logger.info("count: " + count);
        
    }
}
