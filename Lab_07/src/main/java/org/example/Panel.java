package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Panel extends JPanel {

    List<Point> points;
    List<Line> lines;
    List<MeshGenerator> fronts;
    List<Triangle> triangles;

    private int xAxisStart;
    private int yAxisStart;

    public Panel() {
        this.setPreferredSize(new Dimension(800, 800));
        points = new ArrayList<>();
        lines = new ArrayList<>();
        fronts = new ArrayList<>();
        triangles = new ArrayList<>();
    }

    public void drawPoint(Point p) {
        if(p != null) {
            points.add(p);
            repaint();
        }
    }

    public void drawLine(Line l) {
        if(l != null) {
            lines.add(l);
            repaint();
        }
    }

    public void drawFront(MeshGenerator f) {
        if(f != null) {
            fronts.add(f);
            repaint();
        }
    }

    public void drawTriangle(Triangle t) {
        if(t != null) {
            triangles.add(t);
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // Środek układu współrzędnych
        xAxisStart = 0;
        yAxisStart = this.getHeight();

        for(Point p : points) {
            g2d.fillOval((int )p.getX(), (int) p.getY(), 2, 2);
        }

        for(Line l : lines) {
            g2d.drawLine((int) l.p1.getX(), (int) l.p1.getY(), (int) l.p2.getX(), (int) l.p2.getY());
        }

        for(MeshGenerator f : fronts) {
            g2d.setPaint(Color.black);
            /*
            int[] xPoints = new int[]{f.points.size()};
            int[] yPoints = new int[]{f.lines.size()};
            for(int i = 0; i < f.points.size(); i++) {
                xPoints[i] = (int)f.points.get(i).getX();
                yPoints[i] = (int)f.points.get(i).getY();
            }
            g2d.drawPolygon(xPoints, yPoints, f.points.size());

             */


            for(Line l : f.lines) {
                g2d.setPaint(Color.green);
                g2d.drawLine(xAxisStart + (int) l.p1.getX(), yAxisStart -(int) l.p1.getY(), xAxisStart + (int) l.p2.getX(), yAxisStart -(int) l.p2.getY());
            }


            for(Point p : f.points) {
                g2d.fillOval( xAxisStart + (int)p.getX() - 2, yAxisStart - (int)p.getY() - 2, 4, 4);
            }




            for(Triangle t : f.triangles) {
                g2d.setPaint(Color.red);
                int[] xPoints = {(int) (xAxisStart + t.a.getX()), (int) (xAxisStart + t.b.getX()), (int) (xAxisStart + t.c.getX())};
                int[] yPoints = {(int) (yAxisStart - t.a.getY()), (int) (yAxisStart - t.b.getY()), (int) (yAxisStart - t.c.getY())};
                g2d.drawPolygon(xPoints, yPoints, 3);
            }





        }

    }
}
