package com.apkbilisim.pe.p109;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P109 {

	private Map<String, Integer>	ST	= new HashMap<>();
	private Map<String, Integer>	D	= new HashMap<>();

	private Map<String, Integer>	P	= new HashMap<>();

	public static void main(String[] args) {

		logger.info("started.");

		P109 problem = new P109();
		problem.start();

		logger.info("finished.");
	}

	private void start() {

		initialize();

		Set<Checkout> set = new HashSet<>();

		for (String c1 : P.keySet()) {
			for (String c2 : P.keySet()) {
				for (String c3 : D.keySet()) {
					Checkout c = new Checkout(c1, c2, c3);
					set.add(c);
				}
			}
		}

		logger.info("" + set.size());

		int count = 0;

		for (Checkout c : set) {
			if (c.point < 100) {
				count++;
			}
		}

		logger.info("count: " + count);
	}

	private void initialize() {

		for (Integer p = 1; p <= 20; p++) {
			String prefix = (p < 10) ? "0" : "";
			ST.put("S" + prefix + p, p);
			D.put("D" + prefix + p, p * 2);
			ST.put("T" + prefix + p, p * 3);
		}

		ST.put("S25", 25);
		D.put("D25", 50);

		P.putAll(ST);
		P.putAll(D);

		P.put("A00", 0);
	}

	private class Checkout implements Comparable<Checkout> {

		private String[]	cs;

		private Integer		point;

		private String		key;

		public Checkout(String... cs) {

			if (cs.length == 1) {
				this.cs = new String[] { "A00", "A00", cs[0] };

			} else if (cs.length == 2) {
				this.cs = new String[] { "A00", cs[0], cs[1] };

			} else {
				this.cs = cs;
			}

			this.point = P.get(this.cs[0]) + P.get(this.cs[1]) + P.get(this.cs[2]);

			String[] first2 = { this.cs[0], this.cs[1] };
			Arrays.sort(first2);

			this.key = first2[0] + "-" + first2[1] + "-" + this.cs[2];
		}

		@Override
		public int compareTo(Checkout o) {
			return key.compareTo(o.key);
		}

		@Override
		public int hashCode() {
			return key.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if ((obj == null) || !(obj instanceof Checkout)) {
				return false;
			}

			Checkout c = (Checkout) obj;
			return key.equals(c.key);
		}
	}

}
