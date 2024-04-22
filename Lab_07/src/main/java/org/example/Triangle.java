package org.example;

public class Triangle {
    Point a;
    Point b;
    Point c;

    Line l1, l2, l3;

    public Triangle(Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;

        l1 = new Line(a, b);
        l2 = new Line(a, c);
        l3 = new Line(a, b);
    }

    public boolean contains(Point p) {
        return a.equals(p) && b.equals(p) && c.equals(p);
    }

    public boolean hasLine(Line line) {
        return (line.equals(new Line(a, b)) || line.equals(new Line(b, a)) ||
                line.equals(new Line(b, c)) || line.equals(new Line(c, b)) ||
                line.equals(new Line(c, a)) || line.equals(new Line(a, c)));
    }

    public boolean checkIfIntersects(Line line) {
        if (line.findIntersectionSegment(new Line(a, b)) != null
        && line.findIntersectionSegment(new Line(b, a)) != null
            && line.findIntersectionSegment(new Line(b, c)) != null
            && line.findIntersectionSegment(new Line(c, a)) != null
            && line.findIntersectionSegment(new Line(c, b)) != null
            && line.findIntersectionSegment(new Line(c, a)) != null && !hasLine(line))
            return true;
        else return false;
    }

    public boolean chceckIfIntersectsTriangle(Triangle triangle) {
        if(triangle.checkIfIntersects(new Line(a, b)) && triangle.checkIfIntersects(new Line(b, a)) && triangle.checkIfIntersects(new Line(a, c)) && triangle.checkIfIntersects(new Line(b, c))){
            return true;
        }
        return false;
    }

}
