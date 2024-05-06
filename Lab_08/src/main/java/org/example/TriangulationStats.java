package org.example;

import java.util.*;

class TriangulationStats {
    List<Double> areas = new ArrayList<>();
    long triangulationTimeMillis;

    public double median() {
        Collections.sort(areas);
        int middle = areas.size() / 2;
        if (areas.size() % 2 == 0) {
            return (areas.get(middle - 1) + areas.get(middle)) / 2.0;
        } else {
            return areas.get(middle);
        }
    }

    public double mode() {
        Map<Double, Integer> frequencyMap = new HashMap<>();
        for (double area : areas) {
            frequencyMap.put(area, frequencyMap.getOrDefault(area, 0) + 1);
        }
        int maxFreq = Collections.max(frequencyMap.values());
        for (Map.Entry<Double, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() == maxFreq) {
                return entry.getKey();
            }
        }
        return 0; // Nie powinno się zdarzyć
    }

    public void addArea(double area) {
        areas.add(area);
    }

    public void setTriangulationTime(long time) {
        triangulationTimeMillis = time;
    }
}
