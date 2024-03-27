public class Line implements Printable{
    Point p1, p2;
    double a, b;

    public Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
        a = (p1.y - p2.y) / (p1.x - p2.x);
        b = p1.y - ((p1.y - p2.y) / (p1.x - p2.x)) * p1.x;
    }

    public boolean isPointOnLine(Point p) {
        return p.y == (a * p.x) + b;
    }

    public boolean isPointOnSegment(Point p3) {
        return isPointOnLine(p3) && p3.x >= Math.min(p1.x, p2.y) && p3.x <= Math.max(p1.x, p2.x) && p3.y >= Math.min(p1.y, p2.y) && p3.y <= Math.max(p1.y, p2.y);
    }

    public void pointPosition(Point p) {
        double temp = p.x * a + b - p.y;
        if(temp < 0)
            System.out.println("Punkt znajduje się na prawo od prostej");
        else if (temp > 0)
            System.out.println("Punkt znajduje się na lewo od prostej");
        else
            System.out.println("Punkt znajduje się na prostej");
    }

    void translate(double dx, double dy) {
        p1.x += dx;
        p1.y += dy;
        p2.x += dx;
        p2.y += dy;

        a = (p1.y - p2.y) / (p1.x - p2.x);
        b = p1.y - ((p1.y - p2.y) / (p1.x - p2.x)) * p1.x;
    }

    Point reflectPoint(Point p) {
        Point newPoint = new Point();
        if(a == 0 & b == 0) {
            newPoint.x = p.x;
            newPoint.y = -p.y;
        }
        else if(a == 1 && b == 0) {
            newPoint.x = p.y;
            newPoint.y = p.x;
        }
        else if(a == 0) {
            newPoint.x = p.x;
            newPoint.y = 2 * b - p.y;
        }
        else if(b == 0) {
            newPoint.x = (1 - a * a) / (1 + a * a) * p.x + (2 * a) / (1 + a * a) * p.y;
            newPoint.y = (2 * a) / (1 + a * a) * p.x - (1 - a * a) / (1 + a * a) * p.y;
        }
        else {
            double A = a, B = -1, C = b;
            //double d = Math.abs((A * p.x) + (B * p.y) + C) / Math.sqrt((A * A) + (B * B));
            double a2 = -Math.pow(a, -1);
            double b2 = p.y - p.x * a2;

            double tempx = (b2 - b) / (a - a2);
            double tempy = a * tempx + b;

            newPoint.x = - p.x + 2 * tempx;
            newPoint.y = - p.y + 2 * tempy;

            //System.out.println("y = " + a2 + "x + " + b2);

        }
        return newPoint;
    }

    @Override
    public void print() {
        System.out.print("p1:");
        p1.print();
        System.out.print("p2:");
        p2.print();
        if(b>0)
            System.out.println("y = " + a + "x + "+b);
        else if(b<0)
            System.out.println("y = " + a + " x "+b);
        else
            System.out.println("y = " + a + "x");
    }


}
