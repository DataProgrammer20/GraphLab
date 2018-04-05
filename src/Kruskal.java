/*
    Author: William Kingsley, Allen Simpson
	Date: 2/18/2018
	Overview:
 */
import java.util.Comparator;
import java.util.PriorityQueue;
//Example of Kruskal's Algorithm
public class Kruskal {
    private final static int INF = Integer.MAX_VALUE, graphSize = 4;
    private void printSpanningTree(Edge spanTree[]) {
        System.out.println("Minimum Spanning Tree:");
        for (int i = 0; i < spanTree.length - 1; i++) {
            System.out.println(spanTree[i].xLocation + "-" + spanTree[i].yLocation);
        }
    }
    private void JosephKruskal(int graph[][]) {
        /* for each vertex v in G do
                Define an elementary cluster C(v) = {v}
         */
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(new MyComparator());
        for (int c = 0; c < graphSize; c++){
            for (int d = 0; d <graphSize; d++) {
                priorityQueue.add(new Edge(graph[c][d], c, d));
            }
        }
        Edge MST[] = new Edge[graphSize];
        int count = 0;
        while (count < graph.length - 1) {
            Edge edge = priorityQueue.poll();
            if (edge.xLocation != edge.yLocation) {
                MST[count] = edge;
                count += 1;
            }
        }
        printSpanningTree(MST);
    }
    public static void main(String[] args) {
        int graph[][] = {{0, 5, INF, 10},
                        {INF, 0, 3, INF},
                        {INF, INF, 0, 1},
                        {INF, INF, INF, 0}
                        };
        Kruskal kruskal = new Kruskal();
        kruskal.JosephKruskal(graph);
    }
}

class MyComparator implements Comparator<Edge> {
    public int compare(Edge x, Edge y) {
        return x.key - y.key;
    }
}