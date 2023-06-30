package com.apkbilisim.pe.p108;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P108 {

	private final ObjectMapper	mapper	= new ObjectMapper();

	private int[]				primes	= null;

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
	 
	private String tojson(Object object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (Exception e) {
			logger.error("", e);
		}

		return null;
	}
}
