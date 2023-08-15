package com.apkbilisim.pe.p147;

import static java.lang.Math.abs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class P147 {

    private List<Point> points = new ArrayList<>();

    public static void main(String[] args) {

        logger.info("started.");

        P147 problem = new P147();
        problem.start();

        logger.info("finished.");
    }

    private void start() {

        final int COLS = 47, ROWS = 43;
//        final int COLS = 3, ROWS = 2;
        
        long sum = 0;
        
        Map<String, Long> counter = new HashMap<>();
        
        for(int cols = 1; cols <= COLS; cols++) {
            for(int rows = 1; rows <= ROWS; rows++) {
                String key = (cols > rows) ? ("" + cols + "-" + rows) : ("" + rows + "-" + cols);
                
                if(counter.containsKey(key)) {
                    sum += counter.get(key);
                    continue;
                }
                
                generatePoints(cols, rows);
                long count = countRectangles(cols, rows);
                
                counter.put(key, count);
                
                logger.info("(" + cols + "," + rows + "): " + count);
                sum += count;
            }
        }
        
        logger.info("sum: " + sum);
    }

    private long countRectangles(int cols, int rows) {

        Set<String> set = new HashSet<>();

        for (Point p1 : points) {

            List<Point> corners = new ArrayList<>();

            for (Point p : points) {
                if (p == p1) {
                    continue;
                }

                boolean check = (p.x % 2 == 0) && (p1.x == p.x || p1.y == p.y);
                check |= abs(p1.x - p.x) == abs(p1.y - p.y);
                if (check) {
                    corners.add(p);
                }
            }

            for (Point p2 : corners) {
                
                int dx = p2.x - p1.x;
                int dy = p2.y - p1.y;

                if (dx == 0) { // vertical edge
                    for (int x = 0; x <= cols * 2; x += 2) {
                        if (x == p2.x) {
                            continue;
                        }

                        Point p3 = new Point(x, p1.y);
                        Point p4 = new Point(x, p2.y);

                        Rectangle r = new Rectangle(p1, p2, p3, p4);
                        set.add(r.toString());
                    }

                } else if (dy == 0) { // horizontal edge
                    for (int y = 0; y <= rows * 2; y += 2) {
                        if (y == p2.y) {
                            continue;
                        }

                        Point p3 = new Point(p1.x, y);
                        Point p4 = new Point(p2.x, y);

                        Rectangle r = new Rectangle(p1, p2, p3, p4);
                        set.add(r.toString());
                    }
                } else if (dx * dy > 0) { // left diagonal
                    int x1 = p1.x, y1 = p1.y, x2 = p2.x, y2 = p2.y;
                    
                    while(--x1 >= 0 && --x2 >= 0 && ++y1 <= rows*2 && ++y2 <= rows*2) {
                        Point p3 = new Point(x1, y1);
                        Point p4 = new Point(x2, y2);
                        
                        Rectangle r = new Rectangle(p1, p2, p3, p4);
                        set.add(r.toString());
                    }
                    
                    x1 = p1.x; y1 = p1.y; x2 = p2.x; y2 = p2.y;
                    
                    while(++x1 <= cols*2 && ++x2 <= cols*2 && --y1 >= 0 && --y2 >= 0) {
                        Point p3 = new Point(x1, y1);
                        Point p4 = new Point(x2, y2);
                        
                        Rectangle r = new Rectangle(p1, p2, p3, p4);
                        set.add(r.toString());
                    }

                } else if (dx * dy < 0) { // right diagonal
                    
                    int x1 = p1.x, y1 = p1.y, x2 = p2.x, y2 = p2.y;
                    
                    while(++x1 <= cols*2 && ++x2 <= cols*2 && ++y1 <= rows*2 && ++y2 <= rows*2) {
                        Point p3 = new Point(x1, y1);
                        Point p4 = new Point(x2, y2);
                        
                        Rectangle r = new Rectangle(p1, p2, p3, p4);
                        set.add(r.toString());
                    }
                    
                    x1 = p1.x; y1 = p1.y; x2 = p2.x; y2 = p2.y;
                    
                    while(--x1 >= 0 && --x2 >= 0 && --y1 >= 0 && --y2 >= 0) {
                        Point p3 = new Point(x1, y1);
                        Point p4 = new Point(x2, y2);
                        
                        Rectangle r = new Rectangle(p1, p2, p3, p4);
                        set.add(r.toString());
                    }
                }
            }
        }

        return set.size();
    }

    private void generatePoints(int cols, int rows) {
        
        points.clear();
        
        for (int row = 0; row <= rows*2; row += 2) {
            for (int col = 0; col <= cols*2; col += 2) {
                points.add(new Point(col, row));

                if (row < rows*2 && col < cols*2) {
                    points.add(new Point(col + 1, row + 1));
                }
            }
        }
    }

    private class Rectangle {

        private List<Point> vertices = new ArrayList<>();

        public Rectangle(Point p1, Point p2, Point p3, Point p4) {
            vertices.addAll(Set.of(p1, p2, p3, p4));
        }

        @Override
        public String toString() {
            Collections.sort(vertices);
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < vertices.size(); i++) {
                if (i > 0) {
                    sb.append("-");
                }

                sb.append(vertices.get(i).toString());
            }

            return sb.toString();
        }
    }

    private class Point implements Comparable<Point> {

        private int x;

        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }

        @Override
        public int hashCode() {
            return toString().hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if ((obj == null) || !(obj instanceof Point)) {
                return false;
            }

            Point p = (Point) obj;
            return x == p.x && y == p.y;
        }

        @Override
        public int compareTo(Point o) {

            int c = Integer.compare(o.x, x);
            if (c != 0) {
                return c;
            }

            return Integer.compare(o.y, y);
        }

    }

}
