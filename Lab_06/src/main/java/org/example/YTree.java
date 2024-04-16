package org.example;

import java.util.*;

public class YTree {
    public static class Node {
        int x, y;
        Node left, right;

        Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public Node root;

    public void build(int[][] points) {
        Arrays.sort(points, Comparator.comparingInt(p -> p[1]));
        root = buildTree(points, 0, points.length - 1);
    }

    private Node buildTree(int[][] points, int start, int end) {
        if (start > end) {
            return null;
        }
        int mid = (start + end) / 2;
        Node node = new Node(points[mid][0], points[mid][1]);
        node.left = buildTree(points, start, mid - 1);
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
        /*
        for(int r : result)
            if(r == node.y)
                return;

         */
        if (node.y >= start && node.y <= end) {
            result.add(node.y);
        }
        if (node.y > start) {
            queryRange(node.left, start, end, result);
        }
        if (node.y < end) {
            queryRange(node.right, start, end, result);
        }
    }
}