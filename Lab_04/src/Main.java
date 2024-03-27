import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        // Ustawienia okna
        Frame frame = new Frame();
        Panel panel = new Panel();
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);

        // --------------------------------------------------------------------------------


        //Trójkąt
        Point p1 = new Point(9, 6);
        Point p2 = new Point(-7, 5);
        Point p3 = new Point(4, 10);

        Triangle triangle = new Triangle(p1, p2, p3);

        panel.drawTriangle(triangle, Color.red);

        triangle.print();

        Point test = new Point(14, 7);
        triangle.containsPointByLines(test);
        triangle.containsPointByArea(test);

        panel.drawPoint(test, Color.blue);

        //Kąt
        Point p11 = new Point(-14, 5);
        Point p12 = new Point(-7,5);
        Line l1 = new Line(p11, p12);

        Point p21 = new Point(-14, 5);
        Point p22 = new Point(-13,8);
        Line l2 = new Line(p21, p22);

        panel.drawLine(l1, Color.ORANGE, false);
        panel.drawLine(l2, Color.ORANGE, false);

        System.out.println("Kąt pomiędzy prostymi: " + l2.angleBetweenLines(l1));

        //Wielokąt
        Point[] points = {
                new Point(-10, -5),
                new Point(-7, -10),
                new Point(-16, -14),
                new Point(0, -14),
                new Point(4, -5),
                new Point(7, 0),
                new Point(6, 4),
                new Point(-5, -5),
                new Point(-10, 4)
        };

        Polygon polygon = new Polygon();

        for (Point p : points) {
            polygon.addPoint(p);
        }

        panel.drawPolygon(polygon, Color.BLUE);

        Point test2 = new Point(-10, 6);



        polygon.containsPoint(test2);

        for (Point p : polygon.points) {
            panel.drawPoint(p, Color.yellow);
        }

        panel.drawPoint(test2, Color.red);
    }

}