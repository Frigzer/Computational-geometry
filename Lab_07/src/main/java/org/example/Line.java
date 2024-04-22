package org.example;

public class Line {
    Point p1, p2;

    double a;
    double b;

    public Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;

        a = (p1.getY() - p2.getY()) / (p1.getX() - p2.getX());
        b = p1.getY() - ((p1.getY() - p2.getY()) / (p1.getX() - p2.getX())) * p1.getX();
    }


    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Line line = (Line) obj;
        return (p1.equals(line.p1) && p2.equals(line.p2)) || (p1.equals(line.p2) && p2.equals(line.p1));
    }

    @Override
    public int hashCode() {
        return p1.hashCode() + p2.hashCode(); // Prosty sposób na generowanie hashCode, który nie zależy od kierunku linii
    }

    Point reflectPoint(Point p) {
        Point newPoint = new Point();

        double a = (p1.getY() - p2.getY()) / (p1.getX() - p2.getX());
        double b = p1.getY() - ((p1.getY() - p2.getY()) / (p1.getX() - p2.getX())) * p1.getX();

        //double d = Math.abs((A * p.x) + (B * p.y) + C) / Math.sqrt((A * A) + (B * B));
        double a2 = -Math.pow(a, -1);
        double b2 = p.getY() - p.getX() * a2;

        double tempx = (b2 - b) / (a - a2);
        double tempy = a * tempx + b;

        newPoint.setX(-p.getX() + 2 * tempx);
        newPoint.setY(-p.getY() + 2 * tempy);

        //System.out.println("y = " + a2 + "x + " + b2);


        return newPoint;
    }

    public boolean isPointOnSegment(Point p3) {
        return isPointOnLine(p3) && p3.getX() >= Math.min(p1.getX(), p2.getX()) && p3.getX() <= Math.max(p1.getX(), p2.getX()) && p3.getY() >= Math.min(p1.getY(), p2.getY()) && p3.getY() <= Math.max(p1.getY(), p2.getY());
    }

    public boolean isPointOnLine(Point p) {
        if (p != null) {
            if (p1.getX() == p2.getX() && p.getX() == p1.getX())
                return true;
            return p.getY() == (a * p.getX()) + b;
        }
        return false;
    }

    public Point findIntersectionSegment(Line line) {
        if(this.a == line.a) {
            return null;
        }
        else {
            Point newPoint = new Point();

            if(this.p1.getX() == this.p2.getX()) {
                newPoint.setX(this.b);
                newPoint.setY(line.a * newPoint.getX() + line.b);
            }
            else if (line.p1.getX() == line.p2.getX()) {
                newPoint.setX(line.b);
                newPoint.setY(this.a * newPoint.getX() + this.b);
            } else {
                newPoint.setX((line.b - b) / (a - line.a));
                newPoint.setY(a * newPoint.getX() + b);
            }

            if(this.isPointOnSegment(newPoint) && line.isPointOnSegment(newPoint)) {
                return newPoint;
            }

            return null;
        }
    }

    public int pointPosition(Point p) {
        double temp = p.getX() * a + b - p.getY();
        //double temp = (p.x - p1.x) * (p2.y - p1.y) - (p.y - p1.y) * (p2.x - p1.x);
        //double A = this.a, B = -1, C = this.b;
        //double temp = A * p.x + B * p.y + C;
        if(temp < 0)
            return -1;
            //System.out.println("Punkt znajduje się na prawo od prostej");
        else if (temp > 0)
            return 1;
            //System.out.println("Punkt znajduje się na lewo od prostej");
        else
            return 0;
        //System.out.println("Punkt znajduje się na prostej");
    }

}