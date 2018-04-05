/*
    Author: William Kingsley, Allen Simpson
	Date: 2/18/2018
	Overview:
 */
//Example implementation of Prim's Algorithm without Queue (Malfunctioning)
public class Prim {
    private static final int INF = Integer.MAX_VALUE, graphSize = 5;
    private void printGraph(int parent[], /*int n,*/ int graph[][]) {
        System.out.println("Edge    Weight");
        for (int i = 1; i < graphSize; i++) {
            System.out.println(parent[i] + " - " + i + "    " + graph[i][parent[i]]);
        }
    }
    private int minKey(int key[], Boolean mstSet[]) {
        int min = INF, min_index = -1;
        for (int c = 0; c < graphSize; c++) {
            if (mstSet[c] == false && key[c] < min) {
                min = key[c];
                min_index = c;
            }
        }
        return min_index;
    }
    public void PrimJarnik(int graph[][]) {
        //Use to store constructed MST
        int parent[] = new int[graphSize];
        //Key values used to pick minimum weight edge
        int key[] = new int[graphSize];
        //Represent set of vertices not yet include in MST
        Boolean mstSet[] = new Boolean[graphSize];
        //Initializing all keys as INF
        for (int i = 0; i < graphSize; i++) {
            key[i] = INF;
            mstSet[i] = false;
        }
        key[0] = 0;
        parent[0] = -1;
        for (int count = 0; count < graphSize - 1; count++) {
            int u = minKey(key, mstSet);
            mstSet[u] = true;
            //MST will have graphSize number of vertices
            for (int v = 0; v < graphSize - 1; v++) {
                    if (graph[u][v] != 0 && mstSet[v] == false && graph[u][v] < key[v]) {
                        parent[v] = u;
                        key[v] = graph[u][v];
                    }
            }
        }
        printGraph(parent, /*graphSize,*/ graph);
    }
    public static void main(String[] args) {
        Prim prim = new Prim();
        int graph[][] = new int[][] {{0, 2, 0, 6, 0},
                                     {2, 0, 3, 8, 5},
                                     {0, 3, 0, 0, 7},
                                     {6, 8, 0, 0, 9},
                                     {0, 5, 7, 9, 0},
                                    };
        prim.PrimJarnik(graph);
    }
}