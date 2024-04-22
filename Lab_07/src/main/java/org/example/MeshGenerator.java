package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MeshGenerator {

    List<Point> points;

    List<Line> lines;

    List<Line> front;

    List<Triangle> triangles;

    List<Point> edges;

    int r;

    int scale;

    int figure;


    public MeshGenerator(String filename, int figure){

        points = new ArrayList<>();
        lines = new ArrayList<>();
        edges = new ArrayList<>();
        triangles = new ArrayList<>();

        this.figure = figure;

        if(figure == 1){
            r = 22;
            scale = 4;
        } else if(figure == 2){
            r = 18;
            scale = 4;
        } else if(figure == 3){
            r = 15;
            scale = 3;
        } else if(figure == 4){
            r = 22;
            scale = 5;
        } else if(figure == 5){
            r = 12;
            scale = 3;
        } else if(figure == 7){
            r = 15;
            scale = 3;
        }


        loadPointsFromFile(filename);

        createLines();


        generateMesh();

    }

    private void loadPointsFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length == 2) {
                    double x = Double.parseDouble(parts[0]);
                    double y = Double.parseDouble(parts[1]);
                    points.add(new Point(x, y));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createLines() {
        int n = points.size();
        for (int i = 0; i < n - 1; i++) {
            lines.add(new Line(points.get(i), points.get(i + 1)));
        }
        lines.add(new Line(points.get(n - 1), points.getFirst()));
    }

    public void generateMesh() {
        initializeFront();

        int ogSize = edges.size();
        int halfSize = ogSize;
        System.out.println(halfSize);
        int breakedSize = 0;


        for (int i = 0; i < this.edges.size() - 1; i++) {
            if(i == ogSize + breakedSize - 1 || (figure == 1 && i == 449)) {
                System.out.println(ogSize);
                //System.out.println(i);

                if(distance(edges.get(ogSize - 1), edges.get(i - halfSize + 1)) <= 50) {
                    Point newPoint = generatePoint(edges.get(ogSize - 1), edges.get(i - halfSize + 1), 1);

                    newPoint = checkNearbyPoints(newPoint);
                    if(containsPoint(newPoint))
                    {
                        edges.remove(edges.get(i - halfSize + 1));
                        i--;
                        halfSize--;
                        ogSize--;
                        System.out.println("break");
                        ogSize += halfSize;
                        continue;
                    }


                    edges.add(newPoint);
                    addTriangle(this.edges.get(ogSize - 1), this.edges.get((i - halfSize + 1) % this.edges.size()), newPoint);
                    for(Line line : lines) {
                        if(triangles.getLast().checkIfIntersects(line))
                        {
                            edges.remove(newPoint);
                            triangles.remove(triangles.getLast());
                            break;
                        }
                    }
                    front.add(new Line(this.edges.get(ogSize - 1), newPoint));

                    front.add(new Line(newPoint, this.edges.get((i - halfSize + 1) % this.edges.size())));

                    ogSize += halfSize;

                    if(r >= 19 && figure == 1)
                        r -= scale / 2;

                    if(r >= 19 && figure == 5)
                        r -= 1;

                    if(figure == 2)
                        r += 5;

                    if(figure == 4)
                        r += 5;

                    if(figure == 3)
                        r += 2;

                    if(figure == 7)
                        r += 2;
                };
            }
            else if((figure == 2 && distance(edges.get(i), edges.get(i + 1)) <= 68) || (figure ==1 && distance(edges.get(i), edges.get(i + 1)) <= 50) || (figure == 3 && distance(edges.get(i), edges.get(i + 1)) <= 50) || (figure == 4 && distance(edges.get(i), edges.get(i + 1)) <= 70) || (figure ==5 && distance(edges.get(i), edges.get(i + 1)) <= 50) || (figure == 7 && distance(edges.get(i), edges.get(i + 1)) <= 70)) {
                //System.out.println(i);
                Point newPoint = generatePoint(edges.get(i), edges.get(i + 1), 1);

                newPoint = checkNearbyPoints(newPoint);
                if(containsPoint(newPoint))
                {
                    edges.remove(edges.get(i + 1));
                    i--;
                    ogSize--;
                    halfSize--;
                    continue;
                }
                if(containsPoint1(newPoint)) {
                    continue;
                }


                edges.add(newPoint);
                addTriangle(this.edges.get(i), this.edges.get((i + 1) % this.edges.size()), newPoint);
                for(Line line : lines) {
                    if(triangles.getLast().checkIfIntersects(line))
                    {
                        edges.remove(newPoint);
                        triangles.remove(triangles.getLast());
                        break;
                    }
                }


                front.add(new Line(this.edges.get(i), newPoint));

                front.add(new Line(newPoint, this.edges.get((i + 1) % this.edges.size())));

                if(figure == 2 && i >= 400)
                    r += 4;

                if(figure == 4 && i >= 110)
                    r += 4;

                //if(figure == 2 && i > 200)
                 //   r += 1;
                //if(figure == 2 && ogSize == 51)
                //    r += 50;


                if(figure == 3 && i >= 200)
                    r += 1;

                if(figure == 7 && i >= 200)
                    r += 1;

            }


            if(triangles.size() > 1000)
                break;

        }
        System.out.println(edges.size());
    }

    private boolean checkIfInside(Point newPoint) {
        if(newPoint.getX() > findRightmostPoint().getX() + 5)
            return false;
        else if (newPoint.getX() < findLeftmostPoint().getX() - 5) {
            return false;
        } else if (newPoint.getY() > findTopmostPoint().getX() + 5) {
            return false;
        } else if (newPoint.getY() < findBottommostPoint().getX() - 5) {
            return false;
        }
        return true;
    }

    private Point generatePoint(Point p1, Point p2, int direction) {
        double xCenter = (p1.getX() + p2.getX()) / 2;
        double yCenter = (p1.getY() + p2.getY()) / 2;

        double vx = p2.getX() - p1.getX();
        double vy = p2.getY() - p1.getY();

        double length = Math.sqrt(vx * vx + vy * vy);
        double normalizedVx = vx / length;
        double normalizedVy = vy / length;

        double perpendicularVx = -normalizedVy * direction;
        double perpendicularVy = normalizedVx * direction;

        double a = length;

        double distance = (a * Math.sqrt(3)) / 2;

        double xResult = xCenter + distance * perpendicularVx;
        double yResult = yCenter + distance * perpendicularVy;


        return new Point(xResult, yResult);
    }

    private Point checkNearbyPoints(Point newPoint) {
        for (Point edgePoint : edges) {
            if(!edgePoint.equals(newPoint)) {
                if (distance(newPoint, edgePoint) <= r) {
                    return edgePoint;
                }
            }
        }

        return newPoint;
    }

    private double distance(Point p1, Point p2) {
        double dx = p1.getX() - p2.getX();
        double dy = p1.getY() - p2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }


    private void addTriangle(Point p1, Point p2, Point p3) {
        triangles.add(new Triangle(p1, p2, p3));
        //System.out.println("Added triangle: " + p1 + ", " + p2 + ", " + p3);
    }

    private void initializeFront() {
        front = new ArrayList<>();
        for (int i = 0; i < points.size() - scale + 1; i += scale) {
            edges.add(new Point(points.get(i).getX(), points.get(i).getY()));
        }

        for (int i = 0; i < edges.size(); i++) {
            Line newLine = new Line(edges.get(i), edges.get((i + 1) % edges.size()));
            front.add(newLine);
        }
    }

    private void updateFront(Line oldLine, Point newPoint) {
        // Usuń starą krawędź z frontu
        front.remove(oldLine);

        // Dodaj nowe krawędzie do frontu, o ile nie są one już częścią frontu
        Line newLine1 = new Line(oldLine.getP1(), newPoint);
        Line newLine2 = new Line(newPoint, oldLine.getP2());

        if (!front.contains(newLine1) && !isLineRedundant(newLine1)) {
            front.add(newLine1);
        }
        if (!front.contains(newLine2) && !isLineRedundant(newLine2)) {
            front.add(newLine2);
        }
    }

    private boolean isLineonLine(Line line) {
        for(Line l : front)
            if(l.equals(line))
                return true;
        return false;
    }

    private boolean isLineRedundant(Line line) {
        // Sprawdź, czy tworzenie tej linii nie zamknęłoby już pokrytego obszaru
        for (Triangle triangle : triangles) {
            if (triangle.hasLine(line)) {
                return true; // Linia już istnieje w siatce trójkątów
            }
        }
        return false;
    }


    private boolean containsPoint(Point p) {
        for(Line line : front) {
            if(line.pointPosition(p) == 1)
                return false;
        }
        return true;
    }





    public boolean containsPoint1(Point p) {
        createLines();
        Point temp1 = new Point(p.getX() + 5, p.getY());
        Line l1 = new Line(p, temp1);

        Point temp2 = new Point(p.getX(), p.getY() + 5);
        Line l2 = new Line(p, temp2);

        int numOFLeftIntersections = 0;
        int numOFRightIntersections = 0;
        int numOFTopIntersections = 0;
        int numOFBotIntersections = 0;

        for(Line side : lines) {
            if(side.isPointOnSegment(p)) {
                numOFLeftIntersections = 1;
                numOFRightIntersections = 1;
                numOFTopIntersections = 1;
                numOFBotIntersections = 1;
                break;
            }
            else {
                Point intersection1 = side.findIntersectionSegment(l1);
                Point intersection2 = side.findIntersectionSegment(l2);
                if((intersection1 != null) && (intersection1.getX() < p.getX())) {
                    numOFLeftIntersections++;
                }
                if((intersection1 != null) && (intersection1.getX() > p.getX())) {
                    numOFRightIntersections++;
                }
                if((intersection2 != null) && (intersection2.getY() > p.getY())) {
                    numOFTopIntersections++;
                }
                if((intersection2 != null) && (intersection2.getY() < p.getY())) {
                    numOFBotIntersections++;
                }
            }
        }

        if(numOFLeftIntersections == 0 || numOFRightIntersections == 0 || numOFTopIntersections == 0 || numOFBotIntersections == 0)
            //System.out.println("Punkt nie znajduje sie w wielokacie");
            return false;
        return true;
        //else System.out.println("Punkt znajduje sie w wielokacie");
    }

    private Point findLeftmostPoint() {
        Point leftmost = points.get(0);
        for (Point p : points) {
            if (p.getX() < leftmost.getX())
                leftmost = p;
        }
        return leftmost;
    }

    private Point findRightmostPoint() {
        Point rightmost = points.get(0);
        for (Point p : points) {
            if (p.getX() > rightmost.getX())
                rightmost = p;
        }
        return rightmost;
    }

    private Point findTopmostPoint() {
        Point topmost = points.get(0);
        for (Point p : points) {
            if (p.getY() > topmost.getY())
                topmost = p;
        }
        return topmost;
    }

    private Point findBottommostPoint() {
        Point bottommost = points.get(0);
        for (Point p : points) {
            if (p.getY() < bottommost.getY())
                bottommost = p;
        }
        return bottommost;
    }


}
