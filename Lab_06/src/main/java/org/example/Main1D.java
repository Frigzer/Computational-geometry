package org.example;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.List;

public class Main1D {
    private static Graph graph;

    public static void visualize(RangeTree1D tree) {
        System.setProperty("org.graphstream.ui", "swing"); // Ustawienie UI package na "swing"
        graph = new SingleGraph("Range Tree 1D");
        graph.setAttribute("ui.stylesheet", "url('style.css')");

        visualizeNode(tree.root, null, 0, false, 0, 0);

        graph.display(false);
    }

    private static final int WIDTH_PER_LEVEL = 450; // szerokość przestrzeni dla każdego poziomu
    private static final int HEIGHT_PER_LEVEL = 200; // wysokość przestrzeni dla każdego poziomu

    private static void visualizeNode(RangeTree1D.Node node, String parentId, int depth, boolean isLeft, double x, double y) {
        if (node != null) {
            String nodeId = node.value + "_" + depth + (isLeft ? "_L" : "_R");
            org.graphstream.graph.Node graphNode = graph.addNode(nodeId);
            graphNode.setAttribute("ui.label", Integer.toString(node.value));
            graphNode.setAttribute("xyz", x, y, 0);

            if (parentId != null) {
                String edgeId = nodeId + "-" + parentId;
                if(graph.getEdge(edgeId) == null) {
                    graph.addEdge(edgeId, nodeId, parentId);
                }
            }

            // Jeśli węzeł jest liściem (nie ma dzieci), zmień jego kształt na kwadrat i kolor na zielony
            if (node.left == null && node.right == null) {
                graphNode.setAttribute("ui.class", "leaf");
            }

            // Dynamically adjust the offset based on the depth of the node
            double childrenOffsetX = WIDTH_PER_LEVEL / (Math.pow(2, depth) + 1);
            double childrenOffsetY = -HEIGHT_PER_LEVEL;

            if(node.left == null || node.right == null)
                graphNode.setAttribute("shape: box");

            if (node.left != null) {
                visualizeNode(node.left, nodeId, depth + 1, true, x - childrenOffsetX, y + childrenOffsetY);
            }
            if (node.right != null) {
                visualizeNode(node.right, nodeId, depth + 1, false, x + childrenOffsetX, y + childrenOffsetY);
            }
        }
    }



    private static int getDepth(RangeTree1D.Node node) {
        if (node == null) {
            return 0;
        } else {
            int leftDepth = getDepth(node.left);
            int rightDepth = getDepth(node.right);
            return Math.max(leftDepth, rightDepth) + 1;
        }
    }


    public static void main(String[] args) {
        //int[] points = {43, 12, 4, -4, 0, 1, 96, 400, 13, 312, 39, 500};
        int[] points = {49, 37, 3, 10 ,19, 23, 30, 97, 93, 59, 62, 70, 80, 89};
        RangeTree1D tree = new RangeTree1D();
        tree.build(points);
        List<Integer> result = tree.queryRange(25, 90);
        System.out.println("Points within the range [25, 90]: " + result);
        visualize(tree);

        // złożoność czasowa w najgorszym przypadku to O(n), a w przypadku
        // optymistycznym O(log n), gdzie n to liczba węzłów w drzewie. Złożoność przestrzenna wynosi
        // O(log n) dla zrównoważonego drzewa binarnego.
    }
}
