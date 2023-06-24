package com.apkbilisim.pe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P103 {
	
	private final ObjectMapper mapper = new ObjectMapper();
	
	public static void main(String[] args) {
		
		logger.info("started.");
		
		P103 problem = new P103();
		problem.start();
	}
	
	private void start() {
		
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		
		final int N = 7, UBOUND = 30;
		
		List<int[]> minSumSets = new ArrayList<>();
		
		for(int a1 = N - 1; a1 < UBOUND; a1++) {
			for(int a2 = a1 + 1; a2 <= UBOUND; a2++) {
				
				int[] arr = createSet(a1, a2);
				List<int[]> properSubsets = findProperSubsets(arr, N);
				
				if(!properSubsets.isEmpty()) {
					int[] minSumSet = minSumSet(properSubsets);
					minSumSets.add(minSumSet);
				}
			}
		}
		
		int[] minSumSet = minSumSet(minSumSets);
		
		logger.info(tojson(minSumSet));
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
	
	
	private int[] minSumSet(List<int[]> subsets) {
		
		int minSum = Integer.MAX_VALUE;
		int[] minSumSubset = null;
		
		for(int i = 0; i < subsets.size(); i++) {
			int[] subset = subsets.get(i);
			int sum = sum(subset);
			
			if(minSum > sum) {
				minSum = sum;
				minSumSubset = subset;
			}
		}
		
		return minSumSubset;
	}
	
	private List<int[]> findProperSubsets(int[] arr, final int N) {
		
		List<int[]> properSubsets = new ArrayList<int[]>();
		List<int[]> subsets = subsets(arr, N);
		
		for(int[] set : subsets) {
			if(checkSubset(set)) {
				properSubsets.add(set);
			}
		}
		
		return properSubsets;
	}
	
	private boolean checkSubset(int[] set) {
		
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
	
	private int[] createSet(int a1, int a2) {
		
		int length = a1 + 1;
		
		int[] arr = new int[length];
		
		arr[0] = a1;
		arr[1] = a2;
		
		for(int i = a2 + 1; i < a1 + a2; i++) {
			arr[i - a2 + 1] = i;
		}
		
		return arr;
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
