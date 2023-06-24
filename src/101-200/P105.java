package com.apkbilisim.pe.p105;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P105 {
	
	private final ObjectMapper mapper = new ObjectMapper();
	
	public static void main(String[] args) {
		
		logger.info("started.");
		
		P105 problem = new P105();
		problem.start();
	}
	
	private void start() {
		
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		
		List<int[]> sets = readSets();
		
		logger.info("sets: " + sets.size());
		
		int sum = 0;
		
		for(int[] set : sets) {
			
			if(checkSet(set)) {
				logger.info(tojson(set));
				sum += sum(set);
			}
		}
		
		logger.info("sum: " + sum);
		
	}
	
	private List<int[]> readSets() {
		
		List<int[]> sets = new ArrayList<>();
		
		try {
			
			File f = new File("sets.txt");
			FileInputStream fis = new FileInputStream(f);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader reader = new BufferedReader(isr);
			String line = null;
			
			while((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				int[] set = new int[parts.length];
				
				for(int i = 0; i < parts.length; i++) {
					set[i] = Integer.valueOf(parts[i]);
				}
				
				sets.add(set);
			}
			
		} catch (Exception e) {
			logger.error("", e);
		}
		
		return sets;
		
	}
	
	private boolean checkSet(int[] set) {
		
		int subsetCount = 0;
		Set<Integer> sums = new HashSet<>();
		int prevMaxSum = 0;
		
		for(int n = 1; n <= set.length; n++) {
			List<int[]> subsets = subsets(set, n);
			subsetCount += subsets.size();
			
			for(int[] subset : subsets) {
				sums.add(sum(subset));
			}
			
			if(sums.size() != subsetCount) {
				return false;
			}
			
			int minSum = minSum(subsets);
			if(minSum < prevMaxSum) {
				return false;
			}
			
			prevMaxSum = maxSum(subsets);
		}
		
		return true;
	}
	
	private int sum(int[] set) {
		
		int sum = 0;
		
		for(int value : set) {
			sum += value;
		}
		
		return sum;
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
	
	private int maxSum(List<int[]> subsets) {
		
		int maxSum = Integer.MIN_VALUE;
		
		for(int i = 0; i < subsets.size(); i++) {
			int[] subset = subsets.get(i);
			int sum = sum(subset);
			
			if(maxSum < sum) {
				maxSum = sum;
			}
		}
		
		return maxSum;
	}
	
	
	private int minSum(List<int[]> subsets) {
		
		int minSum = Integer.MAX_VALUE;
		
		for(int i = 0; i < subsets.size(); i++) {
			int[] subset = subsets.get(i);
			int sum = sum(subset);
			
			if(minSum > sum) {
				minSum = sum;
			}
		}
		
		return minSum;
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








