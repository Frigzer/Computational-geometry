package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RangeTree1D {
    public static class Node {
        int value;
        Node left, right;

        Node(int value) {
            this.value = value;
        }
    }

    public Node root;

    public void build(int[] points) {
        List<Integer> sortedPoints = new ArrayList<>();
        for (int point : points) {
            sortedPoints.add(point);
        }
        Collections.sort(sortedPoints);
        root = buildTree(sortedPoints, 0, sortedPoints.size() - 1);
    }

    private Node buildTree(List<Integer> points, int start, int end) {

        if (start == end) {
            Node leafNode = new Node(points.get(start));
            leafNode.left = null; // Albo po prostu zostawiamy, ponieważ domyślnie są null w Javie.
            leafNode.right = null;
            return leafNode;
        }

        int mid = (start + end) / 2;
        Node node = new Node(points.get(mid));
        node.left = buildTree(points, start, mid);
        node.right = buildTree(points, mid + 1, end);
        return node;
    }

    public List<Integer> queryRange(int start, int end) {
        List<Integer> result = new ArrayList<>();
        queryRange(root, start, end, result);
        return result;
    }

    private void queryRange(Node node, int start, int end, List<Integer> result) {
        if (node == null) {
            return;
        }
        for(int r : result)
            if(r == node.value)
                return;
        if (node.value >= start && node.value <= end) {
            result.add(node.value);
        }
        if (node.value > start) {
            queryRange(node.left, start, end, result);
        }
        if (node.value < end) {
            queryRange(node.right, start, end, result);
        }
    }
}