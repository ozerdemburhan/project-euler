package com.apkbilisim.pe.p120;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P120 {
    
    public static void main(String[] args) {
        
        logger.info("started.");

        P120 problem = new P120();
        problem.start();

        logger.info("finished.");
    } 
    
    private void start() {
        
        long sum = 0;
        
        for(int a = 3; a <= 1000; a++) {
            
            int n = (int) (a / 2);
            if(2 * n == a) {
                n--;
            }
            
            sum += 2 * n * a;
        }
        
        logger.info("sum: " + sum);
    }
}
