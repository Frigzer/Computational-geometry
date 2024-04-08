import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Point extends Point2D implements Printable{
    public double x, y;
    public double spawnTime, dx, dy;
    public boolean spawned = false;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(double spawnTime, double x, double y, double dx, double dy) {
        this.spawnTime = spawnTime;
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
    }

    public Point(){}

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }

    double segmentLength(Point p) {
        return Math.sqrt(Math.pow(p.x - this.x, 2) + Math.pow(p.y - this.y, 2));
    }

    @Override
    public void print() {
        System.out.println("(" + x + ", " + y + ")");
    }

    void translate() {
        this.x += dx;
        this.y += dy;
    }



}
