package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AdvancingFrontVisualization extends JPanel {

    static class Point {
        double x, y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Triangle {
        Point p1, p2, p3;

        Triangle(Point p1, Point p2, Point p3) {
            this.p1 = p1;
            this.p2 = p2;
            this.p3 = p3;
        }

        void draw(Graphics2D g) {
            int[] xPoints = {(int) p1.x, (int) p2.x, (int) p3.x};
            int[] yPoints = {(int) p1.y, (int) p2.y, (int) p3.y};
            g.drawPolygon(xPoints, yPoints, 3);
        }
    }

    private ArrayList<Triangle> triangles;

    AdvancingFrontVisualization(ArrayList<Triangle> triangles) {
        this.triangles = triangles;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(Color.BLACK);
        for (Triangle triangle : triangles) {
            triangle.draw(g2d);
        }
    }

    public static void main(String[] args) {
        // Przykładowe punkty
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(0, 0));
        points.add(new Point(100, 100));
        points.add(new Point(200, 0));
        points.add(new Point(100, 200));
        points.add(new Point(50, 100));
        points.add(new Point(150, 100));

        // Triangulacja
        ArrayList<Triangle> triangles = triangulate(points);

        // Wyświetlenie w oknie Swing
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Triangulacja Advancing Front");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new AdvancingFrontVisualization(triangles));
            frame.setSize(400, 400);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    // Funkcja triangulacji zbioru punktów za pomocą algorytmu Advancing Front
    static ArrayList<Triangle> triangulate(ArrayList<Point> points) {
        ArrayList<Triangle> triangles = new ArrayList<>();

        // Sortowanie punktów po współrzędnej x
        points.sort((p1, p2) -> Double.compare(p1.x, p2.x));

        // Inicjalizacja advancing front
        ArrayList<Point> front = new ArrayList<>();
        front.add(points.get(0));
        front.add(points.get(1));

        // Przetwarzanie kolejnych punktów
        for (int i = 2; i < points.size(); i++) {
            Point q = points.get(i);
            ArrayList<Triangle> newTriangles = new ArrayList<>();

            // Przechodzenie po aktualnym froncie i tworzenie trójkątów
            for (int j = 0; j < front.size() - 1; j++) {
                Point a = front.get(j);
                Point b = front.get(j + 1);
                Triangle triangle = new Triangle(a, b, q);
                newTriangles.add(triangle);
            }

            // Usuwanie trójkątów, których środki znajdują się wewnątrz nowo utworzonego trójkąta
            ArrayList<Triangle> invalidTriangles = new ArrayList<>();
            for (Triangle triangle : triangles) {
                Point center = new Point((triangle.p1.x + triangle.p2.x + triangle.p3.x) / 3,
                        (triangle.p1.y + triangle.p2.y + triangle.p3.y) / 3);
                for (Triangle newTriangle : newTriangles) {
                    if (pointInTriangle(newTriangle.p1, newTriangle.p2, newTriangle.p3, center)) {
                        invalidTriangles.add(triangle);
                        break;
                    }
                }
            }
            triangles.removeAll(invalidTriangles);

            // Dodanie nowo utworzonych trójkątów
            triangles.addAll(newTriangles);

            // Aktualizacja advancing front
            front.add(q);
        }

        return triangles;
    }

    // Funkcja sprawdzająca, czy punkt q jest wewnątrz trójkąta abc
    static boolean pointInTriangle(Point a, Point b, Point c, Point q) {
        double s1 = (b.y - a.y) * (q.x - b.x) - (b.x - a.x) * (q.y - b.y);
        double s2 = (c.y - b.y) * (q.x - c.x) - (c.x - b.x) * (q.y - c.y);
        double s3 = (a.y - c.y) * (q.x - a.x) - (a.x - c.x) * (q.y - a.y);
        return (s1 >= 0 && s2 >= 0 && s3 >= 0) || (s1 <= 0 && s2 <= 0 && s3 <= 0);
    }
}
