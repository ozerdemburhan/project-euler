
package com.apkbilisim.pe.p107;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P107 {

	public static void main(String[] args) {

		P107 problem = new P107();
		problem.start();

	}

	private void start() {

		Integer[][] graph = initialize("network.txt");

		List<Edge> edges = initializeEdges(graph);
		Set<Edge> T = new HashSet<>();

		logger.info("edges: " + edges.size());

		while (T.size() < graph.length - 2) {
			for (Edge edge : edges) {
				if (!isCyclic(T, edge)) {
					T.add(edge);
				}
			}
		}
		
		
		int total = 0;
		for(Edge e : edges) {
			total += e.getWeight();
		}
		
		logger.info("total: " + total);

		int minTotal = 0;

		for (Edge e : T) {
			minTotal += e.getWeight();
		}

		logger.info("min total:" + minTotal);
		logger.info("savings: " + (total - minTotal));
	}

	private boolean isCyclic(Set<Edge> T, Edge edge) {

		List<Edge> list = new ArrayList<>(T);

		if (list.size() < 2) {
			return false;
		}

		while (true) {

			int size = list.size();

			for (int i = 0; i < size - 1; i++) {
				Edge e1 = list.get(i);

				for (int j = i + 1; j < size; j++) {
					Edge e2 = list.get(j);
					Edge concat = concat(e1, e2);

					if (concat == null) {
						continue;
					}

					if (!list.contains(concat)) {
						list.add(concat);
					}
				}
			}

			if (size == list.size()) {
				break;
			}

			size = list.size();
		}

		for (Edge e : list) {

			boolean check = (e.getV() == edge.getV()) && (e.getW() == edge.getW());
			if (check) {
				return true;
			}
		}

		return false;
	}

	private Edge concat(Edge e1, Edge e2) {

		if (e1.getV() == e2.getV()) {
			return new Edge(e1.getW(), e2.getW(), 0);

		} else if (e1.getV() == e2.getW()) {
			return new Edge(e1.getW(), e2.getV(), 0);

		} else if (e1.getW() == e2.getV()) {
			return new Edge(e1.getV(), e2.getW(), 0);

		} else if (e1.getW() == e2.getW()) {
			return new Edge(e1.getV(), e2.getV(), 0);
		}

		return null;
	}

	private List<Edge> initializeEdges(Integer[][] graph) {

		Set<Edge> edges = new HashSet<>();

		for (Integer v = 0; v < graph.length; v++) {
			for (Integer w = 0; w < graph[0].length; w++) {
				Integer weight = graph[v][w];
				if (weight == null) {
					continue;
				}

				Edge edge = new Edge(v, w, weight);
				edges.add(edge);
			}
		}

		List<Edge> list = new ArrayList<>(edges);

		Comparator<Edge> c = (e1, e2) -> {
			return Integer.compare(e1.getWeight(), e2.getWeight());
		};

		Collections.sort(list, c);

		return list;

	}

	private Integer[][] initialize(String fname) {

		Integer[][] graph = null;

		BufferedReader reader = null;

		try {

			FileInputStream fis = new FileInputStream(fname);
			InputStreamReader isr = new InputStreamReader(fis);
			reader = new BufferedReader(isr);
			String line = null;
			Integer v = 0;

			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");

				if (graph == null) {
					graph = new Integer[parts.length][parts.length];
				}

				for (Integer w = 0; w < parts.length; w++) {
					if (parts[w].trim().equals("-")) {
						graph[v][w] = null;
					} else {
						Integer weight = Integer.parseInt(parts[w].trim());
						graph[v][w] = weight;
					}
				}

				v++;
			}
		} catch (Exception e) {
			logger.error("", e);

		} finally {
			try {
				reader.close();
			} catch (Exception e) {
				logger.error("", e);
			}
		}

		return graph;
	}

}

@Getter
@Setter
class Edge {
	
	private Integer	v;

	private Integer	w;

	private int		weight;

	public Edge(Integer v, Integer w, Integer weight) {
		
		if(v < w) {
			Integer temp = v;
			v = w;
			w = temp;
		}
		
		this.v = v;
		this.w = w;
		this.weight = weight;
	}
	
	@Override
	public boolean equals(Object obj) {
		if((obj == null) || !(obj instanceof Edge)) {
			return false;
		}
		
		Edge edge = (Edge) obj;
		boolean equals = (v == edge.v && w == edge.w);
		equals |= (v == edge.w && w == edge.v);
		
		return equals;
	}
	
	@Override
	public int hashCode() {
		return (v + "-" + w).hashCode();
	}
	
	@Override
	public String toString() {
		return v + "-" + w + ": " + weight;
	}
	
}
