package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static org.example.SuperTriangle.createSuperTriangle;

public class DelaunayTriangulation {

    private List<Point> points;
    public List<Triangle> triangles;
    private TriangulationStats stats = new TriangulationStats();

    public DelaunayTriangulation(String filename) {
        points = new ArrayList<>();
        triangles = new ArrayList<>();
        loadPointsFromFile(filename);
        long startTime = System.currentTimeMillis();
        triangulate();
        long endTime = System.currentTimeMillis();
        stats.setTriangulationTime(endTime - startTime);

        // Stats
        System.out.println("DelaunayTriangulation");
        System.out.println("Triangulation time: " + stats.triangulationTimeMillis + "ms");
        System.out.println("Median area: " + stats.median());
        System.out.println("Mode area: " + stats.mode());
        System.out.println("Number of triangles: " + triangles.size());
    }

    void triangulate() {
        if (points.isEmpty()) return; // Sprawdzenie czy są punkty

        Triangle superTriangle = createSuperTriangle(points);
        triangles.add(superTriangle); // Super trójkąt

        for (Point point : points) {
            List<Edge> edgeBuffer = new ArrayList<>();

            // Sprawdź trójkąty do usunięcia
            Iterator<Triangle> iterator = triangles.iterator();
            while (iterator.hasNext()) {
                Triangle triangle = iterator.next();
                if (triangle.pointInCircumcircle(point)) {
                    // Zaznacz rogi przed usunięciem
                    edgeBuffer.add(new Edge(triangle.a, triangle.b));
                    edgeBuffer.add(new Edge(triangle.b, triangle.c));
                    edgeBuffer.add(new Edge(triangle.c, triangle.a));
                    iterator.remove();
                }
            }

            // Usunięcie duplikatów krawędzi
            Set<Edge> uniqueEdges = new HashSet<>();
            for (Edge edge : edgeBuffer) {
                if (edgeBuffer.indexOf(edge) != edgeBuffer.lastIndexOf(edge)) {
                    uniqueEdges.remove(edge);
                } else {
                    uniqueEdges.add(edge);
                }
            }

            // Tworzenie nowych trójkątów
            for (Edge edge : uniqueEdges) {
                Triangle newTriangle = new Triangle(edge.p1, edge.p2, point);
                triangles.add(newTriangle);

            }
        }


        // Usunięcie super trójkąta
        triangles.removeIf(t -> t.containsVertex(superTriangle.a) ||
                t.containsVertex(superTriangle.b) ||
                t.containsVertex(superTriangle.c));

        for(Triangle triangle: triangles) {
            stats.addArea(area(triangle.a, triangle.b, triangle.c));
        }


    }

    private double area(Point A, Point B, Point C) {
        return Math.abs((B.getX() - A.getX()) * (C.getY() - A.getY()) - (C.getX() - A.getX()) * (B.getY() - A.getY())) / 2.0;
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

    private double calculateArea(Point A, Point B, Point C) {
        return Math.abs((A.getX() * (B.getY() - C.getY()) + B.getX() * (C.getY() - A.getY()) + C.getX() * (A.getY() - B.getY())) / 2.0);
    }

    public List<Double> getTriangleAreas() {
        List<Double> areas = new ArrayList<>();
        for (Triangle triangle : triangles) {
            double area = calculateArea(triangle.a, triangle.b, triangle.c);
            areas.add(area);
        }
        return areas;
    }
}
