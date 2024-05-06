package org.example;

import java.util.List;

public class SuperTriangle {
    public static Triangle createSuperTriangle(List<Point> points) {
        double minX = Double.MAX_VALUE;
        double maxX = -Double.MIN_VALUE;
        double minY = Double.MAX_VALUE;
        double maxY = -Double.MIN_VALUE;

        for (Point p : points) {
            if (p.getX() < minX) minX = p.getX();
            if (p.getX() > maxX) maxX = p.getX();
            if (p.getY() < minY) minY = p.getY();
            if (p.getY() > maxY) maxY = p.getY();
        }

        double dx = maxX - minX;
        double dy = maxY - minY;
        double dmax = Math.max(dx, dy);
        double midX = (minX + maxX) / 2;
        double midY = (minY + maxY) / 2;

        Point p1 = new Point(midX - 2 * dmax, midY - dmax);
        Point p2 = new Point(midX + 2 * dmax, midY - dmax);
        Point p3 = new Point(midX, midY + 2 * dmax);

        return new Triangle(p1, p2, p3);
    }
}
