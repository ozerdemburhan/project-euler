package com.apkbilisim.pe.p106;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P106 {
	
	private final ObjectMapper mapper = new ObjectMapper();
	
	public static void main(String[] args) {
		
		logger.info("started.");
		
		P106 problem = new P106();
		problem.start();		
	}
	
	private void start() {
		
//		int[] set = new int[] {20,31,38,39,40,42,45};
//		int[] set = new int[] {4,5,6,7};
		int[] set = new int[] {11,12,13,14,15,16,17,18,19,20,21,22};
//		int[] set = new int[] {591,887,1035,1115,1167,1175,1182,1183,1184,1186,1197,1219};
//		int[] set = new int[] {4,5,6,7};
		checkSet(set);
	}
	
	private void checkSet(int[] set) {
		
		int total = 0;
		int count = 0;
		
		List<int[]> subsets = subsets(set);
		
		for(int i = 0; i < subsets.size() - 1; i++) {
			int[] seti = subsets.get(i);
			int mini = seti[0];
			int maxi = seti[seti.length - 1];
			
			for(int j = i + 1; j < subsets.size(); j++) {
				int[] setj = subsets.get(j);
				int minj = setj[0];
				int maxj = setj[setj.length - 1];
				
				if(hasCommon(seti, setj)) {
					continue;
				}
				
				total++;
				
				if(seti.length != setj.length) {
					continue;
				}
				
				
				if(noNeedTest(seti, setj)) {
					continue;
				}
				
				logger.info(tojson(seti) + "-" + tojson(setj) + (sum(seti) == sum(setj) ? "+" : ""));
				count++;
				
			}
		}
		
		logger.info("count: " + count);
		logger.info("total: " + total);
	}
	
	private boolean noNeedTest(int[] set1, int[] set2) {
		
		boolean lt = true, gt = true;
		
		for(int i = 0; i < set1.length; i++) {
			lt &= set1[i] < set2[i];
			gt &= set1[i] > set2[i];
		}
		
		return lt || gt;
	}
	
	private boolean hasCommon(int[] arr1, int[] arr2) {
		
		for(int i : arr1) {
			for(int j : arr2) {
				if(j == i) {
					return true;
				}
				
				if(j > i) {
					break;
				}
			}
		}
		
		return false;
	}	
	
	private int sum(int[] set) {
		
		int sum = 0;
		
		for(int value : set) {
			sum += value;
		}
		
		return sum;
	}
	
	private List<int[]> subsets(int[] set) {
		
		List<int[]> subsets = new ArrayList<>();
		
		for(int n = 1; n <= set.length; n++) {
			subsets.addAll(subsets(set, n));
		}
		
		return subsets;
	}
	
	private List<int[]> subsets(int[] arr, int n) {
		
		List<int[]> subsets = new ArrayList<>();
		
		if(n == 0) {
			subsets.add(new int[0]);
			return subsets;
		}
		
		for(int i = 0; i <= arr.length - n; i++) {
			int[] temp = new int[arr.length - i - 1];
			System.arraycopy(arr, i + 1, temp, 0, temp.length);
			
			List<int[]> ss = subsets(temp, n - 1);
			subsets.addAll(merge(arr[i], ss));
		}
		
		return subsets;
	}
	
	private List<int[]> merge(int value, List<int[]> ss) {
		
		List<int[]> subsets = new ArrayList<>(ss.size());
		
		for(int[] arr : ss) {
			int[] temp = new int[arr.length + 1];
			temp[0] = value;
			System.arraycopy(arr, 0, temp, 1, arr.length);
			subsets.add(temp);
		}
		
		return subsets;
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
