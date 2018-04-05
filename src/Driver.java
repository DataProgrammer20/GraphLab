import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class Driver {
    public static void main (String args[]) {
        String pathName = args.length>0 ? args[0] :  "./input.in";
        AdjacencyMatrix am = new AdjacencyMatrix(readFile(pathName));
        testPrim(am);
        testPrimJarnik(am);
        testKruskal(am);
        testWarshall(am);
    }
    private static void testPrimJarnik(AdjacencyMatrix am) {
        System.out.println("Running PrimJarnik:");
        System.out.println("");
        PrimJarnik primJ = new PrimJarnik();
        PrimJarnik.Edge[] edges=primJ.primJarnik(am,am.vertices[0]);
        System.out.println(edges[0].arrayToString(edges));
        System.out.println("");
        
    }
    private static void testWarshall(AdjacencyMatrix am) {
        System.out.println("Running Warshall:");
        System.out.println("");
        Warshall warshall = new Warshall();
        warshall.floydWarshall(am.getGraph(),am);
        System.out.println("");
    }
    private static void testPrim(AdjacencyMatrix am) {
        System.out.println("Running Prim:");
        System.out.println("");
        Prim prim = new Prim();
        prim.PrimJarnik(am.getGraph());
        System.out.println("");

    }
    private static void testKruskal(AdjacencyMatrix am) {
        System.out.println("Running Kruskal:");
        System.out.println("");
        Kruskal kurskal = new Kruskal();
        kurskal.JosephKruskal(am.getGraph());
        System.out.println("");
    }
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