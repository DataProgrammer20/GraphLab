/*
Author: William Kingsley, Allen Simpson
	Date: 2/18/2018
	Overview: Floyd Warshall algorithm.
 */
import java.lang.*;
public class Warshall {
    private final static int INF = Integer.MAX_VALUE, graphSize = 4;
    private void printGraph(int graph[][]) {
        for (int i = 0; i < graphSize; ++i) {
            for (int j = 0; j  < graphSize; ++j) {
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
        int i, j, k;
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
            for (i = 0; i < graphSize; i++) {
                for (j = 0; j < graphSize; j++) {
                    if ((dist[k][j] != INF) && (dist[i][k] != INF) && (dist[i][k] + dist[k][j] < dist[i][j])) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }
        //printGraph(dist);
    }
    public static void main(String[] args) {
        int graph[][] = {{0, 5, INF, 10},
                        {INF, 0, 3, INF},
                        {INF, INF, 0, 1},
                        {INF, INF, INF, 0}
                        };
        Warshall warshall = new Warshall();
        warshall.floydWarshall(graph);
    }
}