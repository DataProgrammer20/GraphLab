/*
Author: William Kingsley, Allen Simpson
	Date: 2/18/2018
	Overview: Floyd Warshall algorithm.
 */
import java.lang.*;
public class Warshall {
    private final static int INF = Integer.MAX_VALUE, graphSize = 4;
    private void printGraph(int graph[][]) {
        for (int i = 0; i < graphSize; i++) {
            for (int j = 0; j  < graphSize; j++) {
                if (graph[i][j] == INF) {
                    System.out.print("INF ");
                }
                else {
                    System.out.print(graph[i][j] + "   ");
                }
            }
            System.out.println();
        }
    }
    private void floydWarshall(int graph[][]) {
        int dist[][] = new int[graphSize][graphSize];
        int i, j, k, x, y;
        //Copying content contained within graph[][] to dist[][]
        for (i = 0; i < graphSize; i++) {
            for (j = 0; j < graphSize; j++) {
                dist[i][j] = graph[i][j];
            }
        }
        //Populating dist[][] with shortest paths
        for (k = 0; k < graphSize; k++) {
            printGraph(dist);
            System.out.println();
            for (x = 0; x < graphSize; x++) {
                for (y = 0; y < graphSize; y++) {
                    if (dist[x][k] + dist[k][y] < dist[x][y]) {
                        dist[x][y] = dist[x][k] + dist[k][y];
                    }
                }
            }
        }
        //printGraph(dist);
    }
    public static void main(String[] args) {
        int graph[][] = { {0, 5, INF, 10},
                          {INF, 0, 3, INF},
                          {INF, INF, 0, 1},
                          {INF, INF, INF, 0}
                        };
        Warshall warshall = new Warshall();
        warshall.floydWarshall(graph);
    }
}