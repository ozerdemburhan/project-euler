package com.apkbilisim.pe.p84;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P84 {

	private final ObjectMapper										mapper	= new ObjectMapper();

	private final int												N		= 4;

	private final double											RN		= 1.0 / (N * N);

	private final double											R16		= 1.0 / 16;

	private final String[]											ARENA	= { "GO", "A1", "CC1", "A2", "T1", "R1", "B1", "CH1", "B2", "B3", "JAIL", "C1",
		"U1", "C2", "C3", "R2", "D1", "CC2", "D2", "D3", "FP", "E1", "CH2", "E2", "E3", "R3", "F1", "F2", "U2", "F3", "G2J", "G1", "G2", "CC3", "G3", "R4",
		"CH3", "H1", "T2", "H2" };

	private final int												GO		= 0;
	private final int												JAIL	= 10;
	private final int												C1		= 11;
	private final int												E3		= 24;
	private final int												H2		= 39;
	private final int												R1		= 5;
	private final int												G2J		= 30;

	private final Map<Integer, Map<String, Map<String, double[]>>>	PS		= new HashMap<>();

	private Map<String, double[]>									ps		= new HashMap<>();

	public static void main(String[] args) {
		
		P84 problem = new P84();
		problem.start();

	}
	
	private void start() {
		
		logger.info("started.");
		
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		
		prepareProbabilities();
		
		play(1000000);
		
		summarize();
	}
	
	private void summarize() {
		
		double[] summary = new double[ARENA.length];
		Arrays.fill(summary, 0.0);
		
		for(String nextDoubles : ps.keySet()) {
			double[] src = ps.get(nextDoubles);
			
			for(int i = 0; i < ARENA.length; i++) {
				summary[i] += src[i];
			}
		}
		
		List<Integer> maxIndexes = new ArrayList<>();
		
		for(int i = 0; i < 3; i++) {
			double max = Double.MIN_VALUE;
			int maxIndex = -1;
			
			for(int j = 0; j < ARENA.length; j++) {
				if(maxIndexes.indexOf(j) != -1) {
					continue;
				}
				
				if(max < summary[j]) {
					maxIndex = j;
					max = summary[j];
				}
			}
			
			maxIndexes.add(maxIndex);			
		}
		
		for(Integer index : maxIndexes) {
			logger.info(index + " " + ARENA[index] + ": " + summary[index]);
		}		
	}
	
	private void play(int count) {
		
		Map<String, double[]> lps = new HashMap<>();
		
		while (count-- > 0) {
			
			if(ps.isEmpty()) {
				merge(GO, "FF", 1.0, ps);
				continue;
			}
			
			for(String nextDoubles : ps.keySet()) {
				double[] src = ps.get(nextDoubles);
				
				for(int pos = 0; pos < ARENA.length; pos++) {
 					double pr = src[pos];
					merge(pos, nextDoubles, pr, lps);
				}
			}
			
			mergeps(lps);
			normalizeps();
		}
	}
	
	private void mergeps(Map<String, double[]> lps) {
		
		for(String nextDoubles : lps.keySet()) {
			double[] src = lps.get(nextDoubles);
			
			if(!ps.containsKey(nextDoubles)) {
				double[] dest = new double[ARENA.length];
				System.arraycopy(src, 0, dest, 0, ARENA.length);
				ps.put(nextDoubles, dest);
				
			} else {
				double[] dest = ps.get(nextDoubles);
				
				for(int i = 0; i < ARENA.length; i++) {
					dest[i] += src[i];
				}
			}
		}
	}
	
	
	private void merge(int pos, String doubles, double pr, Map<String, double[]> lps) {
		
		Map<String, double[]> map = PS.get(pos).get(doubles);
		
		for(String nextDoubles : map.keySet()) {
			double[] src = map.get(nextDoubles); 
			
			if(!lps.containsKey(nextDoubles)) {
				double[] dest = new double[ARENA.length];
				System.arraycopy(src, 0, dest, 0, ARENA.length);
				lps.put(nextDoubles, dest);
				
			} else {
				double[] dest = lps.get(nextDoubles);
				
				for(int i = 0; i < ARENA.length; i++) {
					dest[i] += src[i] * pr;
				}
			}
		}
	}
	
	private void normalizeps() {
		
		double total = 0.0;
		
		for(String nextDoubles : ps.keySet()) {
			for(double pr : ps.get(nextDoubles)) {
				total += pr;
			}
		}
		
		for(String nextDoubles : ps.keySet()) {
			double[] dest = ps.get(nextDoubles);
			
			for(int i = 0; i < ARENA.length; i++) {
				dest[i] /= total;
			}
		}
	}
	
	
	
	private void prepareProbabilities() {
		
		String[] doublesArr = {"FF", "FT", "TT", "TF"};

		for(int from = 0; from < ARENA.length; from++) {
			
			for(String doubles : doublesArr) {
				prepareProbabilities(from, doubles);
			}
		}
	}
	
	private void prepareProbabilities(int from, String doubles) {
		
		for(int d1 = 1; d1 <= N; d1++) {
			
			for(int d2 = 1; d2 <= N; d2++) {

				StringBuilder sb = new StringBuilder(doubles);
				sb.append(d1 == d2 ? "T" : "F");
				
				if(sb.indexOf("F") == -1) {
					sb.deleteCharAt(0);
					addVisit(from, JAIL, doubles, "FF", RN);
					continue;
				}
				sb.deleteCharAt(0);
				
				int pos = (from + d1 + d2) % ARENA.length;
				prepareProbabilities(from, pos, doubles, sb.toString(), RN);
			}
		}
	}
	
	private void prepareProbabilities(int from, int pos, String doubles, String nextDoubles, double pmain) {
		
		String square = ARENA[pos];
		
		if(square.startsWith("CC")) {
			double p = pmain * R16;
			addVisit(from, GO, doubles, nextDoubles, p);
			addVisit(from, JAIL, doubles, nextDoubles, p);
			
			p = pmain * R16 * 14;
			addVisit(from, pos, doubles, nextDoubles, p);
			
		} else if(square.startsWith("CH")) {
			double p = pmain * R16;
			
			addVisit(from, GO, doubles, nextDoubles, p);
			addVisit(from, JAIL, doubles, nextDoubles, p);
			addVisit(from, C1, doubles, nextDoubles, p);
			addVisit(from, E3, doubles, nextDoubles, p);
			addVisit(from, H2, doubles, nextDoubles, p);
			addVisit(from, R1, doubles, nextDoubles, p);
			
			int nextr = pos;
			p = pmain * R16 * 2;
			while(!ARENA[++nextr % ARENA.length].startsWith("R"));
			nextr %= ARENA.length;
			prepareProbabilities(from, nextr, doubles, nextDoubles, p);
			
			int nextu = pos;
			p = pmain * R16;
			while(!ARENA[++nextu % ARENA.length].startsWith("U"));
			nextu %= ARENA.length;
			prepareProbabilities(from, nextu, doubles, nextDoubles, p);
			
			int bck3 = (pos - 3) % ARENA.length;
			if(bck3 < 0) bck3 += ARENA.length;
			prepareProbabilities(from, bck3, doubles, nextDoubles, p);

			p = pmain * R16 * 6;
			addVisit(from, pos, doubles, nextDoubles, p);
			
		} else if(pos == G2J) {
			addVisit(from, JAIL, doubles, nextDoubles, pmain);
			
		} else {
			addVisit(from, pos, doubles, nextDoubles, pmain);
		}
	}
	
	private void addVisit(int from, int pos, String doubles, String nextDoubles, double pr) {
		
		if(!PS.containsKey(from)) {
			PS.put(from, new HashMap<>());
		}
		
		Map<String, Map<String, double[]>> posVisit = PS.get(from);
		
		if(!posVisit.containsKey(doubles)) {
			posVisit.put(doubles, new HashMap<>());
		}
		
		Map<String, double[]> posDoublesVisit = posVisit.get(doubles);
		
		if(!posDoublesVisit.containsKey(nextDoubles)) {
			double[] arr = new double[ARENA.length];
			Arrays.fill(arr, 0.0);
			posDoublesVisit.put(nextDoubles, arr);
		}
		
		double[] arr = posDoublesVisit.get(nextDoubles);
		arr[pos] += pr;
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
		







