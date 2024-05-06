package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

public class Panel extends JPanel {
    List<Point> points;
    List<Edge> edges;
    List<Triangle> triangles;
    List<DelaunayTriangulation> delaunayTriangulations;
    List<EarClippingTriangulation> earClippingTriangulations;

    private int xAxisStart;
    private int yAxisStart;

    private int scale = 20;

    private int lastX, lastY;
    private int offsetX = 0, offsetY = 0;

    public Panel() {
        this.setPreferredSize(new Dimension(800, 800));

        points = new ArrayList<>();
        edges = new ArrayList<>();
        triangles = new ArrayList<>();
        delaunayTriangulations = new ArrayList<>();
        earClippingTriangulations = new ArrayList<>();

        MouseAdapter ma = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                lastX = e.getX();
                lastY = e.getY();
            }

            public void mouseDragged(MouseEvent e) {
                offsetX += e.getX() - lastX;
                offsetY += e.getY() - lastY;
                lastX = e.getX();
                lastY = e.getY();
                repaint();
            }
        };
        addMouseListener(ma);
        addMouseMotionListener(ma);

        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getPreciseWheelRotation() < 0) {
                    scale += 2;  // Zoom in
                } else {
                    scale = Math.max(2, scale - 2);  // Zoom out, prevent scale from being too small
                }
                repaint();
            }
        });
    }


    public void drawPoint(Point p) {
        if(p != null) {
            points.add(p);
            repaint();
        }
    }

    public void drawLine(Edge l) {
        if(l != null) {
            edges.add(l);
            repaint();
        }
    }

    public void drawTriangle(Triangle t) {
        if(t != null) {
            triangles.add(t);
            repaint();
        }
    }

    public void drawDelunay(DelaunayTriangulation d) {
        delaunayTriangulations.add(d);
        repaint();

    }

    public void drawEarClipping(EarClippingTriangulation d) {
        earClippingTriangulations.add(d);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // Środek układu współrzędnych
        xAxisStart = this.getWidth() / 2 + offsetX;
        yAxisStart = this.getHeight() / 2 + offsetY;

        g2d.scale((double)scale/20, (double)scale/20);

        for(Point p : points) {
            g2d.fillOval((int) (xAxisStart + p.getX()) - 4, (int) (yAxisStart - p.getY()) - 4, 8, 8);
        }

        for(Edge l : edges) {
            g2d.drawLine((int) l.p1.getX(), (int) l.p1.getY(), (int) l.p2.getX(), (int) l.p2.getY());
        }

        for(Triangle triangle : triangles) {
            g2d.setPaint(Color.black);
            int[] xPoints = {(int) (xAxisStart + triangle.a.getX()), (int) (xAxisStart + triangle.b.getX()), (int) (xAxisStart + triangle.c.getX())};
            int[] yPoints = {(int) (yAxisStart - triangle.a.getY()), (int) (yAxisStart - triangle.b.getY()), (int) (yAxisStart - triangle.c.getY())};
            g2d.drawPolygon(xPoints, yPoints, 3);
        }


        for (DelaunayTriangulation delaunayTriangulation : delaunayTriangulations) {
            for(Triangle triangle : delaunayTriangulation.triangles) {
                g2d.setPaint(Color.red);
                int[] xPoints = {(int) (xAxisStart + triangle.a.getX()), (int) (xAxisStart + triangle.b.getX()), (int) (xAxisStart + triangle.c.getX())};
                int[] yPoints = {(int) (yAxisStart - triangle.a.getY()), (int) (yAxisStart - triangle.b.getY()), (int) (yAxisStart - triangle.c.getY())};
                g2d.drawPolygon(xPoints, yPoints, 3);
            }
        }

        for (EarClippingTriangulation earClippingTriangulation : earClippingTriangulations) {
            for(Triangle triangle : earClippingTriangulation.triangles) {
                g2d.setPaint(Color.red);
                int[] xPoints = {(int) (xAxisStart + triangle.a.getX()), (int) (xAxisStart + triangle.b.getX()), (int) (xAxisStart + triangle.c.getX())};
                int[] yPoints = {(int) (yAxisStart - triangle.a.getY()), (int) (yAxisStart - triangle.b.getY()), (int) (yAxisStart - triangle.c.getY())};
                g2d.drawPolygon(xPoints, yPoints, 3);
            }
        }
    }
}
