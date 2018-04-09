/*
    Author: William Kingsley, Allen Simpson
	Date: 4/7/2018
	Overview: The driver class is used for the purpose
	 of executing the Prim, Warshall, and Kruskal algorithms.
 */
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class Driver {
    public static void main (String args[]) {
        String pathName = args.length > 0 ? args[0] : "input.in";
        AdjacencyMatrix am = new AdjacencyMatrix(readFile(pathName));
        testPrimJarnik(am); //Works
        testKruskal(am);   //Works
        testWarshall(am);   //Works

    }
    //Method for testing and execution of the Prim algorithm
    private static void testPrimJarnik(AdjacencyMatrix am) {
        System.out.println("Running PrimJarnik:");
        System.out.println("");
        PrimJarnik primJ = new PrimJarnik();
        Edge[] edges=primJ.primJarnik(am,am.vertices[0]);
        System.out.println(Edge.arrayToString(edges));
        System.out.println("");
        
    }
    //Method for testing/execution of the Warshall algorithm
    private static void testWarshall(AdjacencyMatrix am) {
        System.out.println("Running Warshall:");
        System.out.println("");
        Warshall warshall = new Warshall();
        warshall.floydWarshall(am.getGraph(),am);
        System.out.println("");
    }
    //Method for testing/execution of the Kruskal algorithm
    private static void testKruskal(AdjacencyMatrix am) {
        System.out.println("Running Kruskal:");
        System.out.println("");
        Kruskal kruskal = new Kruskal();
        Edge[] edges=kruskal.kruskal(am);
        System.out.println(Edge.arrayToString(edges));
        System.out.println("");
    }
    //Method readFile is used for reading an input file containing a adjacency matrix
    private static String readFile (String pathName) {
        try {
            List<String> lines = java.nio.file.Files.readAllLines(Paths.get(pathName));
            StringBuilder sb = new StringBuilder();
            for (String l : lines) {
                sb.append(l+"\n");
            }
            return sb.toString();
        } catch (IOException e) {
            System.out.println(e.toString());
            return "";
        }
    }
}