import java.awt.*;

public class Triangle implements Drawable, Printable{
    private Line l1, l2, l3;
    public Point p1, p2, p3;
    private double a, b, c;

    public Triangle(Point p1, Point p2, Point p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;

        this.a = p1.segmentLength(p2);
        this.b = p2.segmentLength(p3);
        this.c = p3.segmentLength(p1);

        this.l1 = new Line(p1, p2);
        this.l2 = new Line(p2, p3);
        this.l3 = new Line(p3, p1);
    }

    public Triangle(Line l1, Line l2, Line l3) {

        if(!isPossible(l1, l2, l3))
        {
            System.err.println("Nie można utworzyć trojkąta!!!");

        }
        else {
            this.a = p1.segmentLength(p2);
            this.b = p2.segmentLength(p3);
            this.c = p3.segmentLength(p1);

            this.l1 = l1;
            this.l2 = l2;
            this.l3 = l3;
        }

    }

    public boolean isPossible(Line l1, Line l2, Line l3) {

        p1 = l1.findIntersectionPoint(l2);
        p2 = l1.findIntersectionPoint(l3);
        if(p1 != null && p2 != null) {
            if((p1.x == p2.x) && (p1.y == p2.y))
                return false;
            else {
                p3 = l2.findIntersectionPoint(l3);
                if((p1.x == p3.x) && (p1.y == p3.y))
                    return false;
            }
            return l1.a != l2.a && l1.a != l3.a && l2.a != l3.a;
        }
        else return false;


    }

    public double calculateArea() {
        double p = (a + b + c) / 2;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

    public double calculatePerimeter() {
        return a + b + c;
    }

    public void containsPointByLines(Point p) {
        Point temp1 = l2.findIntersectionFromGeneralForm(l3);
        int t1 = l1.pointPosition(temp1);
        if(t1 == l1.pointPosition(p)) {
            Point temp2 = l1.findIntersectionFromGeneralForm(l3);
            int t2 = l2.pointPosition(temp2);
            if(t2 == l2.pointPosition(p)) {
                Point temp3 = l1.findIntersectionFromGeneralForm(l2);
                int t3 = l3.pointPosition(temp3);
                if(t3 == l3.pointPosition(p)) {
                    System.out.println("Punkt znajduje się w trojkącie");
                }
                else System.out.println("Punkt nie znajduje się w trojkącie");
            }
            else System.out.println("Punkt nie znajduje się w trojkącie");
        }
        else System.out.println("Punkt nie znajduje się w trojkącie");
    }

    public void containsPointByArea(Point p) {
        Triangle t1 = new Triangle(p, p1, p2);
        Triangle t2 = new Triangle(p, p1, p3);
        Triangle t3 = new Triangle(p, p2, p3);

        if((int) this.calculateArea() >= (int) (t1.calculateArea() + t2.calculateArea() + t3.calculateArea()))
            System.out.println("Punkt w trójkącie");
        else System.out.println("Punkt poza trójkątem");
        //System.out.println(this.calculateArea());
        //System.out.println(t1.calculateArea() + t2.calculateArea() + t3.calculateArea());

    }

    @Override
    public void draw(Color c) {

    }

    @Override
    public void print() {
        System.out.println("Trójkąt: boki = " + a + ", " + b + ", " + c +
                ", pole = " + calculateArea() + ", obwód = " + calculatePerimeter());
    }
}
