import javax.swing.*;
import java.awt.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        // Ustawienia okna
        Frame frame = new Frame();
        Panel panel = new Panel();
        frame.add(panel);
        frame.pack();

        // --------------------------------------------------------------------------------

        Point[] points = {
                new Point(5, 7),
                new Point(-5, 3),
                new Point(4, 1),
                new Point(-1, -10),
                new Point(-9, 14),
                new Point(-6, 0)
                //new Point(5, 7), // linia na linii
                //new Point(-5, 3), // linia na linii
        };


        Line[] lines = {
                new Line(points[0], points[1]),
                new Line(points[2], points[3]),
                new Line(points[4], points[5]),
        };



        for(Line l : lines) {
            panel.drawLine(l, Color.BLUE, true);
        }

        Point p1 = lines[0].findIntersectionPoint(lines[1]);
        Point p2 = lines[0].findIntersectionFromGeneralForm(lines[2]);
        Point p3 = lines[2].findIntersectionPoint(lines[1]);

        panel.drawPoint(p1, Color.pink);
        panel.drawPoint(p2, Color.yellow);
        panel.drawPoint(p3, Color.green);


        Point test = new Point(-8, -10);
        System.out.println(lines[2].getPointLineDistance(test));
        panel.drawPoint(test, Color.CYAN);


        Triangle triangle = new Triangle(lines[0], lines[1], lines[2]);

        panel.drawTriangle(triangle, Color.red);





        // Przyklad 2 ---------------------------------------------------------------------------

        /*
        Point[] points2 = {
               // new Point(0, 0),
                new Point(2, 8),
                new Point(4, 5),
                //new Point(0, 0),
                new Point(3, -5),
                new Point(-7, -2),
                //new Point(0, 0),
                new Point(3, 1),
                new Point(-4, 6)
        };


        Line[] lines2 = {
                new Line(points2[0], points2[1]),
                new Line(points2[2], points2[3]),
                new Line(points2[4], points2[5]),
        };


        for(Line l : lines2) {
            panel.drawLine(l, Color.RED, true);
        }

        Point p11 = lines2[0].findIntersectionPoint(lines2[1]);
        Point p22 = lines2[0].findIntersectionFromGeneralForm(lines2[2]);
        Point p33 = lines2[2].findIntersectionFromGeneralForm(lines2[1]);

        panel.drawPoint(p11, Color.blue);
        panel.drawPoint(p22, Color.magenta);
        panel.drawPoint(p33, Color.ORANGE);

        Triangle triangle2 = new Triangle(lines2[0], lines2[1], lines2[2]);

        panel.drawTriangle(triangle2, Color.GREEN);


         */


    }
    /*
    Point findIntersectionPoint(double x1, double y1, double x2, double y2,double x11, double y11, double x22, double y22 ) {

        if((y1- y2) / (x1 - x2) == (y11 - y22) / (x11 - x22))
            return null;
        else {
            Point newPoint = new Point();


            newPoint.x = ((y11 - ((y11 - y22) / (x11 - x22)) * x11) - (y1 - ((y1 - y2) / (x1 - x2)) * x1)) / ((y1 - y2) / (x1 - x2) - (y11 - y22) / (x11 - x22));
            newPoint.y = (y1 - y2) / (x1 - x2) * newPoint.x + (y1 - ((y1 - y2) / (x1 - x2)) * x1);

            return newPoint;
        }
    }

     */

    /*
    Point findIntersectionFromGeneralForm(double A, double B, double C, double D, double E, double F) {
        if(A/D == B/E)
            return null;
        else {
            Point newPoint = new Point();

            newPoint.x = (B * F - C * E) / (A * E - B * D);
            newPoint.y = (C * D - A * F) / (A * E - B * D);

            return newPoint;
        }

    }

     */

}