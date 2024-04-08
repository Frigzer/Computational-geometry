import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ConvexHull {
    List<Point> points;
    List<Line> sides;
    Point center;

    public ConvexHull(String filename) {
        points = new ArrayList<>();
        sides = new ArrayList<>();
        loadPointsFromFile(filename);
        //calculateQuickHull();

        double x0 = (findLeftmostPoint().x + findRightmostPoint().x) / 2;
        double y0 = (findTopmostPoint().y + findBottommostPoint().y) / 2;

        center = new Point(x0, y0);
    }
    public void calculateJarvis() {
        if (points.size() < 3)
            return;

        Point startPoint = findLeftmostPoint();
        Point currentPoint = startPoint;
        do {
            Point nextVertex = null;
            for (Point vertex : points) {
                if (vertex.equals(currentPoint))
                    continue;
                if (nextVertex == null || isClockwise(currentPoint, nextVertex, vertex)) {
                    nextVertex = vertex;
                }
            }
            sides.add(new Line(currentPoint, nextVertex));
            currentPoint = nextVertex;
        } while (!currentPoint.equals(startPoint));
    }

    public void calculateQuickHull() {
        if (points.size() < 3)
            return;

        Point leftmost = findLeftmostPoint();
        Point rightmost = findRightmostPoint();

        List<Point> convexHull = new ArrayList<>();
        convexHull.add(leftmost);
        convexHull.add(rightmost);

        List<Point> pointsAbove = new ArrayList<>();
        List<Point> pointsBelow = new ArrayList<>();

        for (Point p : points) {
            if (quickOrientation(leftmost, rightmost, p) == 1)
                pointsAbove.add(p);
            else if (quickOrientation(leftmost, rightmost, p) == -1)
                pointsBelow.add(p);
        }

        findHull(leftmost, rightmost, pointsAbove, convexHull);
        findHull(rightmost, leftmost, pointsBelow, convexHull);

        // Clear existing sides and create new ones based on the convex hull points
        sides.clear();
        for (int i = 0; i < convexHull.size() - 1; i++) {
            sides.add(new Line(convexHull.get(i), convexHull.get(i + 1)));
        }
        sides.add(new Line(convexHull.get(convexHull.size() - 1), convexHull.get(0)));
    }

    private void findHull(Point p1, Point p2, List<Point> points, List<Point> convexHull) {
        int index = convexHull.indexOf(p2);
        if (points.size() == 0)
            return;

        if (points.size() == 1) {
            convexHull.add(index, points.get(0));
            return;
        }

        double maxDistance = 0;
        int farthestPointIndex = -1;

        for (int i = 0; i < points.size(); i++) {
            double distance = findDistance(p1, p2, points.get(i));
            if (distance > maxDistance) {
                maxDistance = distance;
                farthestPointIndex = i;
            }
        }

        Point farthestPoint = points.get(farthestPointIndex);
        convexHull.add(index, farthestPoint);

        List<Point> newPoints1 = new ArrayList<>();
        List<Point> newPoints2 = new ArrayList<>();

        for (Point point : points) {
            if (quickOrientation(p1, farthestPoint, point) == 1)
                newPoints1.add(point);
            else if (quickOrientation(farthestPoint, p2, point) == 1)
                newPoints2.add(point);
        }

        findHull(p1, farthestPoint, newPoints1, convexHull);
        findHull(farthestPoint, p2, newPoints2, convexHull);
    }

    private double findDistance(Point p1, Point p2, Point p) {
        return Math.abs((p.y - p1.y) * (p2.x - p1.x) - (p2.y - p1.y) * (p.x - p1.x));
    }

    private boolean isClockwise(Point a, Point b, Point c) {
        return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x) > 0;
    }

    // Przydatna metoda do określenia orientacji punktów
    private int orientation(Point p, Point q, Point r) {
        int val = (int) ((q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y));
        if (val == 0) return 0;  // kolinearne
        return (val > 0) ? 1 : 2; // zgodnie lub przeciwnie do ruchu wskazówek zegara
    }

    private int quickOrientation(Point p, Point q, Point r) {
        int val = (int) ((q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y));
        if (val == 0) return 0;  // kolinearne
        return (val > 0) ? 1 : -1; // zgodnie lub przeciwnie do ruchu wskazówek zegara
    }


    private Point findLeftmostPoint() {
        Point leftmost = points.get(0);
        for (Point p : points) {
            if (p.x < leftmost.x)
                leftmost = p;
        }
        return leftmost;
    }

    private Point findRightmostPoint() {
        Point rightmost = points.get(0);
        for (Point p : points) {
            if (p.x > rightmost.x)
                rightmost = p;
        }
        return rightmost;
    }

    private Point findTopmostPoint() {
        Point topmost = points.get(0);
        for (Point p : points) {
            if (p.y > topmost.y)
                topmost = p;
        }
        return topmost;
    }

    private Point findBottommostPoint() {
        Point bottommost = points.get(0);
        for (Point p : points) {
            if (p.y < bottommost.y)
                bottommost = p;
        }
        return bottommost;
    }


    private void loadPointsFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length == 2) {
                    int x = Integer.parseInt(parts[0]);
                    int y = Integer.parseInt(parts[1]);
                    points.add(new Point(x, y));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void translate(double dx, double dy) {
        for(Point point : points) {
            point.x += dx;
            point.y += dy;
        }
    }

    void setPosition(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            // Odczytaj pierwszą linię i pierwszy wiersz
            double x0 = Double.parseDouble(scanner.next());
            double y0 = Double.parseDouble(scanner.next());

            // Odczytaj drugą linię i drugi wiersz
            double dx = Double.parseDouble(scanner.next()) / 200.0;
            double dy = Double.parseDouble(scanner.next()) / 200.0;



            Point topPoint = findTopmostPoint();

            Point toCenterVector = new Point(500 - topPoint.x, 500 - topPoint.y);

            double angle = Math.atan2(dy, dx) - Math.atan2(topPoint.y, topPoint.x);


            AffineTransform rotation = AffineTransform.getRotateInstance(angle, center.x, center.y);

            for (Point point : points) {
                rotation.transform(point, point);
            }

            double tempX = x0 - center.x;
            double tempY = y0 - center.y;

            translate(tempX, tempY);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void setInMotion(String filename) {
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            // Odczytaj pierwszą linię i pierwszy wiersz
            double x0 = Double.parseDouble(scanner.next());
            double y0 = Double.parseDouble(scanner.next());

            // Odczytaj drugą linię i drugi wiersz
            double dx = Double.parseDouble(scanner.next()) / 200.0;
            double dy = Double.parseDouble(scanner.next()) / 200.0;

            translate(dx, dy);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void detectCollision(Point point, Panel panel, double delay) {
        if(point.spawned) {
            for (Line side : sides) {
                Line2D.Double line = new Line2D.Double(side.p1.x, side.p1.y, side.p2.x, side.p2.y);
                Point2D.Double point2D = new Point2D.Double(point.x, point.y);
                if (line.ptSegDist(point2D) <= 5) {
                    panel.drawExplosion(point);
                    panel.removePointAfterCollision(point);
                    //point.spawned = false;
                    panel.changeConvexHullSideColor(this, side);
                    panel.drawExplosion(point);
                    System.out.println("Czas kolizji: " + delay);
                    panel.drawExplosion(point);
                    break;

                }
            }
        }
    }

}
