package org.example;


import java.awt.*;
import java.util.List;

public class Point{

    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point() {

    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean isInside(List<Line> lines) {
        int intersections = 0;
        Line ray = new Line(this, new Point(Double.MAX_VALUE, this.y));
        for (Line line : lines) {
            if (ray.findIntersectionSegment(line) != null) {
                intersections++;
            }
        }
        return intersections % 2 == 1;
    }

    public void print() {
        System.out.println("Point: " + x + ", " + y);
    }
}
