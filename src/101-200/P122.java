package com.apkbilisim.pe.p122;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P122 {
    
    public static void main(String[] args) {

        logger.info("started.");

        P122 problem = new P122();
        problem.start();

        logger.info("finished.");
    }
    
    private void start() {
        
        int count = 0;
        
        for(int k = 1; k <= 200; k++) {
            
            if(k == 191) {
                continue;
            }
            
            Set<Integer> set = new HashSet<>();
            set.add(1);
            List<Set<Integer>> list = new ArrayList<>();
            list.add(set);
            
            count += m(list, k);
        }
        
        logger.info("count: " + count);
    }
    
    private int m(List<Set<Integer>> ms, int k) {
        
        if(k == 1) {
            return 0;
        }

        int min = Integer.MAX_VALUE;
        Set<Integer> mset = null;
        List<Set<Integer>> list = new ArrayList<>();
        
        for(Set<Integer> m : ms) {
            Integer[] arr = m.toArray(new Integer[0]);
            Arrays.sort(arr);
            int last = arr[arr.length - 1];
            
            for(int i = 0; i < arr.length; i++) {
                int el = last + arr[i];
                
                if(el > k) {
                    break;
                }
                
                Set<Integer> temp = new HashSet<>(m);
                temp.add(el);
                
                if(el == k) {
                    if(min > temp.size()) {
                        min = temp.size() - 1;
                        mset = new HashSet<>(temp);
                    }
                } else {
                    list.add(temp);                
                }
            }
        }
        
        if(mset == null) {
            return m(list, k);
            
        } else {
            logger.info(k + ": " + min);            
        }
        
        return min;
    }

}

