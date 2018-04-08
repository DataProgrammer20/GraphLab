/*
    Author: William Kingsley
	Date: 2/18/2018
	Overview: This file contains the Warshall algorithm
	for finding the finding the shortest distance between
	every vertex in a graph.
 */
import java.lang.*;
public class Warshall {
    //Method for printing the graph
    private void printGraph(int graph[][], AdjacencyMatrix am) {
        System.out.print("  ");
        for (String v : am.vertices) {
            System.out.print(" " + v + "  ");
        }
        System.out.print("\n");
        for (int i = 0; i < graph.length; i++) {
            System.out.print(am.getVertex(i) + " ");
            for (int j = 0; j < graph.length; j++) {
                if (graph[i][j] == AdjacencyMatrix.INF) {
                    System.out.print("INF ");
                } else {
                    System.out.print(String.format("%3d", graph[i][j]) + " ");
                }
            }
            System.out.println();
        }
    }

    //Sum distance and account for wrap-around signed integers (INF).
    private static int addDistance(int d1, int d2) {
        int s = d1 + d2;
        if (s < d1 || s < d2) {
            return AdjacencyMatrix.INF;
        }
        return s;
    }

    public void floydWarshall(int graph[][], AdjacencyMatrix am) {
        int dist[][] = new int[graph.length][graph.length];
        int i, j, k, x, y;
        //Copying content contained within graph[][] to dist[][]
        for (i = 0; i < graph.length; i++) {
            for (j = 0; j < graph.length; j++) {
                dist[i][j] = graph[i][j];
            }
        }
        //Populating dist[][] with shortest paths
        for (k = 0; k < graph.length; k++) {
            printGraph(dist, am);
            System.out.println();
            for (x = 0; x < graph.length; x++) {
                for (y = 0; y < graph.length; y++) {
                    int sum = addDistance(dist[x][k], dist[k][y]);
                    if (sum < dist[x][y]) {
                        dist[x][y] = sum;
                    }
                }
            }
        }
    }
}