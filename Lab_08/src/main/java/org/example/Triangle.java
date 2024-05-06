package org.example;

import java.util.ArrayList;
import java.util.List;

public class Triangle {
    Point a;
    Point b;
    Point c;

    public Triangle(Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;

    }

    public boolean containsVertex(Point p) {
        return (a.equals(p) || b.equals(p) || c.equals(p));
    }

    double distance(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
    }

    Point circumcenter() {
        double x1 = this.a.getX(), y1 = this.a.getY();
        double x2 = this.b.getX(), y2 = this.b.getY();
        double x3 = this.c.getX(), y3 = this.c.getY();

        double D = 2 * (x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2));

        double Ux = ((x1 * x1 + y1 * y1) * (y2 - y3) + (x2 * x2 + y2 * y2) * (y3 - y1) + (x3 * x3 + y3 * y3) * (y1 - y2)) / D;
        double Uy = ((x1 * x1 + y1 * y1) * (x3 - x2) + (x2 * x2 + y2 * y2) * (x1 - x3) + (x3 * x3 + y3 * y3) * (x2 - x1)) / D;

        return new Point(Ux, Uy);
    }

    boolean pointInCircumcircle(Point pt) {
        Point circumcenter = this.circumcenter();
        double radius = this.distance(this.a, circumcenter);
        double distToPoint = this.distance(pt, circumcenter);

        return distToPoint <= radius;
    }

    // Metoda sprawdzająca czy punkt (x, y) znajduje się w trójkącie
    boolean isInside(Point pt) {
        double x1 = this.a.getX(), y1 = this.a.getY();
        double x2 = this.b.getX(), y2 = this.b.getY();
        double x3 = this.c.getX(), y3 = this.c.getY();
        double x = pt.getX(), y = pt.getY();

        // Obliczenie wyrażeń do sprawdzenia orientacji punktu
        double expr1 = (x2 - x1) * (y - y1) - (x - x1) * (y2 - y1);
        double expr2 = (x3 - x2) * (y - y2) - (x - x2) * (y3 - y2);
        double expr3 = (x1 - x3) * (y - y3) - (x - x3) * (y1 - y3);

        // Sprawdzenie czy punkt znajduje się po tej samej stronie każdego boku
        return (expr1 >= 0 && expr2 >= 0 && expr3 >= 0) || (expr1 <= 0 && expr2 <= 0 && expr3 <= 0);
    }

}
