public class Point implements Printable{
    public double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(){}

    double segmentLength(Point p) {
        return Math.sqrt(Math.pow(p.x - this.x, 2) + Math.pow(p.y - this.y, 2));
    }

    @Override
    public void print() {
        System.out.println("(" + x + ", " + y + ")");
    }
}
