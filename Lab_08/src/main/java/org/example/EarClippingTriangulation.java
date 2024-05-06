package org.example;

import java.util.ArrayList;
import java.util.List;

public class EarClippingTriangulation {
    private Polygon polygon;
    public List<Triangle> triangles;
    private TriangulationStats stats = new TriangulationStats();

    public EarClippingTriangulation(String filename) {

        this.polygon = new Polygon(filename);
        this.triangles = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        this.triangles = triangulate();
        long endTime = System.currentTimeMillis();
        stats.setTriangulationTime(endTime - startTime);

        // Stats
        System.out.println("EarClippingTriangulation");
        System.out.println("Triangulation time: " + stats.triangulationTimeMillis + "ms");
        System.out.println("Median area: " + stats.median());
        System.out.println("Mode area: " + stats.mode());
        System.out.println("Number of triangles: " + triangles.size());
        System.out.println();
    }

    public List<Triangle> triangulate() {
        List<Point> vertices = new ArrayList<>(polygon.getVertices());
        int n = vertices.size();
        if (n < 3) return triangles;

        int[] V = new int[n];
        if (area() > 0) {
            for (int v = 0; v < n; v++) V[v] = v;
        } else {
            for (int v = 0; v < n; v++) V[v] = (n - 1) - v;
        }

        int nv = n;
        int count = 2 * nv;
        for (int m = 0, v = nv - 1; nv > 2; ) {
            if ((count--) <= 0) return triangles;

            int u = v;
            if (nv <= u) u = 0;
            v = u + 1;
            if (nv <= v) v = 0;
            int w = v + 1;
            if (nv <= w) w = 0;

            if (isEar(u, v, w, vertices, V)) {
                int a, b, c;
                a = V[u];
                b = V[v];
                c = V[w];
                Triangle newTriangle = new Triangle(vertices.get(a), vertices.get(b), vertices.get(c));
                triangles.add(newTriangle);

                for (int s = v, t = v + 1; t < nv; s++, t++) V[s] = V[t];
                nv--;
                count = 2 * nv;
            }
        }

        for(Triangle triangle: triangles) {
            stats.addArea(area(triangle.a, triangle.b, triangle.c));
        }

        return triangles;
    }

    private boolean isEar(int u, int v, int w, List<Point> vertices, int[] V) {
        Point A = vertices.get(V[u]), B = vertices.get(V[v]), C = vertices.get(V[w]);
        if (area(A, B, C) <= 0) return false;
        for (Point p : vertices) {
            if (p.equals(A) || p.equals(B) || p.equals(C)) continue;
            if (isInside(A, B, C, p)) return false;
        }
        return true;
    }

    private boolean isInside(Point A, Point B, Point C, Point P) {
        double cross1 = (B.getX() - A.getX()) * (P.getY() - A.getY()) - (B.getY() - A.getY()) * (P.getX() - A.getX());
        double cross2 = (C.getX() - B.getX()) * (P.getY() - B.getY()) - (C.getY() - B.getY()) * (P.getX() - B.getX());
        double cross3 = (A.getX() - C.getX()) * (P.getY() - C.getY()) - (A.getY() - C.getY()) * (P.getX() - C.getX());
        return (cross1 >= 0 && cross2 >= 0 && cross3 >= 0) || (cross1 <= 0 && cross2 <= 0 && cross3 <= 0);
    }

    private double area() {
        double area = 0;
        int n = polygon.size();
        for (int i = 0, j = n - 1; i < n; j = i++) {
            Point v0 = polygon.get(j);
            Point v1 = polygon.get(i);
            area += (v0.getX() - v1.getX()) * (v1.getY() + v0.getY());
        }
        return area / 2;
    }

    private double area(Point a, Point b, Point c) {
        return (b.getX() - a.getX()) * (c.getY() - a.getY()) - (b.getY() - a.getY()) * (c.getX() - a.getX());
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
