package com.apkbilisim.pe.p108;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P108 {

	public static void main(String[] args) {
		
		logger.info("started.");
		
		P108 problem = new P108();
		problem.start();
	}
	
	private void start() {
		
		long n = 1260;
		
		while(true) {
			
			int count = 0;
			
			for(int k = 1; k <= n; k++) {
				long x = n + k;
				
				if(n * x % k == 0) {
					count++;
				}
			}
			
			if(count > 1000) {
				logger.info("n: " + n + ", count: " + count);
				break;
			}
			
			n++;
		}
	}
}
