package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Polygon {
    private List<Point> vertices;

    public Polygon(String filename) {
        this.vertices = new ArrayList<>();
        loadPointsFromFile(filename);
    }

    public List<Point> getVertices() {
        return vertices;
    }

    public void removeVertex(Point vertex) {
        vertices.remove(vertex);
    }

    public int size() {
        return vertices.size();
    }

    public Point get(int index) {
        return vertices.get(index);
    }

    private void loadPointsFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length == 2) {
                    double x = Double.parseDouble(parts[0]);
                    double y = Double.parseDouble(parts[1]);
                    vertices.add(new Point(x, y));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}