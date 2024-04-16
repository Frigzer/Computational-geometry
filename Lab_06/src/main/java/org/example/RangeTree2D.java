package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class RangeTree2D {
    public static class Node {
        int x, y;
        Node left, right;
        YTree yTree;

        Node(int x, int y) {
            this.x = x;
            this.y = y;
            this.yTree = new YTree();
        }

        @Override
        public String toString() {
            return "{" + x + ", " + y + "}";
        }
    }

    public Node root;

    public void build(int[][] points) {
        Arrays.sort(points, Comparator.comparingInt(p -> p[0]));
        root = buildTree(points, 0, points.length - 1);
    }

    private Node buildTree(int [][] points, int start, int end) {
        if (start > end) {
            return null;
        }
        int mid = (start + end) / 2;
        Node node = new Node(points[mid][0], points[mid][1]);

        node.yTree.build(Arrays.copyOfRange(points, start, end + 1));

        node.left = buildTree(points, start, mid - 1);
        node.right = buildTree(points, mid + 1, end);
        return node;
    }

    // Metoda do zapytania 2D
    public List<Node> queryRange(int xStart, int xEnd, int yStart, int yEnd) {
        List<Node> result = new ArrayList<>();
        queryRange(root, xStart, xEnd, yStart, yEnd, result);
        return result;
    }

    private void queryRange(Node node, int xStart, int xEnd, int yStart, int yEnd, List<Node> result) {
        if (node == null) {
            return;
        }
        if (node.x >= xStart && node.x <= xEnd) {
            if (node.y >= yStart && node.y <= yEnd) {
                result.add(node);
            }
        }

        if (xStart < node.x) {
            queryRange(node.left, xStart, xEnd, yStart, yEnd, result);
        }
        if (xEnd > node.x) {
            queryRange(node.right, xStart, xEnd, yStart, yEnd, result);
        }
    }
}
