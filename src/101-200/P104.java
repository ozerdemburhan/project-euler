package com.apkbilisim.pe.p104;

import java.math.BigInteger;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P104 {
	
	private final ObjectMapper	mapper	= new ObjectMapper();

	private final String		DIGITS	= "123456789";

	private final BigInteger	MOD		= new BigInteger("1000000000");

	public static void main(String[] args) {
		
		logger.info("started.");
		
		P104 problem = new P104();
		problem.start();
	}
	
	private void start() {
		
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		
		BigInteger f1 = BigInteger.ONE;
		BigInteger f2 = BigInteger.ONE;
		int k = 2;
		BigInteger f = BigInteger.ZERO;
		
		boolean check = false;
		
		while(!check) {
			f = f1.add(f2);
			f1 = f2;
			f2 = f;
			
			k++;
			
			if(checkLast9Digits(f.remainder(MOD).toString())) {
				if(checkFirst9Digits(f.toString())) {
					logger.info(f.toString());
					logger.info("k: " + k + " " + f.toString().length());
					break;
				}				
			}
		}
	}
	
	private boolean checkFirst9Digits(String s) {
		
		if(s.length() < 9) {
			return false;
		}
		
		String left = s.substring(0, 9);
		char[] oleft = left.toCharArray();
		Arrays.sort(oleft);
		
		String ols = new String(oleft);
		
		if(!DIGITS.equals(ols)) {
			return false;
		}
		
		return true;
	}
	
	private boolean checkLast9Digits(String s) {
		
		if(s.length() < 9) {
			return false;
		}
		
		String right = s.substring(s.length() - 9);
		char[] oright = right.toCharArray();
		Arrays.sort(oright);
		
		String ors = new String(oright);
		
		if(!DIGITS.equals(ors)) {
			return false;
		}
		
		return true;
	}
	
	
	private String tojson(Object object) {
		String s = null;
		
		try {
			s = mapper.writeValueAsString(object);
		} catch (Exception e) {
			logger.error("", e);
		}
		
		return s;
	}	

}








