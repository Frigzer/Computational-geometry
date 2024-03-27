public class Point implements Printable{
    public double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(){}

    @Override
    public void print() {
        System.out.println("(" + x + ", " + y + ")");
    }
}
