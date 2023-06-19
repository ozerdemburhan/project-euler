package com.apkbilisim.pe.p177;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P177 {
	
	private final double TOLERANCE = 0.000000001d;
	
	private final int[][] cyclics = new int[][] {
		{0, 1, 2, 3, 4, 5, 6, 7},
		{2, 3, 4, 5, 6, 7, 0, 1},
		{4, 5, 6, 7, 0, 1, 2, 3},
		{6, 7, 0, 1, 2, 3, 4, 5},
		{7, 6, 5, 4, 3, 2, 1, 0},
		{5, 4, 3, 2, 1, 0, 7, 6},
		{3, 2, 1, 0, 7, 6, 5, 4},
		{1, 0, 7, 6, 5, 4, 3, 2},
	};
	
	private final double P =  1.0;
	
	public static void main(String[] args) {
		
		logger.info("started.");
		
		P177 p = new P177();
		p.start();
	}
	
	private void start() {
		
		List<int[]> ts = new ArrayList<>(100);
		Set<String> ps = new HashSet<>();
		Map<String, List<int[]>> map = new HashMap<>();
		
		for(int A = 1; A <= 178; A++) {
			for(int B = 2; B <= 178; B++) {

				int C = 180 - (A + B);
				
				if(C > 0) {
					ts.add(new int[] {A, B, C});
				}
			}
		}
		
		logger.info("ts: " + ts.size());
		
		long count = 0;
		
		for(int i = 0; i < ts.size(); i++) {
			int[] t1 = ts.get(i);
			
			for(int j = 0; j < ts.size(); j++) {
				int[] t2 = ts.get(j);
				
				boolean check1 = (t2[0] < t1[1]) && (t2[1] > t1[2]);
				
				if(!check1) {
					continue;
				}
				
				check1 &= (t1[0] + t1[1] - t2[0]) == (t2[1] - t1[2] + t2[2]);
				
				if(!check1) {
					continue;
				}
				
				int[] angles = calculateAngles(t1, t2);
				
				if(angles == null) {
					continue;
				}
				
				int cangle = angles[0];
				int pangle = angles[1];
				
				int[] q = new int[] { t1[0], t1[1] - t2[0], t2[0], t1[2], t2[1] - t1[2], t2[2], cangle, pangle };
				
				if(appendQuadrilateral(ps, q)) {
					count++;
					
					int[] title = new int[] { t1[0] + pangle, t1[1], t2[1], t2[2] + cangle };
					Arrays.sort(title);
					String key = toString(title);
					
					if(!map.containsKey(key)) {
						map.put(key, new ArrayList<int[]>());
					}
					map.get(key).add(q);
				}
			}
		}
		
		printToFile(map);
		
		logger.info("count: " + count);
		
	}
	
	private double sin(int angle) {
		return Math.sin(Math.toRadians(angle));
	}
	
	private double cos(int angle) {
		return Math.cos(Math.toRadians(angle));
	}
	
	private double sinDiv(int num, int denom) {
		return Math.sin(Math.toRadians(num)) / Math.sin(Math.toRadians(denom));
	}
	
	private int[] calculateAngles(int[] t1, int[] t2) {
		
		double c = sinDiv(t1[0], t1[1]) * sinDiv(t2[0], t2[2]);
		int angle = t2[1] - t1[2];
		
		double atan2 = Math.atan2(c * sin(angle), P - c * cos(angle));
		double dpangle = Math.toDegrees(atan2);
				
		int lbound = (int) Math.floor(dpangle);
		int ubound = (int) Math.ceil(dpangle);
		int pangle = lbound;
		
		boolean checkl = Math.abs(dpangle - lbound) < TOLERANCE;
		boolean checku = Math.abs(dpangle - ubound) < TOLERANCE;
		
		if(checku) {
			pangle = ubound;
		}
		
		if(checkl || checku) {
			int cangle = t2[0] + t1[2] - pangle;
			return new int[] { cangle, pangle };
		}
		
		return null;		
	}
	
	private void printToFile(Map<String, List<int[]>> map) {
		File f = new File("out.txt");
		
		if(f.exists()) {
			f.delete();
		}
		
		try {
			PrintWriter pw = new PrintWriter(f);
			
			for(String key : map.keySet()) {
				for(int[] q : map.get(key)) {
					String line = key + "\t" + toString(q);
					pw.println(line);
				}
			}
			
			pw.close();
			
		} catch (Exception e) {
			logger.error("", e);
		}
		
	}
	
	private String toString(int[] arr) {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < arr.length; i++) {
			if(i > 0) {
				sb.append("-");
			}
			
			sb.append(arr[i]);
		}
		
		return sb.toString();
	}
	
	private boolean appendQuadrilateral(Set<String> ps, int[] q) {
		
		int[][] similars = new int[q.length * 2][q.length];
		
		for(int i = 0; i < cyclics.length; i++) {
			for(int j = 0; j < cyclics[0].length; j++) {
				similars[i][j] = q[cyclics[i][j]];
			}
		}
		
		for(int[] sim : similars) {
			String s = toString(sim);
			
			if(ps.contains(s)) {
				return false;
			}			
		}
		
		ps.add(toString(q));
		return true;
	}
}



