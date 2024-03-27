import javax.swing.*;
import java.awt.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Frame frame = new Frame();
        Panel panel = new Panel();
        frame.add(panel);

        // Wyznaczenie równania


        Point p1 = new Point(4, 5);
        Point p2 = new Point(10, 5);
        Line l1 = new Line(p1, p2);
        l1.print();
        panel.drawPoint(p1, Color.yellow);
        panel.drawPoint(p2, Color.yellow);
        panel.drawLine(l1, Color.blue, true);



        Point p11 = new Point(-12, 12);
        Point p22 = new Point(8, -8);
        Line l2 = new Line(p11, p22);
        l2.print();
        panel.drawPoint(p11, Color.yellow);
        panel.drawPoint(p22, Color.yellow);
        panel.drawLine(l2, Color.green, false);



        // Sprawdzenie przynależności punktu do prostej i linii



        Point p3 = new Point(0, 0);
        Point p4 = new Point(-15, -15);
        Point p5 = new Point(7, 8);
        Point p6 = new Point(-7, 7);

        //panel.drawPoint(p3, Color.red);
        panel.drawPoint(p4, Color.blue);
        //panel.drawPoint(p5, Color.red);
        //panel.drawPoint(p6, Color.green);



        /*
        if(l1.isPointOnLine(p6))
            System.out.println("punkt jest na prostej");
        else
            System.out.println("punkt nie jest na prostej");

        if(l2.isPointOnSegment(p6))
            System.out.println("punkt na linii");
        else
            System.out.println("punkt nie jest na linii");

         */

        // Określenie położenia

        //l2.pointPosition(p4);
        //l2.pointPosition(p5);
        //l2.pointPosition(p11);
        //l2.pointPosition(p22);
        //l2.pointPosition(p1);
        //l2.pointPosition(p2);

        // Dokonanie translacji

        //l2.translate(-4, -10);
        //l2.print();
        //l2.pointPosition(p3);

        // Dokonanie odbicia

        Point p7 = l2.reflectPoint(p4);
        //Line l3 = new Line(p4, p7);
        panel.drawPoint(p7, Color.pink);
        //panel.drawLine(l3, Color.red, false);
        //l3.print();

        //Point p8 = l1.reflectPoint(p5);
        //panel.drawPoint(p8, Color.pink);
    }

}