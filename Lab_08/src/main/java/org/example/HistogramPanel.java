package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class HistogramPanel extends JPanel {
    private Map<Double, Integer> histogram;
    private double binSize;

    public HistogramPanel(Map<Double, Integer> histogram, double binSize) {
        this.setPreferredSize(new Dimension(800, 800));
        this.histogram = histogram;
        this.binSize = binSize;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (histogram == null || histogram.isEmpty()) return;

        int width = getWidth() - 40;  // Lepsze marginesy
        int height = getHeight() - 40;  // Lepsze marginesy
        int maxCount = histogram.values().stream().max(Integer::compare).orElse(0);

        int x = 20; // Start trochę dalej od krawędzi
        int barWidth = Math.max(10, width / histogram.size());
        for (Map.Entry<Double, Integer> entry : histogram.entrySet()) {
            int barHeight = (int) ((double) entry.getValue() / maxCount * (height - 20)); // Pozostaw miejsce na etykiety
            int y = height + 20 - barHeight; // Dostosuj pozycję y
            g.fillRect(x, y, barWidth - 5, barHeight); // Dostosuj szerokość słupka
            g.setColor(Color.BLACK);
            g.drawRect(x, y, barWidth - 5, barHeight); // Kontur dla lepszej widoczności
            // Etykieta dla przedziału
            g.drawString(String.format("%.2f", entry.getKey()), x, height + 30);
            // Etykieta dla liczby trójkątów
            g.drawString(String.valueOf(entry.getValue()), x, y - 5);
            x += barWidth;
        }
    }

    public void setHistogram(Map<Double, Integer> histogram) {
        this.histogram = histogram;
        repaint();
    }
}