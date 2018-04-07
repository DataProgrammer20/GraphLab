/*
Author: Allen Simpson
	Date: 4/4/2018
	Overview: Adjacency Matrix Class.
 */

import java.util.ArrayList;
import java.util.HashMap;

//Adjacency Matrix Class
//This class should build an adjacency matrix from the 
//inputed data and store the matrix as a multidimensional array of values
class WEdge implements Comparable {
    int weight;
    String startVertex;
    String endVertex;
    public WEdge(int w, String s, String e) {
        weight = w;
        startVertex = s;
        endVertex = e;
    }
    public boolean equals(Object o) {
        if (o==null) return false;
        if (o instanceof WEdge) {
            WEdge e = (WEdge) o;
            return this.weight==e.weight&&this.startVertex==e.startVertex&&this.endVertex==e.endVertex;
        } else return false;
    } 
    public int compareTo(Object o) {
        if (o instanceof WEdge) {
            WEdge e = (WEdge) o;
            if (e.weight==this.weight) {
                if (e.startVertex==this.startVertex) {
                    return this.endVertex.compareTo(e.endVertex);
                } else return this.startVertex.compareTo(e.startVertex);
            } else return this.weight-e.weight;
        } else return -1;
    }
    public int hashCode() {
        return this.weight^this.startVertex.hashCode()^this.endVertex.hashCode();
    }
    public String toString() {
        return String.format("(%d, %s, %s)", this.weight, this.startVertex, this.endVertex);
    }
    public static String arrayToString (WEdge[] edges){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (WEdge i : edges){
            sb.append(i);
            sb.append("; ");
        }
        sb.append("]");
        return sb.toString();
    }
}

public class AdjacencyMatrix {
    String[] vertices;
    WEdge[] edges;
    public static int INF = Integer.MAX_VALUE;
    public AdjacencyMatrix(String[] vertices, WEdge[] edges){
        this.vertices=vertices;
        this.edges=edges;
    }
    public AdjacencyMatrix(String txt){
        vertices=null;
        edges=null;
        buildAdjecencyMatrix(txt);
    }
    public int graphSize() {
        return this.vertices.length;
    }
    public int[][] getGraph() {
        HashMap rows = new HashMap();
        for (WEdge edge : edges) {
            HashMap columns = (HashMap) rows.get(edge.startVertex);
            if (columns==null) {
                columns = new HashMap();   
                rows.put(edge.startVertex, columns);
            }
            columns.put(edge.endVertex, edge.weight);
        }
        int[][] graph = new int[graphSize()][];
        for (int i = 0; i<graph.length;i++) {
            int[] row = new int[graphSize()];
            graph[i]=row;
            String startVertex = getVertex(i);
            HashMap rowWeights = (HashMap) rows.get(startVertex);
            for(int j=0; j<row.length;j++) {
                String endVertex = getVertex(j);
                if (rowWeights == null) {
                    row[j]=INF;
                } else {
                    Object w = rowWeights.get(endVertex);
                    if (w==null){
                        row[j]=INF;
                    } else {
                        row[j]=((Integer)w).intValue();
                    }
                }
            }
        }
        return graph;
    }
    public String getVertex(int index) {
        if (0<=index&&index<vertices.length) {
            return vertices[index];
        } else {
            return "UNKNOWN";
        }
    }
    private static String[] fixupLine(String line) {
        ArrayList al = new ArrayList();
        for(String s : line.split(",")) { 
            al.add(s); 
        }
        int i = 0;
        while (i < al.size()) {
            String c = (String)al.get(i);
            c = c.trim();
            if (c.isEmpty()) {
                al.remove(i);
            } else {
                al.set(i,c);
                i++;
            }
        }
        return (String[])al.toArray(new String[al.size()]); 
    }
    private static String[][] fixupLines (String txt) {
        ArrayList al = new ArrayList();
        for(String s : txt.split("\n")) { 
            al.add(s);
        }
        int i = 0;
        while (i < al.size()) {
            String line = (String)al.get(i);
            line = line.trim();
            if (line.startsWith("#")||line.startsWith("//")) {
                al.remove(i);
            } else if (line.isEmpty()) {
                al.remove(i);
            } else {
                al.set(i,fixupLine(line));
                i++;
            }
        }
        return (String[][])al.toArray(new String[al.size()][]); 
    }
    private static void mapRowData(String[] vertices, int rowIdx,String[] rowData,ArrayList acc) {
        String rowVertex = vertices[rowIdx];
        for(int i = 0; i < rowData.length; i++) {
            String colVertex = vertices[i];
            if (!rowData[i].equals("-")) {
                int w = Integer.parseInt(rowData[i]);
                WEdge edge = new WEdge(w, rowVertex, colVertex);
                acc.add(edge);
            }
        }
    }
    private void buildAdjecencyMatrix(String txt) {
        String[][] a = fixupLines(txt);
        String[] vertices = a[0];
        ArrayList edgesAl = new ArrayList();
        for(int i = 1; i < a.length; i++) {
            mapRowData(vertices, i-1, a[i], edgesAl);
        }
        WEdge[] edges = (WEdge[])edgesAl.toArray(new WEdge[edgesAl.size()]);
        this.vertices=vertices;
        this.edges=edges;
    }
}