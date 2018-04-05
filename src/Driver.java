import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import sun.tools.jar.resources.jar;

public class Driver {
    public static void main (String args[]) {
        String pathName = args.length>0 ? args[0] :  "./input.in";
        AdjacencyMatrix am = new AdjacencyMatrix(readFile(pathName));
        testWarshall(am);
    }
    private static void testWarshall(AdjacencyMatrix am) {
        Warshall warshall = new Warshall();
        warshall.floydWarshall(am.getGraph(),am);
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