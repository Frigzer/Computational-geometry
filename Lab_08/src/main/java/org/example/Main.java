package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.example.SuperTriangle.createSuperTriangle;

public class Main {
    static List<Point> points = new ArrayList<>();
    public static void main(String[] args) {

        Frame frame = new Frame();

        Panel panel = new Panel();

        frame.add(panel);

        frame.pack();

        frame.setVisible(true);
        // Ear clipping
        loadPointsFromFile("points2.txt");
        for(Point p : points) {
            panel.drawPoint(p);
        }


        EarClippingTriangulation earClippingTriangulation = new EarClippingTriangulation("points2.txt");
        panel.drawEarClipping(earClippingTriangulation);
        List<Double> earClippingStats = earClippingTriangulation.getTriangleAreas();

        // Delaunay
        points.clear();
        loadPointsFromFile("points.txt");
        for(Point p : points) {
            panel.drawPoint(p);
        }

        DelaunayTriangulation delaunayTriangulation = new DelaunayTriangulation("points.txt");
        panel.drawDelunay(delaunayTriangulation);
        List<Double> delaunayStats = delaunayTriangulation.getTriangleAreas();


        Frame frame2 = new Frame();
        HistogramPanel histogramPanel = new HistogramPanel(null, 0.5);
        histogramPanel.setHistogram(calculateHistogram(delaunayStats, 0.5));

        frame2.setTitle("Histogram Delaunay");
        frame2.add(histogramPanel);
        frame2.pack();
        frame2.setVisible(true);

        Frame frame3 = new Frame();
        HistogramPanel histogramPanel2 = new HistogramPanel(null, 0.5);
        histogramPanel2.setHistogram(calculateHistogram(earClippingStats, 0.5));

        frame3.setTitle("Histogram EarClipping");
        frame3.add(histogramPanel2);
        frame3.pack();
        frame3.setVisible(true);


    }
    static void loadPointsFromFile(String filename) {
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

    public static Map<Double, Integer> calculateHistogram(List<Double> areas, double binSize) {
        Map<Double, Integer> histogram = new TreeMap<>();
        for (double area : areas) {
            double bin = Math.floor(area / binSize) * binSize;
            histogram.put(bin, histogram.getOrDefault(bin, 0) + 1);
        }
        return histogram;
    }
}