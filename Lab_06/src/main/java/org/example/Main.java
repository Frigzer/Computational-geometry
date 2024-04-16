package org.example;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

import java.util.List;

public class Main {
    private static Graph graph;

    public static void visualizeXTree(RangeTree2D tree) {
        System.setProperty("org.graphstream.ui", "swing"); // Ustawienie UI package na "swing"
        graph = new SingleGraph("Range Tree 2D");
        graph.setAttribute("ui.stylesheet", "url('style.css')");

        graph.setAttribute("ui.title", "Range Tree 2D");

        visualizeXNode(tree.root, null, 0, false, 0, 0);

        graph.display(false);
    }

    private static final int WIDTH_PER_LEVEL = 450; // szerokość przestrzeni dla każdego poziomu
    private static final int HEIGHT_PER_LEVEL = 200; // wysokość przestrzeni dla każdego poziomu

    private static void visualizeXNode(RangeTree2D.Node node, String parentId, int depth, boolean isLeft, double x, double y) {
        if (node != null) {
            String nodeId = node.x + "_" + depth + (isLeft ? "_L" : "_R");
            org.graphstream.graph.Node graphNode = graph.addNode(nodeId);
            graphNode.setAttribute("ui.label", Integer.toString(node.x) + ", " + Integer.toString(node.y));
            graphNode.setAttribute("xyz", x, y, 0);
            graphNode.setAttribute("yTree", node.yTree);

            if (parentId != null) {
                String edgeId = nodeId + "-" + parentId;
                if(graph.getEdge(edgeId) == null) {
                    graph.addEdge(edgeId, nodeId, parentId);
                }
            }

            // Dodajemy klasę CSS dla interaktywności
            graphNode.setAttribute("ui.class", "yTree");


            /*
            // Jeśli węzeł jest liściem (nie ma dzieci), zmień jego kształt na kwadrat i kolor na zielony
            if (node.left == null && node.right == null) {
                graphNode.setAttribute("ui.class", "leaf");
            }

             */


            // Dynamically adjust the offset based on the depth of the node
            double childrenOffsetX = WIDTH_PER_LEVEL / (Math.pow(2, depth) + 1);
            double childrenOffsetY = -HEIGHT_PER_LEVEL;

            if(node.left == null || node.right == null)
                graphNode.setAttribute("shape: box");

            if (node.left != null) {
                visualizeXNode(node.left, nodeId, depth + 1, true, x - childrenOffsetX, y + childrenOffsetY);
            }
            if (node.right != null) {
                visualizeXNode(node.right, nodeId, depth + 1, false, x + childrenOffsetX, y + childrenOffsetY);
            }

            // Automatyczne wyświetlanie drzewa Y dla tego węzła
            YTree yTree = node.yTree;
            if (yTree != null && yTree.root != null) {

                Graph yGraph = new SingleGraph("Y-Tree for X: " + node.x);
                yGraph.setAttribute("ui.title", "Y-Tree for X: " + nodeId);



                visualizeYTree(yGraph, yTree);  // Wywołanie metody do wizualizacji drzewa Y

                Viewer yViewer = yGraph.display(false);
                yViewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);  // Zamknięcie tego okna tylko je ukrywa

            }
        }
    }

    // Metoda do wizualizacji drzewa Y
    public static void visualizeYTree(Graph yGraph, YTree yTree) {
        //System.setProperty("org.graphstream.ui", "swing"); // Ustawienie UI package na "swing"
        yGraph.setAttribute("ui.stylesheet", "url('style.css')");

        visualizeYNode(yTree.root, null, 0, false, 0, 0, yGraph);
    }

    private static void visualizeYNode(YTree.Node node, String parentId, int depth, boolean isLeft, double x, double y, Graph graph) {
        if (node != null) {
            String nodeId = node.y + "_" + depth + (isLeft ? "_L" : "_R");
            org.graphstream.graph.Node graphNode = graph.addNode(nodeId);
            graphNode.setAttribute("ui.label",   Integer.toString(node.x) + ", " + Integer.toString(node.y));
            graphNode.setAttribute("xyz", x, y, 0);

            if (parentId != null) {
                String edgeId = nodeId + "-" + parentId;
                if(graph.getEdge(edgeId) == null) {
                    graph.addEdge(edgeId, nodeId, parentId);
                }
            }


            /*
            // Jeśli węzeł jest liściem (nie ma dzieci), zmień jego kształt na kwadrat i kolor na zielony
            if (node.left == null && node.right == null) {
                graphNode.setAttribute("ui.class", "leaf");
            }

             */



            // Dynamically adjust the offset based on the depth of the node
            double childrenOffsetX = WIDTH_PER_LEVEL / (Math.pow(2, depth) + 1);
            double childrenOffsetY = -HEIGHT_PER_LEVEL;

            if(node.left == null || node.right == null)
                graphNode.setAttribute("shape: box");

            if (node.left != null) {
                visualizeYNode(node.left, nodeId, depth + 1, true, x - childrenOffsetX, y + childrenOffsetY, graph);
            }
            if (node.right != null) {
                visualizeYNode(node.right, nodeId, depth + 1, false, x + childrenOffsetX, y + childrenOffsetY, graph);
            }
        }
    }


    private static int getDepth(RangeTree2D.Node node) {
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
        //int[][] points = {{4, 3}, {6, 1}, {7, 5}, {-1, 9}, {94, 34}, {35, 12}, {0, 4}};
        int[][] points = {{3, 8}, {4, 2}, {1, 5}, {6, 7}, {5, 9}, {8, 1}, {9, 4}, {7, 3}, {10, 12}, {0, 20}, {4, 80}, {30, 6}, {99, 0}};
        //int[][] points = {{4, 3}, {6, 1}, {7, 5}, {-1, 9}};
        RangeTree2D tree = new RangeTree2D();
        tree.build(points);
        List<RangeTree2D.Node> result = tree.queryRange(3, 30, 1, 10);
        System.out.println("Points within the range [{3, 1}, {30, 10}]: " + result);
        visualizeXTree(tree);

        // złożoność czasowa w najgorszym przypadku to O(n), a w przypadku
        // optymistycznym O(log n), gdzie n to liczba węzłów w drzewie. Złożoność przestrzenna wynosi
        // O(log n) dla zrównoważonego drzewa binarnego.
    }
}
